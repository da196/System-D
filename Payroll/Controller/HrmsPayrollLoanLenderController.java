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

import com.Hrms.Payroll.Entity.HrmsPayrollLoanLender;
import com.Hrms.Payroll.Service.HrmsPayrollLoanLenderService;

@RestController
@RequestMapping("v1/payrollLoanLender")
public class HrmsPayrollLoanLenderController {

	@Autowired
	private HrmsPayrollLoanLenderService hrmsPayrollLoanLenderService;

	@PostMapping(value = "/addPayrollLoanLender")
	public ResponseEntity<HrmsPayrollLoanLender> addPayrollLoanLender(
			@RequestBody HrmsPayrollLoanLender hrmsPayrollLoanLender) {

		return hrmsPayrollLoanLenderService.addPayrollLoanLender(hrmsPayrollLoanLender);

	}

	@GetMapping(value = "/getPayrollLoanLenderById/{id}")
	public ResponseEntity<HrmsPayrollLoanLender> getPayrollLoanLenderById(@PathVariable("id") int id) {

		return hrmsPayrollLoanLenderService.getPayrollLoanLenderById(id);

	}

	@PutMapping(value = "/updatePayrollLoanLender/{id}")
	public ResponseEntity<HrmsPayrollLoanLender> updatePayrollLoanLender(
			@RequestBody HrmsPayrollLoanLender hrmsPayrollLoanLender, @PathVariable("id") int id) {

		return hrmsPayrollLoanLenderService.updatePayrollLoanLender(hrmsPayrollLoanLender, id);

	}

	@DeleteMapping(value = "/deletePayrollLoanLender/{id}")
	public ResponseEntity<?> deletePayrollLoanLender(@PathVariable("id") int id) {

		return hrmsPayrollLoanLenderService.deletePayrollLoanLender(id);

	}

	@GetMapping(value = "/getAllPayrollLoanLender")
	public ResponseEntity<List<HrmsPayrollLoanLender>> getAllPayrollLoanLender() {

		return hrmsPayrollLoanLenderService.getAllPayrollLoanLender();

	}

}
