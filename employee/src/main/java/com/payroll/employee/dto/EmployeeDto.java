package com.payroll.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String firstName;
    private String lastName;
    private LocalDateTime dateOfBirth;
    private String email;
    private String phoneNumber;
    private String jobTitle;
    private String department;
    private Long managerId;
    private EmployeeRoleDto employeeRoleDto;
}
