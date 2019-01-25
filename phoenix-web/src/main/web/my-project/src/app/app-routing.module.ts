import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards';
import {AdminLayoutComponent} from "./components/admin/layout/";
import {AdminHomeComponent} from "./components/admin/home/admin-home.component";
import {UserManagementComponent} from "./components/admin/usermanagement";
import {UserLayoutComponent} from "./components/user/layout";
import {UserHomeComponent} from "./components/user/home";

const routes: Routes = [
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: AdminHomeComponent,
        outlet: 'adminContent'
      },
      {
        path: 'usermanagement',
        component: UserManagementComponent,
        outlet: 'adminContent'
      }
    ]
  },
  {
    path: 'user',
    component: UserLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: UserHomeComponent,
        outlet: 'userContent'
      }
    ]
  },
  { path: 'login', component: LoginComponent },
  { path: '**', redirectTo: 'login' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
