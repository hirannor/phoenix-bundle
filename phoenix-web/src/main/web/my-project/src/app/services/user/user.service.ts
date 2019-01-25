import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../../models/user/user";
import {map} from "rxjs/operators";
import * as _ from "lodash";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  getUsers() {
    return this.http.get<User[]>('/v1/api/usermanagement/users').pipe(map((users: User[]) => {
      return _.orderBy(users, [(user: User) => user.userName.toLocaleLowerCase()], ['asc']);
    }));
  }

  updateUser(user: User) {
    return this.http.put('/v1/api/usermanagement/user/' + user.userName, user);
  }

  deleteUser(userName: string) {
    return this.http.delete('/v1/api/usermanagement/user/' + userName);
  }

  getUser() {
    return this.http.get<User>('/v1/api/user').pipe(map(user => {
      return user;
    }));
  }

  resetPassword(userName: string) {
    return this.http.post('/v1/api/usermanagement/resetPassword/' + userName, {});
  }
}
