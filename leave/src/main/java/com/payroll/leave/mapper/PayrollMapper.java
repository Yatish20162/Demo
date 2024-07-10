package com.payroll.leave.mapper;

import com.payroll.leave.dto.PayrollDto;
import com.payroll.leave.entity.Payroll;

public class PayrollMapper {
    public static Payroll mapToPayroll(Payroll payroll, PayrollDto payrollDto)
    {
        payroll.setHra(payrollDto.getHra());
        payroll.setBenefits(payrollDto.getBenefits());
        payroll.setDeduction(payrollDto.getDeduction());
        payroll.setBaseSalary(payrollDto.getBaseSalary());
        payroll.setNetSalary(payrollDto.getNetSalary());
        return payroll;
    }
    public static PayrollDto mapToPayrollDto(PayrollDto payrollDto, Payroll payroll)
    {
        payrollDto.setHra(payroll.getHra());
        payrollDto.setBenefits(payroll.getBenefits());
        payrollDto.setDeduction(payroll.getDeduction());
        payrollDto.setBaseSalary(payroll.getBaseSalary());
        payrollDto.setNetSalary(payroll.getNetSalary());
        return payrollDto;
    }
}
