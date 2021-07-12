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

import com.Hrms.Employee.Entity.HrmsLocationCountry;
import com.Hrms.Employee.Service.HrmsLocationCountryService;

@RestController
@RequestMapping("/v1/locationCountry")
public class HrmsLocationCountryController {

	@Autowired
	private HrmsLocationCountryService hrmsLocationCountryService;

	@PostMapping(value = "/addLocationCountry")
	public ResponseEntity<HrmsLocationCountry> addLocationCountry(
			@RequestBody HrmsLocationCountry hrmsLocationCountry) {
		return hrmsLocationCountryService.addLocationCountry(hrmsLocationCountry);
	}

	@GetMapping(value = "/getLocationCountry/{id}")
	public ResponseEntity<Optional<HrmsLocationCountry>> getLocationCountry(@PathVariable("id") int id) {

		return hrmsLocationCountryService.getLocationCountry(id);

	}

	@PutMapping(value = "/updateLocationCountry/{id}")
	public ResponseEntity<HrmsLocationCountry> updateLocationCountry(
			@RequestBody HrmsLocationCountry hrmsLocationCountry, @PathVariable("id") int id) {

		return hrmsLocationCountryService.updateLocationCountry(hrmsLocationCountry, id);

	}

	@DeleteMapping(value = "/deleteLocationCountry/{id}")
	public ResponseEntity<?> deleteLocationCountry(@PathVariable("id") int id) {

		return hrmsLocationCountryService.deleteLocationCountry(id);

	}

	@GetMapping(value = "/getAllLocationCountry")
	public ResponseEntity<List<HrmsLocationCountry>> listLocationCountry() {

		return hrmsLocationCountryService.listLocationCountry();

	}

}
