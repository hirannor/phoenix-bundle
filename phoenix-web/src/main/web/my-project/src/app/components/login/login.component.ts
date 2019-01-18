import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {AlertService, AuthenticationService} from '../../services';
import {first} from "rxjs/operators";
import decode from 'jwt-decode';
import {TokenStorage} from "../../helpers/token.storage";
import {RoleType} from "../../models/base";
import {UserCredentials} from "../../models/user";

@Component({
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.scss'
  ]
})
export class LoginComponent implements OnInit {

  credentials: UserCredentials;
  adminUrl: string;
  userUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService, private tokenStorage: TokenStorage) {

    this.credentials = new  UserCredentials();
    this.adminUrl = '/admin';
    this.userUrl = '/user'
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe( params => {
      if(params.get('error')) {
        this.alertService.error(params.get('error'))
      }
    })
  }

  onSubmit() {
    this.authenticationService.login(this.credentials).pipe(first()).subscribe(data =>{
      let token = this.tokenStorage.getToken();
        if(token) {
          let tokenPayload = decode(token);
          if(RoleType.ADMIN == tokenPayload.role[0]) {
            this.router.navigate([this.adminUrl], {queryParams: {success: data.message}},);
          }
      }
    },
error => {
        this.alertService.error(error);
    });
  }

}
