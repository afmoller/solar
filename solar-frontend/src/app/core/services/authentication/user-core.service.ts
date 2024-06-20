import { Injectable } from '@angular/core';
import { JwtService } from './jwt.service';
import { SocialAuthService } from '@abacritt/angularx-social-login';

@Injectable({ providedIn: 'root' })
export class UserCoreService {

  constructor(
    private jwtService: JwtService,
    private socialAuthService: SocialAuthService,
  ) {}

  /**
   * Purge the user and sign out from the SocialAuthService library.
   */
  signOut(): void {
    this.purgeUser();
    this.socialAuthService.signOut();
    return;
  }

  /**
   * Destroy the JWT from local storage. Update currentUserSubject to unauthenticated BackEndAuthenticatedUserProjection
   *
   * @returns
   */
  private purgeUser(): void {
    this.jwtService.destroyToken();
    return;
  }
}
