# dREST
dREST provides REST endpoints for dUI to get data from database (mysql)


[[initial]]
== Create the database

Go to the terminal (command Prompt `cmd` in Microsoft Windows). Open MySQL client with a user that can create new users.

For example: On a Linux, use the command

[source, sh]
----
$ sudo mysql --password
----

NOTE: This connects to MySQL as a root, this is *not the recommended way* for a production server.

Create a new database

[source, mysql]
----
mysql> create database localdb; -- Create the new database
mysql> create user 'user'@'localhost' identified by 'password'; -- Creates the user
mysql> grant all on localdb.* to 'user'@'localhost'; -- Gives all the privileges to the new user on the newly created database
----

== Create the `application.properties` file

Spring Boot gives you defaults on all things, the default in database is `H2`, so when you want to change this and use any other database you must define the connection attributes in the `application.properties` file.

In the sources folder, you create a resource file `src/main/resources/application.properties`

[source, java]
----
include::src/main/resources/application.propertie
----

Here, `spring.jpa.hibernate.ddl-auto` can be `none`, `update`, `create`, `create-drop`, refer to the Hibernate documentation for details.

* `none` This is the default for `MySQL`, no change to the database structure.
* `update` Hibernate changes the database according to the given Entity structures.
* `create` Creates the database every time, but don't drop it when close.
* `create-drop` Creates the database then drops it when the `SessionFactory` closes.

We here begin with `create` because we don't have the database structure yet. After the first run, we could switch it to `update` or `none` according to program requirements. Use `update` when you want to make some change to the database structure.

The default for `H2` and other embedded databases is `create-drop`, but for others like `MySQL` is `none`

It is good security practice that after your database is in production state, you make this `none` and revoke all privileges from the MySQL user connected to the Spring application, then give him only SELECT, UPDATE, INSERT, DELETE.

This is coming in details in the end of this guide.

== Create the `@Entity` model

`src/main/java/xyz/fullstack/development/domain/User.java`
[source,java]
----
include::src/main/java/xyz/fullstack/development/domain/User.java
----

This is the entity class which Hibernate will automatically translate into a table.

== Create the repository

`src/main/java/xyz/fullstack/development/dao/UserRepository.java`
[source,java]
----
include::src/main/java/xyz/fullstack/development/dao/UserRepository.java
----

This is the repository interface, this will be automatically implemented by Spring in a bean with the same name with changing case
The bean name will be `userRepository`

== Create a new controller for your Spring application

`src/main/java/xyz/fullstack/development/controller/MainController.java`
[source,java]
----
include::src/main/java/xyz/fullstack/development/controller/MainController.java
----

NOTE: The above example does not explicitly specify `GET` vs. `PUT`, `POST`, and so forth, because `@GetMapping` is a shortcut for `@RequestMapping(method=GET)`. `@RequestMapping` maps all HTTP operations by default. Use `@RequestMapping(method=GET)` or https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html[other shortcut annotations] to narrow this mapping.


== Make the application executable

Although it is possible to package this service as a traditional link:/understanding/WAR[WAR] file for deployment to an external application server, the simpler approach demonstrated below creates a standalone application. You package everything in a single, executable JAR file, driven by a good old Java `main()` method. Along the way, you use Spring's support for embedding the link:/understanding/Tomcat[Tomcat] servlet container as the HTTP runtime, instead of deploying to an external instance.


`src/main/java/xyz/fullstack/development/Application.java`
[source,java]
----
include::src/main/java/xyz/fullstack/development/Application.java
----

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_an_executable_jar_subhead.adoc[]

include::https://raw.githubusercontent.com/spring-guides/getting-started-macros/master/build_an_executable_jar_with_both.adoc[]


Logging output is displayed. The service should be up and running within a few seconds.


== Test the application

Now that the application is running, you can test it.

Use `curl` for example.
Now you have 2 REST Web Services you can test

`localhost:8080/dREST/getSource` This gets all source data
`localhost:8080/dREST/addSource` This adds one user to the data
`localhost:8080/dREST/getSource` This gets all user data (if available in user table)

[source,sh]
----
$ curl 'localhost:8080/dREST/addSource?name=SourceA&path=some/path/to/file'
----

The reply should be

[source,sh]
----
Saved
----

[source,sh]
----
$ curl 'localhost:8080/dREST/getSources'
----

The reply should be

[source,json]
----
[{"id":1,"name":"SourceA","email":"some/path/to/file"}]
----

