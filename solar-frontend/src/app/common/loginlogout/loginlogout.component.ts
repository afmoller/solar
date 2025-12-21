
import { JwtService } from '../../core/services/authentication/jwt.service';
import { Subscription } from 'rxjs';
import { MatIconModule } from '@angular/material/icon';
import { UserCoreService } from '../../core/services/authentication/user-core.service';
import {
  Router,
  RouterModule
} from '@angular/router';

import {
  OnInit,
  Component,
  OnDestroy,
  inject
} from '@angular/core';

import {
  SocialUser,
  SocialAuthService,
  GoogleSigninButtonModule
} from '@abacritt/angularx-social-login';

@Component({
    selector: 'app-google-login-logout',
    templateUrl: './loginlogout.component.html',
    styleUrl: './loginlogout.component.scss',
    imports: [
      RouterModule,
      MatIconModule,
      GoogleSigninButtonModule
    ]
})

export class LoginLogoutComponent implements OnInit, OnDestroy {

  private router = inject(Router);
  private jwtService = inject(JwtService);
  private userService = inject(UserCoreService);
  private authService = inject(SocialAuthService);

  accountDeletedSubscription: Subscription = new Subscription();
  createGoogleUserSubscription: Subscription = new Subscription();

  ngOnInit(): void {
    this.authService.authState.subscribe((user) => {
      if (user != null) {
        this.jwtService.saveToken(user.idToken);
        this.jwtService.saveFirstName(user.firstName);

        this.router.navigateByUrl('/return-on-investment');
      }
    });
  }

  logOutUser(): void {
    this.userService.signOut();
    return;
  }

  isSignedIn(): boolean {
    return this.userService.isSignedIn();
  }

  getFirstName(): string {
    return this.jwtService.getFirstName();
  }

  ngOnDestroy(): void {
    this.accountDeletedSubscription.unsubscribe();
    this.createGoogleUserSubscription.unsubscribe();
  }
}
