import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';

import {AlertService} from '../../services';
import {AlertType} from "../../models/alert/alert.type";
import {Alert} from "../../models/alert/alert";

@Component({
  selector: 'alert',
  templateUrl: 'alert.component.html',
  styleUrls: [
    './alert.component.scss'
  ]
})
export class AlertComponent implements OnInit, OnDestroy {
  private subscription: Subscription;

  alert: Alert;

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.subscription = this.alertService.getMessage().subscribe(alert => {
      this.alert = alert;
    });
  }

  removeAlert(alert: Alert) {
    this.alert = null;
  }

  cssClass(alert: Alert) {
    if (!alert) {
      return;
    }

    switch (alert.type) {
      case AlertType.Success:
        return 'alert alert-success';
      case AlertType.Error:
        return 'alert alert-danger';
      case AlertType.Info:
        return 'alert alert-info';
      case AlertType.Warning:
        return 'alert alert-warning';
    }
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }
}
