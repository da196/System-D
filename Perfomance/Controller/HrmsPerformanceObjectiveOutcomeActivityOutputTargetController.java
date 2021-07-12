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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputTargetResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveOutcomeActivityOutputTargetService;

@RestController
@RequestMapping("v1/performanceObjectiveOutcomeActivityOutputTarget")
public class HrmsPerformanceObjectiveOutcomeActivityOutputTargetController {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputTargetService hrmsPerformanceObjectiveOutcomeActivityOutputTargetService;

	@PostMapping(value = "/addPerformanceObjectiveOutcomeActivityOutputTarget")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> addPerformanceObjectiveOutcomeActivityOutputTarget(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputTargetService
				.addPerformanceObjectiveOutcomeActivityOutputTarget(
						hrmsPerformanceObjectiveOutcomeActivityOutputTarget);

	}

	@GetMapping(value = "/getPerformanceObjectiveOutcomeActivityOutputTargetById/{id}")
	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputTargetResponse> getPerformanceObjectiveOutcomeActivityOutputTargetById(
			@PathVariable("id") int id) {
		return hrmsPerformanceObjectiveOutcomeActivityOutputTargetService
				.getPerformanceObjectiveOutcomeActivityOutputTargetById(id);
	}

	@PutMapping(value = "/updatePerformanceObjectiveOutcomeActivityOutputTarget/{id}")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> updatePerformanceObjectiveOutcomeActivityOutputTarget(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget,
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputTargetService
				.updatePerformanceObjectiveOutcomeActivityOutputTarget(
						hrmsPerformanceObjectiveOutcomeActivityOutputTarget, id);

	}

	@DeleteMapping(value = "/deletePerformanceObjectiveOutcomeActivityOutputTarget/{id}")
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutputTarget(@PathVariable("id") int id) {
		return hrmsPerformanceObjectiveOutcomeActivityOutputTargetService
				.deletePerformanceObjectiveOutcomeActivityOutputTarget(id);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputTarget")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputTargetResponse>> getAllPerformanceObjectiveOutcomeActivityOutputTarget() {

		return hrmsPerformanceObjectiveOutcomeActivityOutputTargetService
				.getAllPerformanceObjectiveOutcomeActivityOutputTarget();
	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputByOutputid/{outputid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputTargetResponse>> getAllPerformanceObjectiveOutcomeActivityOutputByOutputid(
			@PathVariable("outputid") int outputid) {
		return hrmsPerformanceObjectiveOutcomeActivityOutputTargetService
				.getAllPerformanceObjectiveOutcomeActivityOutputByOutputid(outputid);

	}

}
