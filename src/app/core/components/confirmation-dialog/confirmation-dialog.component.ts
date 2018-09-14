import { Component,Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatSnackBar} from '@angular/material';


@Component({
  selector: 'qna-confirmation-dialog',
  templateUrl: './confirmation-dialog.component.html',
  styleUrls: ['./confirmation-dialog.component.css']
})
export class ConfirmationDialogComponent {
  constructor( public dialogRef: MatDialogRef<ConfirmationDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public message: string) { }

    confirm(result:boolean){
      this.dialogRef.close(result);
    }
  

}
