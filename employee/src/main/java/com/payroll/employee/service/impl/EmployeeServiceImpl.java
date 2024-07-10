package com.payroll.employee.service.impl;

import com.payroll.employee.dto.EmployeeDto;
import com.payroll.employee.entity.Employee;
import com.payroll.employee.entity.EmployeeRole;
import com.payroll.employee.enums.Role;
import com.payroll.employee.exception.EmployeeAlreadyExistException;
import com.payroll.employee.exception.ResourceNotFoundException;
import com.payroll.employee.mapper.EmployeeMapper;
import com.payroll.employee.repository.EmployeeRepository;
import com.payroll.employee.repository.EmployeeRoleRepository;
import com.payroll.employee.service.IEmployeeService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements IEmployeeService {
        private final EmployeeRepository employeeRepository;
        private final EmployeeRoleRepository employeeRoleRepository;

        @Override
        public void createEmployee(EmployeeDto employeeDto){
                Optional<Employee> foundEmployee = employeeRepository.findByPhoneNumber(employeeDto.getPhoneNumber());

                if (foundEmployee.isPresent()) {
                        throw new EmployeeAlreadyExistException("Employee already exists for this mobile number - " + employeeDto.getPhoneNumber());
                }
                Employee newEmployee = EmployeeMapper.mapToEmployee(employeeDto, new Employee());
                employeeRepository.save(newEmployee);

                for(Role role: employeeDto.getEmployeeRoles()){
                        employeeRoleRepository.save(new EmployeeRole(newEmployee.getEmployeeId(), role));
                }
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
