package com.payroll.report.controller;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.ResponseDto;
import com.payroll.report.service.ReportService;
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


    private final ReportService reportService;
//    @GetMapping("/")
//    public ResponseEntity<PayrollDto>fetchEmployeeReport(@RequestParam String employeeName, @RequestParam Date startTime, @RequestParam Date endTime)
//    {
//        PayrollDto payrollDto =reportService.fetchEmployeeReport(employeeName,startTime,endTime);
//        return ResponseEntity.status(HttpStatus.OK).body(payrollDto);
//    }

    @PostMapping("/create")

    public ResponseEntity<ResponseDto> createReport(@RequestBody ReportDto reportDto)
    {
        reportService.createReport(reportDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto("201", "Created Successfully"));
    }

    @GetMapping("/fetch")
    public ResponseEntity<EmployeeDto> fetchEmployee(@RequestParam String employeeName, @RequestParam Long employeeId, @RequestParam Date endTime)
    {
        EmployeeDto employeeDto = reportService.fetchEmployeeDetails(employeeName, employeeId, endTime);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(employeeDto);
    }

    @GetMapping("/fetchall")
    public ResponseEntity<List<EmployeeDto>> getallEmployee(){

        EmployeeDto customerDto=new EmployeeDto();
        List<EmployeeDto>a = reportService.fetchAllEmployee();

        System.out.println(a);
        return ResponseEntity.status(HttpStatus.OK).body(a);

    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateDetails(@RequestParam String employeeName, @RequestParam Long employeeId, @RequestParam Date endTime)
    {
        boolean isUpdated = reportService.updateAccount(employeeName, employeeId, endTime);
        if (isUpdated) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("204", "Updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("500", "Internal Server Error"));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam String employeeName, @RequestParam Long employeeId, @RequestParam Date endTime)
    {
        boolean isDeleted = ReportService.deleteAccount(employeeName, employeeId, endTime);
        if (isDeleted) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("204", "Deleted successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("500", "Internal Server Error"));
        }
    }


}
