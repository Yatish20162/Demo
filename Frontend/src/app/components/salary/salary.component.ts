import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminDashboardService } from '../admin-dashboard/admin-dashboard.service';
import { SalaryInfo } from '../../model/salaryInfo.model';


@Component({
  selector: 'app-salary',
  templateUrl: './salary.component.html',
  styleUrl: './salary.component.css'
})
export class SalaryComponent {

  id: string = '';
  errorMessage: string = '';
  
  salary: SalaryInfo = {
    employeeId: 0,
    hra: 0,
    baseSalary: 0,
    benefits: 0
  }

  constructor(private route: ActivatedRoute, private adminDashboardService: AdminDashboardService){}
  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.id = String(params.get('id') ? params.get('id') : '') 
      if(this.id!= ''){
        this.getSalaryByEmployeeId(this.id);
      }
    });
  }

    getSalaryByEmployeeId(employeeId: string): void{
      this.adminDashboardService.getSalaryByEmployeeId(employeeId).subscribe(
        (response) => {
          // console.log("Employee Data Fetched Successfully:  ", response)
          this.salary = response;
        },
        (error) => {
          this.errorMessage = 'Error fetching employees.';
        }
      );
    }
}
