import {Component, Input} from "@angular/core";
import {NgbActiveModal} from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'phoenix-modal',
  templateUrl: 'modal.component.html'
})
export class ModalComponent {

  @Input() title = `Information`;

  constructor(
    public activeModal: NgbActiveModal
  ) {}

  ngOnInit() {
  }
}
