import {Component, OnInit} from '@angular/core';
import {AlertService, CommonService} from "../../services";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";
import {first} from "rxjs/operators";

@Component({
  selector: 'phoenix-modal-forgot-password',
  templateUrl: './modal-forgot-password.component.html',
})
export class ModalForgotPasswordComponent implements OnInit {

  userName: string = '';

  constructor(private commonService: CommonService, private alertService: AlertService, private activeModal: NgbActiveModal){
  }

  ngOnInit(): void {}

  onSubmit() {
    this.commonService.sendResetPasswordNotification(this.userName).pipe(first()).subscribe(data => {
        this.activeModal.close();
        this.alertService.success('Reset Password notification has been sent!');
      },
      error => {
        this.activeModal.dismiss();
        this.alertService.error(error);
      });
  }
}
