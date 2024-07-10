package com.payroll.leave.service.impl;

import com.payroll.leave.dto.EmployeeDto;
import com.payroll.leave.dto.PayrollDto;
import com.payroll.leave.dto.ReportDto;
import com.payroll.leave.service.ReportService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    @Override
    public List<EmployeeDto> fetchAllEmployee() {
        return List.of();
    }

    @Override
    public boolean deleteAccount(String employeeName, Long employeeId) {
        return false;
    }

    @Override
    public PayrollDto fetchEmployeeReport(String employeeName, Date startTime, Date endTime) {
    return null;
    }

    @Override
    public EmployeeDto fetchEmployeeDetails(String employeeName, Long employeeId, Date endTime) {
        return null;
    }

    @Override
    public boolean updateAccount(String employeeName, Long employeeId, Date endTime) {
        return false;
    }

    @Override
    public void createReport(ReportDto reportDto) {


    }
}
