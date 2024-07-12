package com.payroll.leave.service.impl;

import com.payroll.leave.LeaveApplication;
import com.payroll.leave.dto.EmployeeDto;
import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.dto.LeaveRequestDto;
import com.payroll.leave.dto.NotificationResponseDto;
import com.payroll.leave.entity.Leave;
import com.payroll.leave.entity.LeaveRequest;
import com.payroll.leave.exception.ResourceNotFoundException;
import com.payroll.leave.mapper.LeaveMapper;
import com.payroll.leave.mapper.LeaveRequestMapper;
import com.payroll.leave.repository.LeaveRepository;
import com.payroll.leave.repository.LeaveRequestRepository;
import com.payroll.leave.service.ILeaveService;
import com.payroll.leave.service.clients.EmployeeFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        System.out.println("IN Service" + leaveRequest.getEmployeeId());
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

//       code for send Notification to manager

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

    @Override
    public boolean approveLeave(NotificationResponseDto notificationResponseDto) {

        Long employeeId = notificationResponseDto.getEmployeeId();
        Long leaveRequestId = notificationResponseDto.getLeaveRequestId();
        NotificationResponseDto.Status status = notificationResponseDto.getStatus();

        if(status != NotificationResponseDto.Status.APPROVED){
            return false;
        }


        Leave leave = leaveRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("leaves", "employeeID", employeeId)
        );

        LeaveRequest leaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequestId).orElseThrow(
                () -> new ResourceNotFoundException("leaves", "leaveRequestId", leaveRequestId)
        );

        long daysBetween = ChronoUnit.DAYS.between(leaveRequest.getStartDate(), leaveRequest.getEndDate());


        if(leave.getRemainingLeaves() >= daysBetween){

            leaveRequest.setRemainingLeaves(leave.getRemainingLeaves() - daysBetween);

            leave.setRemainingLeaves(leave.getRemainingLeaves() - daysBetween);

        }
        else{

            leaveRequest.setRemainingLeaves(0L);

            leaveRequest.setLwp(daysBetween - leave.getRemainingLeaves());

            leave.setLwp(leave.getLwp() + (daysBetween - leave.getRemainingLeaves()));
            leave.setRemainingLeaves(0L);
        }

        leaveRequest.setStatus(LeaveRequest.Status.APPROVED);
        leaveRequestRepository.save(leaveRequest);
        leaveRepository.save(leave);
        return true;
    }
}
