import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { BaseResponse } from "../models/baseresponse";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        if(sessionStorage.getItem('response')) {
          let response: BaseResponse =  <BaseResponse>JSON.parse(sessionStorage.getItem('response'));
          if(response.success) {
            return true;
          }
        }
        this.router.navigate(['/login']);
    }
}
