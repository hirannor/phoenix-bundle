import {Injectable} from '@angular/core';
import {NavigationStart, Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {Alert} from "../../models/alert/alert";
import {AlertType} from "../../models/alert/alert.type";

@Injectable({
  providedIn: 'root'
})
export class AlertService {
  private subject = new Subject<Alert>();
  private keepAfterNavigationChange = false;

  constructor(private router: Router) {
    router.events.subscribe(event => {
      if (event instanceof NavigationStart) {
        if (this.keepAfterNavigationChange) {
          this.keepAfterNavigationChange = false;
        } else {
          this.subject.next();
        }
      }
    });
  }

  getMessage(): Observable<any> {
    return this.subject.asObservable();
  }

  success(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Success, message, keepAfterNavigationChange);
  }

  error(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Error, message, keepAfterNavigationChange);
  }

  info(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Info, message, keepAfterNavigationChange);
  }

  warn(message: string, keepAfterNavigationChange = false) {
    this.alert(AlertType.Warning, message, keepAfterNavigationChange);
  }

  alert(type: AlertType, message: string, keepAfterNavigationChange = false) {
    this.keepAfterNavigationChange = keepAfterNavigationChange;
    this.subject.next(<Alert>{ type: type, message: message });
  }

  clear() {
    this.subject.next();
  }
}
