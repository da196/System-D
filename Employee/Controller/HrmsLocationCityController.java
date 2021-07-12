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

import com.Hrms.Employee.Entity.HrmsLocationCity;
import com.Hrms.Employee.Service.HrmsLocationCityService;

@RestController
@RequestMapping("v1/locationCity")
public class HrmsLocationCityController {
	@Autowired
	private HrmsLocationCityService hrmsLocationCityService;

	@PostMapping(value = "/addLocationCity")
	public ResponseEntity<HrmsLocationCity> addLocationCity(@RequestBody HrmsLocationCity hrmsLocationCity) {
		return hrmsLocationCityService.addLocationCity(hrmsLocationCity);
	}

	@GetMapping(value = "/getLocationCity/{id}")
	public ResponseEntity<Optional<HrmsLocationCity>> getLocationCity(@PathVariable("id") int id) {

		return hrmsLocationCityService.getLocationCity(id);

	}

	@PutMapping(value = "/updateLocationCity/{id}")
	public ResponseEntity<HrmsLocationCity> updateLocationCity(@RequestBody HrmsLocationCity hrmsLocationCity,
			@PathVariable("id") int id) {

		return hrmsLocationCityService.updateLocationCity(hrmsLocationCity, id);

	}

	@DeleteMapping(value = "/deleteLocationCity/{id}")
	public ResponseEntity<?> deleteLocationCity(@PathVariable("id") int id) {

		return hrmsLocationCityService.deleteLocationCity(id);

	}

	@GetMapping(value = "/getAllLocationCity")
	public ResponseEntity<List<HrmsLocationCity>> listLocationCity() {
		return hrmsLocationCityService.listLocationCity();

	}

	@GetMapping(value = "/getAllLocationCityByCountryId/{countryid}")
	public ResponseEntity<List<HrmsLocationCity>> listLocationCityByCountryId(
			@PathVariable("countryid") int countryid) {

		return hrmsLocationCityService.listLocationCityByCountryId(countryid);

	}

}
