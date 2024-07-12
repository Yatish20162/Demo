package com.payroll.report.repository;

import com.payroll.report.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Payroll,Long> {
}
