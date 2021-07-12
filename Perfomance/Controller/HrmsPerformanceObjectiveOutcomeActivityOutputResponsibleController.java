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

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputResponsibleResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputResponsible;
import com.Hrms.Perfomance.Service.HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService;

@RestController
@RequestMapping("v1/performanceObjectiveOutcomeActivityOutputResponsible")
public class HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleController {

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService;

	@PostMapping(value = "/addPerformanceObjectiveOutcomeActivityOutputResponsible")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> addPerformanceObjectiveOutcomeActivityOutputResponsible(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivityOutputResponsible hrmsPerformanceObjectiveOutcomeActivityOutputResponsible) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.addPerformanceObjectiveOutcomeActivityOutputResponsible(
						hrmsPerformanceObjectiveOutcomeActivityOutputResponsible);

	}

	@GetMapping(value = "/getPerformanceObjectiveOutcomeActivityOutputResponsibleById/{id}")
	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> getPerformanceObjectiveOutcomeActivityOutputResponsibleById(
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.getPerformanceObjectiveOutcomeActivityOutputResponsibleById(id);

	}

	@PutMapping(value = "/updatePerformanceObjectiveOutcomeActivityOutputResponsible/{id}")
	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputResponsible> updatePerformanceObjectiveOutcomeActivityOutputResponsible(
			@RequestBody HrmsPerformanceObjectiveOutcomeActivityOutputResponsible hrmsPerformanceObjectiveOutcomeActivityOutputResponsible,
			@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.updatePerformanceObjectiveOutcomeActivityOutputResponsible(
						hrmsPerformanceObjectiveOutcomeActivityOutputResponsible, id);

	}

	@DeleteMapping(value = "/deletePerformanceObjectiveOutcomeActivityOutputResponsible/{id}")
	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutputResponsible(@PathVariable("id") int id) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.deletePerformanceObjectiveOutcomeActivityOutputResponsible(id);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputResponsible")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsible() {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.getAllPerformanceObjectiveOutcomeActivityOutputResponsible();

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetid/{targetid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetid(
			@PathVariable("targetid") int targetid) {
		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetid(targetid);

	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetidAndUnitId/{targetid}/{unitid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetidAndUnitId(
			@PathVariable("targetid") int targetid, @PathVariable("unitid") int unitid) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByTargetIdAndUnitId(targetid, unitid);
	}

	@GetMapping(value = "/getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByUnitid/{unitid}")
	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse>> getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByUnitid(
			@PathVariable("unitid") int unitid) {

		return hrmsPerformanceObjectiveOutcomeActivityOutputResponsibleService
				.getAllPerformanceObjectiveOutcomeActivityOutputResponsibleByUnitid(unitid);

	}
}
