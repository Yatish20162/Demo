import { Component, OnInit } from '@angular/core';
import { Employee } from '../../model/employee.model';
import { UserRole } from '../../model/user-role.model';
import { AdminDashboardService } from './admin-dashboard.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css',
})


export class AdminDashboardComponent implements OnInit{
  employees: Employee[] = []; // Placeholder data
  errorMessage: string = '';

  constructor(private adminDashboardService: AdminDashboardService) { }

  ngOnInit(): void {
    // Fetch leave request count from the database
    this.getAllEmployeesData();
  }

  getAllEmployeesData() {
    this.adminDashboardService.getAllEmployees().subscribe(
      (response) => {
        console.log("Employees Data Fetched Successfully:  ", response)
        this.employees = response
      },
      (error) => {
        this.employees = [];
        this.errorMessage = 'Error fetching employees.';
      }
    );
  }

}
