package com.payroll.leave.service;

import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.dto.LeaveRequestDto;

import java.time.LocalDateTime;

public interface ILeaveService {

    LeaveDto viewLeaves(Long employeeId);

    boolean generateLeaveRequest(LeaveRequestDto leaveRequestDto);

    Long fetchLwp(Long employeeId, LocalDateTime startDate, LocalDateTime endDate);
}
