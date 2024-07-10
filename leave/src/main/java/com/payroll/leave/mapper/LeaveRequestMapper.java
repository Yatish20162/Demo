package com.payroll.leave.mapper;

import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.dto.LeaveRequestDto;
import com.payroll.leave.entity.Leave;
import com.payroll.leave.entity.LeaveRequest;

public class LeaveRequestMapper {

    public static LeaveRequestDto mapToLeaveRequestDto(LeaveRequest leaveRequest, LeaveRequestDto leaveRequestDto) {
        leaveRequestDto.setStartDate(leaveRequest.getStartDate());
        leaveRequestDto.setEndDate(leaveRequest.getEndDate());
        leaveRequestDto.setRemainingLeaves(leaveRequest.getRemainingLeaves());
        leaveRequestDto.setLwp(leaveRequest.getLwp());
        return leaveRequestDto;
    }

    public static LeaveRequest mapToLeaveRequest(LeaveRequestDto leaveRequestDto, LeaveRequest leaveRequest) {
        leaveRequest.setStartDate(leaveRequestDto.getStartDate());
        leaveRequest.setEndDate(leaveRequestDto.getEndDate());
        leaveRequest.setRemainingLeaves(leaveRequestDto.getRemainingLeaves());
        leaveRequest.setLwp(leaveRequestDto.getLwp());
        return leaveRequest;
    }
}
