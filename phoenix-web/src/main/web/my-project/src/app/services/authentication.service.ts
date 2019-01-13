import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';

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
      }, this.httpOptions).subscribe(resp =>{
        console.log(resp);
      });
    }

}

