import { Injectable, inject } from '@angular/core';
import { JwtService } from './jwt.service';
import { SocialAuthService } from '@abacritt/angularx-social-login';

@Injectable({ providedIn: 'root' })
export class UserCoreService {

  private jwtService = inject(JwtService);
  private socialAuthService = inject(SocialAuthService);

  /**
   * Purge the user and sign out from the SocialAuthService library.
   */
  signOut(): void {
    this.purgeUser();
    this.socialAuthService.signOut();
    return;
  }

  isSignedIn(): boolean {
    return this.jwtService.getToken() !== undefined;
  }

  /**
   * remove the JWT and email address from local storage.
   *
   * @returns
   */
  private purgeUser(): void {
    this.jwtService.destroyToken();
    this.jwtService.removeFirstName();
    return;
  }
}
