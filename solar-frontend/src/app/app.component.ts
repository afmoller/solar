import { Component } from '@angular/core';
import {MatSidenavModule} from '@angular/material/sidenav';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatExpansionModule} from '@angular/material/expansion';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
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
