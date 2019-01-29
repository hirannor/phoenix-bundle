import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {map} from "rxjs/operators";
import {User} from "../../models/user";

@Injectable({
  providedIn: 'root'
})
export class CommonService {

  constructor(private http: HttpClient) { }

  signup(user: User) {
    return this.http.post('/common/signup', user).pipe(map(response => {
      return response;
    }));
  }

  sendResetPasswordNotification(userName: string) {
    return this.http.post('/common/resetPassword/' + userName, {});
  }
}
