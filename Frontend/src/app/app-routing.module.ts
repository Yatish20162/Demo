import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ManagerDashboardComponent } from './components/manager-dashboard/manager-dashboard.component';
import { HomeComponent } from './components/home/home.component';
import { EmployeeDashboardComponent } from './components/employee-dashboard/employee-dashboard.component';
import { AdminDashboardComponent } from './components/admin-dashboard/admin-dashboard.component';

const routes: Routes = [
  {
    path: 'manager-dashboard',
    component: ManagerDashboardComponent,
    pathMatch: 'full'
  },
  {
    path: 'employee-dashboard',
    component: EmployeeDashboardComponent,
    pathMatch: 'full'
  },
  {
    path: 'admin-dashboard',
    component: AdminDashboardComponent,
    pathMatch: 'full'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
