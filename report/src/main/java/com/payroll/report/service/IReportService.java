package com.payroll.report.service;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.PayrollDto;
import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.SalaryDto;

import java.util.Date;
import java.util.List;


public interface IReportService {


    static boolean deleteAccount(String employeeName, Long employeeId, Date endTime) {
        return false;
    }

    List<EmployeeDto> fetchAllEmployee();

    boolean deleteAccount(String employeeName, Long employeeId);

    PayrollDto fetchEmployeeReport(String employeeName, Date startTime, Date endTime);

//    EmployeeDto fetchEmployeeDetails(String employeeName, Long employeeId, Date endTime);

    boolean updateSalary(SalaryDto salaryDto);

    void createReport(ReportDto reportDto);
}
