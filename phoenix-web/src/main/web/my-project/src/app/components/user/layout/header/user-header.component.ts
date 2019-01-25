import {Component} from '@angular/core';
import {AuthenticationService} from "../../../../services";
import {Router} from "@angular/router";

@Component({
  selector: 'phoenix-user-header',
  templateUrl: './user-header.component.html',
  styleUrls: ['./user-header.component.scss']
})
export class UserHeaderComponent {

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

}
