package com.payroll.leave.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

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
    private LocalDateTime localDateTime;
}
