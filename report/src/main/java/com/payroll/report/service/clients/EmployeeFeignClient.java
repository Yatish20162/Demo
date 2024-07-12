package com.payroll.report.service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("employee")
public interface EmployeeFeignClient {

}
