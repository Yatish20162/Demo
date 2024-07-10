package com.payroll.report.dto;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportDto {
    private String employeeName;
    private Long employeeId;
    private Date startTime;
    private Date endTime;
}
