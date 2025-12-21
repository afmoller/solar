import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JwtService {
  
  tokenKey = 'jwtToken';
  firstName = 'firstName';

  getToken(): String {
    return window.localStorage[this.tokenKey];
  }

  saveToken(token: String) {
    window.localStorage[this.tokenKey] = token;
  }

  destroyToken() {
    window.localStorage.removeItem(this.tokenKey);
  }

  getFirstName(): string {
    return window.localStorage[this.firstName];
  }

  saveFirstName(firstName: string) {
    window.localStorage[this.firstName] = firstName;
  }

  removeFirstName() {
    window.localStorage.removeItem(this.firstName);
  }
}