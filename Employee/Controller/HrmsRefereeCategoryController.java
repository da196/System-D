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

import com.Hrms.Employee.Entity.HrmsRefereeCategory;
import com.Hrms.Employee.Service.HrmsRefereeCategoryService;

@RestController
@RequestMapping("v1/refereeCategory")
public class HrmsRefereeCategoryController {
	@Autowired
	private HrmsRefereeCategoryService hrmsRefereeCategoryService;

	@PostMapping(value = "/addRefereeCategory")
	public ResponseEntity<HrmsRefereeCategory> addRefereeCategory(
			@RequestBody HrmsRefereeCategory hrmsRefereeCategory) {
		return hrmsRefereeCategoryService.addRefereeCategory(hrmsRefereeCategory);

	}

	@GetMapping(value = "/getRefereeCategory/{id}")
	public ResponseEntity<Optional<HrmsRefereeCategory>> getRefereeCategory(@PathVariable("id") int id) {
		return hrmsRefereeCategoryService.getRefereeCategory(id);

	}

	@PutMapping(value = "/updateRefereeCategory/{id}")
	public ResponseEntity<HrmsRefereeCategory> updateRefereeCategory(
			@RequestBody HrmsRefereeCategory hrmsRefereeCategory, @PathVariable("id") int id) {

		return hrmsRefereeCategoryService.updateRefereeCategory(hrmsRefereeCategory, id);

	}

	@DeleteMapping(value = "/deleteRefereeCategory/{id}")
	public ResponseEntity<?> deleteRefereeCategory(@PathVariable("id") int id) {

		return hrmsRefereeCategoryService.deleteRefereeCategory(id);

	}

	@GetMapping(value = "/getAllRefereeCategory")
	public ResponseEntity<List<HrmsRefereeCategory>> listRefereeCategory() {

		return hrmsRefereeCategoryService.listRefereeCategory();

	}

}
