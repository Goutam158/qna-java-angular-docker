import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardComponent } from './dashboard.component';
import { MatInputModule, MatButtonModule, MatCardModule, MatSnackBarModule, MatSnackBar, MatDialogModule, MatDialogRef } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../../auth/auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { RouterModule } from '@angular/router';
import { ContainerComponent } from '../container/container.component';
import { CoreService } from '../../core.service';
import { Observable } from 'rxjs/Observable';
import { of } from 'rxjs/observable/of';
import { ActivatedRoute } from '@angular/router';
import { MockCoreService } from '../../mock.core.service';

describe('DashboardComponent', () => {
  let component: DashboardComponent;
  let fixture: ComponentFixture<DashboardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers :[
        {
          provide: CoreService,
          useClass : MockCoreService
        },
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
      declarations: [ 
        DashboardComponent, 
        ContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DashboardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create  and all the topics', () => {
    expect(component).toBeTruthy();
  });
});
