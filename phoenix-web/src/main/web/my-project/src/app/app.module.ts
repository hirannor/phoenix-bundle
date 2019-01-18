import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login';
import {HomeComponent} from './components/admin/home';
import {AlertComponent} from './directives';
import {ErrorInterceptor, TokenInterceptor} from "./interceptors";
import {AdminLayoutComponent, ContentComponent, FooterComponent, HeaderComponent} from "./components/admin/layout";
import {TokenStorage} from "./helpers/token.storage";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
],
  declarations: [
    AppComponent,
    LoginComponent,
    HomeComponent,
    AlertComponent,
    AdminLayoutComponent,
    HeaderComponent,
    ContentComponent,
    FooterComponent
  ],
  providers: [
    TokenStorage,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
