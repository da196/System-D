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

import com.Hrms.Perfomance.DTO.PerformanceEppaReviewMidYearResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewMidYear;
import com.Hrms.Perfomance.Service.HrmsPerformanceEppaReviewMidYearService;

@RestController
@RequestMapping("v1/performanceEppaReviewMidYear")
public class HrmsPerformanceEppaReviewMidYearController {

	@Autowired
	private HrmsPerformanceEppaReviewMidYearService hrmsPerformanceEppaReviewMidYearService;

	@PostMapping(value = "/addPerformanceEppaReviewMidYear")
	public ResponseEntity<HrmsPerformanceEppaReviewMidYear> addPerformanceEppaReviewMidYear(
			@RequestBody HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear) {

		return hrmsPerformanceEppaReviewMidYearService
				.addPerformanceEppaReviewMidYear(hrmsPerformanceEppaReviewMidYear);

	}

	@GetMapping(value = "/getPerformanceEppaReviewMidYearById/{id}")
	public ResponseEntity<PerformanceEppaReviewMidYearResponse> getPerformanceEppaReviewMidYearById(
			@PathVariable("id") int id) {

		return hrmsPerformanceEppaReviewMidYearService.getPerformanceEppaReviewMidYearById(id);

	}

	@PutMapping(value = "/updatePerformanceEppaReviewMidYear/{id}")
	public ResponseEntity<HrmsPerformanceEppaReviewMidYear> updatePerformanceEppaReviewMidYear(
			@RequestBody HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear,
			@PathVariable("id") int id) {

		return hrmsPerformanceEppaReviewMidYearService
				.updatePerformanceEppaReviewMidYear(hrmsPerformanceEppaReviewMidYear, id);

	}

	@DeleteMapping(value = "/deletePerformanceEppaReviewMidYear/{id}")
	public ResponseEntity<?> deletePerformanceEppaReviewMidYear(@PathVariable("id") int id) {

		return hrmsPerformanceEppaReviewMidYearService.deletePerformanceEppaReviewMidYear(id);

	}

	@GetMapping(value = "/getAllPerformanceEppaReviewMidYear/")
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYear() {

		return hrmsPerformanceEppaReviewMidYearService.getAllPerformanceEppaReviewMidYear();

	}

	@GetMapping(value = "/getAllPerformanceEppaReviewMidYearByEppaId/{eppaid}")
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearByEppaId(
			@PathVariable("eppaid") int eppaid) {

		return hrmsPerformanceEppaReviewMidYearService.getAllPerformanceEppaReviewMidYearByEppaId(eppaid);
	}

	@GetMapping(value = "/getAllPerformanceEppaReviewMidYearByObjectiveId/{objectiveid}")
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearByObjectiveId(
			@PathVariable("objectiveid") int objectiveid) {

		return hrmsPerformanceEppaReviewMidYearService.getAllPerformanceEppaReviewMidYearByObjectiveId(objectiveid);

	}

	@GetMapping(value = "/getAllPerformanceEppaReviewMidYearBySupervisorDesigantionId/{supervisordesignationid}")
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearBySupervisorDesigantionId(
			@PathVariable("supervisordesignationid") int supervisordesignationid) {

		return hrmsPerformanceEppaReviewMidYearService
				.getAllPerformanceEppaReviewMidYearBySupervisorDesigantionId(supervisordesignationid);

	}

}
