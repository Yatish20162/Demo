package com.payroll.leave.controller;

import com.payroll.leave.dto.LeaveDto;
import com.payroll.leave.service.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LeaveController {

    private final ILeaveService iLeaveService;

    @GetMapping("/getLeaves")
    public ResponseEntity<LeaveDto> getLeaves(@RequestParam Long employeeId){
        LeaveDto leaveDto = iLeaveService.getLeaves(employeeId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(leaveDto);
    }

}
