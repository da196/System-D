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

import com.Hrms.Employee.Entity.HrmsRelativeCategory;
import com.Hrms.Employee.Service.HrmsRelativeCategoryService;

@RestController
@RequestMapping("v1/relativeCategory")
public class HrmsRelativeCategoryController {

	@Autowired
	private HrmsRelativeCategoryService hrmsRelativeCategoryService;

	@PostMapping(value = "/addRelativeCategory")
	public ResponseEntity<HrmsRelativeCategory> addRelativeCategory(
			@RequestBody HrmsRelativeCategory HrmsRelativeCategory) {
		return hrmsRelativeCategoryService.addRelativeCategory(HrmsRelativeCategory);

	}

	@GetMapping(value = "/getRelativeCategory/{id}")
	public ResponseEntity<Optional<HrmsRelativeCategory>> getRelativeCategory(@PathVariable("id") int id) {
		return hrmsRelativeCategoryService.getHrmsRelativeCategory(id);

	}

	@PutMapping(value = "/updateRelativeCategory/{id}")
	public ResponseEntity<HrmsRelativeCategory> updateRelativeCategory(
			@RequestBody HrmsRelativeCategory hrmsRelativeCategory, @PathVariable("id") int id) {
		return hrmsRelativeCategoryService.updateRelativeCategory(hrmsRelativeCategory, id);
	}

	@DeleteMapping(value = "/deleteRelativeCategory/{id}")
	public ResponseEntity<?> deleteRelativeCategory(@PathVariable("id") int id) {
		return hrmsRelativeCategoryService.deleteRelativeCategory(id);
	}

	@GetMapping(value = "/getAllRelativeCategory")
	public ResponseEntity<List<HrmsRelativeCategory>> listRelativeCategory() {

		return hrmsRelativeCategoryService.listRelativeCategory();

	}

}
