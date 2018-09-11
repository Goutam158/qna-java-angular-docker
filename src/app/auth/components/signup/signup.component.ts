import { Component } from '@angular/core';
import { AuthService } from '../../auth.service';
import { UserModel } from '../../../core/user.model';

@Component({
  selector: 'qna-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {


  errorMesage:string;
  message:string;
  userModel:UserModel = new UserModel();
  constructor(private _authService:AuthService) { }

  signup(){
    if(this.validateForm()){
       this._authService.signup(this.userModel)
        .subscribe(
          res=>{
            this.clear();
            console.log(res);
            this.message=res;
          },
          error=>{
            console.error(error);
            this.message=undefined;
            this.errorMesage = error;
          }
        );
    }
  }

  private validateForm():boolean{
    if(this.userModel.firstName == undefined || this.userModel.firstName == null || this.userModel.firstName ==''){
      this.errorMesage = 'First Name is blank';
      return false;
    }
    if(this.userModel.lastName == undefined || this.userModel.lastName == null || this.userModel.lastName ==''){
      this.errorMesage = 'Last Name is blank';
      return false;
    }
    if(this.userModel.email == undefined || this.userModel.email == null || this.userModel.email ==''){
      this.errorMesage = 'Email is blank';
      return false;
    }
    if(this.userModel.password == undefined || this.userModel.password == null || this.userModel.password ==''){
      this.errorMesage = 'Password is blank';
      return false;
    }
    if(this.userModel.retypePassword == undefined || this.userModel.retypePassword == null || this.userModel.retypePassword ==''){
      this.errorMesage = 'Retype password is blank';
      return false;
    }
    if( this.userModel.password != this.userModel.retypePassword){
      this.errorMesage = 'Password and Retype password is not matching';
      return false;
    }
  
    return true;
  }

  clear(){
    this.errorMesage = undefined;
    this.message = undefined;
    this.userModel = new UserModel();
  }


}