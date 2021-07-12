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

import com.Hrms.Payroll.Entity.HrmsPayrollLoanFrequency;
import com.Hrms.Payroll.Service.HrmsPayrollLoanFrequencyService;

@RestController
@RequestMapping("v1/payrollLoanFrequency")
public class HrmsPayrollLoanFrequencyController {

	@Autowired
	private HrmsPayrollLoanFrequencyService HrmsPayrollLoanFrequencyService;

	@PostMapping(value = "/addPayrollLoanFrequency")
	public ResponseEntity<HrmsPayrollLoanFrequency> addPayrollLoanFrequency(
			@RequestBody HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency) {

		return HrmsPayrollLoanFrequencyService.addPayrollLoanFrequency(hrmsPayrollLoanFrequency);

	}

	@GetMapping(value = "/getPayrollLoanFrequencyById/{id}")
	public ResponseEntity<HrmsPayrollLoanFrequency> getPayrollLoanFrequencyById(@PathVariable("id") int id) {

		return HrmsPayrollLoanFrequencyService.getPayrollLoanFrequencyById(id);

	}

	@PutMapping(value = "/updatePayrollLoanFrequency/{id}")
	public ResponseEntity<HrmsPayrollLoanFrequency> updatePayrollLoanFrequency(
			@RequestBody HrmsPayrollLoanFrequency hrmsPayrollLoanFrequency, @PathVariable("id") int id) {

		return HrmsPayrollLoanFrequencyService.updatePayrollLoanFrequency(hrmsPayrollLoanFrequency, id);

	}

	@DeleteMapping(value = "/deletePayrollLoanFrequency/{id}")
	public ResponseEntity<?> deletePayrollLoanFrequency(@PathVariable("id") int id) {
		return HrmsPayrollLoanFrequencyService.deletePayrollLoanFrequency(id);

	}

	@GetMapping(value = "/getAllPayrollLoanFrequency")
	public ResponseEntity<List<HrmsPayrollLoanFrequency>> getAllPayrollLoanFrequency() {

		return HrmsPayrollLoanFrequencyService.getAllPayrollLoanFrequency();

	}

}
