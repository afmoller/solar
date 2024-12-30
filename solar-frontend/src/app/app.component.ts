import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatTableModule } from '@angular/material/table';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatExpansionModule } from '@angular/material/expansion';
import { MatFormFieldModule } from '@angular/material/form-field';
import { LoginLogoutComponent } from './common/loginlogout/loginlogout.component';

import {
  RouterModule,
  RouterOutlet
} from '@angular/router';

import {
  FormsModule,
  ReactiveFormsModule
} from '@angular/forms';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],

    imports: [
      FormsModule,
      RouterModule,
      RouterOutlet,
      MatIconModule,
      MatInputModule,
      MatTableModule,
      MatSidenavModule,
      MatToolbarModule,
      MatExpansionModule,
      MatFormFieldModule,
      ReactiveFormsModule,      
      LoginLogoutComponent
  ]
})
export class AppComponent {
  title = 'Solar-Frontend';
  pageTitle = '';
  events: string[] = [];
  opened: boolean = true;

  setPageTitle(pageTitle: string): void {
    this.pageTitle = pageTitle;
  }
}
