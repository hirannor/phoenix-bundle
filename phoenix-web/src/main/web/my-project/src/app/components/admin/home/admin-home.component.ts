import {Component, OnInit} from '@angular/core';
import {AlertService, UserService} from "../../../services";
import {User} from "../../../models/user/user";
import {ActivatedRoute} from "@angular/router";
import {first} from "rxjs/operators";

@Component({
  selector: 'phoenix-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.scss']
})
export class AdminHomeComponent implements OnInit {

  currentUser: User

  constructor(private userService: UserService,
              private route: ActivatedRoute, private alertService: AlertService) {
    this.currentUser = new User();
  }

  ngOnInit() {
    this.route.queryParamMap.subscribe(params => {
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

}
