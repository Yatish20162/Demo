// import { APP_INITIALIZER, ApplicationConfig, provideZoneChangeDetection } from '@angular/core';
// import { provideRouter } from '@angular/router';

// import { routes } from './app.routes';
// import { provideClientHydration } from '@angular/platform-browser';
// import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
// import { provideHttpClient } from '@angular/common/http';

// import { AuthConfig, OAuthService, provideOAuthClient } from 'angular-oauth2-oidc';

// const authCodeFlowConfig: AuthConfig = {
//   issuer: 'http://localhost:7080/realms/test-realm',
//   tokenEndpoint: 'http://localhost:7080/realms/test-realm/.well-known/openid-configuration',
//   redirectUri: 'http://localhost:4200/',
//   clientId: 'test-client',
//   responseType: 'code',
//   scope: 'openid profile'
// };

// function initializeOAuth(oauthService: OAuthService): () => Promise<void> {
//   return (): Promise<void> => {
//     return new Promise((resolve, reject) => {
//       oauthService.configure(authCodeFlowConfig);
//       oauthService.setupAutomaticSilentRefresh();
//       oauthService.loadDiscoveryDocumentAndLogin().then(() => resolve()).catch(reject);
//     });
//   };
// }

// export const appConfig: ApplicationConfig = {
//   providers: [
//     provideZoneChangeDetection({ eventCoalescing: true }),
//     provideRouter(routes),
//     provideClientHydration(),
//     provideAnimationsAsync(),
//     provideHttpClient(),
//     provideOAuthClient(),
//     {
//       provide: APP_INITIALIZER,
//       useFactory: initializeOAuth,
//       multi: true,
//       deps: [OAuthService]
//     }
//   ],
// };

import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import { provideClientHydration } from '@angular/platform-browser';

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), provideClientHydration()]
};
 