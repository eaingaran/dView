# dView
dView application to demonstrate full stack application development

This application has 3 components -
    dUI
    dREST
    dWorker
    
1) dUI
        This is an Angular based User Interface module that gets data in a JSON format from respective REST end points of dREST module. This component will not show live data if dREST is not running.

2) dREST
        This is a middle layer module that acts as backbone to the dUI component. It can run anywhere (cloud ready), and it can run on any platform that has java. This module essentially connects to database using JPA and sends response in JSON format as per the REST call. This module can add data to database as well.

3) dWorker
        This is the backend scheduled job runner component. This module fetched files from specified location and persists the data into respective tables which will be used by dREST when a REST call for that data is made by dUI. This can also run anywhere (cloud ready) and can run on any platform that contains java.


**For more information on individual components, check out README.md inside the module.


Technology Used:

dUI - Angular 5.x

dREST -  Springboot 1.4.1.RELEASE
                Java 8
                JPA
                mysql
                maven
                
dWorker - Springboot 1.4.1.RELEASE
                Java 8
                JPA
                Univocity Parser 2.1.1
                Apache Commons-io 2.5
                mysql
                maven

Recommonded tools:

 dUI - Atom
 dREST - Intellij Idea
 dWorker - Intellij Idea
