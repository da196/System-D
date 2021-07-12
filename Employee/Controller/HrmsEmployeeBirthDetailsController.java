package com.Hrms.Employee.Controller;

import java.util.List;

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

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.EmployeeBirthDetailsRequest;
import com.Hrms.Employee.DTO.EmployeeBirthDetailsResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeBirthDetails;
import com.Hrms.Employee.Service.HrmsEmployeeBirthDetailsService;

@RestController
@RequestMapping("v1/employeeBirthDetails")
public class HrmsEmployeeBirthDetailsController {

	@Autowired
	private HrmsEmployeeBirthDetailsService hrmsEmployeeBirthDetailsService;

	@PostMapping(value = "/addEmpBirthDetails")
	public ResponseEntity<HrmsEmployeeBirthDetails> addEmpBirthDetails(
			@RequestBody EmployeeBirthDetailsRequest employeeBirthDetailsRequest) {

		return hrmsEmployeeBirthDetailsService.addEmpBirthDetails(employeeBirthDetailsRequest);

	}

	@GetMapping(value = "/getEmployeeBirthDetailsByEmpId/{empid}")
	public ResponseEntity<EmployeeBirthDetailsResponse> getEmployeeBirthDetailsByEmpId(
			@PathVariable("empid") int empid) {
		return hrmsEmployeeBirthDetailsService.getEmployeeBirthDetailsByEmpId(empid);
	}

	@GetMapping(value = "/getEmployeeBirthDetailsById/{id}")
	public ResponseEntity<EmployeeBirthDetailsResponse> getEmployeeBirthDetailsById(@PathVariable("id") int id) {
		return hrmsEmployeeBirthDetailsService.getEmployeeBirthDetailsById(id);
	}

	@PutMapping(value = "/updateEmployeeBirthDetails/{id}")
	public ResponseEntity<HrmsEmployeeBirthDetails> updateEmployeeBirthDetails(
			@RequestBody EmployeeBirthDetailsRequest employeeBirthDetailsRequest, @PathVariable("id") int id) {

		return hrmsEmployeeBirthDetailsService.updateEmployeeBirthDetails(employeeBirthDetailsRequest, id);
	}

	@DeleteMapping(value = "/deleteEmployeeBirthDetails/{id}")
	public ResponseEntity<?> deleteEmployeeBirthDetails(@PathVariable("id") int id) {

		return hrmsEmployeeBirthDetailsService.deleteEmployeeBirthDetails(id);

	}

	@GetMapping(value = "/getAllEmployeeBirthDetails")
	public ResponseEntity<List<EmployeeBirthDetailsResponse>> listEmployeeBirthDetails() {
		return hrmsEmployeeBirthDetailsService.listEmployeeBirthDetails();
	}

	@GetMapping(value = "/getAllEmployeeBirthDetailsNonApproved")
	public ResponseEntity<List<EmployeeBirthDetailsResponse>> listEmployeeBirthDetailsNonApproved() {
		return hrmsEmployeeBirthDetailsService.listEmployeeBirthDetailsNonApproved();
	}

	@PutMapping(value = "/approveOrRejectEmployeeBirthDetails/{id}/{status}")
	public ResponseEntity<?> approveOrRejectEmployeeBirthDetails(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsEmployeeBirthDetailsService.approveOrRejectEmployeeBirthDetails(employeeApprovalComment, id, status);
	}

}
