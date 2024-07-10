package com.payroll.leave.controller;

import com.payroll.leave.service.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class LeaveController {

    private final ILeaveService iLeaveService;


}
