package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

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

import com.Hrms.Employee.Entity.HrmsInsuranceProvider;
import com.Hrms.Employee.Service.HrmsInsuranceProviderService;

@RestController
@RequestMapping("v1/insuranceProvider")
public class HrmsInsuranceProviderController {
	@Autowired
	private HrmsInsuranceProviderService hrmsInsuranceProviderService;

	@PostMapping(value = "/addInsuranceProvider")
	public ResponseEntity<HrmsInsuranceProvider> addInsuranceProvider(
			@RequestBody HrmsInsuranceProvider hrmsInsuranceProvider) {
		return hrmsInsuranceProviderService.addInsuranceProvider(hrmsInsuranceProvider);

	}

	@GetMapping(value = "/getInsuranceProvider/{id}")
	public ResponseEntity<Optional<HrmsInsuranceProvider>> getInsuranceProvider(@PathVariable("id") int id) {
		return hrmsInsuranceProviderService.getInsuranceProvider(id);

	}

	@PutMapping(value = "/updateInsuranceProvider/{id}")
	public ResponseEntity<HrmsInsuranceProvider> updateInsuranceProvider(
			@RequestBody HrmsInsuranceProvider hrmsInsuranceProvider, @PathVariable("id") int id) {

		return hrmsInsuranceProviderService.updateInsuranceProvider(hrmsInsuranceProvider, id);

	}

	@DeleteMapping(value = "/deleteInsuranceProvider/{id}")
	public ResponseEntity<?> deleteInsuranceProvider(@PathVariable("id") int id) {

		return hrmsInsuranceProviderService.deleteInsuranceProvider(id);

	}

	@GetMapping(value = "/getAllInsuranceProvider")
	public ResponseEntity<List<HrmsInsuranceProvider>> listInsuranceProvider() {

		return hrmsInsuranceProviderService.listInsuranceProvider();

	}

}
