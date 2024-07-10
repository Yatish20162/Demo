package com.payroll.employee.service;

import com.payroll.employee.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {
    void createEmployee(); // accessible to admin

    EmployeeDto getEmployeeById(); // accessible to all

    void updateEmployee(); // accessible to admin

    void deleteEmployee(); // accessible to admin

    List<Long> getEmployeeIdsByManagerId(); // accessible to admin, manager
}
