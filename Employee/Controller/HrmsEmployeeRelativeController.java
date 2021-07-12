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
import com.Hrms.Employee.DTO.EmployeeRelativeRequest;
import com.Hrms.Employee.DTO.EmployeeRelativeResponse;
import com.Hrms.Employee.Service.HrmsEmployeeRelativeService;

@RestController
@RequestMapping("v1/employeeRelative")
public class HrmsEmployeeRelativeController {

	@Autowired
	private HrmsEmployeeRelativeService hrmsEmployeeRelativeService;

	@PostMapping(value = "/addEmployeeRelative")
	public ResponseEntity<EmployeeRelativeRequest> addEmployeeRelative(
			@RequestBody EmployeeRelativeRequest employeeRelativeRequest) {

		return hrmsEmployeeRelativeService.addEmployeeRelative(employeeRelativeRequest);

	}

	@GetMapping(value = "/getEmployeeRelativeByEmpId/{empId}")
	public ResponseEntity<List<EmployeeRelativeResponse>> getEmployeeRelativeByEmpId(@PathVariable("empId") int empId) {

		return hrmsEmployeeRelativeService.getEmployeeRelativeByEmpId(empId);

	}

	@GetMapping(value = "/getEmployeeRelativeById/{id}")
	public ResponseEntity<EmployeeRelativeResponse> getEmployeeRelativeById(@PathVariable("id") int id) {
		return hrmsEmployeeRelativeService.getEmployeeRelativeById(id);
	}

	@PutMapping(value = "/updateEmployeeRelative/{id}")
	public ResponseEntity<EmployeeRelativeRequest> updateEmployeeRelative(
			@RequestBody EmployeeRelativeRequest employeeRelativeRequest, @PathVariable("id") int id) {

		return hrmsEmployeeRelativeService.updateEmployeeRelative(employeeRelativeRequest, id);

	}

	@DeleteMapping(value = "/deleteEmployeeRelative/{id}")
	public ResponseEntity<?> deleteEmployeeRelative(@PathVariable("id") int id) {
		return hrmsEmployeeRelativeService.deleteEmployeeRelative(id);

	}

	@GetMapping(value = "/getAllEmployeeRelative")
	public ResponseEntity<List<EmployeeRelativeResponse>> listEmployeeRelatives() {

		return hrmsEmployeeRelativeService.listEmployeeRelatives();

	}

	@GetMapping(value = "/getAllEmployeeRelativesNonApproved")
	public ResponseEntity<List<EmployeeRelativeResponse>> listEmployeeRelativesNonApproved() {
		return hrmsEmployeeRelativeService.listEmployeeRelativesNonApproved();
	}

	@PutMapping(value = "/approveOrRejectEmployeeRelative/{id}/{status}")
	public ResponseEntity<?> approveOrRejectEmployeeRelative(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsEmployeeRelativeService.approveOrRejectEmployeeRelative(employeeApprovalComment, id, status);

	}

}
