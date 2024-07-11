package com.payroll.report.service.impl;

import com.payroll.report.dto.EmployeeDto;
import com.payroll.report.dto.PayrollDto;
import com.payroll.report.dto.ReportDto;
import com.payroll.report.dto.SalaryDto;
import com.payroll.report.entity.Salary;
import com.payroll.report.exception.ResourceNotFoundException;
import com.payroll.report.mapper.SalaryMapper;
import com.payroll.report.repository.SalaryRepository;
import com.payroll.report.service.IReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements IReportService {

    private final SalaryRepository salaryRepository;

    @Override
    public List<EmployeeDto> fetchAllEmployee() {
        return List.of();
    }

    @Override
    public boolean deleteAccount(String employeeName, Long employeeId) {
        return false;
    }

    @Override
    public PayrollDto fetchEmployeeReport(String employeeName, Date startTime, Date endTime) {
    return null;
    }

//    @Override
//    public EmployeeDto fetchEmployeeDetails(String employeeName, Long employeeId, LocalDate endTime) {
//        return null;
//    }

    @Override
    public boolean updateSalary(SalaryDto salaryDto) {
        boolean isUpdated = false;

        Salary salary = salaryRepository.findByEmployeeId(salaryDto.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException("salary", "employee", salaryDto.getEmployeeId())
        );

        if(salary != null){
            Salary updatedSalary = SalaryMapper.mapToSalary(salaryDto, salary);
            salaryRepository.save(updatedSalary);

            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public void createReport(ReportDto reportDto) {


    }
}
