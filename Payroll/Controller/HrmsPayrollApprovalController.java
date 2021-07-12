package com.Hrms.Payroll.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Payroll.Entity.HrmsPayrollApproval;
import com.Hrms.Payroll.Service.HrmsPayrollApprovalService;

@RestController
@RequestMapping("v1/payrollApproval")
public class HrmsPayrollApprovalController {

	@Autowired
	private HrmsPayrollApprovalService hrmsPayrollApprovalService;

	@PostMapping(value = "/approvePayroll/{payrollId}/{status}")
	public ResponseEntity<HrmsPayrollApproval> approvePayroll(@RequestBody HrmsPayrollApproval hrmsPayrollApproval,
			@PathVariable("payrollId") int payrollId, @PathVariable("status") int status) {

		return hrmsPayrollApprovalService.approvePayroll(hrmsPayrollApproval, payrollId, status);

	}

	public ResponseEntity<?> getPayrollApprovalById(int id) {
		return hrmsPayrollApprovalService.getPayrollApprovalById(id);

	}

	@PutMapping(value = "/updatePayrollApproval/{id}/{status}")
	public ResponseEntity<HrmsPayrollApproval> updatePayrollApproval(
			@RequestBody HrmsPayrollApproval hrmsPayrollApproval, @PathVariable("id") int id,
			@PathVariable("status") int status) {
		return hrmsPayrollApprovalService.updatePayrollApproval(hrmsPayrollApproval, id, status);

	}

	@DeleteMapping(value = "/deletePayrollApproval/{id}")
	public ResponseEntity<?> deletePayrollApproval(int id) {
		return hrmsPayrollApprovalService.deletePayrollApproval(id);

	}

	public ResponseEntity<List<HrmsPayrollApproval>> getAllPayrollApproval() {
		return hrmsPayrollApprovalService.getAllPayrollApproval();

	}

	public ResponseEntity<List<?>> getAllPayrollApprovalByPayrollId(int payrollId) {

		return hrmsPayrollApprovalService.getAllPayrollApprovalByPayrollId(payrollId);

	}

}
