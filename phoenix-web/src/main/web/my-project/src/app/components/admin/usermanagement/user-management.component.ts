import {Component, OnInit} from '@angular/core';
import {UserService} from "../../../services";
import {first} from "rxjs/operators";
import {User} from "../../../models/user";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {ModalEditComponent} from "./modal-edit.component";
import * as _ from 'lodash';

@Component({
  selector: 'user-management',
  templateUrl: './user-management.component.html',
  styleUrls: ['./user-management.component.scss']
})
export class UserManagementComponent implements OnInit {

  users: User[] = [];

  constructor(private userService: UserService, private modalService: NgbModal) {
  }

  ngOnInit() {
    this.loadAllUsers();
  }

  deleteUser(userName: string) {
    this.userService.deleteUser(userName).pipe(first()).subscribe(() => {
      this.loadAllUsers();
    });
  }

  updateUser(user: User) {
    this.userService.updateUser(user).pipe(first()).subscribe(() => {
      this.loadAllUsers();
    })
  }

  openEditModal(user: User) {
    const modalRef = this.modalService.open(ModalEditComponent);
    modalRef.componentInstance.user = _.cloneDeep(user);
    modalRef.result.then((result)=> {
      this.loadAllUsers();
    }, (result) => {
      console.log("Rejected");
    });
  }

  private loadAllUsers() {
    this.userService.getUsers().pipe(first()).subscribe(users => {
      this.users = users;
    });
  }
}
