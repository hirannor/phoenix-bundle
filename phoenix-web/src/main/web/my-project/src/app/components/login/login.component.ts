import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {AlertService, AuthenticationService} from '../../services';
import {first} from "rxjs/operators";
import {UserCredentials} from "../../models/user/user-credentials";

@Component({
  templateUrl: './login.component.html',
  styleUrls: [
    './login.component.scss'
  ]
})
export class LoginComponent implements OnInit {

  credentials: UserCredentials;
  returnUrl: string;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private authenticationService: AuthenticationService,
    private alertService: AlertService) {

    this.credentials = new  UserCredentials();
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe( params => {
      if(params.get('error')) {
        this.alertService.error(params.get('error'))
      }
    })
    this.returnUrl = '/admin';
  }

  onSubmit() {
    this.authenticationService.login(this.credentials).pipe(first()).subscribe(data =>{
      this.router.navigate([this.returnUrl], {queryParams: {success: data.message}},);
    },
error => {
        this.alertService.error(error);
    });
  }

}
