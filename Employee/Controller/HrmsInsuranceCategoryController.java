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

import com.Hrms.Employee.Entity.HrmsInsuranceCategory;
import com.Hrms.Employee.Service.HrmsInsuranceCategoryService;

@RestController
@RequestMapping("v1/insuranceCategory")
public class HrmsInsuranceCategoryController {

	@Autowired
	private HrmsInsuranceCategoryService hrmsInsuranceCategoryService;

	@PostMapping(value = "/addInsuranceCategory")
	public ResponseEntity<HrmsInsuranceCategory> addInsuranceCategory(
			@RequestBody HrmsInsuranceCategory hrmsInsuranceCategory) {

		return hrmsInsuranceCategoryService.save(hrmsInsuranceCategory);

	}

	@GetMapping(value = "/getInsuranceCategory/{id}")
	public ResponseEntity<Optional<HrmsInsuranceCategory>> getInsuranceCategory(@PathVariable("id") int id) {
		return hrmsInsuranceCategoryService.getHrmsInsuranceCategory(id);

	}

	@PutMapping(value = "/updateInsuranceCategory/{id}")
	public ResponseEntity<HrmsInsuranceCategory> updateInsuranceCategory(
			@RequestBody HrmsInsuranceCategory hrmsInsuranceCategory, @PathVariable("id") int id) {
		return hrmsInsuranceCategoryService.updateInsuranceCategory(hrmsInsuranceCategory, id);

	}

	@DeleteMapping(value = "/deleteInsuranceCategory/{id}")
	public ResponseEntity<?> deleteInsuranceCategory(@PathVariable("id") int id) {
		return hrmsInsuranceCategoryService.deleteInsuranceCategory(id);

	}

	@GetMapping(value = "/getAllInsuranceCategory")
	public ResponseEntity<List<HrmsInsuranceCategory>> listInsuranceCategory() {
		return hrmsInsuranceCategoryService.listInsuranceCategory();

	}

}
