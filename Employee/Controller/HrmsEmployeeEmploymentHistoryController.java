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
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseByEmpId;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseById;
import com.Hrms.Employee.Service.HrmsEmployeeEmploymentHistoryService;

@RestController
@RequestMapping("v1/employeeEmploymentHistory")
public class HrmsEmployeeEmploymentHistoryController {
	@Autowired
	private HrmsEmployeeEmploymentHistoryService HrmsEmployeeEmploymentHistoryService;

	@PostMapping(value = "/addEmployeeEmploymentHistory")
	public ResponseEntity<HrmsEmployeeEmploymentHistoryRequest> addEmployeeEmploymentHistory(
			@RequestBody HrmsEmployeeEmploymentHistoryRequest hrmsEmployeeEmploymentHistoryRequest) {

		return HrmsEmployeeEmploymentHistoryService.save(hrmsEmployeeEmploymentHistoryRequest);

	}

	@GetMapping(value = "/getEmployeeEmploymentHistoryById/{id}")
	public ResponseEntity<HrmsEmployeeEmploymentHistoryResponseById> getEmployeeEmploymentHistoryById(
			@PathVariable("id") int id) {

		return HrmsEmployeeEmploymentHistoryService.getEmployeeEmploymentHistoryById(id);

	}

	@GetMapping(value = "/getEmployeeEmploymentHistoryByEmpId/{empId}")
	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseByEmpId>> getEmployeeEmploymentHistoryByEmpId(
			@PathVariable("empId") int empId) {

		return HrmsEmployeeEmploymentHistoryService.getEmployeeEmploymentHistoryByEmpId(empId);
	}

	@PutMapping(value = "/updateEmployeeEmploymentHistory/{id}")
	public ResponseEntity<HrmsEmployeeEmploymentHistoryRequest> updateEmployeeEmploymentHistory(
			@RequestBody HrmsEmployeeEmploymentHistoryRequest hrmsEmployeeEmploymentHistoryRequest,
			@PathVariable("id") int id) {

		return HrmsEmployeeEmploymentHistoryService
				.updateEmployeeEmploymentHistory(hrmsEmployeeEmploymentHistoryRequest, id);

	}

	@DeleteMapping(value = "/deleteEmployeeEmploymentHistory/{id}")
	public ResponseEntity<?> deleteEmployeeEmploymentHistory(@PathVariable("id") int id) {

		return HrmsEmployeeEmploymentHistoryService.deleteEmployeeEmploymentHistory(id);

	}

	@GetMapping(value = "/getAllEmployeeEmploymentHistory")
	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseById>> listEmployeeEmploymentHistory() {

		return HrmsEmployeeEmploymentHistoryService.listEmployeeEmploymentHistory();

	}

	@GetMapping(value = "/getAllEmployeeEmploymentHistoryNonApproved")
	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseById>> listEmployeeEmploymentHistoryNonApproved() {
		return HrmsEmployeeEmploymentHistoryService.listEmployeeEmploymentHistoryNonApproved();
	}

	@PutMapping(value = "/approveOrRejectEmployeeEmploymentHistory/{id}/{status}")
	public ResponseEntity<?> approveOrRejectEmployeeEmploymentHistory(
			@RequestBody EmployeeApprovalComment employeeApprovalComment, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return HrmsEmployeeEmploymentHistoryService.approveOrRejectEmployeeEmploymentHistory(employeeApprovalComment,
				id, status);
	}

}
