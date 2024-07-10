package com.payroll.report.service.impl;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.PayrollDto;
import com.payroll.report.dto.ReportDto;
import com.payroll.report.service.ReportService;
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
