package com.payroll.employee.controller;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.dto.ResponseDto;
import com.payroll.employee.service.IEmployeeService;
import com.payroll.employee.service.impl.EmployeeServiceImpl;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeServiceImpl employeeService;
    private  final IEmployeeService iEmployeeService;

    @GetMapping("/ping")
    public ResponseEntity<String> Ping() {
        return ResponseEntity.status(HttpStatus.OK).body("Server is Up!");
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createEmployee(@RequestBody EmployeeDto employeeDto){
        employeeService.createEmployee(employeeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto("201", "Created Successfully"));
    }

@DeleteMapping("/delete/{employeeId}")
public  ResponseEntity<ResponseDto> deleteEmployee(@PathVariable Long employeeId) {
    boolean isDeleted = iEmployeeService.deleteEmployee(employeeId);
    if (isDeleted) {
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("204", "Deleted Successfully"));
    } else {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("500", "Internal Server Error"));
    }

}




    @GetMapping("/{employeeId}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@PathVariable Long employeeId){ // Not sure if Long will work or have to use String
        EmployeeDto employeeDto = employeeService.getEmployeeById(employeeId);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDto);
    }

    @GetMapping("manager/{managerId}")
    public ResponseEntity<List<EmployeeDto>> getEmployeeByManagerId(@PathVariable Long managerId){ // Not sure if Long will work or have to use String
        List<EmployeeDto> employeeDtoList = employeeService.getEmployeesByManagerId(managerId);
        return ResponseEntity.status(HttpStatus.OK).body(employeeDtoList);
    }
}