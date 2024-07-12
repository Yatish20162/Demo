package com.payroll.report.controller;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.ResponseDto;
import com.payroll.report.dto.SalaryDto;
import com.payroll.report.service.IReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class ReportController {


    private final IReportService reportService;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createReport(@RequestBody ReportDto reportDto)
    {
        boolean isCreated = reportService.createReport(reportDto);
        if (isCreated) {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    and this
                    .body(new ResponseDto("201", "Created successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto("500", "Internal Server Error"));
        }
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateSalaryDetails(@RequestBody SalaryDto salaryDto)
    {
        boolean isUpdated = reportService.updateSalary(salaryDto);
        if (isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto("204", "Updated successfully"));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto("500", "Internal Server Error"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam Long employeeId)
    {
        boolean isDeleted = reportService.deleteAccount(employeeId);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("204", "Deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("500", "Internal Server Error"));
        }
    }
}
