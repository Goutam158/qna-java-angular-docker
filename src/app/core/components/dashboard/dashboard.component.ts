import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../core.service';
import { TopicModel } from '../../topic.model';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog, MatSnackBar } from '@angular/material';

@Component({
  selector: 'qna-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private _coreService : CoreService,
              private _snackBar : MatSnackBar,
              private _dialog:MatDialog) { }
  topics : TopicModel[] = [];
  topic: TopicModel = new TopicModel();
  errorMessage : string;
  
  ngOnInit() {
    this.getTopics();
  }


  addTopic(){
    if(this.topic.name==undefined || this.topic.name.trim() == ''){
      this.errorMessage='Topic name cannot be blank';
      return;
    }
    if(this.topic.description==undefined || this.topic.description.trim() == ''){
      this.errorMessage='Topic description cannot be blank';
      return;
    }
    this._coreService.addTopic(this.topic).subscribe(
      res=>{
        this.clear();
        this.getTopics();
        this._snackBar.open(`Topic addition successful`,null,{duration : 4000,});
      },
      errorResponse=>{
        this._snackBar.open(`Topic addition failed ${errorResponse.error}`,null,{duration : 4000,});
      }
    );
  }

  deleteTopic(id:number){
    const dialogRef = this._dialog.open(ConfirmationDialogComponent, {
      width: '400px',
      data: 'Deleting the Topic would delete all of the Questions and Comments it had. Do you still want to delete the Topic?'
    });
  
    dialogRef.afterClosed().subscribe(result => {
      if(result){
        this._deleteTopic(id);
      }
    });
  }
  private _deleteTopic(id:number){
    this._coreService.deleteTopic(id).subscribe(
      res=>{
        this.clear();
        this.getTopics();
        this._snackBar.open(`Topic deletion successful`,null,{duration : 4000,});
      },
      errorResponse=>{
        this._snackBar.open(`Topic deletion failed ${errorResponse.error}`,null,{duration : 4000,});
      }
    );
  }

  private getTopics(){
    this._coreService.getTopics().subscribe(
      res=>{
        this.topics = res;
      },
      errorRes=>{
        this._snackBar.open(`Failed to fetch topics ${errorRes.error}`,null,{duration : 6000,});
      }
    );
  }

  private clear(){
    this.topic = new TopicModel();
    this.errorMessage = undefined;
  }

}
