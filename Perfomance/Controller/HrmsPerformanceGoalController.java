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

import com.Hrms.Perfomance.DTO.PerformanceGoalResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;
import com.Hrms.Perfomance.Service.HrmsPerformanceGoalService;

@RestController
@RequestMapping("v1/performanceGoal")
public class HrmsPerformanceGoalController {

	@Autowired
	private HrmsPerformanceGoalService hrmsPerformanceGoalService;

	@PostMapping(value = "/addPerformanceGoal")
	public ResponseEntity<HrmsPerformanceGoal> addPerformanceGoal(
			@RequestBody HrmsPerformanceGoal hrmsPerformanceGoal) {
		return hrmsPerformanceGoalService.addPerformanceGoal(hrmsPerformanceGoal);

	}

	@GetMapping(value = "/getPerformanceGoalById/{id}")
	public ResponseEntity<PerformanceGoalResponse> getPerformanceGoalById(@PathVariable("id") int id) {

		return hrmsPerformanceGoalService.getPerformanceGoalById(id);

	}

	@PutMapping(value = "/updatePerformanceGoal/{id}")
	public ResponseEntity<HrmsPerformanceGoal> updatePerformanceGoal(
			@RequestBody HrmsPerformanceGoal hrmsPerformanceGoal, @PathVariable("id") int id) {

		return hrmsPerformanceGoalService.updatePerformanceGoal(hrmsPerformanceGoal, id);

	}

	@DeleteMapping(value = "/deletePerformanceGoal/{id}")
	public ResponseEntity<?> deletePerformanceGoal(@PathVariable("id") int id) {

		return hrmsPerformanceGoalService.deletePerformanceGoal(id);

	}

	@GetMapping(value = "/getAllPerformanceGoal")
	public ResponseEntity<List<PerformanceGoalResponse>> getAllPerformanceGoal() {

		return hrmsPerformanceGoalService.getAllPerformanceGoal();

	}

	@GetMapping(value = "/getAllPerformanceGoalByPlanId/{planid}")
	public ResponseEntity<List<PerformanceGoalResponse>> getAllPerformanceGoalByPlanId(
			@PathVariable("planid") int planid) {

		return hrmsPerformanceGoalService.getAllPerformanceGoalByPlanId(planid);

	}

}
