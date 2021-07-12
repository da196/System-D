package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.employeesalaryResponse;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;

@Service
public interface HrmsemployeesalaryService {

	public ResponseEntity<Hrmsemployeesalary> addEmployeesalary(Hrmsemployeesalary hrmsemployeesalary);

	public ResponseEntity<Optional<Hrmsemployeesalary>> viewEmployeesalary(int id);

	public ResponseEntity<employeesalaryResponse> getEmployeesalaryById(int id);

	public ResponseEntity<employeesalaryResponse> getEmployeesalaryByEmpId(int empid);

	public ResponseEntity<List<employeesalaryResponse>> getAllemployeesalary();

	public ResponseEntity<Hrmsemployeesalary> updateEmployeesalary(Hrmsemployeesalary hrmsemployeesalary, int id);

	public ResponseEntity<Hrmsemployeesalary> approveEmployeesalary(Hrmsemployeesalary hrmsemployeesalary, int id);

	public ResponseEntity<?> deleteHrmsemployeesalary(int id);

	public ResponseEntity<List<Hrmsemployeesalary>> listHrmsemployeesalary();

}
