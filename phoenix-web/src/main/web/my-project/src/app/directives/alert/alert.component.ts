import {Component, OnInit} from '@angular/core';
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
export class AlertComponent implements OnInit {
  private subscription: Subscription;

  alerts: Alert[] = [];

  constructor(private alertService: AlertService) { }

  ngOnInit() {
    this.alertService.getMessage().subscribe((message: Alert) => {
      if (!message) {
        this.alerts = [];
        return;
      }

      this.alerts.push(message);
    });
  }

  removeAlert(alert: Alert) {
    this.alerts = this.alerts.filter(x => x !== alert);
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
}
