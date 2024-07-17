import { Component, OnInit } from '@angular/core';
import { ManagerDashboardService } from './manager-dashboard.service';
import { LeaveRequest } from '../../model/Leave-Request.model';
import {NotificationResponse} from '../../model/notification-response'
import { status } from '../../model/request-status.model';

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {
  leaveRequestCount: number = 0;
  errorMessage: string = '';
  leaveRequests: LeaveRequest[] = [];
  selectedLeaveRequest: LeaveRequest | null = null;


  constructor(private managerDashboardService: ManagerDashboardService) {}

  ngOnInit(): void {

    this.fetchLeaveRequestCount();
    this.fetchLeaveRequests();
  }

  fetchLeaveRequestCount() {
    this.managerDashboardService.getLeaveRequestCount(2).subscribe(
      (response) => {
        this.leaveRequestCount = response.totalLeaves;
        this.errorMessage = '';
      },
      (error) => {
        this.leaveRequestCount = 0;
        this.errorMessage = 'Error fetching leave request count.';
      }
    );
  }

  fetchLeaveRequests() {
    // Replace '1' with the actual manager ID
    this.managerDashboardService.fetchLeaveRequests(1001).subscribe(
      (response) => {
        this.leaveRequests = response;
        console.log('Full Response:', response);
      },
      (error) => {
        console.error('Error fetching leave requests:', error);
      }
    );
  }

  onLeaveRequestSelected(leaveRequest: LeaveRequest) {
    this.selectedLeaveRequest = leaveRequest;
  }

  approveLeave() {
    console.log("Approve function Enetred")
    if (this.selectedLeaveRequest) {
      const notificationResponseDto: NotificationResponse = {
        leaveRequestId: this.selectedLeaveRequest.employeeId,
        status: status.APPROVED,
        employeeId: this.selectedLeaveRequest.employeeId
      };

      this.managerDashboardService.approveLeave(notificationResponseDto).subscribe(
        (response) => {
          console.log(response.message);
          // Refresh the leave requests
          this.fetchLeaveRequests();
        },
        (error) => {
          console.error('Error approving leave:', error);
        }
      );
    }
  }

  declineLeave() {
    if (this.selectedLeaveRequest) {
      const notificationResponseDto: NotificationResponse = {
        leaveRequestId: this.selectedLeaveRequest.employeeId,
        status: status.DECLINED,
        employeeId: this.selectedLeaveRequest.employeeId
      };

      this.managerDashboardService.approveLeave(notificationResponseDto).subscribe(
        (response) => {
          console.log(response.message);
          // Refresh the leave requests
          this.fetchLeaveRequests();
        },
        (error) => {
          console.error('Error declining leave:', error);
        }
      );
    }
  }
}