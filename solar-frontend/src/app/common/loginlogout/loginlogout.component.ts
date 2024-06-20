import { Subscription } from 'rxjs';
import { NgIf } from '@angular/common';
import { RouterModule, RouterOutlet } from '@angular/router';
import { JwtService } from '../../core/services/authentication/jwt.service';
import { UserCoreService } from '../../core/services/authentication/user-core.service';
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
  standalone: true,
  imports: [
    NgIf,
    RouterOutlet,
    RouterModule,
    GoogleSigninButtonModule,
  ],
  templateUrl: './loginlogout.component.html',
  styleUrl: './loginlogout.component.scss',
})
export class LoginLogoutComponent implements OnInit, OnDestroy {
  accountDeletedSubscription: Subscription = new Subscription();
  createGoogleUserSubscription: Subscription = new Subscription();

  loggedIn: boolean = false;
  user: SocialUser = {} as SocialUser;

  constructor(
    private authService: SocialAuthService,
    private jwtService: JwtService,

    public userService: UserCoreService,
  ) {}

  ngOnInit(): void {
    this.authService.authState.subscribe((user) => {
      this.user = user;
      this.loggedIn = user != null;
      if (user != null) {
        this.jwtService.saveToken(user.idToken);
      }
    });
  }

  logOutUser(): void {
    this.userService.signOut();
    return;
  }

  ngOnDestroy(): void {
    this.accountDeletedSubscription.unsubscribe();
    this.createGoogleUserSubscription.unsubscribe();
  }
}
