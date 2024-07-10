package com.payroll.leave.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="salary")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Salary {
    @Id
    private Long salaryId;
    private Long baseSalary;
    private Long hra;
    private Long benefits;
    private Long employeeId;


}
