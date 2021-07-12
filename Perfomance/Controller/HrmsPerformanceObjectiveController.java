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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveService;

@RestController
@RequestMapping("v1/performanceObjective")
public class HrmsPerformanceObjectiveController {

	@Autowired
	private HrmsPerformanceObjectiveService hrmsPerformanceObjectiveService;

	@PostMapping(value = "/addPerformanceObjective")
	public ResponseEntity<HrmsPerformanceObjective> addPerformanceObjective(
			@RequestBody HrmsPerformanceObjective hrmsPerformanceObjective) {

		return hrmsPerformanceObjectiveService.addPerformanceObjective(hrmsPerformanceObjective);

	}

	@GetMapping(value = "/getPerformanceObjectiveById/{id}")
	public ResponseEntity<PerformanceObjectiveResponse> getPerformanceObjectiveById(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveService.getPerformanceObjectiveById(id);

	}

	@PutMapping(value = "/updatePerformanceObjective/{id}")
	public ResponseEntity<HrmsPerformanceObjective> updatePerformanceObjective(
			@RequestBody HrmsPerformanceObjective hrmsPerformanceObjective, @PathVariable("id") int id) {

		return hrmsPerformanceObjectiveService.updatePerformanceObjective(hrmsPerformanceObjective, id);

	}

	@DeleteMapping(value = "/deletePerformanceObjective/{id}")
	public ResponseEntity<?> deletePerformanceObjective(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveService.deletePerformanceObjective(id);

	}

	@GetMapping(value = "/getAllPerformanceObjective")
	public ResponseEntity<List<PerformanceObjectiveResponse>> getAllPerformanceObjective() {

		return hrmsPerformanceObjectiveService.getAllPerformanceObjective();

	}

	@GetMapping(value = "/getAllPerformanceObjectiveByGoalId/{goalid}")
	public ResponseEntity<List<PerformanceObjectiveResponse>> getAllPerformanceObjectiveByGoalId(
			@PathVariable("goalid") int goalid) {

		return hrmsPerformanceObjectiveService.getAllPerformanceObjectiveByGoalId(goalid);

	}

}
