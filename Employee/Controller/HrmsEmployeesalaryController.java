package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.employeesalaryResponse;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Service.HrmsemployeesalaryService;

@RestController
@RequestMapping("/v1/employeesalary")
public class HrmsEmployeesalaryController {

	@Autowired
	private HrmsemployeesalaryService hrmsemployeesalaryService;

	@PostMapping(value = "/addEmployeesalary")
	public ResponseEntity<Hrmsemployeesalary> addEmployeesalary(@RequestBody Hrmsemployeesalary hrmsemployeesalary) {
		return hrmsemployeesalaryService.addEmployeesalary(hrmsemployeesalary);
	}

	@GetMapping(value = "/getEmployeesalary/{id}")
	public ResponseEntity<Optional<Hrmsemployeesalary>> getEmployeesalary(@PathVariable("id") int id) {

		return hrmsemployeesalaryService.viewEmployeesalary(id);

	}

	@PutMapping(value = "/updateEmployeesalary/{id}")
	public ResponseEntity<Hrmsemployeesalary> updateEmployeesalary(@RequestBody Hrmsemployeesalary hrmsemployeesalary,
			@PathVariable("id") int id) {
		return hrmsemployeesalaryService.updateEmployeesalary(hrmsemployeesalary, id);

	}

	@PutMapping(value = "/approveEmployeesalary/{id}")
	public ResponseEntity<Hrmsemployeesalary> approveEmployeesalary(@RequestBody Hrmsemployeesalary hrmsemployeesalary,
			@PathVariable("id") int id) {

		return hrmsemployeesalaryService.approveEmployeesalary(hrmsemployeesalary, id);

	}

	@DeleteMapping(value = "/deleteEmployeesalary/{id}")
	public ResponseEntity<?> deleteEmployeesalary(@PathVariable("id") int id) {
		return hrmsemployeesalaryService.deleteHrmsemployeesalary(id);
	}

	@GetMapping(value = "/getAllEmployeesalary")
	public ResponseEntity<List<Hrmsemployeesalary>> listEmployeesalary() {

		return hrmsemployeesalaryService.listHrmsemployeesalary();

	}

	@GetMapping(value = "/getEmployeesalaryByIdV2/{id}")
	public ResponseEntity<employeesalaryResponse> getEmployeesalaryById(@PathVariable("id") int id) {
		return hrmsemployeesalaryService.getEmployeesalaryById(id);

	}

	@GetMapping(value = "/getAllemployeesalaryv2")
	public ResponseEntity<List<employeesalaryResponse>> getAllemployeesalary() {
		return hrmsemployeesalaryService.getAllemployeesalary();

	}

	@GetMapping(value = "/getEmployeesalaryByEmpId/{empid}")
	public ResponseEntity<employeesalaryResponse> getEmployeesalaryByEmpId(@PathVariable("empid") int empid) {
		return hrmsemployeesalaryService.getEmployeesalaryByEmpId(empid);

	}

}
