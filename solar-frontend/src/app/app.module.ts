import { NgModule } from '@angular/core';
import { NgChartsModule } from 'ng2-charts';
import { routes } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideRouter } from '@angular/router';
import { NgChartsConfiguration } from 'ng2-charts';
import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { MatTableModule } from '@angular/material/table';
import { environment } from '../environments/environment';
import { BrowserModule } from '@angular/platform-browser';
import { MatFormFieldModule} from '@angular/material/form-field';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AllComponent } from './features/diagrams/all/all.component';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginLogoutComponent } from './common/loginlogout/loginlogout.component';
import { httpStatusInterceptor } from './core/interceptors/http-status.interceptor';
import { jwtTokenAuthInterceptor } from './core/interceptors/jwt-token-auth.interceptor';
import { YearOverYearComponent} from './features/diagrams/yearoveryear/year-over-year.component';
import { DataexportentryComponent} from './features/dataexportentry/dataexportentry.component';
import { AccumulatedAllComponent } from './features/diagrams/accumulated/accumulated-all.component';
import { SummaryperdayentryComponent} from './features/tables/summaryperdayentry/summaryperdayentry.component';
import { SummarypermonthentryComponent } from './features/tables/symmarypermonthentry/summarypermonthentry.component';
import {
  GoogleInitOptions,
  GoogleLoginProvider,
  SocialAuthServiceConfig,
  SocialLoginModule
} from '@abacritt/angularx-social-login';

const googleInitOptions: GoogleInitOptions = {
  oneTapEnabled: false,
};

@NgModule({
  declarations: [
    AppComponent,
    AllComponent,
    YearOverYearComponent,
    AccumulatedAllComponent,
    DataexportentryComponent,
  ],
  imports: [
    FormsModule,
    BrowserModule,
    MatTableModule,
    NgChartsModule,
    AppRoutingModule,
    HttpClientModule,
    SocialLoginModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    LoginLogoutComponent,
    BrowserAnimationsModule,
    SummaryperdayentryComponent,
    SummarypermonthentryComponent,
  ],
  providers: [
    provideHttpClient(withInterceptors([jwtTokenAuthInterceptor, httpStatusInterceptor])),
    provideRouter(routes),
    {
      provide: NgChartsConfiguration,
      useValue:
        {
          generateColors: false
        }
    },
    {
      provide: 'SocialAuthServiceConfig',
      useValue: {
        autoLogin: false,
        providers: [
          {
            id: GoogleLoginProvider.PROVIDER_ID,
            provider: new GoogleLoginProvider(
              environment.clientId,
              googleInitOptions
            ),
          },
        ],
        onError: (err: any) => {
          console.error(err);
        },
      } as SocialAuthServiceConfig,
    },
  ],

  bootstrap: [AppComponent]
})
export class AppModule { }
