package com.payroll.leave.repository;

import com.payroll.leave.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Payroll,Long> {
}
