import { TestBed, inject } from '@angular/core/testing';

import { CoreService } from './core.service';
import { HttpClient, HttpHandler } from '@angular/common/http';

describe('CoreService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CoreService, HttpClient, HttpHandler ]
    });
  });

  it('should be created', inject([CoreService], (service: CoreService) => {
    expect(service).toBeTruthy();
  }));
});
