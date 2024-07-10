package com.payroll.employee.service.impl;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.employee.repository.EmployeeRoleRepository;
import com.payroll.employee.service.IEmployeeService;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@NoArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
        EmployeeRepository employeeRepository;
        EmployeeRoleRepository employeeRoleRepository;

        @Override
        public void createEmployee(EmployeeDto employeeDto){
                
                return;
        }

        @Override
        public EmployeeDto getEmployeeById(Long employeeId) {
                return null;
        }

        @Override
        public Boolean updateEmployee(Long employeeId, EmployeeDto employeeDto) {
                return null;
        }

        @Override
        public Boolean deleteEmployee(Long employeeId) {
                return null;
        }

        @Override
        public List<EmployeeDto> getEmployeesByManagerId(Long managerId) {
                return List.of();
        }

//        List<Employee> getEmployeesByManagerId(Long managerId){
//            employeeRepository.findByManagerId(managerId).stream().map(()->{
//
//            })
//        }
}
