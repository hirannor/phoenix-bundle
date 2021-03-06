import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {AlertService, AuthenticationService} from '../../services';
import {first} from "rxjs/operators";
import decode from 'jwt-decode';
import {TokenStorage} from "../../helpers/token.storage";
import {RoleType} from "../../models/base";
import {UserCredentials} from "../../models/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ModalSignupComponent} from "../signup/modal-signup.component";
import {ModalForgotPasswordComponent} from "../forgotpassword/modal-forgot-password.component";
import {TranslateService} from "@ngx-translate/core";
import {HttpClient} from "@angular/common/http";

@Component({
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.scss'
  ]
})
export class LoginComponent implements OnInit {

  selectedLanguage: string;
  languages: string[];
  credentials: UserCredentials;
  adminUrl: string;
  userUrl: string;

  constructor(
    private route: ActivatedRoute, private router: Router,
    private authenticationService: AuthenticationService, private tokenStorage: TokenStorage,
    private alertService: AlertService, private modalService: NgbModal,
    private translateService: TranslateService, private http: HttpClient) {

    this.credentials = new UserCredentials();
    this.adminUrl = '/admin';
    this.userUrl = '/user'
    this.languages = ['en', 'de'];
    this.selectedLanguage = this.translateService.getDefaultLang();

  }

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
      if (params.get('error')) {
        this.alertService.error(params.get('error'))
      }
    })
  }

  onChange(language: string) {
    this.translateService.use(language);
  }

  onSubmit() {
    this.authenticationService.login(this.credentials).pipe(first()).subscribe(data => {
        let token = this.tokenStorage.getToken();
        if (token) {
          this.routeByRole(decode(token).role[0]);
        }
      },
      error => {
        this.alertService.error(error);
      });
  }

  openSignupModal() {
    const modalRef = this.modalService.open(ModalSignupComponent);
  }

  openForgotPasswordModal() {
    const modalRef = this.modalService.open(ModalForgotPasswordComponent);
  }

  routeByRole(role: String) {
    switch (role) {
      case RoleType.ADMIN: {
        this.router.navigate([this.adminUrl]);
        break;
      }
      case RoleType.USER: {
        this.router.navigate([this.userUrl]);
        break;
      }
      default: {
        break;
      }
    }
  }
}
