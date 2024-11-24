import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class JwtService {
  
  tokenKey = 'jwtToken';
  emailKey = 'email';

  getToken(): String {
    return window.localStorage[this.tokenKey];
  }

  saveToken(token: String) {
    window.localStorage[this.tokenKey] = token;
  }

  destroyToken() {
    window.localStorage.removeItem(this.tokenKey);
  }

  getEmail(): string {
    return window.localStorage[this.emailKey];
  }

  saveEmail(email: string) {
    window.localStorage[this.emailKey] = email;
  }

  removeEmail() {
    window.localStorage.removeItem(this.emailKey);
  }
}