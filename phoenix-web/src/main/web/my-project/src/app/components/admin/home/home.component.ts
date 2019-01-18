import {Component, OnInit} from '@angular/core';
import {AlertService, AuthenticationService, UserService} from "../../../services";
import {User} from "../../../models/user/user";
import {ActivatedRoute, Router} from "@angular/router";
import {first} from "rxjs/operators";

@Component({
  selector: 'phoenix-admin-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  currentUser: User

  constructor(private userService: UserService, private authenticationService: AuthenticationService,
              private activatedRoute: ActivatedRoute, private alertService: AlertService, private router: Router) {
    this.currentUser = new User();
  }

  ngOnInit() {
    this.activatedRoute.queryParamMap.subscribe(params => {
      if(params.get('success')) {
        this.alertService.success(params.get('success'))
      }
    })
    this.getUser();
  }

  private getUser() {
    this.userService.getUser().pipe(first()).subscribe(user => {
      this.currentUser = user;
    });
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
