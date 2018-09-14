import { Component, OnInit } from '@angular/core';
import { CoreService } from '../../core.service';
import { TopicModel } from '../../topic.model';
import { Observable } from 'rxjs/Observable';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { MatDialog } from '@angular/material';

@Component({
  selector: 'qna-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  constructor(private _coreService : CoreService,
              private _dialog:MatDialog) { }
  topics : Observable<TopicModel[]>;
  topic: TopicModel = new TopicModel();
  errorMessage : string;
  
  ngOnInit() {
    this.getTopics();
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
        this.clear();
        this.getTopics();
      },
      errorResponse=>{
        console.error(errorResponse.error);
        this.errorMessage = errorResponse.error;
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
      },
      errorResponse=>{
        this.errorMessage = errorResponse.error;
      }
    );
  }

  private getTopics(){
    this.topics = this._coreService.getTopics()  
  }

  private clear(){
    this.topic = new TopicModel();
    this.errorMessage = undefined;
  }

}
