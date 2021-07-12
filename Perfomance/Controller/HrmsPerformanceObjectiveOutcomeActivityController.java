package com.Hrms.Perfomance.Controller;

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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveOutcomeActivityService;

@RestController
@RequestMapping("v1/performanceObjectiveOutcomeActivity")
public class HrmsPerformanceObjectiveOutcomeActivityController {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityService hrmsPerformanceObjectiveOutcomeActivityService;

	@PostMapping(value = "/addPerformanceObjectiveOutcomeActivity")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivity> addPerformanceObjectiveOutcomeActivity(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity) {

		return hrmsPerformanceObjectiveOutcomeActivityService
				.addPerformanceObjectiveOutcomeActivity(hrmsPerformanceObjectiveOutcomeActivity);
	}

	@GetMapping(value = "/getPerformanceObjectiveOutcomeActivityById/{id}")
	public ResponseEntity<PerformanceObjectiveOutcomeActivityResponse> getPerformanceObjectiveOutcomeActivityById(
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityService.getPerformanceObjectiveOutcomeActivityById(id);

	}

	@PutMapping(value = "/updatePerformanceObjectiveOutcomeActivity/{id}")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivity> updatePerformanceObjectiveOutcomeActivity(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity,
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityService
				.updatePerformanceObjectiveOutcomeActivity(hrmsPerformanceObjectiveOutcomeActivity, id);

	}

	@DeleteMapping(value = "/deletePerformanceObjectiveOutcomeActivity/{id}")
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivity(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityService.deletePerformanceObjectiveOutcomeActivity(id);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivity")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityResponse>> getAllPerformanceObjectiveOutcomeActivity() {

		return hrmsPerformanceObjectiveOutcomeActivityService.getAllPerformanceObjectiveOutcomeActivity();

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityByObjectiveOutcomeId/{objectiveOutcomeid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityResponse>> getAllPerformanceObjectiveOutcomeActivityByObjectiveOutcomeId(
			@PathVariable("objectiveOutcomeid") int objectiveOutcomeid) {
		return hrmsPerformanceObjectiveOutcomeActivityService
				.getAllPerformanceObjectiveOutcomeActivityByObjectiveOutcomeId(objectiveOutcomeid);
	}

}
