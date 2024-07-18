import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LeaveRequest } from '../../model/Leave-Request.model';
import { EmployeeDashboardService } from './employee-dashboard.service';
import { SalaryInfo } from '../../model/salaryInfo.model';


@Component({
  selector: 'app-employee-dashboard',
  templateUrl: './employee-dashboard.component.html',
  styleUrls: ['./employee-dashboard.component.css']
})
export class EmployeeDashboardComponent implements OnInit {
   leaveRequestCount : number = 0;
  leaveRequest :LeaveRequest|null =null;
  errorMessage: string ='';
  salaryInfo: SalaryInfo | null = null;


  constructor(
    private employeeDashboardService: EmployeeDashboardService
  ) {}

  ngOnInit() {
 
     this.getLeave();
     this.getSalary();
   
  }

   getLeave(){
    this.employeeDashboardService.getLeave(2).subscribe(
      (response) =>{
        console.log("leave data fetch successfully: ",response)
        this.leaveRequest = response
      },
      (error)=>{
        // this.leaveRequest=null;
        this.errorMessage='error fetching leaves';
      }
    );
   }

   getSalary(){
    this.employeeDashboardService.getSalary(2).subscribe(
      (response) =>{
        console.log("salary data fetch successfully: ",response)
        this.salaryInfo = response
      },
      (error)=>{
        // this.leaveRequest=null;
        this.errorMessage='error fetching salary info';
      }
    );
   }


  
 

  

  

  
}