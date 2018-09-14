import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../core.service';
import { QuestionModel } from '../../question.model';
import { Observable } from 'rxjs/Observable';
import { ActivatedRoute } from '@angular/router';
import { CommentModel } from '../../comment.model';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'qna-question',
  templateUrl: './question.component.html',
  styleUrls: ['./question.component.css']
})
export class QuestionComponent implements OnInit {
  questionDetails : QuestionModel;
  questionId:number;
  comment:CommentModel = new CommentModel();
  errorMessage:string;
  constructor(private _route:ActivatedRoute,
              private _coreService:CoreService,
              private _dialog : MatDialog) { }

  ngOnInit(){
    this._route.params.subscribe(
      params=>{
        this.questionId  = params['id'];
        this.fetchQuestionDetails();
      },
      error =>{
          this.errorMessage = error.error;
      }
    );
  }

  private fetchQuestionDetails(){
    this._coreService.getQuestion(this.questionId).subscribe(
      res=>{
        this.questionDetails = res;
      },
      errorRes=>{
        this.errorMessage = 'Failed to fetch question details';
        console.error(errorRes.error);
      }
    );
  }

  addComment(){
    if(this.comment.description==undefined){
      this.errorMessage='Comment description cannot be blank';
      return;
    }
    let question = new QuestionModel();
    question.id=this.questionId;
    this.comment.question = question;
      this._coreService.addComment(this.comment).subscribe(
        res=>{
          this.clear();
          this.fetchQuestionDetails();
        },
        errorResponse=>{
          this.errorMessage = errorResponse.error;
        }
      );
  }

  deleteComment(id:number){
    const dialogRef = this._dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: 'Do you want to delete the comment?'
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this._deleteComment(id);
      }
    });
  }
  private _deleteComment(id:number){
    this._coreService.deleteComment(id).subscribe(
      res=>{
        this.clear();
        this.fetchQuestionDetails();
      },
      errorResponse=>{
        this.errorMessage = errorResponse.error;
      }
    );
  }

  private clear(){
    this.comment = new CommentModel();
    this.errorMessage = undefined;
  }

}

