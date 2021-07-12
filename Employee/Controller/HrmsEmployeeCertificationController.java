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
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationResponse;
import com.Hrms.Employee.Service.HrmsEmployeeCertificationService;

@RestController
@RequestMapping("v1/employeeCertification")
public class HrmsEmployeeCertificationController {

	@Autowired
	private HrmsEmployeeCertificationService hrmsEmployeeCertificationService;

	@PostMapping(value = "/addEmployeeCertification")
	public ResponseEntity<HrmsEmployeeCertificationRequest> addEmployeeCertification(
			@RequestBody HrmsEmployeeCertificationRequest hrmsEmployeeCertificationRequest) {

		return hrmsEmployeeCertificationService.addEmployeeCertification(hrmsEmployeeCertificationRequest);

	}

	@GetMapping(value = "/getEmployeeCertification/{empid}")
	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> getEmployeeCertificationByEmpId(
			@PathVariable("empid") int empid) {
		return hrmsEmployeeCertificationService.getEmployeeCertificationByEmpId(empid);
	}

	@GetMapping(value = "/getEmployeeCertificationById/{id}")
	public ResponseEntity<HrmsEmployeeCertificationResponse> getEmployeeCertificationById(@PathVariable("id") int id) {
		return hrmsEmployeeCertificationService.getEmployeeCertificationById(id);
	}

	@PutMapping(value = "/updateEmployeeCertification/{id}")
	public ResponseEntity<HrmsEmployeeCertificationRequest> updateEmployeeCertification(
			@RequestBody HrmsEmployeeCertificationRequest hrmsEmployeeCertificationRequest,
			@PathVariable("id") int id) {
		return hrmsEmployeeCertificationService.update(hrmsEmployeeCertificationRequest, id);

	}

	@DeleteMapping(value = "/deleteEmployeeCertification/{id}")
	public ResponseEntity<?> deleteEmployeeCertification(@PathVariable("id") int id) {
		return hrmsEmployeeCertificationService.deleteEmployeeCertification(id);

	}

	@GetMapping(value = "/getAllEmployeeCertification")
	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> listEmployeeCertification() {
		return hrmsEmployeeCertificationService.listEmployeeCertification();

	}

	@GetMapping(value = "/getAllEmployeeCertificationNonApproved")
	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> listEmployeeCertificationNonApproved() {
		return hrmsEmployeeCertificationService.listEmployeeCertificationNonApproved();
	}

	@PutMapping(value = "/approveOrRejectEmployeeCertification/{id}/{status}")
	public ResponseEntity<?> approveOrRejectEmployeeCertification(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsEmployeeCertificationService.approveOrRejectEmployeeCertification(employeeApprovalComment, id,
				status);
	}

}
