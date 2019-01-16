import {Component, OnInit} from '@angular/core';
import {AlertService, AuthenticationService, UserService} from "../services";
import {User} from "../models/user";
import {ActivatedRoute, Router} from "@angular/router";

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
    this.activatedRoute.queryParamMap.subscribe(params => {
      if(params.get('success')) {
        this.alertService.success(params.get('success'))
      }
    })
    this.getUser();
  }

  private getUser() {
    this.userService.get().subscribe(user => {
      this.currentUser = user;
    });
  }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }
}
