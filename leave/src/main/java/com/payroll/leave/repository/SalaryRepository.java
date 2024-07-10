package com.payroll.leave.repository;

import com.payroll.leave.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaryRepository extends JpaRepository<Salary,Long> {

}
