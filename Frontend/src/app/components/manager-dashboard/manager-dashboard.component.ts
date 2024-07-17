import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {
  leaveRequestCount: number = 0;

  constructor() { }

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