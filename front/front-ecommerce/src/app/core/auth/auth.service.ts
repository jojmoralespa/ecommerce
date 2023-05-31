import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private isAuthenticathed : boolean = false;

  constructor(
    private http : HttpClient,
    // private userService : 
  ) { }

  set accessToken(token : string) {
    localStorage.setItem('accessToken', token);
  }
  
  get accessToken() {
    return localStorage.getItem('accessToken') ?? '';
  }

  signIn(credentials :{ email : string; password : string }) : Observable<any> {
    if (this.isAuthenticathed) {
      return throwError("User already authenticated");
    }

    return this.http.post('http://localhost:8080/api/user/authenticate', credentials);
  }

  signOut(): Observable<any>
    {
        // Remove the access token from the local storage
        localStorage.removeItem('accessToken');

        // Set the authenticated flag to false
        this.isAuthenticathed = false;

        // Return the observable
        return of(true);
    }

}
