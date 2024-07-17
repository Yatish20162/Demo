package com.payroll.leave;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payroll.leave.dto.EmployeeDto;
import com.payroll.leave.dto.LeaveRequestDto;
import com.payroll.leave.dto.NotificationResponseDto;
import com.payroll.leave.entity.Leave;
import com.payroll.leave.entity.LeaveRequest;
import com.payroll.leave.repository.LeaveRepository;
import com.payroll.leave.repository.LeaveRequestRepository;
import com.payroll.leave.service.clients.EmployeeFeignClient;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
class LeaveApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @MockBean
    private EmployeeFeignClient employeeFeignClient;

    @BeforeEach
    public void setUp() {

        leaveRepository.deleteAll();
        leaveRequestRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testViewLeavesByEmployeeId() throws Exception {
        Leave leave = new Leave();
        leave.setEmployeeId(1L);
        leave.setTotalLeaves(50L);
        leave.setRemainingLeaves(40L);
        leave.setLwp(3L);
        leaveRepository.save(leave);

        mockMvc.perform(get("/leave/viewLeaves?employeeId=" + leave.getEmployeeId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalLeaves").value(50))
                .andExpect(jsonPath("$.remainingLeaves").value(40))
                .andExpect(jsonPath("$.lwp").value(3));
    }

    @Test
    @Transactional
    @Rollback
    public void testFetchLwp() throws Exception {
        // Setup test data in the database
        Leave leave = new Leave();
        leave.setEmployeeId(1L);
        leave.setTotalLeaves(50L);
        leave.setRemainingLeaves(40L);
        leave.setLwp(3L);
        leaveRepository.save(leave);

        // Setup test data for Leave Requests
        LeaveRequest leaveRequest1 = new LeaveRequest();
        leaveRequest1.setEmployeeId(1L);
        leaveRequest1.setStartDate(LocalDate.now());
        leaveRequest1.setEndDate(LocalDate.now().plusDays(2));
        leaveRequest1.setRemainingLeaves(40L);
        leaveRequest1.setLwp(2L);
        leaveRequest1.setStatus(LeaveRequest.Status.APPROVED);
        leaveRequest1.setManagerId(2L);
        leaveRequestRepository.save(leaveRequest1);

        LeaveRequest leaveRequest2 = new LeaveRequest();
        leaveRequest2.setEmployeeId(1L);
        leaveRequest2.setStartDate(LocalDate.now().plusDays(3));
        leaveRequest2.setEndDate(LocalDate.now().plusDays(5));
        leaveRequest2.setRemainingLeaves(40L);
        leaveRequest2.setLwp(1L);
        leaveRequest2.setStatus(LeaveRequest.Status.APPROVED);
        leaveRequest2.setManagerId(2L);
        leaveRequestRepository.save(leaveRequest2);

        // Perform GET request to /leave/fetchlwp endpoint
        mockMvc.perform(get("/leave/fetchlwp")
                        .param("employeeId", "1")
                        .param("startDate", LocalDate.now().toString())
                        .param("endDate", LocalDate.now().plusDays(5).toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(3L));
    }

    @Test
    @Transactional
    public void testApproveLeave_Success() throws Exception {
        Leave leave = new Leave();
        leave.setEmployeeId(1L);
        leave.setTotalLeaves(50L);
        leave.setRemainingLeaves(20L);
        leave.setLwp(3L);
        leaveRepository.save(leave);

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(1L);
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));
        leaveRequest.setRemainingLeaves(20L);
        leaveRequest.setLwp(0L);
        leaveRequest.setStatus(LeaveRequest.Status.PENDING);
        leaveRequest.setManagerId(2L);
        leaveRequestRepository.save(leaveRequest);

        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        notificationResponseDto.setEmployeeId(1L);
        notificationResponseDto.setLeaveRequestId(leaveRequest.getLeaveRequestId());
        notificationResponseDto.setStatus(NotificationResponseDto.Status.APPROVED);

        mockMvc.perform(MockMvcRequestBuilders.post("/leave/approveleave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificationResponseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("Leave Request Approved"));

        Leave updatedLeave = leaveRepository.findByEmployeeId(1L).orElseThrow();
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequest.getLeaveRequestId()).orElseThrow();
        assertEquals(15L, updatedLeave.getRemainingLeaves());
        assertEquals(15L, updatedLeaveRequest.getRemainingLeaves());

    }
    @Test
    @Transactional
    public void testApproveLeave_InsufficientLeaves() throws Exception {
        Leave leave = new Leave();
        leave.setEmployeeId(1L);
        leave.setTotalLeaves(50L);
        leave.setRemainingLeaves(2L);
        leave.setLwp(3L);
        leaveRepository.save(leave);

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(1L);
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));
        leaveRequest.setRemainingLeaves(2L);
        leaveRequest.setLwp(0L);
        leaveRequest.setStatus(LeaveRequest.Status.PENDING);
        leaveRequest.setManagerId(2L);
        leaveRequestRepository.save(leaveRequest);

        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        notificationResponseDto.setEmployeeId(1L);
        notificationResponseDto.setLeaveRequestId(leaveRequest.getLeaveRequestId());
        notificationResponseDto.setStatus(NotificationResponseDto.Status.APPROVED);

        mockMvc.perform(MockMvcRequestBuilders.post("/leave/approveleave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificationResponseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("Leave Request Approved"));


        Leave updatedLeave = leaveRepository.findByEmployeeId(1L).orElseThrow();
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequest.getLeaveRequestId()).orElseThrow();

        assertEquals(0L, updatedLeave.getRemainingLeaves());
        assertEquals(6L, updatedLeave.getLwp());
        assertEquals(3L, updatedLeaveRequest.getLwp());
        assertEquals(LeaveRequest.Status.APPROVED, updatedLeaveRequest.getStatus());
    }

    @Test
    @Transactional
    public void testApproveLeave_RequestDeclined() throws Exception {
        Leave leave = new Leave();
        leave.setEmployeeId(1L);
        leave.setTotalLeaves(50L);
        leave.setRemainingLeaves(20L);
        leave.setLwp(3L);
        leaveRepository.save(leave);

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployeeId(1L);
        leaveRequest.setStartDate(LocalDate.now());
        leaveRequest.setEndDate(LocalDate.now().plusDays(5));
        leaveRequest.setRemainingLeaves(20L);
        leaveRequest.setLwp(0L);
        leaveRequest.setStatus(LeaveRequest.Status.PENDING);
        leaveRequest.setManagerId(2L);
        leaveRequestRepository.save(leaveRequest);

        NotificationResponseDto notificationResponseDto = new NotificationResponseDto();
        notificationResponseDto.setEmployeeId(1L);
        notificationResponseDto.setLeaveRequestId(leaveRequest.getLeaveRequestId());
        notificationResponseDto.setStatus(NotificationResponseDto.Status.DECLINED);

        mockMvc.perform(MockMvcRequestBuilders.post("/leave/approveleave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificationResponseDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value("200"))
                .andExpect(jsonPath("$.message").value("Leave Request Declined"));

        // Verify Leave and LeaveRequest updates remain unchanged
        Leave updatedLeave = leaveRepository.findByEmployeeId(1L).orElseThrow();
        LeaveRequest updatedLeaveRequest = leaveRequestRepository.findByLeaveRequestId(leaveRequest.getLeaveRequestId()).orElseThrow();

        assertEquals(20L, updatedLeave.getRemainingLeaves());
        assertEquals(3L, updatedLeave.getLwp());
        assertEquals(LeaveRequest.Status.PENDING, updatedLeaveRequest.getStatus());
    }

    @Test
    @Transactional
    public void testFetchLeaveRequest() throws Exception {
        Long managerId = 2L;

        LeaveRequest leaveRequest1 = new LeaveRequest();
        leaveRequest1.setEmployeeId(1L);
        leaveRequest1.setStartDate(LocalDate.now());
        leaveRequest1.setEndDate(LocalDate.now().plusDays(3));
        leaveRequest1.setRemainingLeaves(10L);
        leaveRequest1.setLwp(0L);
        leaveRequest1.setStatus(LeaveRequest.Status.PENDING);
        leaveRequest1.setManagerId(managerId);
        leaveRequestRepository.save(leaveRequest1);

        LeaveRequest leaveRequest2 = new LeaveRequest();
        leaveRequest2.setEmployeeId(2L);
        leaveRequest2.setStartDate(LocalDate.now().plusDays(1));
        leaveRequest2.setEndDate(LocalDate.now().plusDays(4));
        leaveRequest2.setRemainingLeaves(5L);
        leaveRequest2.setLwp(0L);
        leaveRequest2.setStatus(LeaveRequest.Status.PENDING);
        leaveRequest2.setManagerId(managerId);
        leaveRequestRepository.save(leaveRequest2);


        mockMvc.perform(get("/leave/fetchleaverequest")
                        .param("managerId", String.valueOf(managerId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Check that there are 2 requests
                .andExpect(jsonPath("$[0].employeeId").value(1L))
                .andExpect(jsonPath("$[0].startDate").value(LocalDate.now().toString()))
                .andExpect(jsonPath("$[0].endDate").value(LocalDate.now().plusDays(3).toString()))
                .andExpect(jsonPath("$[1].employeeId").value(2L))
                .andExpect(jsonPath("$[1].startDate").value(LocalDate.now().plusDays(1).toString()))
                .andExpect(jsonPath("$[1].endDate").value(LocalDate.now().plusDays(4).toString()));
    }

    @Test
    @Transactional
    public void testGetEmployeeDetails() throws Exception {

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setFirstName("John");
        employeeDto.setLastName("Doe");
        employeeDto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        employeeDto.setEmail("john.doe@example.com");
        employeeDto.setPhoneNumber("1234567890");
        employeeDto.setJobTitle("Developer");
        employeeDto.setDepartment("Engineering");
        employeeDto.setManagerId(2L);

        // Mock the behavior of the Feign client
        when(employeeFeignClient.getEmployeeById(1L))
                .thenReturn(ResponseEntity.ok(employeeDto));

        // Perform the GET request
        mockMvc.perform(get("/leave/getEmployeeDetails/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));

    }

    @Test
    @Transactional
    @Rollback
    public void testLeaveRequest() throws Exception {

        Leave leave = new Leave();
        leave.setEmployeeId(1L);
        leave.setTotalLeaves(50L);
        leave.setRemainingLeaves(40L);
        leave.setLwp(3L);
        leaveRepository.save(leave);

        // Create and submit multiple leave requests
        for (int i = 0; i < 3; i++) {
            LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
            leaveRequestDto.setEmployeeId(1L);
            leaveRequestDto.setManagerId(2L);
            leaveRequestDto.setStartDate(LocalDate.now().plusDays(i * 3));
            leaveRequestDto.setEndDate(LocalDate.now().plusDays(i * 3 + 2));

            mockMvc.perform(MockMvcRequestBuilders.post("/leave/leaveRequest")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(leaveRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.statusCode").value("204"))
                    .andExpect(jsonPath("$.message").value("Leave Request Created Successfully"));
        }

        // Verify all leave requests were saved
        List<LeaveRequest> savedRequests = leaveRequestRepository.findByEmployeeIdAndStartDateBetween(
                1L, LocalDate.now(), LocalDate.now().plusDays(9)); // Cover all requests
        assertEquals(3, savedRequests.size());

        // Validate each saved request
        for (int i = 0; i < savedRequests.size(); i++) {
            LeaveRequest savedRequest = savedRequests.get(i);
            assertEquals(1L, savedRequest.getEmployeeId());
            assertEquals(2L, savedRequest.getManagerId());
            assertEquals(LocalDate.now().plusDays(i * 3), savedRequest.getStartDate());
            assertEquals(LocalDate.now().plusDays(i * 3 + 2), savedRequest.getEndDate());


//        Leave leave = new Leave();
//        leave.setEmployeeId(1L);
//        leave.setTotalLeaves(50L);
//        leave.setRemainingLeaves(40L);
//        leave.setLwp(3L);
//        leaveRepository.save(leave);
//
//
//        LeaveRequestDto leaveRequestDto = new LeaveRequestDto();
//        leaveRequestDto.setEmployeeId(1L);
//        leaveRequestDto.setManagerId(2L);
//        leaveRequestDto.setStartDate(LocalDate.now());
//        leaveRequestDto.setEndDate(LocalDate.now().plusDays(2));
//
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/leave/leaveRequest")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(leaveRequestDto)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.statusCode").value("204"))
//                .andExpect(jsonPath("$.message").value("Leave Request Created Successfully"));
//
//
//        List<LeaveRequest> savedRequests = leaveRequestRepository.findByEmployeeIdAndStartDateBetween(
//                1L, leaveRequestDto.getStartDate(), leaveRequestDto.getEndDate());
//        assertEquals(1, savedRequests.size());
//
//        LeaveRequest savedRequest = savedRequests.get(0);
//        assertEquals(leaveRequestDto.getEmployeeId(), savedRequest.getEmployeeId());
//        assertEquals(leaveRequestDto.getManagerId(), savedRequest.getManagerId());
//        assertEquals(leaveRequestDto.getStartDate(), savedRequest.getStartDate());
//        assertEquals(leaveRequestDto.getEndDate(), savedRequest.getEndDate());
//
        }
    }
}

