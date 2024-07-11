package com.payroll.leave.service.impl;

import com.payroll.leave.LeaveApplication;
import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.dto.LeaveRequestDto;
import com.payroll.leave.entity.Leave;
import com.payroll.leave.entity.LeaveRequest;
import com.payroll.leave.exception.ResourceNotFoundException;
import com.payroll.leave.mapper.LeaveMapper;
import com.payroll.leave.mapper.LeaveRequestMapper;
import com.payroll.leave.repository.LeaveRepository;
import com.payroll.leave.repository.LeaveRequestRepository;
import com.payroll.leave.service.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements ILeaveService {

    private final LeaveRepository leaveRepository;
    private final LeaveRequestRepository leaveRequestRepository;


    @Override
    public LeaveDto viewLeaves(Long employeeId) {
        Leave leave = leaveRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("leaves", "employeeID", employeeId)
        );

        LeaveDto leaveDto = LeaveMapper.mapToLeaveDto(leave, new LeaveDto());
        return leaveDto;
    }

    @Override
    public boolean generateLeaveRequest(LeaveRequestDto leaveRequestDto) {
        boolean isCreated = false;
        LeaveRequest leaveRequest = LeaveRequestMapper.mapToLeaveRequest(leaveRequestDto , new LeaveRequest());
        Leave leave = leaveRepository.findByEmployeeId(leaveRequest.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException("leaves", "employeeID", leaveRequest.getEmployeeId())
        );

        if(leave != null){
            Long remainingLeaves = leave.getRemainingLeaves();
            Long lwp = leave.getLwp();
//            Long plwp = leave.getLwp();

            leaveRequest.setRemainingLeaves(remainingLeaves);
            leaveRequest.setLwp(lwp);
//            leaveRequest.setPlwp(plwp);
            leaveRequest.setStatus(LeaveRequest.Status.PENDING);
            leaveRequestRepository.save(leaveRequest);
            isCreated = true;
        }

        return isCreated;
    }

    @Override
    public Long fetchLwp(Long employeeId, LocalDate startDate, LocalDate endDate) {

        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeIdAndStartDateBetween(employeeId, startDate, endDate);

        System.out.println("LeaveRequests List : " + leaveRequests);


        Long lwpDays = 0L;

        for (LeaveRequest leaveRequest : leaveRequests) {
            System.out.println(leaveRequest);
            if (leaveRequest.getStatus().equals(LeaveRequest.Status.APPROVED)) {
                System.out.println(leaveRequest.getLwp());
                System.out.println(leaveRequest.getStartDate());
                System.out.println(leaveRequest.getEndDate());
                lwpDays += leaveRequest.getLwp();
            }
        }
        System.out.println("lwpDays" + lwpDays);
        return lwpDays;
    }
}
