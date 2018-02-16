import { Component, OnInit } from '@angular/core';
import {Router} from "@angular/router";
import {FormGroup, FormBuilder, FormControl, Validators} from '@angular/forms';
import { Http,Response, RequestOptions, Headers } from '@angular/http';
import { Observable } from 'rxjs';

//import {Ob}

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
email: string;
password: string;

//value: Observable;

public eMail = new FormControl();
public pass = new FormControl();

public URL: string = "http://localhost:8080/dREST/getSources";

public headers = new Headers({'Content-type':'application/json',
    'Access-Control-Allow-Origin':'*'
  });
public requestOptions = new RequestOptions({headers: this.headers});

public loginForm: FormGroup;

  constructor(private router : Router,private fb:FormBuilder,public http: Http) {}
  
  //Observable<Response> ob = this.http.get(this.url);

  sendValuesToAPI(email:string,password:string)
  {
    //let jsonObj = [{'name':email,'path':password}];
    let jsonObj = [{"id":1,"name":"ABC","email":email}]
    let serializedObj = JSON.stringify(jsonObj);
    console.log(serializedObj);

    let value = this.http.get(this.URL).toPromise().then(this.extractData);

    console.log(value);
    // this.http.post(this.URL,serializedObj,this.requestOptions);
  }
  private extractData(res:Response){
    return res.json();
  }
  ngOnInit() {
  }  

onSubmit(eMail: string,password: string)
{
  alert(eMail+" "+password);
  //this.login(eMail,password);
  this.sendValuesToAPI(eMail,password);
}

login(email: string, password: string) {
// if(this.email == 'dhiraj@gmail.com' && this.password == 'password') {
// this.router.navigate(['user']);
// }else {
// alert("Invalid credentials.")
// }
}

}
