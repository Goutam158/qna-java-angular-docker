import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { UserModel } from '../../../core/user.model';
import { Router } from '@angular/router';

@Component({
  selector: 'qna-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {


  errorMessage:string;
  message:string;
  userModel:UserModel = new UserModel();
  nameRegex:RegExp = new RegExp('^[a-zA-Z]+$');
  emailRegex:RegExp = new RegExp('^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+[.][a-zA-Z]{2,4}$');
  passwordRegex:RegExp = new RegExp('^(((?=.*[a-z])(?=.*[A-Z]))|((?=.*[a-z])(?=.*[0-9]))|((?=.*[A-Z])(?=.*[0-9])))(?=.{8,})');
  constructor(private _authService:AuthService,
              private _router:Router) { }

  signup(){
    if(this.validateForm()){
       this._authService.signup(this.userModel)
        .subscribe(
          res=>{
            this.clear();
            console.log(res);
            this.message=res;
            this._router.navigateByUrl('/login');
          },
          errorResp=>{
            console.error(errorResp.error);
            this.message=undefined;
            this.errorMessage = errorResp.error;
          }
        );
    }
  }

  private validateForm():boolean{
    if(this.userModel.firstName == undefined || this.userModel.firstName == null || this.userModel.firstName ==''){
      this.errorMessage = 'First Name is blank';
      return false;
    }
    if(!this.nameRegex.test(this.userModel.firstName)){
      this.errorMessage = 'First Name must contain only english alphabets';
      return false;  
    }
    
    if(this.userModel.lastName == undefined || this.userModel.lastName == null || this.userModel.lastName ==''){
      this.errorMessage = 'Last Name is blank';
      return false;
    }
    if(!this.nameRegex.test(this.userModel.lastName)){
      this.errorMessage = 'Last Name must contain only english alphabets';
      return false;  
    }
    if(this.userModel.email == undefined || this.userModel.email == null || this.userModel.email ==''){
      this.errorMessage = 'Email is blank';
      return false;
    }
    if(!this.emailRegex.test(this.userModel.email)){
      this.errorMessage = 'Malformed email id';
      return false;  
    }
    if(this.userModel.password == undefined || this.userModel.password == null || this.userModel.password ==''){
      this.errorMessage = 'Password is blank';
      return false;
    }
    if(!this.passwordRegex.test(this.userModel.password)){
      this.errorMessage = 'Password must contain Minimum eight characters, at least one letter, one number';
      return false;  
    }
    if(this.userModel.retypePassword == undefined || this.userModel.retypePassword == null || this.userModel.retypePassword ==''){
      this.errorMessage = 'Retype password is blank';
      return false;
    }
    if( this.userModel.password != this.userModel.retypePassword){
      this.errorMessage = 'Password and Retype password is not matching';
      return false;
    }
  
    return true;
  }

  clear(){
    this.errorMessage = undefined;
    this.message = undefined;
    this.userModel = new UserModel();
  }


}