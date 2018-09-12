import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../core.service';
import { TopicModel } from '../../topic.model';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'qna-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private _coreService : CoreService) { }
  topics : Observable<TopicModel[]>;
  topic: TopicModel = new TopicModel();
  errorMessage : string;
  
  ngOnInit() {
    this._getTopics();
  }

  private _getTopics(){
    this.topics = this._coreService.getTopics()  
  }

  addTopic(){
    if(this.topic.name==undefined){
      this.errorMessage='Topic name cannot be blank';
      return;
    }
    if(this.topic.description==undefined){
      this.errorMessage='Topic description cannot be blank';
      return;
    }
    this._coreService.addTopic(this.topic).subscribe(
      res=>{
        this._getTopics();
        this.topic = new TopicModel();
        this.errorMessage = undefined;
      },
      errorResponse=>{
        console.error(errorResponse.error);
        this.errorMessage = errorResponse.error;
      }
    );
  }

}
