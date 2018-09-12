import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../auth/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'qna-container',
  templateUrl: './container.component.html',
  styleUrls: ['./container.component.css']
})
export class ContainerComponent implements OnInit {

  constructor(
    private _authService : AuthService,
    private _router: Router) { }
  isLoggedIn:boolean = false;
  ngOnInit() {
    this.isLoggedIn = this._authService.isLoggedIn();
  }

  logout(){
    this._authService.logout();
    this.isLoggedIn=false;
    this._router.navigateByUrl('/login');
  }

}
