import { NgIf } from '@angular/common';
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
} from '@angular/core';

import {
  SocialUser,
  SocialAuthService,
  GoogleSigninButtonModule,
} from '@abacritt/angularx-social-login';

@Component({
  selector: 'app-google-login-logout',
  templateUrl: './loginlogout.component.html',
  styleUrl: './loginlogout.component.scss',
  standalone: true,
  imports: [
    NgIf,
    RouterModule,
    MatIconModule,
    GoogleSigninButtonModule
  ],
})

export class LoginLogoutComponent implements OnInit, OnDestroy {
  
  accountDeletedSubscription: Subscription = new Subscription();
  createGoogleUserSubscription: Subscription = new Subscription();

  constructor(
    private authService: SocialAuthService,
    private jwtService: JwtService,
    private router: Router,
    private userService: UserCoreService)
  {}

  ngOnInit(): void {
    debugger;
    this.authService.authState.subscribe((user) => {
      if (user != null) {
        this.jwtService.saveToken(user.idToken);
        this.jwtService.saveEmail(user.email);

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

  getEmailAddress(): string {
    return this.jwtService.getEmail();
  }

  ngOnDestroy(): void {
    this.accountDeletedSubscription.unsubscribe();
    this.createGoogleUserSubscription.unsubscribe();
  }
}
