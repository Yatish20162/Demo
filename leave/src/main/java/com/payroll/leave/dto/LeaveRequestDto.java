package com.payroll.leave.dto;

import com.payroll.leave.entity.LeaveRequest;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class LeaveRequestDto {

    public enum Status {
        APPROVED, PENDING , DECLINED;
    }

    private Long employeeId;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long remainingLeaves;

    private Long lwp;

    private Long plwp;

    @Enumerated(EnumType.STRING)
    private Status status;
    private Long managerID;

}
