import { Component, OnInit } from '@angular/core';
import {AuthenticationService, UserService} from "../services";
import {User} from "../models/user";
import {first} from "rxjs/operators";
import {Router} from "@angular/router";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  currentUser: User;
  constructor(private userService: UserService, private authenticationService: AuthenticationService, private router: Router) { }

  ngOnInit() {
    this.getUser();
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
