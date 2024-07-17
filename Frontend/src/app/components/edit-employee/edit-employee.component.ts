import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminDashboardService } from '../admin-dashboard/admin-dashboard.service';
import { Employee } from '../../model/employee.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-edit-employee',
  templateUrl: './edit-employee.component.html',
  styleUrl: './edit-employee.component.css',
})
export class EditEmployeeComponent {

  id : string = '';
  errorMessage: string = '';
  employeeForm: FormGroup;

  employee: Employee = {
    employeeId: 0,
    firstName: '',
    lastName: '',
    email: '',
    jobTitle: '',
    managerId: 0,
    phoneNumber: '',
    employeeRoles: []
  };

  constructor(private route: ActivatedRoute, private adminDashboardService: AdminDashboardService, private fb: FormBuilder) {
      this.employeeForm = this.fb.group({
      employeeId: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      jobTitle: ['', Validators.required],
      managerId: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      employeeRoles: this.fb.array([])  // You can populate this array with default roles if needed
    });
  }

  onSubmit(): void {
    if (this.employeeForm.valid) {
      console.log(this.employeeForm.value);
    } else {
      console.log('Form is not valid');
    }
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.id = String(params.get('id') ? params.get('id') : '') 
      if(this.id!= ''){
        this.getEmployeeById(this.id);
      }
    });
  }

  getEmployeeById(employeeId: string) {
    this.adminDashboardService.getEmployeeById(this.id).subscribe(
      (response) => {
        // console.log("Employee Data Fetched Successfully:  ", response)
        this.employee = response;
      },
      (error) => {
        this.errorMessage = 'Error fetching employees.';
      }
    );
  }
}
