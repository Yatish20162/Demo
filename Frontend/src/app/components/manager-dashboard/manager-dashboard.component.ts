import { Component, OnInit } from '@angular/core';
import { ManagerDashboardService } from './manager-dashboard.service';
import { LeaveRequest } from '../../model/Leave-Request.model';
import {NotificationResponse} from '../../model/notification-response'
import { status } from '../../model/request-status.model';
import { Leave } from '../../model/leave.model';
import {   FormBuilder, FormControl, FormGroup, Validators, FormsModule, ReactiveFormsModule } from '@angular/forms';

@Component({
  selector: 'app-manager-dashboard',
  templateUrl: './manager-dashboard.component.html',
  styleUrls: ['./manager-dashboard.component.css']
})
export class ManagerDashboardComponent implements OnInit {
  leave: Leave = new Leave();

  leaveRequestCount: number = 0;
  errorMessage: string = '';
  leaveRequests: LeaveRequest[] = [];
  selectedLeaveRequest: LeaveRequest | null = null;

  leaveForm: FormGroup;

  constructor(private managerDashboardService: ManagerDashboardService , private fb: FormBuilder) {
    this.leaveForm = this.fb.group({
      employeeId: ['', Validators.required],
      managerId: ['', Validators.required],
      startDate: ['', Validators.required],
      endDate: ['', Validators.required]
    });
  }

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

  onSubmit() {

    if (this.leaveForm.valid) {
      // Process leave request submission
      console.log(this.leaveForm.value);
      // Optionally, reset the form after submission
      this.leaveForm.reset();
    } else {
      console.log('Form is not valid');
    }
  
    // console.log('Submitting leave request:', this.leave);
    // this.managerDashboardService.createLeaveRequest(this.leave).subscribe(
    //   response => {
    //     console.log('Leave request submitted successfully!', response);
    //     this.leave = new Leave(); // Reset form after submission
    //     this.fetchLeaveRequests(); // Refresh leave requests
    //   },
    //   error => {
    //     console.error('Error submitting leave request:', error);
    //   }
    // );
  }

  
}