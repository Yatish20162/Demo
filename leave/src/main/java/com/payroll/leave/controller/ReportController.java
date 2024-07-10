package com.payroll.leave.controller;

import com.payroll.leave.dto.PayrollDto;
import com.payroll.leave.entity.Payroll;
import com.payroll.leave.service.ReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {

    private  final ReportService reportService;
    @GetMapping("/")
    public ResponseEntity<PayrollDto>fetchEmployeeReport(@RequestParam String employeeName, @RequestParam Date startTime, @RequestParam Date endTime)
    {
        PayrollDto payrollDto =reportService.fetchEmployeeReport(employeeName,startTime,endTime);
        return ResponseEntity.status(HttpStatus.OK).body(payrollDto);
    }

}
