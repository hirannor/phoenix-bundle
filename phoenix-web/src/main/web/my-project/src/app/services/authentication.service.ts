import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {map} from "rxjs/operators";

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
      }),
      withCredentials: true
    }
  }

  login(form) {
    return this.http.post('/auth', {
      userName: form.userName.value,
      password: form.password.value
    }, this.httpOptions).pipe(map(baseresponse => {
        sessionStorage.setItem('response', JSON.stringify(baseresponse));
        return baseresponse;
    }));
  }

  logout() {
    return this.http.post('/logout', {}).pipe(map(baseresponse => {
      return baseresponse;
    }));
  }
}


