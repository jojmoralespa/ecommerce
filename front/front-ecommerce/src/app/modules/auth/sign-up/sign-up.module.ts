import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SignUpComponent } from './sign-up.component';
import { RouterModule } from '@angular/router';
import { authSignupRoutes } from './sign-up.routing';



@NgModule({
  declarations: [
    SignUpComponent
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(authSignupRoutes)
  ]
})
export class SignUpModule { }
