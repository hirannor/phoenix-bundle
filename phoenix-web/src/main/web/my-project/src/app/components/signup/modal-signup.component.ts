import {Component, OnInit} from '@angular/core';
import {AlertService, CommonService} from "../../services";
import {User} from "../../models/user";
import {first} from "rxjs/operators";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'phoenix-modal-signup',
  templateUrl: './modal-signup.component.html',
})
export class ModalSignupComponent implements OnInit {

  user: User;

  minAge: number = 18;
  maxAge: number = 99;

  constructor(private commonService: CommonService, private alertService: AlertService, private activeModal: NgbActiveModal){
    this.user = new User();
  }

  ngOnInit(): void {}

  onSubmit() {
    this.commonService.signup(this.user).pipe(first()).subscribe(data => {
        this.activeModal.close();
        this.alertService.success('Registered successfuly!');
      },
      error => {
        this.activeModal.dismiss();
        this.alertService.error(error);
      });
  }
}
