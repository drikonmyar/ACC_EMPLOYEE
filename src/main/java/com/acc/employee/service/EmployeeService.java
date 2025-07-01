package com.acc.employee.service;

import com.acc.employee.dto.EmployeeDto;
import com.acc.employee.entity.Employee;
import com.acc.employee.repository.EmployeeRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee addEmployee(EmployeeDto employeeDto){
        Employee e = new Employee();
        e.setEname(employeeDto.getEname());
        e.setSalary(employeeDto.getSalary());
        return employeeRepository.save(e);
    }
    
    public ResponseEntity<Employee> findEmployee(String ename) {
    	Optional<Employee> optionalEmployee = employeeRepository.findByEname(ename);
    	if(!optionalEmployee.isEmpty()) {
    		return new ResponseEntity<>(optionalEmployee.get(), HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    public ResponseEntity<Employee> updateEmployee(String eid, EmployeeDto employeeDto){
    	Optional<Employee> optionalEmployee = employeeRepository.findByEid(eid);
    	if(!optionalEmployee.isEmpty()) {
    		Employee e = new Employee();
    		e.setEid(eid);
            e.setEname(employeeDto.getEname());
            e.setSalary(employeeDto.getSalary());
            return new ResponseEntity<>(employeeRepository.save(e), HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    public ResponseEntity<?> deleteEmployee(String eid){
    	Optional<Employee> optionalEmployee = employeeRepository.findByEid(eid);
    	if(!optionalEmployee.isEmpty()) {
    		employeeRepository.delete(optionalEmployee.get());
    		return new ResponseEntity<>(HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    public List<Employee> getEmployeesBasedOnSalary(int minSalary, int maxSalary){
    	return employeeRepository.getEmployeesBasedOnSalary(minSalary, maxSalary);
    }
    
    public ResponseEntity<?> calculateTax(String eid) {
    	Optional<Employee> optionalEmployee = employeeRepository.findByEid(eid);
    	if(!optionalEmployee.isEmpty()) {
    		int annualIncome = optionalEmployee.get().getSalary();
    		double tax = 0;

            if (annualIncome > 1500000) {
                tax += (annualIncome - 1500000) * 0.30;
            }
            if (annualIncome > 1200000) {
                tax += (annualIncome - 1200000) * 0.20;
            }
            if (annualIncome > 900000) {
                tax += (annualIncome - 900000) * 0.15;
            }
            if (annualIncome > 600000) {
                tax += (annualIncome - 600000) * 0.10;
            }
            if (annualIncome > 300000) {
                tax += (annualIncome - 300000) * 0.05;
            }

            return new ResponseEntity<>(tax, HttpStatus.OK);
    	}
    	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
