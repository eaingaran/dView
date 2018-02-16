import { NgModule } from '@angular/core';
//import { CommonModule } from '@angular/common';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { UserComponent } from './user/user.component';

const routes: Routes = [
{ path: 'user', component: UserComponent },
{ path: 'login', component: LoginComponent },
{path : '', component : LoginComponent}
];

@NgModule({
  imports: [
    //CommonModule
    RouterModule.forRoot(routes)
  ],
  exports: [ RouterModule ]
  //declarations: []
})
export class AppRoutingModule { }
