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

import com.Hrms.Employee.Entity.HrmsInsuranceBeneficiaryType;
import com.Hrms.Employee.Service.HrmsInsuranceBeneficiaryTypeService;

@RestController
@RequestMapping("v1/insuranceBeneficiaryType")
public class HrmsInsuranceBeneficiaryTypeController {

	@Autowired
	private HrmsInsuranceBeneficiaryTypeService hrmsInsuranceBeneficiaryTypeService;

	@PostMapping(value = "/addInsuranceBeneficiaryType")
	public ResponseEntity<HrmsInsuranceBeneficiaryType> addInsuranceBeneficiaryType(
			@RequestBody HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType) {
		return hrmsInsuranceBeneficiaryTypeService.addInsuranceBeneficiaryType(hrmsInsuranceBeneficiaryType);
	}

	@GetMapping(value = "/getInsuranceBeneficiaryType/{id}")
	public ResponseEntity<Optional<HrmsInsuranceBeneficiaryType>> getInsuranceBeneficiaryType(
			@PathVariable("id") int id) {

		return hrmsInsuranceBeneficiaryTypeService.getInsuranceBeneficiaryType(id);

	}

	@PutMapping(value = "/updateInsuranceBeneficiaryType/{id}")
	public ResponseEntity<HrmsInsuranceBeneficiaryType> updateInsuranceBeneficiaryType(
			@RequestBody HrmsInsuranceBeneficiaryType hrmsInsuranceBeneficiaryType, @PathVariable("id") int id) {
		return hrmsInsuranceBeneficiaryTypeService.updateInsuranceBeneficiaryType(hrmsInsuranceBeneficiaryType, id);

	}

	@DeleteMapping(value = "/deleteInsuranceBeneficiaryType/{id}")
	public ResponseEntity<?> deleteInsuranceBeneficiaryType(@PathVariable("id") int id) {
		return hrmsInsuranceBeneficiaryTypeService.deleteInsuranceBeneficiaryType(id);

	}

	@GetMapping(value = "/getAllInsuranceBeneficiaryType")
	public ResponseEntity<List<HrmsInsuranceBeneficiaryType>> listInsuranceBeneficiaryType() {

		return hrmsInsuranceBeneficiaryTypeService.listInsuranceBeneficiaryType();

	}

}
