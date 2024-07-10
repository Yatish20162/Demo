package com.payroll.leave.controller;

import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.dto.LeaveRequestDto;
import com.payroll.leave.dto.ResponseDto;
import com.payroll.leave.service.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LeaveController {

    private final ILeaveService iLeaveService;

    @GetMapping("/viewLeaves")
    public ResponseEntity<LeaveDto> viewLeaves(@RequestParam Long employeeId){
        LeaveDto leaveDto = iLeaveService.viewLeaves(employeeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveDto);
    }
    @PostMapping("/leaveRequest")
    public ResponseEntity<ResponseDto> leaveRequest(@RequestBody LeaveRequestDto leaveRequestDto){
        boolean isCreated = iLeaveService.generateLeaveRequest(leaveRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("204" , "Leave Request Created Successfully"));
    }

    @GetMapping("/fetchLwp")
    public ResponseEntity<Long> fetchLwp(@RequestParam Long employeeId , @RequestParam LocalDateTime startDate , @RequestParam LocalDateTime endDate){
        Long lwpCount = iLeaveService.fetchLwp(employeeId , startDate , endDate);
        return ResponseEntity.status(HttpStatus.OK).body(lwpCount);
    }


}
