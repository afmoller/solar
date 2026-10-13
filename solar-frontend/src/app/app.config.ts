import { routes } from "./app.routes";
import { provideRouter } from "@angular/router";
import { ApplicationConfig } from "@angular/core";
import { environment } from "/environments/environment";
import { httpStatusInterceptor } from "./core/interceptors/http-status.interceptor";
import { jwtTokenAuthInterceptor } from "./core/interceptors/jwt-token-auth.interceptor";

import {
  withInterceptors,
  provideHttpClient,
  withXhr
} from "@angular/common/http";

import {
  provideCharts,
  withDefaultRegisterables
} from "ng2-charts";

import {
  GoogleLoginProvider,
  SocialAuthServiceConfig
} from "@abacritt/angularx-social-login";

export const appConfig: ApplicationConfig = {
  providers: [
    provideRouter(routes),
    provideCharts(withDefaultRegisterables()),
    provideHttpClient(withXhr(), withInterceptors([jwtTokenAuthInterceptor, httpStatusInterceptor])),
      {
        provide: "SocialAuthServiceConfig",
        useValue: {
          autoLogin: false,
          providers: [
            {
              id: GoogleLoginProvider.PROVIDER_ID,
              provider: new GoogleLoginProvider(environment.clientId, {
                oneTapEnabled: false,
              }),
            },
          ],
          onError: (err) => {
            console.error(err);
          },
        } as SocialAuthServiceConfig,
      }
  ]
};
