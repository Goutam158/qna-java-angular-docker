import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';

import { TopicModel } from './topic.model';
import { QuestionModel } from './question.model';

export class MockCoreService {

  getTopics():Observable<any>{
      let topics : TopicModel[] =  [];
        return of(topics);
      }
    
  getTopic(id:number):Observable<any>{
        return of(new TopicModel());
      }


  getQuestion(id:number):Observable<any>{
    return of(new QuestionModel());
  }

}