package com.payroll.report.service;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.PayrollDto;
import com.payroll.report.dto.ReportDto;

import java.util.Date;
import java.util.List;


public interface ReportService {


    static boolean deleteAccount(String employeeName, Long employeeId, Date endTime) {
        return false;
    }

    List<EmployeeDto> fetchAllEmployee();

    boolean deleteAccount(String employeeName, Long employeeId);

    PayrollDto fetchEmployeeReport(String employeeName, Date startTime, Date endTime);

    EmployeeDto fetchEmployeeDetails(String employeeName, Long employeeId, Date endTime);

    boolean updateAccount(String employeeName, Long employeeId, Date endTime);





    void createReport(ReportDto reportDto);
}
