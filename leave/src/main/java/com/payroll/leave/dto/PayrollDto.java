package com.payroll.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PayrollDto {
    private Long baseSalary;
    private Long hra;
    private Long benefits;
    private Long netSalary;
    private Long deduction;
}
