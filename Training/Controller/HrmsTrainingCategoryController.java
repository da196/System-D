package com.Hrms.Training.Controller;

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

import com.Hrms.Training.Entity.HrmsTrainingCategory;
import com.Hrms.Training.Service.HrmsTrainingCategoryService;

@RestController
@RequestMapping("v1/trainingCategory")
public class HrmsTrainingCategoryController {

	@Autowired
	private HrmsTrainingCategoryService hrmsTrainingCategoryService;

	@GetMapping(value = "/getAllTrainingCategory")
	public ResponseEntity<List<HrmsTrainingCategory>> getAllTrainingCategory() {

		return hrmsTrainingCategoryService.getAllTrainingCategory();
	}

	@PostMapping(value = "/addTrainingCategory")
	public ResponseEntity<HrmsTrainingCategory> addTrainingCategory(
			@RequestBody HrmsTrainingCategory hrmsTrainingCategory) {

		return hrmsTrainingCategoryService.addTrainingCategory(hrmsTrainingCategory);

	}

	@GetMapping(value = "/getTrainingCategoryById/{id}")
	public ResponseEntity<HrmsTrainingCategory> getTrainingCategoryById(@PathVariable("id") int id) {

		return hrmsTrainingCategoryService.getTrainingCategoryById(id);

	}

	@PutMapping(value = "/updateTrainingCategory/{id}")
	public ResponseEntity<HrmsTrainingCategory> updateTrainingCategory(
			@RequestBody HrmsTrainingCategory hrmsTrainingCategory, @PathVariable("id") int id) {
		return hrmsTrainingCategoryService.updateTrainingCategory(hrmsTrainingCategory, id);
	}

	@DeleteMapping(value = "/deleteTrainingCategory/{id}")
	public ResponseEntity<?> deleteTrainingCategory(@PathVariable("id") int id) {

		return hrmsTrainingCategoryService.deleteTrainingCategory(id);

	}

}
