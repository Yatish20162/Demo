package com.payroll.leave.service;

import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.dto.LeaveRequestDto;
import com.payroll.leave.dto.NotificationResponseDto;

import java.time.LocalDate;
import java.util.List;


public interface ILeaveService {

    LeaveDto viewLeaves(Long employeeId);

    boolean generateLeaveRequest(LeaveRequestDto leaveRequestDto);

    Long fetchLwp(Long employeeId, LocalDate startDate, LocalDate endDate);

    boolean approveLeave(NotificationResponseDto notificationResponseDto);

    List<LeaveRequestDto> fetchLeaveRequest(Long managerId);
}
