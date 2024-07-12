package com.payroll.report.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("leave")
public interface LeaveFeignClient {

    @GetMapping("/api/fetchlwp")
    public ResponseEntity<Long> fetchLwp(@RequestParam Long employeeId , @RequestParam String startDate , @RequestParam String endDate);
}
