package com.payroll.leave.service.impl;

import com.payroll.leave.repository.LeaveRepository;
import com.payroll.leave.service.ILeaveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LeaveServiceImpl implements ILeaveService {

    private final LeaveRepository leaveRepository;
}
