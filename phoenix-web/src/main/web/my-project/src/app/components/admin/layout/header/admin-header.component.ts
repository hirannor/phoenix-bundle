import {Component} from '@angular/core';
import {AuthenticationService} from "../../../../services";
import {Router} from "@angular/router";

@Component({
  selector: 'phoenix-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.scss']
})
export class AdminHeaderComponent {

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

}
