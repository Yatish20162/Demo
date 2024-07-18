package com.payroll.report.service;

import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.SalaryDto;
import org.springframework.http.ResponseEntity;


public interface IReportService {

    boolean deleteAccount(Long employeeId);

    boolean updateSalary(SalaryDto salaryDto);

    boolean createReport(ReportDto reportDto);

    SalaryDto getSalary(Long employeeId);

}
