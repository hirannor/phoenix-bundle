import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from "rxjs/operators";
import {BaseResponse} from "../../models/base";
import {TokenStorage} from "../../helpers/token.storage";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient, private tokenStorage: TokenStorage) {}

  login(credentials) {
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


