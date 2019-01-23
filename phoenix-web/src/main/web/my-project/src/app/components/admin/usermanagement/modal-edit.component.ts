import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../models/user";
import {AlertService, UserService} from "../../../services";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {first} from "rxjs/operators";

@Component({
  selector: 'phoenix-modal-edit',
  templateUrl: './modal-edit.component.html',
})
export class ModalEditComponent  implements OnInit {

  @Input() user: User;

  minAge: number = 18;
  maxAge: number = 99;

  constructor(private userService: UserService, private alertService: AlertService,  private modalService: NgbModal){
    this.user = new User();
  }

  ngOnInit(): void {

  }

  onSubmit() {
    this.userService.updateUser(this.user).pipe(first()).subscribe(data => {
        this.modalService.dismissAll();
        this.alertService.success('Edited successfuly!');
      },
      error => {
        this.modalService.dismissAll();
        this.alertService.error(error);
      });
  }

}
