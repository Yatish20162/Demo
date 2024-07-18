import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AdminDashboardService } from '../admin-dashboard/admin-dashboard.service';

@Component({
  selector: 'app-generate-report',
  templateUrl: './generate-report.component.html',
  styleUrls: ['./generate-report.component.css']
})
export class GenerateReportComponent {
  employeeId: number = 0;
  startDate: string = '';
  endDate: string = '';

  constructor(private route: ActivatedRoute, adminDashboardService: AdminDashboardService) { }

  generateReport() {
    // Logic to generate the report using the input values
    console.log(`Generating report for Employee ID: ${this.employeeId}, Start Date: ${this.startDate}, End Date: ${this.endDate}`);
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.employeeId = Number(params.get('id') ? params.get('id') : '') 
      if(this.employeeId!= 0){
        
      }
    });
  }
}
