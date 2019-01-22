import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './components/login/login.component';
import {AuthGuard} from './guards';
import {AdminLayoutComponent} from "./components/admin/layout/admin.layout.component";
import {HomeComponent} from "./components/admin/home/home.component";
import {UserManagementComponent} from "./components/admin/usermanagement";

const routes: Routes = [
  {
    path: 'admin',
    component: AdminLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: '',
        component: HomeComponent,
        outlet: 'adminContent'
      },
      {
        path: 'usermanagement',
        component: UserManagementComponent,
        outlet: 'adminContent'
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
