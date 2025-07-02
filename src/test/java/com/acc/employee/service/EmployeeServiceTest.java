package com.acc.employee.service;

import com.acc.employee.dto.EmployeeDto;
import com.acc.employee.entity.Employee;
import com.acc.employee.repository.EmployeeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddEmployee() {
        EmployeeDto dto = new EmployeeDto();
        dto.setEname("John");
        dto.setSalary(50000);

        Employee savedEmployee = new Employee();
        savedEmployee.setEname("John");
        savedEmployee.setSalary(50000);

        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        Employee result = employeeService.addEmployee(dto);

        assertEquals("John", result.getEname());
        assertEquals(50000, result.getSalary());
    }

    @Test
    void testFindEmployee_Found() {
        Employee employee = new Employee();
        employee.setEname("Alice");

        when(employeeRepository.findByEname("Alice")).thenReturn(Optional.of(employee));

        ResponseEntity<Employee> response = employeeService.findEmployee("Alice");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Alice", response.getBody().getEname());
    }

    @Test
    void testFindEmployee_NotFound() {
        when(employeeRepository.findByEname("Unknown")).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeService.findEmployee("Unknown");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testUpdateEmployee_Found() {
        EmployeeDto dto = new EmployeeDto();
        dto.setEname("Updated");
        dto.setSalary(60000);

        Employee existing = new Employee();
        existing.setEid("E123");

        when(employeeRepository.findByEid("E123")).thenReturn(Optional.of(existing));
        when(employeeRepository.save(any(Employee.class))).thenAnswer(i -> i.getArgument(0));

        ResponseEntity<Employee> response = employeeService.updateEmployee("E123", dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getEname());
    }

    @Test
    void testUpdateEmployee_NotFound() {
        when(employeeRepository.findByEid("E123")).thenReturn(Optional.empty());

        ResponseEntity<Employee> response = employeeService.updateEmployee("E123", new EmployeeDto());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testDeleteEmployee_Found() {
        Employee employee = new Employee();
        employee.setEid("E001");

        when(employeeRepository.findByEid("E001")).thenReturn(Optional.of(employee));

        ResponseEntity<?> response = employeeService.deleteEmployee("E001");

        verify(employeeRepository).delete(employee);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.findByEid("E001")).thenReturn(Optional.empty());

        ResponseEntity<?> response = employeeService.deleteEmployee("E001");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void testGetEmployeesBasedOnSalary() {
        List<Employee> list = List.of(new Employee(), new Employee());
        when(employeeRepository.getEmployeesBasedOnSalary(30000, 80000)).thenReturn(list);

        List<Employee> result = employeeService.getEmployeesBasedOnSalary(30000, 80000);

        assertEquals(2, result.size());
    }

    @Test
    void testCalculateTax() {
        Employee emp = new Employee();
        emp.setEid("E001");
        emp.setSalary(1800000);

        when(employeeRepository.findByEid("E001")).thenReturn(Optional.of(emp));

        ResponseEntity<?> response = employeeService.calculateTax("E001");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue((double) response.getBody() > 0);
    }

    @Test
    void testCalculateTax_NotFound() {
        when(employeeRepository.findByEid("E001")).thenReturn(Optional.empty());

        ResponseEntity<?> response = employeeService.calculateTax("E001");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
