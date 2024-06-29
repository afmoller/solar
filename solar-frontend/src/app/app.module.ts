import { NgModule } from '@angular/core';
import {
  provideCharts,
  withDefaultRegisterables,
  }  from 'ng2-charts';
import { routes } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideRouter } from '@angular/router';
import { AppRoutingModule } from './app-routing.module';
import { MatTableModule } from '@angular/material/table';
import { environment } from '../environments/environment';
import { BrowserModule } from '@angular/platform-browser';
import { MatFormFieldModule} from '@angular/material/form-field';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginLogoutComponent } from './common/loginlogout/loginlogout.component';
import { httpStatusInterceptor } from './core/interceptors/http-status.interceptor';
import { jwtTokenAuthInterceptor } from './core/interceptors/jwt-token-auth.interceptor';
import { SummaryperdayentryComponent} from './features/tables/summaryperdayentry/summaryperdayentry.component';
import { SummarypermonthentryComponent } from './features/tables/symmarypermonthentry/summarypermonthentry.component';

import {
  FormsModule,
  ReactiveFormsModule
} from '@angular/forms';

import {
  withInterceptors,
  provideHttpClient
} from '@angular/common/http';

import {
  SocialLoginModule,
  GoogleInitOptions,
  GoogleLoginProvider,
  SocialAuthServiceConfig
} from '@abacritt/angularx-social-login';

const googleInitOptions: GoogleInitOptions = {
  oneTapEnabled: false,
};

@NgModule(
  { 
    declarations: [
        AppComponent,
    ],
    bootstrap: [
      AppComponent
    ],
    imports: [
      FormsModule,
      BrowserModule,
      MatTableModule,
      AppRoutingModule,
      SocialLoginModule,
      MatFormFieldModule,
      ReactiveFormsModule,
      LoginLogoutComponent,
      BrowserAnimationsModule,
      SummaryperdayentryComponent,
      SummarypermonthentryComponent
    ],
    providers: [
      provideHttpClient(withInterceptors([jwtTokenAuthInterceptor, httpStatusInterceptor])),
      provideCharts(withDefaultRegisterables()),
      provideRouter(routes),
      {
        provide: 'SocialAuthServiceConfig',
        useValue: {
          autoLogin: false,
          providers: [
            {
              id: GoogleLoginProvider.PROVIDER_ID,
              provider: new GoogleLoginProvider(environment.clientId, googleInitOptions),
            },
          ],
          onError: (err: any) => {
            console.error(err);
          },
        } as SocialAuthServiceConfig,
      },
    ]
  }
)
export class AppModule { }
