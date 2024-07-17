import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrl: './admin-dashboard.component.css'
})

export class AdminDashboardComponent implements OnInit{
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
