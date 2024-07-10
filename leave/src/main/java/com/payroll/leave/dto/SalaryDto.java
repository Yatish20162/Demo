package com.payroll.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDto {
    private Long baseSalary;
    private Long hra;
    private Long benefits;
    private Long employeeId;
}
