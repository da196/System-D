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

import com.Hrms.Training.Entity.HrmsTrainingInitiator;
import com.Hrms.Training.Service.HrmsTrainingInitiatorService;

@RestController
@RequestMapping("v1/trainingInitiator")
public class HrmsTrainingInitiatorController {

	@Autowired
	private HrmsTrainingInitiatorService hrmsTrainingInitiatorService;

	@GetMapping(value = "/getAllTrainingInitiator")
	public ResponseEntity<List<HrmsTrainingInitiator>> getAllTrainingInitiator() {

		return hrmsTrainingInitiatorService.getAllTrainingInitiator();
	}

	@PostMapping(value = "/addTrainingInitiator")
	public ResponseEntity<HrmsTrainingInitiator> addTrainingInitiator(
			@RequestBody HrmsTrainingInitiator hrmsTrainingInitiator) {

		return hrmsTrainingInitiatorService.addTrainingInitiator(hrmsTrainingInitiator);
	}

	@GetMapping(value = "/getTrainingInitiatorById/{id}")
	public ResponseEntity<HrmsTrainingInitiator> getTrainingInitiatorById(@PathVariable("id") int id) {
		return hrmsTrainingInitiatorService.getTrainingInitiatorById(id);

	}

	@PutMapping(value = "/updateTrainingInitiator/{id}")
	public ResponseEntity<HrmsTrainingInitiator> updateTrainingInitiator(
			@RequestBody HrmsTrainingInitiator hrmsTrainingInitiator, @PathVariable("id") int id) {

		return hrmsTrainingInitiatorService.updateTrainingInitiator(hrmsTrainingInitiator, id);

	}

	@DeleteMapping(value = "/deleteTrainingInitiator/{id}")
	public ResponseEntity<?> deleteTrainingInitiator(@PathVariable("id") int id) {

		return hrmsTrainingInitiatorService.deleteTrainingInitiator(id);

	}

}
