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

import com.Hrms.Training.Entity.HrmsTrainingType;
import com.Hrms.Training.Service.HrmsTrainingTypeService;

@RestController
@RequestMapping("v1/trainingType")
public class HrmsTrainingTypeController {

	@Autowired
	private HrmsTrainingTypeService hrmsTrainingTypeService;

	@GetMapping(value = "/getAllTrainingType")
	public ResponseEntity<List<HrmsTrainingType>> getAllTrainingType() {

		return hrmsTrainingTypeService.getAllTrainingType();
	}

	@PostMapping(value = "/addTrainingType")
	public ResponseEntity<HrmsTrainingType> addTrainingType(@RequestBody HrmsTrainingType hrmsTrainingType) {

		return hrmsTrainingTypeService.addTrainingType(hrmsTrainingType);

	}

	@GetMapping(value = "/getTrainingTypeById/{id}")
	public ResponseEntity<HrmsTrainingType> getTrainingTypeById(@PathVariable("id") int id) {

		return hrmsTrainingTypeService.getTrainingTypeById(id);

	}

	@PutMapping(value = "/updateTrainingType/{id}")
	public ResponseEntity<HrmsTrainingType> updateTrainingType(@RequestBody HrmsTrainingType hrmsTrainingType,
			@PathVariable("id") int id) {

		return hrmsTrainingTypeService.updateTrainingType(hrmsTrainingType, id);

	}

	@DeleteMapping(value = "/deleteTrainingType/{id}")
	public ResponseEntity<?> deleteTrainingType(@PathVariable("id") int id) {

		return hrmsTrainingTypeService.deleteTrainingType(id);

	}

}
