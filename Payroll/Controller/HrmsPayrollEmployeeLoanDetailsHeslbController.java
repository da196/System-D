package com.Hrms.Payroll.Controller;

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

import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoanDetailsHeslb;
import com.Hrms.Payroll.Service.HrmsPayrollEmployeeLoanDetailsHeslbService;

@RestController
@RequestMapping("v1/payrollEmployeeLoanDetailsHeslb")
public class HrmsPayrollEmployeeLoanDetailsHeslbController {

	@Autowired
	private HrmsPayrollEmployeeLoanDetailsHeslbService hrmsPayrollEmployeeLoanDetailsHeslbService;

	@PostMapping(value = "/addPayrollEmployeeLoanDetailsHeslb")
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> addPayrollEmployeeLoanDetailsHeslb(
			@RequestBody HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb) {

		return hrmsPayrollEmployeeLoanDetailsHeslbService
				.addPayrollEmployeeLoanDetailsHeslb(hrmsPayrollEmployeeLoanDetailsHeslb);

	}

	@GetMapping(value = "/getPayrollEmployeeLoanDetailsHeslbById/{id}")
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> getPayrollEmployeeLoanDetailsHeslbById(
			@PathVariable("id") int id) {
		return hrmsPayrollEmployeeLoanDetailsHeslbService.getPayrollEmployeeLoanDetailsHeslbById(id);

	}

	@PutMapping(value = "/updatePayrollEmployeeLoanDetailsHeslb/{id}")
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> updatePayrollEmployeeLoanDetailsHeslb(
			@RequestBody HrmsPayrollEmployeeLoanDetailsHeslb hrmsPayrollEmployeeLoanDetailsHeslb,
			@PathVariable("id") int id) {

		return hrmsPayrollEmployeeLoanDetailsHeslbService
				.updatePayrollEmployeeLoanDetailsHeslb(hrmsPayrollEmployeeLoanDetailsHeslb, id);

	}

	@DeleteMapping(value = "/deletePayrollEmployeeLoanDetailsHeslb/{id}")
	public ResponseEntity<?> deletePayrollEmployeeLoanDetailsHeslb(@PathVariable("id") int id) {

		return hrmsPayrollEmployeeLoanDetailsHeslbService.deletePayrollEmployeeLoanDetailsHeslb(id);

	}

	@GetMapping(value = "/getAllPayrollEmployeeLoanDetailsHeslb")
	public ResponseEntity<List<HrmsPayrollEmployeeLoanDetailsHeslb>> getAllPayrollEmployeeLoanDetailsHeslb() {

		return hrmsPayrollEmployeeLoanDetailsHeslbService.getAllPayrollEmployeeLoanDetailsHeslb();

	}

	@GetMapping(value = "/getPayrollEmployeeLoanDetailsHeslbByLoanId/{loanId}")
	public ResponseEntity<HrmsPayrollEmployeeLoanDetailsHeslb> getPayrollEmployeeLoanDetailsHeslbByLoanId(
			@PathVariable("loanId") int loanId) {
		return hrmsPayrollEmployeeLoanDetailsHeslbService.getPayrollEmployeeLoanDetailsHeslbByLoanId(loanId);
	}

}
