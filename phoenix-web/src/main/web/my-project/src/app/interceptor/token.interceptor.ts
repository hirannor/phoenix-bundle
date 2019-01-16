import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import {AuthenticationService} from "../services";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public authenticationService: AuthenticationService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.authenticationService.getToken()}`}
    });

    return next.handle(request);
  }
}
