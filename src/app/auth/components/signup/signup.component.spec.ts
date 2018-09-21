import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SignupComponent } from './signup.component';
import { MatInputModule, MatButtonModule, MatCardModule, MatSnackBarModule, MatSnackBar } from '@angular/material';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../auth.service';
import { Router } from '@angular/router';
import { HttpClient, HttpHandler } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      providers :[
        AuthService,
        HttpClient,
        HttpHandler,
        MatSnackBar,
        {
          provide: Router,
          useClass: class{ navigate = jasmine.createSpy("navigate");}
        }
      ],
      imports: [
        BrowserAnimationsModule,
        MatInputModule, 
        MatButtonModule, 
        MatCardModule,
        MatSnackBarModule, 
        FormsModule],
      declarations: [ SignupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
