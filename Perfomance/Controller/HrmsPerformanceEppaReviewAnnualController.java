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

import com.Hrms.Perfomance.DTO.PerformanceEppaReviewAnnualResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;
import com.Hrms.Perfomance.Service.HrmsPerformanceEppaReviewAnnualService;

@RestController
@RequestMapping("v1/performanceEppaReviewAnnual")
public class HrmsPerformanceEppaReviewAnnualController {
	@Autowired
	private HrmsPerformanceEppaReviewAnnualService hrmsPerformanceEppaReviewAnnualService;

	@PostMapping(value = "/seilfAnnualReview")
	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> seilfAnnualReview(
			@RequestBody HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual) {

		return hrmsPerformanceEppaReviewAnnualService.seilfAnnualReview(hrmsPerformanceEppaReviewAnnual);

	}

	@PostMapping(value = "/supervisorAnnualReview/{id}")
	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> supervisorAnnualReview(
			@RequestBody HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual, @PathVariable("id") int id) {

		return hrmsPerformanceEppaReviewAnnualService.supervisorAnnualReview(hrmsPerformanceEppaReviewAnnual, id);

	}

	@GetMapping(value = "/getAnnualReviewById/{id}")
	public ResponseEntity<PerformanceEppaReviewAnnualResponse> getAnnualReviewById(@PathVariable("id") int id) {

		return hrmsPerformanceEppaReviewAnnualService.getAnnualReviewById(id);

	}

	@PutMapping(value = "/updateAnnualReview/{id}")
	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> updateAnnualReview(
			@RequestBody HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual, @PathVariable("id") int id) {

		return hrmsPerformanceEppaReviewAnnualService.updateAnnualReview(hrmsPerformanceEppaReviewAnnual, id);

	}

	@DeleteMapping(value = "/deleteAnnualReview/{id}")
	public ResponseEntity<?> deleteAnnualReview(@PathVariable("id") int id) {
		return hrmsPerformanceEppaReviewAnnualService.deleteAnnualReview(id);

	}

	@GetMapping(value = "/getAllAnnualReview")
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReview() {

		return hrmsPerformanceEppaReviewAnnualService.getAllAnnualReview();

	}

	@GetMapping(value = "/getAllAnnualReviewByEppaId/{eppaid}")
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewByEppaId(
			@PathVariable("eppaid") int eppaid) {

		return hrmsPerformanceEppaReviewAnnualService.getAllAnnualReviewByEppaId(eppaid);

	}

	@GetMapping(value = "/getAllAnnualReviewByObjectiveId/{objectiveid}")
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewByObjectiveId(
			@PathVariable("objectiveid") int objectiveid) {
		return hrmsPerformanceEppaReviewAnnualService.getAllAnnualReviewByObjectiveId(objectiveid);

	}

	@GetMapping(value = "/getAllAnnualReviewBySupervisorId/{supervisorid}")
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewBySupervisorId(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsPerformanceEppaReviewAnnualService.getAllAnnualReviewBySupervisorId(supervisorid);
	}

	@GetMapping(value = "/getAllAnnualReviewBySupervisorDesignationId/{supervisordesignationid}")
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewBySupervisorDesignationId(
			@PathVariable("supervisordesignationid") int supervisordesignationid) {

		return hrmsPerformanceEppaReviewAnnualService
				.getAllAnnualReviewBySupervisorDesignationId(supervisordesignationid);

	}

}
