import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from "rxjs";
import {TokenStorage} from "../helpers/token.storage";

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(public tokenStorage: TokenStorage) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    request = request.clone({
      setHeaders: {
        Authorization: `Bearer ${this.tokenStorage.getToken()}`}
    });

    return next.handle(request);
  }
}
