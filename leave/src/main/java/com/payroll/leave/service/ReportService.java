package com.payroll.leave.service;

import com.payroll.leave.dto.PayrollDto;

import java.util.Date;

public interface ReportService {


    PayrollDto fetchEmployeeReport(String employeeName, Date startTime, Date endTime);

}
