package com.payroll.leave.service;

import com.payroll.leave.dto.LeaveDto;

public interface ILeaveService {

    LeaveDto getLeaves(Long employeeId);
}
