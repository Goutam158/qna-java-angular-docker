import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionComponent } from './question.component';
import { ContainerComponent } from '../container/container.component';
import { RouterModule } from '@angular/router';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatInputModule, MatButtonModule, MatCardModule, MatSnackBarModule, MatDialogModule, MatDialogRef, MatSnackBar } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { CoreService } from '../../core.service';
import { AuthService } from '../../../auth/auth.service';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { Router } from '@angular/router';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';


describe('QuestionComponent', () => {
  let component: QuestionComponent;
  let fixture: ComponentFixture<QuestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers :[
        CoreService,
        AuthService,
        HttpClient,
        HttpHandler,
        MatSnackBar,
        {
          provide: ActivatedRoute,
          useValue: { params : of({id:1})}
        },
        {
          provide: Router,
          useClass: class{ navigate = jasmine.createSpy("navigate");}
        },
        {
          provide: MatDialogRef,
          useClass: class{ mockDialogRef = jasmine.createSpy("mockDialogRef");}
         }
      ],
      imports: [
        RouterModule,
        BrowserAnimationsModule,
        MatInputModule, 
        MatButtonModule, 
        MatCardModule,
        MatSnackBarModule, 
        MatDialogModule,
        FormsModule],
      declarations: [ QuestionComponent, ContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
