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

import com.Hrms.Perfomance.DTO.PerformanceEppaObjectiveRevisedResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjectiveRevised;
import com.Hrms.Perfomance.Service.HrmsPerformanceEppaObjectiveRevisedService;

@RestController
@RequestMapping("v1/performanceEppaObjectiveRevised")
public class HrmsPerformanceEppaObjectiveRevisedController {
	@Autowired
	private HrmsPerformanceEppaObjectiveRevisedService hrmsPerformanceEppaObjectiveRevisedService;

	@PostMapping(value = "/addPerformanceEppaObjectiveRevised")
	public ResponseEntity<HrmsPerformanceEppaObjectiveRevised> addPerformanceEppaObjectiveRevised(
			@RequestBody HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised) {

		return hrmsPerformanceEppaObjectiveRevisedService
				.addPerformanceEppaObjectiveRevised(hrmsPerformanceEppaObjectiveRevised);

	}

	@GetMapping(value = "/getPerformanceEppaObjectiveRevisedById/{id}")
	public ResponseEntity<PerformanceEppaObjectiveRevisedResponse> getPerformanceEppaObjectiveRevisedById(
			@PathVariable("id") int id) {

		return hrmsPerformanceEppaObjectiveRevisedService.getPerformanceEppaObjectiveRevisedById(id);

	}

	@PutMapping(value = "/updatePerformanceEppaObjectiveRevised/{id}")
	public ResponseEntity<HrmsPerformanceEppaObjectiveRevised> updatePerformanceEppaObjectiveRevised(
			@RequestBody HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised,
			@PathVariable("id") int id) {

		return hrmsPerformanceEppaObjectiveRevisedService
				.updatePerformanceEppaObjectiveRevised(hrmsPerformanceEppaObjectiveRevised, id);

	}

	@DeleteMapping(value = "/deletePerformanceEppaObjectiveRevised/{id}")
	public ResponseEntity<?> deletePerformanceEppaObjectiveRevised(@PathVariable("id") int id) {

		return hrmsPerformanceEppaObjectiveRevisedService.deletePerformanceEppaObjectiveRevised(id);

	}

	@GetMapping(value = "/getAllPerformanceEppaObjectiveRevised")
	public ResponseEntity<List<PerformanceEppaObjectiveRevisedResponse>> getAllPerformanceEppaObjectiveRevised() {

		return hrmsPerformanceEppaObjectiveRevisedService.getAllPerformanceEppaObjectiveRevised();

	}

	@GetMapping(value = "/getAllPerformanceEppaObjectiveRevisedByObjectiveId/{objectiveid}")
	public ResponseEntity<List<PerformanceEppaObjectiveRevisedResponse>> getAllPerformanceEppaObjectiveRevisedByObjectiveId(
			@PathVariable("objectiveid") int objectiveid) {

		return hrmsPerformanceEppaObjectiveRevisedService
				.getAllPerformanceEppaObjectiveRevisedByObjectiveId(objectiveid);

	}

}
