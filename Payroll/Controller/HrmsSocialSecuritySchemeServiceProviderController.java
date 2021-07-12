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

import com.Hrms.Payroll.Entity.HrmsSocialSecuritySchemeServiceProvider;
import com.Hrms.Payroll.Service.HrmsSocialSecuritySchemeServiceProviderService;

@RestController
@RequestMapping("/v1/socialSecuritySchemeServiceProvider")
public class HrmsSocialSecuritySchemeServiceProviderController {
	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderService hrmsSocialSecuritySchemeServiceProviderService;

	@PostMapping(value = "/addSocialSecuritySchemeServiceProvider")
	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> addSocialSecuritySchemeServiceProvider(
			@RequestBody HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider) {

		return hrmsSocialSecuritySchemeServiceProviderService
				.addSocialSecuritySchemeServiceProvider(hrmsSocialSecuritySchemeServiceProvider);

	}

	@GetMapping(value = "/getSocialSecuritySchemeServiceProviderById/{id}")
	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> getSocialSecuritySchemeServiceProviderById(
			@PathVariable("id") int id) {
		return hrmsSocialSecuritySchemeServiceProviderService.getSocialSecuritySchemeServiceProviderById(id);

	}

	@PutMapping(value = "/updateSocialSecuritySchemeServiceProvider/{id}")
	public ResponseEntity<HrmsSocialSecuritySchemeServiceProvider> updateSocialSecuritySchemeServiceProvider(
			@RequestBody HrmsSocialSecuritySchemeServiceProvider hrmsSocialSecuritySchemeServiceProvider,
			@PathVariable("id") int id) {

		return hrmsSocialSecuritySchemeServiceProviderService
				.updateSocialSecuritySchemeServiceProvider(hrmsSocialSecuritySchemeServiceProvider, id);

	}

	@DeleteMapping(value = "/deleteSocialSecuritySchemeServiceProvider/{id}")
	public ResponseEntity<?> deleteSocialSecuritySchemeServiceProvider(@PathVariable("id") int id) {
		return hrmsSocialSecuritySchemeServiceProviderService.deleteSocialSecuritySchemeServiceProvider(id);

	}

	@GetMapping(value = "/getAllSocialSecuritySchemeServiceProvider")
	public ResponseEntity<List<HrmsSocialSecuritySchemeServiceProvider>> getAllSocialSecuritySchemeServiceProvider() {
		return hrmsSocialSecuritySchemeServiceProviderService.getAllSocialSecuritySchemeServiceProvider();

	}

}
