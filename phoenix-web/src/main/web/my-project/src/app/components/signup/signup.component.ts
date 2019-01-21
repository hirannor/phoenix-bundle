import {Component, OnInit} from '@angular/core';
import {AlertService, SignupService} from "../../services";
import {User} from "../../models/user";
import {first} from "rxjs/operators";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'phoenix-signup',
  templateUrl: './signup.component.html',
  styleUrls: [
    './signup.component.scss'
  ]
})
export class SignupComponent implements OnInit  {

  user: User;

  constructor(private signupService: SignupService, private alertService: AlertService,  private modalService: NgbModal){
    this.user = new User();
  }

  ngOnInit(): void {

  }

  onSubmit() {
    this.signupService.signup(this.user).pipe(first()).subscribe(data => {
      this.modalService.dismissAll();
      this.alertService.success('Registered successfuly!');
      },
      error => {
        this.modalService.dismissAll();
        this.alertService.error(error);
      });
  }
}
