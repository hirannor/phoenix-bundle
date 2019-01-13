import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {UserService} from '../services/user.service';


@Component({
  selector: 'app-user-details',
  templateUrl: '../templates/user-details.component.html',
  styleUrls: ['../templates/user-details.component.css']
})
export class UserDetailsComponent implements OnInit {

  user$: Object;
  constructor(private route: ActivatedRoute, private user: UserService) {
    this.route.params.subscribe(params => this.user$ = params.id)
   }

  ngOnInit() {
    this.user.getUser(this.user$).subscribe(
      user => this.user$ = user
    )
  }
}
