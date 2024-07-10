package com.payroll.employee.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/employee")
@AllArgsConstructor
public class EmployeeController {
    @GetMapping("/ping")
    public ResponseEntity<String> Ping() {
        return ResponseEntity.status(HttpStatus.OK).body("Server is Up!");
    }
}