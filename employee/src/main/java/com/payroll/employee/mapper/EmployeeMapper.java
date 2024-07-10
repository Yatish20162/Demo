package com.payroll.employee.mapper;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDto mapToEmployeeDto(Employee employee, EmployeeDto employeeDto){
        employeeDto.setFirstName(employee.getFirstName());
        employeeDto.setLastName(employee.getLastName());
        employeeDto.setDateOfBirth(employee.getDateOfBirth());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setPhoneNumber(employee.getPhoneNumber());
        employeeDto.setJobTitle(employee.getJobTitle());
        employeeDto.setDepartment(employee.getDepartment());
        employeeDto.setManagerId(employee.getManagerId());

        return employeeDto;
    }

    public static Employee mapToCustomer(EmployeeDto  employeeDto , Employee employee){
        employee.setFirstName(employeeDto.getFirstName());
        employee.setLastName(employeeDto.getLastName());
        employee.setDateOfBirth(employeeDto.getDateOfBirth());
        employee.setEmail(employeeDto.getEmail());
        employee.setPhoneNumber(employeeDto.getPhoneNumber());
        employee.setJobTitle(employeeDto.getJobTitle());
        employee.setManagerId(employeeDto.getManagerId());
        employee.setDepartment(employeeDto.getDepartment());

        return employee;
    }

}
