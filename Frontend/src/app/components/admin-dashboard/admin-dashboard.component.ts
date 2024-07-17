import { Component, OnInit } from '@angular/core';
import { Employee } from '../../model/employee.model';
import { UserRole } from '../../model/user-role.model';
import { DialogComponent } from '../dialog/dialog.component';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})

export class AdminDashboardComponent implements OnInit{
  leaveRequestCount: number = 0;

  employees: Employee[] = []; // Placeholder data

  constructor() {
    // Placeholder data
    this.employees = [
      {
        employeeId: 1,
        firstName: 'John',
        lastName: 'Doe',
        email: 'john.doe@example.com',
        jobTitle: 'Software Engineer',
        managerId: 2,
        phoneNumber: '123-456-7890',
        roles: [UserRole.Employee, UserRole.Manager]
      },
      {
        employeeId: 2,
        firstName: 'Jane',
        lastName: 'Smith',
        email: 'jane.smith@example.com',
        jobTitle: 'Project Manager',
        managerId: 1,
        phoneNumber: '987-654-3210',
        roles: [UserRole.Employee, UserRole.Manager]
      },
      {
        employeeId: 3,
        firstName: 'Alice',
        lastName: 'Johnson',
        email: 'alice.johnson@example.com',
        jobTitle: 'Senior Software Engineer',
        managerId: 2,
        phoneNumber: '456-789-0123',
        roles: [UserRole.Admin, UserRole.Manager]
      }
    ];
  }

  ngOnInit(): void {
    // Fetch leave request count from the database
    this.fetchLeaveRequestCount();
  }

  fetchLeaveRequestCount() {
    // Implement the logic to fetch the leave request count from the database
    // and update the `leaveRequestCount` variable
    this.leaveRequestCount = 10; // Dummy data for now
  }

}
