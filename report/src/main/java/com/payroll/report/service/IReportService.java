package com.payroll.report.service;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.PayrollDto;
import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.SalaryDto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface IReportService {

    boolean deleteAccount(Long employeeId);

    boolean updateSalary(SalaryDto salaryDto);

    boolean createReport(ReportDto reportDto);
}
