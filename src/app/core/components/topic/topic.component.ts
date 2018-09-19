import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../core.service';
import { TopicModel } from '../../topic.model';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute } from '@angular/router';
import { QuestionModel } from '../../question.model';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog , MatSnackBar} from '@angular/material';

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
              private _snackBar : MatSnackBar,
              private _dialog:MatDialog) { }

  ngOnInit(){
    this._route.params.subscribe(
      params=>{
        this.topicId  = params['id'];
        this.fetchTopicDetails();
      },
      errorRes =>{
        this._snackBar.open(`Failed to read url paramterers ${errorRes.error}`,null,{duration : 6000,});
      }
    );
  }

  private fetchTopicDetails(){
    this._coreService.getTopic(this.topicId).subscribe(
      res=>{
        this.topicDetails = res;
      },
      errorRes=>{
        this._snackBar.open(`Failed to fetch topic details ${errorRes.error}`,null,{duration : 6000,});
      }
    );
  }

  addQuestion(){
    if(this.question.description==undefined || this.question.description.trim()==''){
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
          this._snackBar.open(`Question addition successful`,null,{duration : 4000,});
        },
        errorResponse=>{
          this._snackBar.open(`Question addition failed`,null,{duration : 4000,});
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
        this._snackBar.open(`Question deletion successful`,null,{duration : 4000,});
      },
      errorResponse=>{
        this._snackBar.open(`Question deletion failed`,null,{duration : 4000,});
      }
    );
  }

  private clear(){
    this.question = new QuestionModel();
    this.errorMessage = undefined;
  }

}
