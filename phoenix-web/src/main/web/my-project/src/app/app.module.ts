import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {LoginComponent} from './components/login';
import {AdminHomeComponent} from './components/admin/home';
import {UserHomeComponent} from './components/user/home';
import {AlertComponent, ModalComponent} from './directives';
import {ErrorInterceptor, TokenInterceptor} from "./interceptors";
import {
  AdminContentComponent,
  AdminFooterComponent,
  AdminHeaderComponent,
  AdminLayoutComponent
} from "./components/admin/layout";
import {
  UserContentComponent,
  UserFooterComponent,
  UserHeaderComponent,
  UserLayoutComponent
} from "./components/user/layout";
import {TokenStorage} from "./helpers/token.storage";
import {NgbActiveModal, NgbModalModule} from "@ng-bootstrap/ng-bootstrap";
import {ModalSignupComponent} from "./components/signup";
import {ModalEditComponent, UserManagementComponent} from "./components/admin/usermanagement";
import {PhoenixMinDirective} from "./directives/validator/phoenix-min-validator.directive";
import {PhoenixMaxDirective} from "./directives/validator/phoenix-max-validator.directive";

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
    ModalSignupComponent,
    ModalEditComponent,
    LoginComponent,
    AdminHomeComponent,
    UserHomeComponent,
    AlertComponent,
    ModalComponent,
    UserManagementComponent,
    AdminLayoutComponent,
    AdminHeaderComponent,
    AdminContentComponent,
    AdminFooterComponent,
    UserLayoutComponent,
    UserHeaderComponent,
    UserContentComponent,
    UserFooterComponent,
    PhoenixMinDirective,
    PhoenixMaxDirective
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
