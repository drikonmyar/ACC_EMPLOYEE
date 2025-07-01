package com.acc.employee.repository;

import com.acc.employee.entity.Employee;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	
	Optional<Employee> findByEname(String ename);
	Optional<Employee> findByEid(String eid);
	
	@Query(value = "SELECT * FROM employee WHERE salary BETWEEN :minSalary AND :maxSalary ORDER BY ename", nativeQuery = true)
	List<Employee> getEmployeesBasedOnSalary(@Param("minSalary") int minSalary, @Param("maxSalary") int maxSalary);
}
