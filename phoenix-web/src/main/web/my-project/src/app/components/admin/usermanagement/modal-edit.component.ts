import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../models/user";
import {AlertService, UserService} from "../../../services";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {first} from "rxjs/operators";
import {RoleService} from "../../../services/role/role.service";

@Component({
  selector: 'phoenix-modal-edit',
  templateUrl: './modal-edit.component.html'
})
export class ModalEditComponent  implements OnInit {

  @Input() user: User;

  roles: String[];

  minAge: number = 18;
  maxAge: number = 99;

  constructor(private userService: UserService, private alertService: AlertService,  private modalService: NgbModal, private roleService: RoleService){
    this.user = new User();
  }

  ngOnInit(): void {
    this.loadAllRoles();
  }

  onSubmit() {
    this.userService.updateUser(this.user).pipe(first()).subscribe(() => {
        this.alertService.success('Edited successfuly!');
      },
      error => {
        this.alertService.error(error);
      });
  }

  private loadAllRoles() {
    this.roleService.getRoles().pipe(first()).subscribe(roles => {
      this.roles = roles;
    });
  }
}
