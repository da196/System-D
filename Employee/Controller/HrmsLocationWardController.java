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

import com.Hrms.Employee.Entity.HrmsLocationWard;
import com.Hrms.Employee.Service.HrmsLocationWardService;

@RestController
@RequestMapping("v1/locationWard")
public class HrmsLocationWardController {
	@Autowired
	private HrmsLocationWardService hrmsLocationWardService;

	@PostMapping(value = "/addLocationWard")
	public ResponseEntity<HrmsLocationWard> addLocationWard(@RequestBody HrmsLocationWard hrmsLocationWard) {
		return hrmsLocationWardService.addLocationWard(hrmsLocationWard);
	}

	@GetMapping(value = "/getLocationWard/{id}")
	public ResponseEntity<Optional<HrmsLocationWard>> getLocationWard(@PathVariable("id") int id) {

		return hrmsLocationWardService.getLocationWard(id);

	}

	@PutMapping(value = "/updateLocationWard/{id}")
	public ResponseEntity<HrmsLocationWard> updateLocationWard(@RequestBody HrmsLocationWard hrmsLocationWard,
			@PathVariable("id") int id) {

		return hrmsLocationWardService.updateLocationWard(hrmsLocationWard, id);

	}

	@DeleteMapping(value = "/deleteLocationWard/{id}")
	public ResponseEntity<?> deleteLocationWard(@PathVariable("id") int id) {

		return hrmsLocationWardService.deleteLocationWard(id);

	}

	@GetMapping(value = "/getAllLocationWard")
	public ResponseEntity<List<HrmsLocationWard>> listLocationWard() {
		return hrmsLocationWardService.listLocationWard();

	}

	@GetMapping(value = "/getAllLocationWardByDistrictId/{districtId}")
	public ResponseEntity<List<HrmsLocationWard>> listLocationWardByDistrictId(
			@PathVariable("districtId") int districtId) {
		return hrmsLocationWardService.listLocationWardByDistrictId(districtId);
	}

}
