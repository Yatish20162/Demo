package com.payroll.report.service;

import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.SalaryDto;


public interface IReportService {

    boolean deleteAccount(Long employeeId);

    boolean updateSalary(SalaryDto salaryDto);

    boolean createReport(ReportDto reportDto);
}
