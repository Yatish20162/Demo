package com.payroll.employee.service;

import com.payroll.employee.dto.EmployeeDto;

import java.util.List;

public interface IEmployeeService {
    void createEmployee(EmployeeDto employeeDto); // accessible to admin

    EmployeeDto getEmployeeById(Long employeeId); // accessible to all

    Boolean updateEmployee(Long employeeId, EmployeeDto employeeDto); // accessible to admin

    Boolean deleteEmployee(Long employeeId); // accessible to admin

    List<EmployeeDto> getAllEmployeeData();

    List<EmployeeDto> getEmployeesByManagerId(Long managerId); // accessible to admin, manager
}
