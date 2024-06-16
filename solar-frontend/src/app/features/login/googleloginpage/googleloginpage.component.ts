import { Component } from '@angular/core';
import { LoginLogoutComponent } from 'src/app/common/loginlogout/loginlogout.component';

@Component({
  selector: 'app-googleloginpage',
  templateUrl: './googleloginpage.component.html',
  styleUrls: ['./googleloginpage.component.scss'],
  standalone: true,

  imports: [
    LoginLogoutComponent
  ],
  providers: [
  ],
})

export class GoogleLoginPage {

}
