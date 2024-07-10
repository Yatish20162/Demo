package com.payroll.employee.service;

import com.payroll.employee.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {
    void createEmployee(); // accessible to admin

    EmployeeDto getEmployeeById(); // accessible to all

    Boolean updateEmployee(); // accessible to admin

    Boolean deleteEmployee(); // accessible to admin

    List<Long> getEmployeeIdsByManagerId(); // accessible to admin, manager
}
