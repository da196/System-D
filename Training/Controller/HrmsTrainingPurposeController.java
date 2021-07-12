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

import com.Hrms.Training.Entity.HrmsTrainingPurpose;
import com.Hrms.Training.Service.HrmsTrainingPurposeService;

@RestController
@RequestMapping("v1/trainingPurpose")
public class HrmsTrainingPurposeController {

	@Autowired
	private HrmsTrainingPurposeService hrmsTrainingPurposeService;

	@GetMapping(value = "/getAllTrainingPurpose")
	public ResponseEntity<List<HrmsTrainingPurpose>> getAllTrainingPurpose() {

		return hrmsTrainingPurposeService.getAllTrainingPurpose();

	}

	@PostMapping(value = "/addTrainingPurpose")
	public ResponseEntity<HrmsTrainingPurpose> addTrainingPurpose(
			@RequestBody HrmsTrainingPurpose hrmsTrainingPurpose) {

		return hrmsTrainingPurposeService.addTrainingPurpose(hrmsTrainingPurpose);
	}

	@GetMapping(value = "/getTrainingPurposeById/{id}")
	public ResponseEntity<HrmsTrainingPurpose> getTrainingPurposeById(@PathVariable("id") int id) {

		return hrmsTrainingPurposeService.getTrainingPurposeById(id);

	}

	@PutMapping(value = "/updateTrainingPurpose/{id}")
	public ResponseEntity<HrmsTrainingPurpose> updateTrainingPurpose(
			@RequestBody HrmsTrainingPurpose hrmsTrainingPurpose, @PathVariable("id") int id) {

		return hrmsTrainingPurposeService.updateTrainingPurpose(hrmsTrainingPurpose, id);
	}

	@DeleteMapping(value = "/deleteTrainingPurpose/{id}")
	public ResponseEntity<?> deleteTrainingPurpose(int id) {

		return hrmsTrainingPurposeService.deleteTrainingPurpose(id);

	}

}
