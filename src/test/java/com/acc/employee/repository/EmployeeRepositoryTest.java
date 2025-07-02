package com.acc.employee.repository;

import com.acc.employee.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeEach
    void setup() {
        Employee e1 = new Employee();
//        e1.setEid("E001");
        e1.setEname("Alice");
        e1.setSalary(40000);

        Employee e2 = new Employee();
//        e2.setEid("E002");
        e2.setEname("Bob");
        e2.setSalary(60000);

        Employee e3 = new Employee();
//        e3.setEid("E003");
        e3.setEname("Charlie");
        e3.setSalary(100000);

        employeeRepository.saveAll(List.of(e1, e2, e3));
    }

    @Test
    void testFindByEname() {
        Optional<Employee> result = employeeRepository.findByEname("Bob");
        assertTrue(result.isPresent());
//        assertEquals("E002", result.get().getEid());
    }

//    @Test
//    void testFindByEid() {
//        Optional<Employee> result = employeeRepository.findByEid("E003");
//        assertTrue(result.isPresent());
//        assertEquals("Charlie", result.get().getEname());
//    }

    @Test
    void testGetEmployeesBasedOnSalary() {
        List<Employee> result = employeeRepository.getEmployeesBasedOnSalary(50000, 100000);
        assertEquals(2, result.size());
        assertEquals("Bob", result.get(0).getEname()); // should be ordered by ename
        assertEquals("Charlie", result.get(1).getEname());
    }

    @Test
    void testGetEmployeesBasedOnSalary_Empty() {
        List<Employee> result = employeeRepository.getEmployeesBasedOnSalary(110000, 150000);
        assertTrue(result.isEmpty());
    }
}