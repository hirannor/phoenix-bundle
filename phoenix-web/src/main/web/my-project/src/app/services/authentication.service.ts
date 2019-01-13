import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from '@angular/common/http';
import {Observable} from "rxjs/internal/Observable";
import {map} from "rxjs/operators";

@Injectable()
export class AuthenticationService {

  private httpOptions: any;

  constructor(private http: HttpClient) {
    this.httpOptions = {
      headers: new HttpHeaders({
        'Content-Type': 'application/json',
        'X-Requested-With': 'XMLHttpRequest'
      }),
      observe: 'response'
    }
  }

  login(form) {
    return this.http.post<any>('/login', {
      username: form.username.value,
      password: form.password.value
    }, this.httpOptions).pipe(map((response: HttpResponse<any>) => {
        if(response.status) {
          localStorage.setItem('isLoggedIn', 'true');
        }
        return response;
    }));
  }
}

