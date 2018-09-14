import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../core.service';
import { TopicModel } from '../../topic.model';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute } from '@angular/router';
import { QuestionModel } from '../../question.model';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'qna-topic',
  templateUrl: './topic.component.html',
  styleUrls: ['./topic.component.css']
})
export class TopicComponent implements OnInit {

  topicDetails : TopicModel;
  topicId:number;
  question:QuestionModel = new QuestionModel();
  errorMessage:string;
  constructor(private _route:ActivatedRoute,
              private _coreService:CoreService,
              private _dialog:MatDialog) { }

  ngOnInit(){
    this._route.params.subscribe(
      params=>{
        this.topicId  = params['id'];
        this.fetchTopicDetails();
      },
      error =>{
          this.errorMessage = error.error;
      }
    );
  }

  private fetchTopicDetails(){
    this._coreService.getTopic(this.topicId).subscribe(
      res=>{
        this.topicDetails = res;
      },
      errorRes=>{
        this.errorMessage = 'Failed to fetch topic details';
        console.error(errorRes.error);
      }
    );
  }

  addQuestion(){
    if(this.question.description==undefined){
      this.errorMessage='Topic description cannot be blank';
      return;
    }
    let topic = new TopicModel();
    topic.id=this.topicId;
    this.question.topic = topic;
      this._coreService.addQuestion(this.question).subscribe(
        res=>{
          this.clear();
          this.fetchTopicDetails();
        },
        errorResponse=>{
          this.errorMessage = errorResponse.error;
        }
      );
  }

  deleteQuestion(id:number){
    const dialogRef = this._dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: 'Deleting the Question would delete all of the Comments it had. Do you still want to delete the Question?'
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this._deleteQuestion(id);
      }
    });
  }
  private _deleteQuestion(id:number){
    this._coreService.deleteQuestion(id).subscribe(
      res=>{
        this.clear();
        this.fetchTopicDetails();
      },
      errorResponse=>{
        this.errorMessage = errorResponse.error;
      }
    );
  }

  private clear(){
    this.question = new QuestionModel();
    this.errorMessage = undefined;
  }

}
