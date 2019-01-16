import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from "rxjs/operators";
import {BaseResponse} from "../models/baseresponse";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private httpOptions: any;

  constructor(private http: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest',
      })
    }
  }

  getToken(): string {
    return localStorage.getItem('token');
  }

  login(form) {
    return this.http.post<any>('/authenticate', {
      userName: form.userName.value,
      password: form.password.value
    }).pipe(map(resp => {
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


