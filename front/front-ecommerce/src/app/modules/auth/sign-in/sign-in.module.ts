import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RouterModule } from '@angular/router';
import { authSigninRoutes } from './sign-in.routing';

import { HttpClientModule } from '@angular/common/http';
import { SignInComponent } from './sign-in.component';

import { MatButtonModule } from '@angular/material/button';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';
import { ReactiveFormsModule } from '@angular/forms';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations';


@NgModule({
  declarations: [
    SignInComponent,
    
  ],
  imports: [
    CommonModule,
    RouterModule.forChild(authSigninRoutes),
    HttpClientModule,
    MatButtonModule,
    MatCheckboxModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatProgressSpinnerModule,
    ReactiveFormsModule,
    // BrowserAnimationsModule
  ]
})
export class SignInModule { }
