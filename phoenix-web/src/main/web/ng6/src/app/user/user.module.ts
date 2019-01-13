import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {UserRoutingModule} from './user-routing.module';
import {UserComponent} from './components/user.component';
import {UserDetailsComponent} from './components/user-details.component'

@NgModule({
  imports: [
    CommonModule,
    UserRoutingModule
  ],
  declarations: [UserComponent, UserDetailsComponent]
})
export class UserModule { }
