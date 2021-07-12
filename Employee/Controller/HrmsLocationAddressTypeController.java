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

import com.Hrms.Employee.Entity.HrmsLocationAddressType;
import com.Hrms.Employee.Service.HrmsLocationAddressTypeService;

@RestController
@RequestMapping("/v1/locationAddressType")
public class HrmsLocationAddressTypeController {

	@Autowired
	private HrmsLocationAddressTypeService hrmsLocationAddressTypeService;

	@PostMapping(value = "/addLocationAddressType")
	public ResponseEntity<HrmsLocationAddressType> addLocationAddressType(
			@RequestBody HrmsLocationAddressType hrmsLocationAddressType) {

		return hrmsLocationAddressTypeService.addLocationAddressType(hrmsLocationAddressType);

	}

	@GetMapping(value = "/getLocationAddressType/{id}")
	public ResponseEntity<Optional<HrmsLocationAddressType>> getLocationAddressType(@PathVariable("id") int id) {

		return hrmsLocationAddressTypeService.getLocationAddressType(id);

	}

	@PutMapping(value = "/updateLocationAddressType/{id}")
	public ResponseEntity<HrmsLocationAddressType> updateLocationAddressType(
			@RequestBody HrmsLocationAddressType hrmsLocationAddressType, @PathVariable("id") int id) {

		return hrmsLocationAddressTypeService.updateLocationAddressType(hrmsLocationAddressType, id);

	}

	@DeleteMapping(value = "/deleteLocationAddressType/{id}")
	public ResponseEntity<?> deleteLocationAddressType(@PathVariable("id") int id) {

		return hrmsLocationAddressTypeService.deleteLocationAddressType(id);

	}

	@GetMapping(value = "/getAllLocationAddressType")
	public ResponseEntity<List<HrmsLocationAddressType>> listLocationAddressType() {

		return hrmsLocationAddressTypeService.listLocationAddressType();

	}

}
