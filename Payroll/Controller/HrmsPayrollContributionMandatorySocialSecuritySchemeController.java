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

import com.Hrms.Payroll.DTO.PayrollContributionMandatorySocialSecuritySchemeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Service.HrmsPayrollContributionMandatorySocialSecuritySchemeService;

@RestController
@RequestMapping("v1/payrollContributionMandatorySocialSecurityScheme")
public class HrmsPayrollContributionMandatorySocialSecuritySchemeController {

	@Autowired
	private HrmsPayrollContributionMandatorySocialSecuritySchemeService hrmsPayrollContributionMandatorySocialSecuritySchemeService;

	@PostMapping(value = "/addPayrollContributionMandatorySocialSecurityScheme")
	public ResponseEntity<HrmsPayrollContributionMandatorySocialSecurityScheme> addPayrollContributionMandatorySocialSecurityScheme(
			@RequestBody HrmsPayrollContributionMandatorySocialSecurityScheme hrmsPayrollContributionMandatorySocialSecurityScheme) {

		return hrmsPayrollContributionMandatorySocialSecuritySchemeService
				.addPayrollContributionMandatorySocialSecurityScheme(
						hrmsPayrollContributionMandatorySocialSecurityScheme);

	}

	@GetMapping(value = "/getPayrollContributionMandatorySocialSecuritySchemeById/{id}")
	public ResponseEntity<PayrollContributionMandatorySocialSecuritySchemeResponse> getPayrollContributionMandatorySocialSecuritySchemeById(
			@PathVariable("id") int id) {
		return hrmsPayrollContributionMandatorySocialSecuritySchemeService
				.getPayrollContributionMandatorySocialSecuritySchemeById(id);
	}

	@PutMapping(value = "/updatePayrollContributionMandatorySocialSecurityScheme/{id}")
	public ResponseEntity<HrmsPayrollContributionMandatorySocialSecurityScheme> updatePayrollContributionMandatorySocialSecurityScheme(
			@RequestBody HrmsPayrollContributionMandatorySocialSecurityScheme hrmsPayrollContributionMandatorySocialSecurityScheme,
			@PathVariable("id") int id) {

		return hrmsPayrollContributionMandatorySocialSecuritySchemeService
				.updatePayrollContributionMandatorySocialSecurityScheme(
						hrmsPayrollContributionMandatorySocialSecurityScheme, id);

	}

	@DeleteMapping(value = "/deletePayrollContributionMandatorySocialSecurityScheme/{id}")
	public ResponseEntity<?> deletePayrollContributionMandatorySocialSecurityScheme(@PathVariable("id") int id) {

		return hrmsPayrollContributionMandatorySocialSecuritySchemeService
				.deletePayrollContributionMandatorySocialSecurityScheme(id);

	}

	@GetMapping(value = "/getAllPayrollContributionMandatorySocialSecurityScheme")
	public ResponseEntity<List<PayrollContributionMandatorySocialSecuritySchemeResponse>> getAllPayrollContributionMandatorySocialSecurityScheme() {

		return hrmsPayrollContributionMandatorySocialSecuritySchemeService
				.getAllPayrollContributionMandatorySocialSecurityScheme();

	}

}
