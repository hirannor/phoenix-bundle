import {Component, OnInit} from '@angular/core';
import {AlertService, UserService} from "../../../services";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'phoenix-user-edit',
  templateUrl: './user-edit.component.html',
  styleUrls: [
    './user-edit.component.scss'
  ]
})
export class UserEditComponent implements OnInit {


  constructor(private userService: UserService, private alertService: AlertService, private modalService: NgbModal) {
    // this.user = new User();
  }

  ngOnInit(): void {
  }

  onSubmit() {
    //   this.userService.updateUser(this.user).pipe(first()).subscribe(data => {
    //       this.modalService.dismissAll();
    //       this.alertService.success('Updated successfuly!');
    //     },
    //     error => {
    //       this.modalService.dismissAll();
    //       this.alertService.error(error);
    //     });
  }
}
