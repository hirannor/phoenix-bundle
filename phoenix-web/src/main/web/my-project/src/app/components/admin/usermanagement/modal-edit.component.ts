import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../models/user";
import {AlertService, UserService} from "../../../services";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {first} from "rxjs/operators";

@Component({
  selector: 'phoenix-modal-edit',
  templateUrl: './modal-edit.component.html'
})
export class ModalEditComponent  implements OnInit {

  @Input() user: User;

  roles: String[];

  minAge: number = 18;
  maxAge: number = 99;

  constructor(private userService: UserService, private alertService: AlertService, private activeModal: NgbActiveModal){
    this.user = new User();
  }

  ngOnInit(): void {
    this.loadAllRoles();
  }

  resetPassword(userName: string) {
    this.userService.resetPassword(userName).subscribe(() => {
      console.log("Mail sent to: " + userName);
    });
  }

  onSubmit() {
    this.userService.updateUser(this.user).pipe(first()).subscribe(() => {
        this.activeModal.close();
        this.alertService.success('Edited successfuly!');
      },
      error => {
        this.activeModal.dismiss();
        this.alertService.error(error);
      });
  }

  private loadAllRoles() {
    this.userService.getRoles().pipe(first()).subscribe(roles => {
      this.roles = roles;
    });
  }
}
