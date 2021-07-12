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

import com.Hrms.Payroll.DTO.PayrollContributionMandatoryInsuranceResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatoryInsurance;
import com.Hrms.Payroll.Service.HrmsPayrollContributionMandatoryInsuranceService;

@RestController
@RequestMapping("v1/payrollContributionMandatoryInsurance")
public class HrmsPayrollContributionMandatoryInsuranceController {

	@Autowired
	private HrmsPayrollContributionMandatoryInsuranceService hrmsPayrollContributionMandatoryInsuranceService;

	@PostMapping(value = "/addPayrollContributionMandatoryInsurance")
	public ResponseEntity<HrmsPayrollContributionMandatoryInsurance> addPayrollContributionMandatoryInsurance(
			@RequestBody HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance) {

		return hrmsPayrollContributionMandatoryInsuranceService
				.addPayrollContributionMandatoryInsurance(hrmsPayrollContributionMandatoryInsurance);

	}

	@GetMapping(value = "/getPayrollContributionMandatoryInsuranceById/{id}")
	public ResponseEntity<PayrollContributionMandatoryInsuranceResponse> getPayrollContributionMandatoryInsuranceById(
			@PathVariable("id") int id) {

		return hrmsPayrollContributionMandatoryInsuranceService.getPayrollContributionMandatoryInsuranceById(id);

	}

	@PutMapping(value = "/updatePayrollContributionMandatoryInsurance/{id}")
	public ResponseEntity<HrmsPayrollContributionMandatoryInsurance> updatePayrollContributionMandatoryInsurance(
			@RequestBody HrmsPayrollContributionMandatoryInsurance hrmsPayrollContributionMandatoryInsurance,
			@PathVariable("id") int id) {

		return hrmsPayrollContributionMandatoryInsuranceService
				.updatePayrollContributionMandatoryInsurance(hrmsPayrollContributionMandatoryInsurance, id);

	}

	@DeleteMapping(value = "/deletePayrollContributionMandatoryInsurance/{id}")
	public ResponseEntity<?> deletePayrollContributionMandatoryInsurance(@PathVariable("id") int id) {
		return hrmsPayrollContributionMandatoryInsuranceService.deletePayrollContributionMandatoryInsurance(id);

	}

	@GetMapping(value = "/getAllPayrollContributionMandatoryInsurance")
	public ResponseEntity<List<PayrollContributionMandatoryInsuranceResponse>> getAllPayrollContributionMandatoryInsurance() {

		return hrmsPayrollContributionMandatoryInsuranceService.getAllPayrollContributionMandatoryInsurance();
	}

}
