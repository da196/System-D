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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveOutcomeActivityOutputService;

@RestController
@RequestMapping("v1/performanceObjectiveOutcomeActivityOutput")
public class HrmsPerformanceObjectiveOutcomeActivityOutputController {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputService hrmsPerformanceObjectiveOutcomeActivityOutputService;

	@PostMapping(value = "/addPerformanceObjectiveOutcomeActivityOutput")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutput> addPerformanceObjectiveOutcomeActivityOutput(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputService
				.addPerformanceObjectiveOutcomeActivityOutput(hrmsPerformanceObjectiveOutcomeActivityOutput);

	}

	@GetMapping(value = "/getPerformanceObjectiveOutcomeActivityOutputById/{id}")
	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputResponse> getPerformanceObjectiveOutcomeActivityOutputById(
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputService
				.getPerformanceObjectiveOutcomeActivityOutputById(id);

	}

	@PutMapping(value = "/updatePerformanceObjectiveOutcomeActivityOutput/{id}")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutput> updatePerformanceObjectiveOutcomeActivityOutput(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput,
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputService
				.updatePerformanceObjectiveOutcomeActivityOutput(hrmsPerformanceObjectiveOutcomeActivityOutput, id);

	}

	@DeleteMapping(value = "/deletePerformanceObjectiveOutcomeActivityOutput/{id}")
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutput(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputService.deletePerformanceObjectiveOutcomeActivityOutput(id);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutput")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponse>> getAllPerformanceObjectiveOutcomeActivityOutput() {

		return hrmsPerformanceObjectiveOutcomeActivityOutputService.getAllPerformanceObjectiveOutcomeActivityOutput();

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputByActivityid/{activityid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponse>> getAllPerformanceObjectiveOutcomeActivityOutputByActivityid(
			@PathVariable("activityid") int activityid) {
		return hrmsPerformanceObjectiveOutcomeActivityOutputService
				.getAllPerformanceObjectiveOutcomeActivityOutputByActivityid(activityid);
	}

}
