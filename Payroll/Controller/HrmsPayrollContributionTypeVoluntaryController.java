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

import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeVoluntary;
import com.Hrms.Payroll.Service.HrmsPayrollContributionTypeVoluntaryService;

@RestController
@RequestMapping("v1/payrollContributionTypeVoluntary")
public class HrmsPayrollContributionTypeVoluntaryController {

	@Autowired
	private HrmsPayrollContributionTypeVoluntaryService hrmsPayrollContributionTypeVoluntaryService;

	@PostMapping(value = "/addPayrollContributionTypeVoluntary")
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> addPayrollContributionTypeVoluntary(
			@RequestBody HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary) {
		return hrmsPayrollContributionTypeVoluntaryService
				.addPayrollContributionTypeVoluntary(hrmsPayrollContributionTypeVoluntary);

	}

	@GetMapping(value = "/getPayrollContributionTypeVoluntaryById/{id}")
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> getPayrollContributionTypeVoluntaryById(
			@PathVariable("id") int id) {
		return hrmsPayrollContributionTypeVoluntaryService.getPayrollContributionTypeVoluntaryById(id);

	}

	@PutMapping(value = "/updatePayrollContributionTypeVoluntary/{id}")
	public ResponseEntity<HrmsPayrollContributionTypeVoluntary> updatePayrollContributionTypeVoluntary(
			@RequestBody HrmsPayrollContributionTypeVoluntary hrmsPayrollContributionTypeVoluntary,
			@PathVariable("id") int id) {

		return hrmsPayrollContributionTypeVoluntaryService
				.updatePayrollContributionTypeVoluntary(hrmsPayrollContributionTypeVoluntary, id);

	}

	@DeleteMapping(value = "/deletePayrollContributionTypeVoluntary/{id}")
	public ResponseEntity<?> deletePayrollContributionTypeVoluntary(@PathVariable("id") int id) {
		return hrmsPayrollContributionTypeVoluntaryService.deletePayrollContributionTypeVoluntary(id);

	}

	@GetMapping(value = "/getAllPayrollContributionTypeVoluntary")
	public ResponseEntity<List<HrmsPayrollContributionTypeVoluntary>> getAllPayrollContributionTypeVoluntary() {
		return hrmsPayrollContributionTypeVoluntaryService.getAllPayrollContributionTypeVoluntary();

	}

	@GetMapping(value = "/getContributionTypeVoluntaryByServiceProviderId/{serviceproviderid}")
	public ResponseEntity<List<HrmsPayrollContributionTypeVoluntary>> getContributionTypeVoluntaryByServiceProviderId(
			@PathVariable("serviceproviderid") int serviceproviderid) {

		return hrmsPayrollContributionTypeVoluntaryService
				.getContributionTypeVoluntaryByServiceProviderId(serviceproviderid);

	}

}
