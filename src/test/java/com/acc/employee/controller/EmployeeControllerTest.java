package com.acc.employee.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.acc.employee.dto.EmployeeDto;
import com.acc.employee.entity.Employee;
import com.acc.employee.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private Employee employee;
    private EmployeeDto employeeDto;

    @BeforeEach
    void setUp() {
        employee = new Employee();
        employee.setEid("E001");
        employee.setEname("John");
        employee.setSalary(50000);

        employeeDto = new EmployeeDto();
        employeeDto.setEname("John");
        employeeDto.setSalary(50000);
    }

    @Test
    void testAddEmployee() throws Exception {
        when(employeeService.addEmployee(any(EmployeeDto.class))).thenReturn(employee);

        mockMvc.perform(post("/employee/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.ename").value("John"))
                .andExpect(jsonPath("$.salary").value(50000));
    }

    @Test
    void testFindEmployee() throws Exception {
        when(employeeService.findEmployee("John")).thenReturn(ResponseEntity.of(Optional.of(employee)));

        mockMvc.perform(get("/employee/find/John"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ename").value("John"));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq("E001"), any(EmployeeDto.class)))
                .thenReturn(ResponseEntity.of(Optional.of(employee)));

        mockMvc.perform(put("/employee/update/E001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.eid").value("E001"))
                .andExpect(jsonPath("$.ename").value("John"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        when(employeeService.deleteEmployee("E001")).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/employee/delete/E001"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetEmployeesBasedOnSalary() throws Exception {
        Employee e1 = new Employee();
        e1.setEid("E002");
        e1.setEname("Alice");
        e1.setSalary(60000);

        when(employeeService.getEmployeesBasedOnSalary(30000, 70000)).thenReturn(List.of(employee, e1));

        mockMvc.perform(get("/employee/getEmployeeBySalary/30000/70000"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }
}