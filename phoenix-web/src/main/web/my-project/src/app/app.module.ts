import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login';
import {HomeComponent} from './components/admin/home';
import {AlertComponent, ModalComponent} from './directives';
import {ErrorInterceptor, TokenInterceptor} from "./interceptors";
import {AdminLayoutComponent, ContentComponent, FooterComponent, HeaderComponent} from "./components/admin/layout";
import {TokenStorage} from "./helpers/token.storage";
import {NgbActiveModal, NgbModalModule} from "@ng-bootstrap/ng-bootstrap";
import {ModalSignupComponent, SignupComponent} from "./components/signup";
import {UserManagementComponent} from "./components/admin/usermanagement";
import {ModalEditComponent} from "./components/admin/usermanagement/modal-edit.component";
import {UserEditComponent} from "./components/admin/usermanagement/user-edit.component";

@NgModule({
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    NgbModalModule
],
  declarations: [
    AppComponent,
    SignupComponent,
    ModalSignupComponent,
    ModalEditComponent,
    UserEditComponent,
    LoginComponent,
    HomeComponent,
    AlertComponent,
    ModalComponent,
    UserManagementComponent,
    AdminLayoutComponent,
    HeaderComponent,
    ContentComponent,
    FooterComponent
  ],
  providers: [
    NgbActiveModal,
    TokenStorage,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    ModalSignupComponent,
    ModalEditComponent
  ]
})
export class AppModule { }
