package com.payroll.leave.mapper;

import com.payroll.leave.dto.SalaryDto;
import com.payroll.leave.entity.Salary;

public class SalaryMapper {
    public static Salary mapToSalary(Salary salary, SalaryDto salaryDto)
    {
        salary.setBaseSalary(salaryDto.getBaseSalary());
        salary.setBenefits(salaryDto.getBenefits());
        salary.setHra(salaryDto.getHra());
        return salary;

    }
    public static SalaryDto mapToSalaryDto(SalaryDto salaryDto, Salary salary)
    {
        salaryDto.setBaseSalary(salaryDto.getBaseSalary());
        salaryDto.setBenefits(salaryDto.getBenefits());
        salaryDto.setHra(salaryDto.getHra());
        return salaryDto;

    }
}
