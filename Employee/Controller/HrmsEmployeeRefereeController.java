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
import com.Hrms.Employee.DTO.HrmsEmployeeRefereeResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeReferee;
import com.Hrms.Employee.Service.HrmsEmployeeRefereeService;

@RestController
@RequestMapping("/v1/employeeReferee")
public class HrmsEmployeeRefereeController {

	@Autowired
	private HrmsEmployeeRefereeService hrmsEmployeeRefereeService;

	@PostMapping(value = "/addEmployeeReferee")
	public ResponseEntity<HrmsEmployeeReferee> addEmployeeReferee(
			@RequestBody HrmsEmployeeReferee hrmsEmployeeReferee) {
		return hrmsEmployeeRefereeService.addEmployeeReferee(hrmsEmployeeReferee);
	}

	@GetMapping(value = "/getEmployeeRefereeByEmpId/{id}")
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> getEmployeeRefereeByEmpId(@PathVariable("id") int id) {

		return hrmsEmployeeRefereeService.getEmployeeRefereeByEmpId(id);

	}

	@GetMapping(value = "/getEmployeeRefereeByEmpEmail/{email}")
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> getEmployeeRefereeByEmail(
			@PathVariable("email") String email) {
		return hrmsEmployeeRefereeService.getEmployeeRefereeByEmail(email);
	}

	@GetMapping(value = "/getEmployeeRefereeById/{id}")
	public ResponseEntity<HrmsEmployeeRefereeResponse> getEmployeeRefereeById(@PathVariable("id") int id) {
		return hrmsEmployeeRefereeService.getEmployeeRefereeById(id);
	}

	@PutMapping(value = "/updateEmployeeReferee/{id}")
	public ResponseEntity<HrmsEmployeeReferee> updateEmployeeReferee(
			@RequestBody HrmsEmployeeReferee hrmsEmployeeReferee, @PathVariable("id") int id) {
		return hrmsEmployeeRefereeService.updateEmployeeReferee(hrmsEmployeeReferee, id);

	}

	@DeleteMapping(value = "/deleteEmployeeReferee/{id}")
	public ResponseEntity<?> deleteEmployeeReferee(@PathVariable("id") int id) {
		return hrmsEmployeeRefereeService.deleteEmployeeReferee(id);

	}

	@GetMapping(value = "/getAllEmployeeReferee")
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> listEmployeeReferee() {
		return hrmsEmployeeRefereeService.listEmployeeReferees();

	}

	@GetMapping(value = "/getAllEmployeeRefereesNonApproved")
	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> listEmployeeRefereesNonApproved() {
		return hrmsEmployeeRefereeService.listEmployeeRefereesNonApproved();
	}

	@PutMapping(value = "/approveOrRejectEmployeeReferee/{id}/{status}")
	public ResponseEntity<?> approveOrRejectEmployeeReferee(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsEmployeeRefereeService.approveOrRejectEmployeeReferee(employeeApprovalComment, id, status);
	}

}
