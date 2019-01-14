import { Component, OnInit } from '@angular/core';
import {AlertService, AuthenticationService, UserService} from "../services";
import {User} from "../models/user";
import {first} from "rxjs/operators";
import {ActivatedRoute, Router} from "@angular/router";
import {BaseResponse} from "../models/baseresponse";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  currentUser: User;
  constructor(private userService: UserService, private authenticationService: AuthenticationService,
              private activatedRoute: ActivatedRoute, private alertService: AlertService, private router: Router) { }

  ngOnInit() {
    this.getUser();
    this.activatedRoute.params.subscribe(data => {
      this.alertService.success((<BaseResponse> data).message);
    })
  }

  private getUser() {
    this.userService.get().subscribe(user => {
      this.currentUser = user;
    });
  }

  logout() {
    this.authenticationService.logout().pipe(first()).subscribe(resp => {
      sessionStorage.clear();
      this.router.navigate(['/login']);
    });
  }
}
