import {Injectable} from '@angular/core';

const TOKEN_KEY = 'auth_token';

@Injectable()
export class TokenStorage {

  constructor() { }

  public saveToken(token: string) {
    return sessionStorage.setItem(TOKEN_KEY, token);
  }

  public getToken(): string {
    return sessionStorage.getItem(TOKEN_KEY);
  }

  public removeToken() {
    sessionStorage.removeItem(TOKEN_KEY);
    sessionStorage.clear();
  }
}
