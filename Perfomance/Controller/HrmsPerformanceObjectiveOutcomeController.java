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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveOutcomeService;

@RestController
@RequestMapping("v1/performanceObjectiveOutcome")
public class HrmsPerformanceObjectiveOutcomeController {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeService hrmsPerformanceObjectiveOutcomeService;

	@PostMapping(value = "/addPerformanceObjectiveOutcome")
	public ResponseEntity<HrmsPerformanceObjectiveOutcome> addPerformanceObjectiveOutcome(
			@RequestBody HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome) {

		return hrmsPerformanceObjectiveOutcomeService.addPerformanceObjectiveOutcome(hrmsPerformanceObjectiveOutcome);
	}

	@GetMapping(value = "/getPerformanceObjectiveOutcomeById/{id}")
	public ResponseEntity<PerformanceObjectiveOutcomeResponse> getPerformanceObjectiveOutcomeById(
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeService.getPerformanceObjectiveOutcomeById(id);

	}

	@PutMapping(value = "/updatePerformanceObjectiveOutcome/{id}")
	public ResponseEntity<HrmsPerformanceObjectiveOutcome> updatePerformanceObjectiveOutcome(
			@RequestBody HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome, @PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeService.updatePerformanceObjectiveOutcome(hrmsPerformanceObjectiveOutcome,
				id);

	}

	@DeleteMapping(value = "/deletePerformanceObjectiveOutcome/{id}")
	public ResponseEntity<?> deletePerformanceObjectiveOutcome(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeService.deletePerformanceObjectiveOutcome(id);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcome")
	public ResponseEntity<List<PerformanceObjectiveOutcomeResponse>> getAllPerformanceObjectiveOutcome() {

		return hrmsPerformanceObjectiveOutcomeService.getAllPerformanceObjectiveOutcome();

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeByObjectiveId/{objectiveid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeResponse>> getAllPerformanceObjectiveOutcomeByObjectiveId(
			@PathVariable("objectiveid") int objectiveid) {

		return hrmsPerformanceObjectiveOutcomeService.getAllPerformanceObjectiveOutcomeByObjectiveId(objectiveid);

	}

}
