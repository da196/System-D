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

import com.Hrms.Payroll.DTO.PayrollContributionVoluntaryResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntary;
import com.Hrms.Payroll.Service.HrmsPayrollContributionVoluntaryService;

@RestController
@RequestMapping("v1/payrollContributionVoluntary")
public class HrmsPayrollContributionVoluntaryController {
	@Autowired
	private HrmsPayrollContributionVoluntaryService hrmsPayrollContributionVoluntaryService;

	@PostMapping(value = "/addPayrollContributionVoluntary")
	public ResponseEntity<HrmsPayrollContributionVoluntary> addPayrollContributionVoluntary(
			@RequestBody HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary) {

		return hrmsPayrollContributionVoluntaryService
				.addPayrollContributionVoluntary(hrmsPayrollContributionVoluntary);

	}

	@GetMapping(value = "/getPayrollContributionVoluntaryById/{id}")
	public ResponseEntity<PayrollContributionVoluntaryResponse> getPayrollContributionVoluntaryById(
			@PathVariable("id") int id) {

		return hrmsPayrollContributionVoluntaryService.getPayrollContributionVoluntaryById(id);

	}

	@PutMapping(value = "/updatePayrollContributionVoluntary/{id}")
	public ResponseEntity<HrmsPayrollContributionVoluntary> updatePayrollContributionVoluntary(
			@RequestBody HrmsPayrollContributionVoluntary hrmsPayrollContributionVoluntary,
			@PathVariable("id") int id) {

		return hrmsPayrollContributionVoluntaryService
				.updatePayrollContributionVoluntary(hrmsPayrollContributionVoluntary, id);

	}

	@DeleteMapping(value = "/deletePayrollContributionVoluntary/{id}")
	public ResponseEntity<?> deletePayrollContributionVoluntary(@PathVariable("id") int id) {
		return hrmsPayrollContributionVoluntaryService.deletePayrollContributionVoluntary(id);

	}

	@GetMapping(value = "/getAllPayrollContributionVoluntary")
	public ResponseEntity<List<PayrollContributionVoluntaryResponse>> getAllPayrollContributionVoluntary() {

		return hrmsPayrollContributionVoluntaryService.getAllPayrollContributionVoluntary();

	}

	@GetMapping(value = "/getPayrollContributionVoluntaryByEmpId/{EmpId}")
	public ResponseEntity<List<PayrollContributionVoluntaryResponse>> getPayrollContributionVoluntaryByEmpId(
			@PathVariable("EmpId") int EmpId) {
		return hrmsPayrollContributionVoluntaryService.getPayrollContributionVoluntaryByEmpId(EmpId);

	}

}
