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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveTargetResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveTarget;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveTargetService;

@RestController
@RequestMapping("v1/performanceObjectiveTarget")
public class HrmsPerformanceObjectiveTargetController {

	@Autowired
	private HrmsPerformanceObjectiveTargetService hrmsPerformanceObjectiveTargetService;

	@PostMapping(value = "/addPerformanceObjectiveTarget")
	public ResponseEntity<HrmsPerformanceObjectiveTarget> addPerformanceObjectiveTarget(
			@RequestBody HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTarget) {

		return hrmsPerformanceObjectiveTargetService.addPerformanceObjectiveTarget(hrmsPerformanceObjectiveTarget);

	}

	@GetMapping(value = "/getPerformanceObjectiveTargetById/{id}")
	public ResponseEntity<PerformanceObjectiveTargetResponse> getPerformanceObjectiveTargetById(
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveTargetService.getPerformanceObjectiveTargetById(id);

	}

	@PutMapping(value = "/updatePerformanceObjectiveTarget/{id}")
	public ResponseEntity<HrmsPerformanceObjectiveTarget> updatePerformanceObjectiveTarget(
			@RequestBody HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTarget, @PathVariable("id") int id) {

		return hrmsPerformanceObjectiveTargetService.updatePerformanceObjectiveTarget(hrmsPerformanceObjectiveTarget,
				id);

	}

	@DeleteMapping(value = "/deletePerformanceObjectiveTarget/{id}")
	public ResponseEntity<?> deletePerformanceObjectiveTarget(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveTargetService.deletePerformanceObjectiveTarget(id);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveTarget")
	public ResponseEntity<List<PerformanceObjectiveTargetResponse>> getAllPerformanceObjectiveTarget() {

		return hrmsPerformanceObjectiveTargetService.getAllPerformanceObjectiveTarget();

	}

	@GetMapping(value = "/getAllPerformanceObjectiveTargetByObjectiveId/{}objectiveid")
	public ResponseEntity<List<PerformanceObjectiveTargetResponse>> getAllPerformanceObjectiveTargetByObjectiveId(
			@PathVariable("objectiveid") int objectiveid) {

		return hrmsPerformanceObjectiveTargetService.getAllPerformanceObjectiveTargetByObjectiveId(objectiveid);

	}

}
