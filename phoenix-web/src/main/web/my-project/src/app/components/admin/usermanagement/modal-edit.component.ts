import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../../models/user";

@Component({
  selector: 'phoenix-modal-edit',
  templateUrl: './modal-edit.component.html',
})
export class ModalEditComponent  implements OnInit {

  @Input() user: User;
  constructor() { }

  ngOnInit() {
    console.log('Username: ' + this.user.userName);
  }

}
