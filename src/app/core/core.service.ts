import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import * as jwt_decode from 'jwt-decode';

import { UserModel } from './user.model';
import { TopicModel } from './topic.model';
import { QuestionModel} from './question.model';
import { CommentModel } from './comment.model';


@Injectable()
export class CoreService {

  authEndpoint : string = 'http://localhost:8080/qna/api/v1';
  private TOKEN = 'qna-auth-jwt-token';
  
  constructor( private  _http: HttpClient) {}

  getTopics():Observable<any>{
    return this._http
    .get(`${this.authEndpoint}/topic`);
  }

  getTopic(id:number):Observable<any>{
    return this._http
    .get(`${this.authEndpoint}/topic/${id}`);
  }

  getQuestion(id:number):Observable<any>{
    return this._http
    .get(`${this.authEndpoint}/question/${id}`);
  }


  deleteTopic(id:number):Observable<any>{
    return this._http
    .delete(`${this.authEndpoint}/topic/${id}`);
  }

  deleteQuestion(id:number):Observable<any>{
    return this._http
    .delete(`${this.authEndpoint}/question/${id}`);
  }

  deleteComment(id:number):Observable<any>{
    return this._http
    .delete(`${this.authEndpoint}/comment/${id}`);
  }

  addTopic(topic:TopicModel):Observable<any>{
    console.log(topic);
    return this._http
    .post(`${this.authEndpoint}/topic`,topic,{responseType : 'text'});
  }

  addQuestion(question:QuestionModel):Observable<any>{
    console.log(question);
    return this._http
    .post(`${this.authEndpoint}/question`,question,{responseType : 'text'});
  }

  addComment(comment:CommentModel):Observable<any>{
    console.log(comment);
    return this._http
    .post(`${this.authEndpoint}/comment`,comment,{responseType : 'text'});
  }

}
