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
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
                Employee foundEmployee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "employeeId", employeeId.toString())
                );
                EmployeeDto foundEmployeeDto = EmployeeMapper.mapToEmployeeDto(foundEmployee, new EmployeeDto());

                List<EmployeeRole> employeeRoleList= employeeRoleRepository.findAllByEmployeeId(employeeId).orElseThrow(
                        ()-> new ResourceNotFoundException("Employee", "EmployeeId", employeeId.toString())
                );

                List<Role> roleList = employeeRoleList.stream().map(EmployeeRole::getRole).toList();
                foundEmployeeDto.setEmployeeRoles(roleList);

                return foundEmployeeDto;
        }

        @Override
        @Modifying
        @Transactional
        public Boolean updateEmployee(Long employeeId, EmployeeDto employeeDto) {

                Employee foundEmployee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "employeeId", employeeId.toString())
                );



               Employee updatedemployee=EmployeeMapper.mapToEmployee(employeeDto, new Employee());
               updatedemployee.setEmployeeId(employeeId);
               employeeRepository.save(updatedemployee);

                employeeRoleRepository.deleteAllByEmployeeId(employeeId);

                for(Role role: employeeDto.getEmployeeRoles()){
                        employeeRoleRepository.save(new EmployeeRole(updatedemployee.getEmployeeId(), role));
                }

                return Boolean.TRUE;
        }





        @Override
        @Transactional
        public Boolean deleteEmployee(Long employeeId) {
                boolean isDeleted = false;


                Employee employee = employeeRepository.findByEmployeeId(employeeId).orElseThrow(
                        () -> new ResourceNotFoundException("Employee", "EmployeeId", employeeId.toString())
                );

                if (employee != null) {
                        employeeRoleRepository.deleteAllByEmployeeId(employeeId);
                        employeeRepository.deleteById(employeeId);

                        isDeleted = true;
                }
                return isDeleted;
        }


        @Override
        public List<EmployeeDto> getEmployeesByManagerId(Long managerId) {
                Optional<List<Employee>> employeeList = employeeRepository.findAllByManagerId(managerId);
                return employeeList.map(employees -> employees.stream().map(
                    (employee) -> getEmployeeById(employee.getEmployeeId())
            ).toList()).orElseGet(ArrayList::new);
        }

        @Override
        public List<EmployeeDto> getAllEmployeeData(){
                List<Employee>employeeList = employeeRepository.findAll();
                return employeeList.stream().map(
                        (employee) -> getEmployeeById(employee.getEmployeeId()))
                        .toList();
        }
}
