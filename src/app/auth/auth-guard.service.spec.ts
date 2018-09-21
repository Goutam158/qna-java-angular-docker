import { TestBed, inject } from '@angular/core/testing';

import { AuthGuardService } from './auth-guard.service';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from './auth.service';
import { HttpClient, HttpHandler } from '@angular/common/http';

describe('AuthGuardService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        AuthGuardService, 
        {
          provide: Router,
          useClass: class{ navigate = jasmine.createSpy("navigate");}
        }
        , AuthService
        , HttpClient
        , HttpHandler
      ]
    });
  });

  it('should be created', 
  inject([AuthGuardService], (service: AuthGuardService) => {
    expect(service).toBeTruthy();
  }));
});
