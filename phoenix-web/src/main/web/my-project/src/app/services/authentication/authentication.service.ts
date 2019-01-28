import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from "rxjs/operators";
import {BaseResponse} from "../../models/base";
import {TokenStorage} from "../../helpers/token.storage";
import {UserCredentials} from "../../models/user";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {}

  login(credentials: UserCredentials) {
    return this.http.post<BaseResponse>('/authenticate', credentials).pipe(map(resp => {
        if (resp && resp.token) {
          this.tokenStorage.saveToken(resp.token);
        }
        return resp;
    }));
  }

  logout() {
    this.tokenStorage.removeToken();
  }
}


