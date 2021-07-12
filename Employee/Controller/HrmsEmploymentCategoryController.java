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

import com.Hrms.Employee.Entity.HrmsEmploymentCategory;
import com.Hrms.Employee.Service.HrmsEmploymentCategoryService;

@RestController
@RequestMapping("/v1/employmentCategory")
public class HrmsEmploymentCategoryController {
	@Autowired
	private HrmsEmploymentCategoryService hrmsEmploymentCategoryService;

	@PostMapping(value = "/addEmploymentCategory")
	public ResponseEntity<HrmsEmploymentCategory> addEmploymentCategory(
			@RequestBody HrmsEmploymentCategory hrmsEmploymentCategory) {

		return hrmsEmploymentCategoryService.save(hrmsEmploymentCategory);

	}

	@GetMapping(value = "/getEmploymentCategory/{id}")
	public ResponseEntity<Optional<HrmsEmploymentCategory>> getEmploymentCategory(@PathVariable("id") int id) {

		return hrmsEmploymentCategoryService.viewHrmsEmploymentCategory(id);

	}

	@PutMapping(value = "/updateEmploymentCategory/{id}")
	public ResponseEntity<HrmsEmploymentCategory> updateEmploymentCategory(
			@RequestBody HrmsEmploymentCategory hrmsEmploymentCategory, @PathVariable("id") int id) {
		return hrmsEmploymentCategoryService.update(hrmsEmploymentCategory, id);

	}

	@DeleteMapping(value = "/deleteEmploymentCategory/{id}")
	public ResponseEntity<?> deleteEmploymentCategory(@PathVariable("id") int id) {

		return hrmsEmploymentCategoryService.deleteHrmsEmploymentCategory(id);

	}

	@GetMapping(value = "/getAllEmploymentCategory")
	public ResponseEntity<List<HrmsEmploymentCategory>> listEmploymentCategory() {

		return hrmsEmploymentCategoryService.listHrmsEmploymentCategory();

	}

}
