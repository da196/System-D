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

import com.Hrms.Employee.Entity.HrmsLocationDistrict;
import com.Hrms.Employee.Service.HrmsLocationDistrictService;

@RestController
@RequestMapping("v1/LocationDistrict")
public class HrmsLocationDistrictController {
	@Autowired
	private HrmsLocationDistrictService hrmsLocationDistrictService;

	@PostMapping(value = "/addLocationDistrict")
	public ResponseEntity<HrmsLocationDistrict> addLocationDistrict(
			@RequestBody HrmsLocationDistrict hrmsLocationDistrict) {
		return hrmsLocationDistrictService.addLocationDistrict(hrmsLocationDistrict);
	}

	@GetMapping(value = "/getLocationDistrict/{id}")
	public ResponseEntity<Optional<HrmsLocationDistrict>> getLocationDistrict(@PathVariable("id") int id) {

		return hrmsLocationDistrictService.getLocationDistrict(id);

	}

	@PutMapping(value = "/updateLocationDistrict/{id}")
	public ResponseEntity<HrmsLocationDistrict> updateLocationDistrict(
			@RequestBody HrmsLocationDistrict hrmsLocationDistrict, @PathVariable("id") int id) {

		return hrmsLocationDistrictService.updateLocationDistrict(hrmsLocationDistrict, id);

	}

	@DeleteMapping(value = "/deleteLocationDistrict/{id}")
	public ResponseEntity<?> deleteLocationCity(@PathVariable("id") int id) {

		return hrmsLocationDistrictService.deleteLocationDistrict(id);

	}

	@GetMapping(value = "/getAllLocationDistrict")
	public ResponseEntity<List<HrmsLocationDistrict>> listLocationCity() {
		return hrmsLocationDistrictService.listLocationDistrict();

	}

	@GetMapping(value = "/getAllLocationDistrictByCityId/{cityId}")
	public ResponseEntity<List<HrmsLocationDistrict>> listLocationDistrictByCityId(@PathVariable("cityId") int cityId) {
		return hrmsLocationDistrictService.listLocationDistrictByCityId(cityId);
	}

}
