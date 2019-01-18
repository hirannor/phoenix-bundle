import {Component} from '@angular/core';
import {AuthenticationService} from "../../../../services";
import {Router} from "@angular/router";

@Component({
  selector: 'phoenix-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {

  constructor(private authenticationService: AuthenticationService, private router: Router) { }

  logout() {
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

}
