import { Injectable } from "@angular/core";
import { HttpRequest,HttpHandler,HttpInterceptor,HttpEvent} from '@angular/common/http';

import { AuthService } from "../auth/auth.service";
import { Observable } from "rxjs/Observable";

@Injectable()
export class InterceptorService implements HttpInterceptor{
private TOKEN = 'qna-auth-jwt-token';
constructor(){}

intercept(request: HttpRequest<any>, next : HttpHandler): Observable<HttpEvent<any>>{
    request = request.clone({
        setHeaders: {
            Authorization: `Bearer ${localStorage.getItem(this.TOKEN)}`
        }
    });
    return next.handle(request);
}

}