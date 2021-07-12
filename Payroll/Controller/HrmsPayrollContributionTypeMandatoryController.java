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

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeMandatory;
import com.Hrms.Payroll.Service.HrmsPayrollContributionTypeMandatoryService;

@RestController
@RequestMapping("v1/payrollContributionTypeMandatory")
public class HrmsPayrollContributionTypeMandatoryController {

	@Autowired
	private HrmsPayrollContributionTypeMandatoryService hrmsPayrollContributionTypeMandatoryService;

	@PostMapping(value = "/addPayrollContributionTypeMandatory")
	public ResponseEntity<HrmsPayrollContributionTypeMandatory> addPayrollContributionTypeMandatory(
			@RequestBody HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory) {

		return hrmsPayrollContributionTypeMandatoryService
				.addPayrollContributionTypeMandatory(hrmsPayrollContributionTypeMandatory);

	}

	@GetMapping(value = "/getPayrollContributionTypeMandatoryById/{id}")
	public ResponseEntity<HrmsPayrollContributionTypeMandatory> getPayrollContributionTypeMandatoryById(
			@PathVariable("id") int id) {
		return hrmsPayrollContributionTypeMandatoryService.getPayrollContributionTypeMandatoryById(id);

	}

	@PutMapping(value = "/updatePayrollContributionTypeMandatory/{id}")
	public ResponseEntity<HrmsPayrollContributionTypeMandatory> updatePayrollContributionTypeMandatory(
			@RequestBody HrmsPayrollContributionTypeMandatory hrmsPayrollContributionTypeMandatory,
			@PathVariable("id") int id) {
		return hrmsPayrollContributionTypeMandatoryService
				.updatePayrollContributionTypeMandatory(hrmsPayrollContributionTypeMandatory, id);

	}

	@DeleteMapping(value = "/deletePayrollContributionTypeMandatory/{id}")
	public ResponseEntity<?> deletePayrollContributionTypeMandatory(@PathVariable("id") int id) {

		return hrmsPayrollContributionTypeMandatoryService.deletePayrollContributionTypeMandatory(id);

	}

	@GetMapping(value = "/getAllPayrollContributionTypeMandatory")
	public ResponseEntity<List<HrmsPayrollContributionTypeMandatory>> getAllPayrollContributionTypeMandatory() {

		return hrmsPayrollContributionTypeMandatoryService.getAllPayrollContributionTypeMandatory();

	}
}
