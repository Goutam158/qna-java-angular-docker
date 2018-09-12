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
    if(this.userModel.lastName == undefined || this.userModel.lastName == null || this.userModel.lastName ==''){
      this.errorMessage = 'Last Name is blank';
      return false;
    }
    if(this.userModel.email == undefined || this.userModel.email == null || this.userModel.email ==''){
      this.errorMessage = 'Email is blank';
      return false;
    }
    if(this.userModel.password == undefined || this.userModel.password == null || this.userModel.password ==''){
      this.errorMessage = 'Password is blank';
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