import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import * as jwt_decode from 'jwt-decode';

import { UserModel } from '../core/user.model';



@Injectable()
export class AuthService {
authEndpoint : string = 'http://localhost:8080/qna/auth-api/v1';
private TOKEN = 'qna-auth-jwt-token';
    constructor( private  _http: HttpClient) {}

    login(email:string, password:string):Observable<any>{
        return this._http
        .post(`${this.authEndpoint}/login?email=${email}&password=${password}`,null,{responseType : 'text'})
    }

    signup(userModel:UserModel):Observable<any>{
        return this._http
        .post(`${this.authEndpoint}/signup`,userModel,{responseType : 'text'});
    }

    setToken(token:string){
        localStorage.setItem(this.TOKEN,token);
    }

    getToken():string{
        return localStorage.getItem(this.TOKEN);
    }

    logout(){
        localStorage.removeItem(this.TOKEN);
    }

    isLoggedIn():boolean{
        let token = localStorage.getItem(this.TOKEN)
        if( token == undefined || token == null ){
            return false;
        }
        return this.validateToken(token);
    }

    private validateToken(token:string):boolean{
        const date:Date = this.getTokenExpirationDate(token);
        if(date === undefined || date === null){
            return false;
        }
        return !(date.valueOf()> new Date().valueOf());
    }

    private getTokenExpirationDate(token: string):Date{
        const decode = jwt_decode(token);
        if(decode.iat === undefined){
            return null;
        }
        const date = new Date(0);
        date.setUTCSeconds(decode.iat);
        return date;
    }

}