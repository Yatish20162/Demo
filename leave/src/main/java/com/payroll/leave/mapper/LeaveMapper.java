package com.payroll.leave.mapper;
import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.entity.Leave;

public class LeaveMapper {
    public static LeaveDto mapToLeaveDto(Leave leave , LeaveDto leavedto){
        leavedto.setTotalLeaves(leave.getTotalLeaves());
        leavedto.setRemainingLeaves(leave.getRemainingLeaves());
        leavedto.setLwp(leave.getLwp());
        return leavedto;
    }

    public static Leave mapToLeave(LeaveDto leaveDto , Leave leave){
        leave.setTotalLeaves(leaveDto.getTotalLeaves());
        leave.setRemainingLeaves(leaveDto.getRemainingLeaves());
        leave.setLwp(leaveDto.getLwp());
        return  leave;
    }


}
