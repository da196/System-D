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

import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntaryServiceProvider;
import com.Hrms.Payroll.Service.HrmsPayrollContributionVoluntaryServiceProviderService;

@RestController
@RequestMapping("v1/payrollContributionVoluntaryServiceProvider")
public class HrmsPayrollContributionVoluntaryServiceProviderController {

	@Autowired
	private HrmsPayrollContributionVoluntaryServiceProviderService hrmsPayrollContributionVoluntaryServiceProviderService;

	@PostMapping(value = "/addContributionVoluntaryServiceProvider")
	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> addContributionVoluntaryServiceProvider(
			@RequestBody HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider) {
		return hrmsPayrollContributionVoluntaryServiceProviderService
				.addContributionVoluntaryServiceProvider(hrmsPayrollContributionVoluntaryServiceProvider);

	}

	@GetMapping(value = "/getContributionVoluntaryServiceProviderById/{id}")
	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> getContributionVoluntaryServiceProviderById(
			@PathVariable("id") int id) {

		return hrmsPayrollContributionVoluntaryServiceProviderService.getContributionVoluntaryServiceProviderById(id);

	}

	@PutMapping(value = "/updateContributionVoluntaryServiceProvider/{id}")
	public ResponseEntity<HrmsPayrollContributionVoluntaryServiceProvider> updateContributionVoluntaryServiceProvider(
			@RequestBody HrmsPayrollContributionVoluntaryServiceProvider hrmsPayrollContributionVoluntaryServiceProvider,
			@PathVariable("id") int id) {

		return hrmsPayrollContributionVoluntaryServiceProviderService
				.updateContributionVoluntaryServiceProvider(hrmsPayrollContributionVoluntaryServiceProvider, id);

	}

	@DeleteMapping(value = "/deleteContributionVoluntaryServiceProvider/{id}")
	public ResponseEntity<?> deleteContributionVoluntaryServiceProvider(@PathVariable("id") int id) {

		return hrmsPayrollContributionVoluntaryServiceProviderService.deleteContributionVoluntaryServiceProvider(id);

	}

	@GetMapping(value = "/getAllContributionVoluntaryServiceProvider")
	public ResponseEntity<List<HrmsPayrollContributionVoluntaryServiceProvider>> getAllContributionVoluntaryServiceProvider() {
		return hrmsPayrollContributionVoluntaryServiceProviderService.getAllContributionVoluntaryServiceProvider();
	}

}
