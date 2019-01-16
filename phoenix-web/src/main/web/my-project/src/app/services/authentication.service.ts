import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {map} from "rxjs/operators";
import {BaseResponse} from "../models/base-response";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(private http: HttpClient) {}

  getToken(): string {
    return localStorage.getItem('token');
  }

  login(credentials) {
    return this.http.post<BaseResponse>('/authenticate', credentials).pipe(map(resp => {
        if (resp && resp.token) {
          localStorage.setItem('token', resp.token);
        }
        return resp;
    }));
  }

  logout() {
    localStorage.removeItem('token');
  }
}


