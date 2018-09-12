import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule , Routes } from '@angular/router';
import { FormsModule } from '@angular/forms';

import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { MatInputModule } from '@angular/material/input';
import { MatDialogModule } from '@angular/material/dialog';
import { MatIconModule } from '@angular/material/icon';

import { DashboardComponent } from './components/dashboard/dashboard.component';
import { TopicComponent } from './components/topic/topic.component';
import { QuestionComponent } from './components/question/question.component';
import { CoreService } from './core.service';
import { ContainerComponent } from './components/container/container.component';
import { InterceptorService } from './interceptor.service';

const coreRoutes: Routes = [
  { path: 'dashboard', component: DashboardComponent },
  { path: 'topic-details/:id', component: TopicComponent},
  { path: 'question-details/:id', component: QuestionComponent }
];

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    FormsModule,
    BrowserModule,
    RouterModule.forChild(coreRoutes),
    MatCardModule,
    MatButtonModule,
    MatToolbarModule,
    MatSnackBarModule,
    MatInputModule,
    MatDialogModule,
    MatIconModule
  ],
  declarations: [
    DashboardComponent, 
    TopicComponent, 
    QuestionComponent, ContainerComponent],
  providers: [CoreService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: InterceptorService,
      multi: true
    }
  ]
})
export class CoreModule { }
