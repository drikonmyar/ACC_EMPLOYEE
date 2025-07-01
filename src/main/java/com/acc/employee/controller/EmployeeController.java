package com.acc.employee.controller;

import com.acc.employee.dto.EmployeeDto;
import com.acc.employee.entity.Employee;
import com.acc.employee.service.EmployeeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/add")
    public ResponseEntity<Employee> addEmployee(@RequestBody EmployeeDto employeeDto){
        Employee e = employeeService.addEmployee(employeeDto);
        return new ResponseEntity<>(e, HttpStatus.CREATED);
    }
    
    @GetMapping("/find/{ename}")
    public ResponseEntity<Employee> findEmployee(@PathVariable String ename){
        return employeeService.findEmployee(ename);
    }
    
    @PutMapping("/update/{eid}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable String eid, @RequestBody EmployeeDto employeeDto){
        return employeeService.updateEmployee(eid, employeeDto);
    }
    
    @DeleteMapping("/delete/{eid}")
    public ResponseEntity<?> deleteEmployee(@PathVariable String eid){
        return employeeService.deleteEmployee(eid);
    }
    
    @GetMapping("/getEmployeeBySalary/{minSalary}/{maxSalary}")
    public List<Employee> getEmployeesBasedOnSalary(@PathVariable int minSalary, @PathVariable int maxSalary){
    	return employeeService.getEmployeesBasedOnSalary(minSalary, maxSalary);
    }
    
    @GetMapping("/tax/{eid}")
    public ResponseEntity<?> calculateTax(@PathVariable String eid){
        return employeeService.calculateTax(eid);
    }

}
