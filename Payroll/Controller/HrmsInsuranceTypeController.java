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

import com.Hrms.Payroll.Entity.HrmsInsuranceType;
import com.Hrms.Payroll.Service.HrmsInsuranceTypeService;

@RestController
@RequestMapping("v1/insuranceType")
public class HrmsInsuranceTypeController {

	@Autowired

	private HrmsInsuranceTypeService hrmsInsuranceTypeService;

	@PostMapping(value = "/addInsuranceType")
	public ResponseEntity<HrmsInsuranceType> addInsuranceType(@RequestBody HrmsInsuranceType hrmsInsuranceType) {

		return hrmsInsuranceTypeService.addInsuranceType(hrmsInsuranceType);

	}

	@GetMapping(value = "/getInsuranceTypeById/{id}")
	public ResponseEntity<HrmsInsuranceType> getInsuranceTypeById(@PathVariable("id") int id) {
		return hrmsInsuranceTypeService.getInsuranceTypeById(id);

	}

	@PutMapping(value = "/updateInsuranceType/{id}")
	public ResponseEntity<HrmsInsuranceType> updateInsuranceType(@RequestBody HrmsInsuranceType hrmsInsuranceType,
			@PathVariable("id") int id) {

		return hrmsInsuranceTypeService.updateInsuranceType(hrmsInsuranceType, id);

	}

	@DeleteMapping(value = "/deleteInsuranceType/{id}")
	public ResponseEntity<?> deleteInsuranceType(@PathVariable("id") int id) {
		return hrmsInsuranceTypeService.deleteInsuranceType(id);

	}

	@GetMapping(value = "/getAllInsuranceType")
	public ResponseEntity<List<HrmsInsuranceType>> getAllInsuranceType() {

		return hrmsInsuranceTypeService.getAllInsuranceType();

	}

}
