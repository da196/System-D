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

import com.Hrms.Perfomance.DTO.PerformanceEppaObjectiveResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Service.HrmsPerformanceEppaObjectiveService;

@RestController
@RequestMapping("v1/performanceEppaObjective")
public class HrmsPerformanceEppaObjectiveController {

	@Autowired
	private HrmsPerformanceEppaObjectiveService hrmsPerformanceEppaObjectiveService;

	@PostMapping(value = "/addPerformanceEppaObjective")
	public ResponseEntity<HrmsPerformanceEppaObjective> addPerformanceEppaObjective(
			@RequestBody HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective) {

		return hrmsPerformanceEppaObjectiveService.addPerformanceEppaObjective(hrmsPerformanceEppaObjective);
	}

	@GetMapping(value = "/getPerformanceEppaObjectiveById/{id}")
	public ResponseEntity<PerformanceEppaObjectiveResponse> getPerformanceEppaObjectiveById(
			@PathVariable("id") int id) {

		return hrmsPerformanceEppaObjectiveService.getPerformanceEppaObjectiveById(id);

	}

	@PutMapping(value = "/updatePerformanceEppaObjective/{id}")
	public ResponseEntity<HrmsPerformanceEppaObjective> updatePerformanceEppaObjective(
			@RequestBody HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective, @PathVariable("id") int id) {

		return hrmsPerformanceEppaObjectiveService.updatePerformanceEppaObjective(hrmsPerformanceEppaObjective, id);

	}

	@DeleteMapping(value = "/deletePerformanceEppaObjective/{id}")
	public ResponseEntity<?> deletePerformanceEppaObjective(@PathVariable("id") int id) {

		return hrmsPerformanceEppaObjectiveService.deletePerformanceEppaObjective(id);

	}

	@GetMapping(value = "/getAllPerformanceEppaObjective")
	public ResponseEntity<List<PerformanceEppaObjectiveResponse>> getAllPerformanceEppaObjective() {

		return hrmsPerformanceEppaObjectiveService.getAllPerformanceEppaObjective();

	}

	@GetMapping(value = "/getAllPerformanceEppaObjectiveByEppaId/{eppaid}")
	public ResponseEntity<List<PerformanceEppaObjectiveResponse>> getAllPerformanceEppaObjectiveByEppaId(
			@PathVariable("eppaid") int eppaid) {

		return hrmsPerformanceEppaObjectiveService.getAllPerformanceEppaObjectiveByEppaId(eppaid);

	}

}
