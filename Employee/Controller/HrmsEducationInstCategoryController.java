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

import com.Hrms.Employee.Entity.HrmsEducationInstCategory;
import com.Hrms.Employee.Service.HrmsEducationInstCategoryService;

@RestController
@RequestMapping("/v1/educationInstCategory")
public class HrmsEducationInstCategoryController {

	@Autowired
	private HrmsEducationInstCategoryService hrmsEducationInstCategoryService;

	@PostMapping(value = "/addEducationInstCategory")
	public ResponseEntity<HrmsEducationInstCategory> addEducationInstCategory(
			@RequestBody HrmsEducationInstCategory hrmsEducationInstCategory) {

		return hrmsEducationInstCategoryService.save(hrmsEducationInstCategory);

	}

	@GetMapping(value = "/getEducationInstCategory/{id}")
	public ResponseEntity<Optional<HrmsEducationInstCategory>> getEducationInstCategory(@PathVariable("id") int id) {
		return hrmsEducationInstCategoryService.viewHrmsEducationInstCategory(id);

	}

	@PutMapping(value = "/updateEducationInstCategory/{id}")
	public ResponseEntity<HrmsEducationInstCategory> updateEducationInstCategory(
			@RequestBody HrmsEducationInstCategory hrmsEducationInstCategory, @PathVariable("id") int id) {

		return hrmsEducationInstCategoryService.update(hrmsEducationInstCategory, id);

	}

	@DeleteMapping(value = "/deleteEducationInstCategory/{id}")
	public ResponseEntity<?> deleteEducationInstCategory(@PathVariable("id") int id) {

		return hrmsEducationInstCategoryService.deleteHrmsEducationInstCategory(id);

	}

	@GetMapping(value = "/getAllEducationInstCategory")
	public ResponseEntity<List<HrmsEducationInstCategory>> listEducationInstCategory() {

		return hrmsEducationInstCategoryService.listHrmsEducationInstCategory();

	}

}
