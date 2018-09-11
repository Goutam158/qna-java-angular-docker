import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../auth.service';

@Component({
  selector: 'qna-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  email:string;
  password:string;
  isLoggedIn:boolean=false;
  errorMessage:string;

  constructor(private _authService:AuthService,
    private _router:Router) { }

  ngOnInit(){
    this.isLoggedIn = this._authService.isLoggedIn();
  }
  
  login(){
    if(this.email==undefined){
      this.errorMessage='User Name cannot be blank';
      return;
    }
    if(this.password==undefined){
      this.errorMessage='Password cannot be blank';
      return;
    }
    this._authService
    .login(this.email,this.password)
    .subscribe(res=>{
      this._authService.setToken(res);
      this.isLoggedIn=true;
      this.clear();
      window.location.reload();
    },
    error=>{
      this.errorMessage='Invalid User Name or Password';
      console.error(error);
      this.isLoggedIn=false;
    });
  }

  logout(){
    this._authService.logout();
    this.isLoggedIn=false;
    if('/movie/home/popular' == this._router.url){
      window.location.reload();
    }else{
      this._router.navigateByUrl('/');
    }
  }

  private clear(){
    this.email=undefined;
    this.password=undefined;
    this.errorMessage=undefined;
  }

}
