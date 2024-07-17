package com.payroll.report.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.payroll.report.dto.*;
import com.payroll.report.entity.Payroll;
import com.payroll.report.entity.Salary;
import com.payroll.report.exception.ResourceNotFoundException;
import com.payroll.report.mapper.SalaryMapper;
import com.payroll.report.repository.PayrollRepository;
import com.payroll.report.repository.SalaryRepository;
import com.payroll.report.service.IReportService;
import com.payroll.report.service.clients.LeaveFeignClient;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public class ReportServiceImpl implements IReportService {

    @Autowired
    private final SalaryRepository salaryRepository;
    @Autowired
    private final PayrollRepository payrollRepository;
    @Autowired
    private final LeaveFeignClient leaveFeignClient;

    @Value("${api.key}")
    private String  apiKey;


    @Override
    public boolean deleteAccount(Long employeeId) {

        boolean isDeleted = false;

        Salary salary = salaryRepository.findByEmployeeId(employeeId).orElseThrow(
                () -> new ResourceNotFoundException("Salary", "employeeId", employeeId)
        );
        if (salary != null) {
            salaryRepository.delete(salary);
            isDeleted = true;
        }

        return isDeleted;
    }

    @Override
    public boolean updateSalary(SalaryDto salaryDto) {
        boolean isUpdated = false;

        Salary salary = salaryRepository.findByEmployeeId(salaryDto.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException("salary", "employee", salaryDto.getEmployeeId())
        );

        if(salary != null){
            Salary updatedSalary = SalaryMapper.mapToSalary(salaryDto, salary);
            salaryRepository.save(updatedSalary);

            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean createReport(ReportDto reportDto) {
        boolean isCreated = false;
        System.out.println("Creating report for: " + reportDto.getEmployeeName() +
                ", Start Date: " + reportDto.getStartDate() +
                ", End Date: " + reportDto.getEndDate());

        Salary salary = salaryRepository.findByEmployeeId(reportDto.getEmployeeId()).orElseThrow(
                () -> new ResourceNotFoundException("salary", "employee", reportDto.getEmployeeId())

        );

        if(salary != null){
            Long lwp = leaveFeignClient.fetchLwp(reportDto.getEmployeeId(), reportDto.getStartDate(), reportDto.getEndDate()).getBody();

            Long deductions = calculateDeductions(lwp);

            Long netSalary = salary.getBaseSalary() + salary.getHra() + salary.getBenefits() - deductions;

            Payroll payroll = new Payroll();
            payroll.setEmployeeId(reportDto.getEmployeeId());
            payroll.setBaseSalary(salary.getBaseSalary());
            payroll.setHra(salary.getHra());
            payroll.setBenefits(salary.getBenefits());
            payroll.setDeduction(deductions);
            payroll.setNetSalary(netSalary);

            payrollRepository.save(payroll);

            Map<String, Object> payrollData = new HashMap<>();
            payrollData.put("Employee ID", reportDto.getEmployeeId());
            payrollData.put("Base Salary", salary.getBaseSalary());
            payrollData.put("HRA", salary.getHra());
            payrollData.put("Benefits", salary.getBenefits());
            payrollData.put("Deductions", deductions);
            payrollData.put("Net Salary", netSalary);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonPayload = objectMapper.writeValueAsString(payrollData);

                File jsonFile = new ClassPathResource("data.json").getFile();
                JsonNode node = objectMapper.readTree(jsonFile);
//                File logoFile = new ClassPathResource("logo.json").getFile();
//                JsonNode nodelogo = objectMapper.readTree(logoFile);
                ObjectNode root = (ObjectNode) node;
                ObjectNode documentSettings=(ObjectNode) root.get("documentSettings");

                ObjectNode footer= (ObjectNode) documentSettings.get("footer");
                ObjectNode header = (ObjectNode) documentSettings.get("header");
                ObjectNode firstColumnDetails = (ObjectNode) root.get("documentContent").get("firstColumnDetails");
                ObjectNode secondColumnDetails = (ObjectNode) root.get("documentContent").get("secondColumnDetails");

                documentSettings.put("documentName","invoice");
                documentSettings.put("logo","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");
                footer.put("rightContent", "\\medskip {\\color{Primary}\\textbf{UKG Services (Pty) Ltd}} \\\\ \\medskip \\color{Primary}Address: \\\\ \\color{Secondary} OKAYA Tower-4 Sector 62, Noida, 201301, India \\\\ \\color{Primary}Telephone: \\\\ \\color{Secondary} +27 82 123 4567 \\\\ \\color{Primary}Email: \\\\ \\color{Secondary} ukg@gmail.com ");
                header.put("rightContent","\\color{Secondary} \\Huge INVOICE #"+reportDto.getEmployeeId()+ " \\\\ \\smallskip \\large 19 July 2024");
                List<String> leftentries = new ArrayList<String>();
                leftentries.add("UKG");
                leftentries.add("OKAYA Tower-4 Sector 62, Noida, 201301, India");
                leftentries.add("Payroll Service");
                leftentries.add("Jaskaran");
                leftentries.add("8168660330");
                leftentries.add("ukg@test.com");
                ArrayNode array = objectMapper.valueToTree(leftentries);
                List<String> rightEntriesHeading = new ArrayList<String>();
                List<String> rightEntries = new ArrayList<String>();
                rightEntriesHeading.add("\\color{Primary} Payable To");
                rightEntriesHeading.add( "\\color{Secondary} Bank");
                rightEntriesHeading.add( "\\color{Secondary} Account Name");
                rightEntriesHeading.add("\\color{Secondary} Account #");
                rightEntriesHeading.add("\\color{Primary} Contact");
                rightEntriesHeading.add( "\\color{Secondary} Tel");

                ArrayNode rightHeadings = objectMapper.valueToTree(rightEntriesHeading);
                rightEntries.add(reportDto.getEmployeeName());
                rightEntries.add("New Bank");
                rightEntries.add("Company");
                rightEntries.add("12 345 678 9");
                rightEntries.add(reportDto.getEmployeeName());
                rightEntries.add("1234567890");

                ArrayNode rightArray = objectMapper.valueToTree(rightEntries);
                System.out.println(rightArray);
                firstColumnDetails.replace("values",array);
                secondColumnDetails.replace("headings",rightHeadings);
                secondColumnDetails.replace("values",rightArray);
////                firstColumnDetails.put("values",)
//                System.out.println(node.toPrettyString());
//                // Send JSON payload to the external API
                RestTemplate restTemplate = new RestTemplate();
                String apiUrl = "https://api.advicement.io/v1/templates/pub-simple-invoice-v1/compile";
////
//////
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
////
                headers.set("Adv-Security-Token", apiKey);
////
                HttpEntity<String> request = new HttpEntity<>(node.toString(), headers);
////
                ResponseEntity<JsonNode> response = restTemplate.postForEntity(apiUrl, request, JsonNode.class);
                System.out.println(response.getBody());
////
//                if (response.getStatusCode() == HttpStatus.OK) {
//                    return true;
//                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            isCreated = true;

        }
        return isCreated;
    }

    @Override
    public SalaryDto getSalary(Long employeeId) {
        Salary salary= salaryRepository.findByEmployeeId(employeeId).orElseThrow( () -> new ResourceNotFoundException("salary", "employee", employeeId));
      //  if(salary!=null)
        {
            SalaryDto salaryDto=SalaryMapper.mapToSalaryDto(salary,new SalaryDto());
            return salaryDto;

        }
//      return null;

    }

    private Long calculateDeductions(Long lwp) {
        return lwp * 2000;
    }


}
