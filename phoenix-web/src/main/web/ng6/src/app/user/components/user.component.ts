import {Component, OnInit} from '@angular/core';
import {UserService} from '../services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: '../templates/user.component.html',
  styleUrls: ['../templates/user.component.css']
})
export class UserComponent implements OnInit {

  users$: Object;

  constructor(private user: UserService) { }

  ngOnInit() {
    this.user.getUsers().subscribe(
      user => this.users$ = user
    );
  }

}
