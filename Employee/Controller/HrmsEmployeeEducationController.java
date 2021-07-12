package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.EmployeeEducationResponseV2;
import com.Hrms.Employee.DTO.HrmsEmployeeEducationRequest;
import com.Hrms.Employee.Service.HrmsEmployeeEducationService;

@CrossOrigin(origins = "http://10.100.112.154:8080")
@RestController
@RequestMapping("v1/employeeEducation")
public class HrmsEmployeeEducationController {
	@Autowired
	private HrmsEmployeeEducationService hrmsEmployeeEducationService;

	@PostMapping(value = "/addEmployeeEducation")
	public ResponseEntity<?> addEmployeeEducation(
			@RequestBody HrmsEmployeeEducationRequest hrmsEmployeeEducationRequest) {
		return hrmsEmployeeEducationService.addEmployeeEducation(hrmsEmployeeEducationRequest);
	}

	@GetMapping(value = "/getEmployeeEducationById/{id}")
	public ResponseEntity<EmployeeEducationResponseV2> getEmployeeEducationById(@PathVariable("id") int id) {

		return hrmsEmployeeEducationService.getEmployeeEducationById(id);
	}

	@GetMapping(value = "/getEmployeeEducationByEmpId/{id}")
	public ResponseEntity<List<EmployeeEducationResponseV2>> getEmployeeEducationByEmpId(@PathVariable("id") int id) {
		return hrmsEmployeeEducationService.getEmployeeEducationByempId(id);

	}

	@PutMapping(value = "/updateEmployeeEducation/{id}")
	public ResponseEntity<HrmsEmployeeEducationRequest> updateEmployeeEducation(
			@RequestBody HrmsEmployeeEducationRequest hrmsEmployeeEducationRequest, @PathVariable("id") int id) {
		return hrmsEmployeeEducationService.updateEmployeeEducation(hrmsEmployeeEducationRequest, id);

	}

	@DeleteMapping(value = "/deleteEmployeeEducation/{id}")
	public ResponseEntity<?> deleteEmployeeEducation(@PathVariable("id") int id) {
		return hrmsEmployeeEducationService.deleteEmployeeEducation(id);

	}

	@GetMapping(value = "/getAllEmployeeEducation")
	public ResponseEntity<List<EmployeeEducationResponseV2>> listEmployeeEducation() {
		return hrmsEmployeeEducationService.listEmployeeEducation();

	}

	@GetMapping(value = "/getAllEmployeeEducationNonApproved")
	public ResponseEntity<List<EmployeeEducationResponseV2>> listEmployeeEducationNonApproved() {

		return hrmsEmployeeEducationService.listEmployeeEducationNonApproved();

	}

	@PutMapping(value = "/approveOrejectEmployeeEducation/{id}/{status}")
	public ResponseEntity<?> approveOrejectEmployeeEducation(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsEmployeeEducationService.approveOrejectEmployeeEducation(employeeApprovalComment, id, status);
	}

}
