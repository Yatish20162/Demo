import { status } from "./request-status.model";

export interface LeaveRequest {
    employeeId: number;
    startDate: Date;
    endDate: Date;
    remainingLeaves: number;
    lwp: number;
    managerId: number;
    status: status;
}

 