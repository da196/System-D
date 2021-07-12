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

import com.Hrms.Perfomance.DTO.PerformanceEabQualityResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Service.HrmsPerformanceEabQualityService;

@RestController
@RequestMapping("v1/performanceEabQuality")
public class HrmsPerformanceEabQualityController {

	@Autowired
	private HrmsPerformanceEabQualityService hrmsPerformanceEabQualityService;

	@PostMapping(value = "/addPerformanceEabQuality")
	public ResponseEntity<HrmsPerformanceEabQuality> addPerformanceEabQuality(
			@RequestBody HrmsPerformanceEabQuality hrmsPerformanceEabQuality) {

		return hrmsPerformanceEabQualityService.addPerformanceEabQuality(hrmsPerformanceEabQuality);

	}

	@GetMapping(value = "/getPerformanceEabQualityById/{id}")
	public ResponseEntity<PerformanceEabQualityResponse> getPerformanceEabQualityById(@PathVariable("id") int id) {

		return hrmsPerformanceEabQualityService.getPerformanceEabQualityById(id);

	}

	@PutMapping(value = "/updatePerformanceEabQuality/{id}")
	public ResponseEntity<HrmsPerformanceEabQuality> updatePerformanceEabQuality(
			@RequestBody HrmsPerformanceEabQuality hrmsPerformanceEabQuality, @PathVariable("id") int id) {

		return hrmsPerformanceEabQualityService.updatePerformanceEabQuality(hrmsPerformanceEabQuality, id);

	}

	@DeleteMapping(value = "/deletePerformanceEabQuality/{id}")
	public ResponseEntity<?> deletePerformanceEabQuality(@PathVariable("id") int id) {

		return hrmsPerformanceEabQualityService.deletePerformanceEabQuality(id);

	}

	@GetMapping(value = "getAllPerformanceEabQuality")
	public ResponseEntity<List<PerformanceEabQualityResponse>> getAllPerformanceEabQuality() {

		return hrmsPerformanceEabQualityService.getAllPerformanceEabQuality();

	}

	@GetMapping(value = "/getAllPerformanceEabQualityByFactorId/{factorid}")
	public ResponseEntity<List<PerformanceEabQualityResponse>> getAllPerformanceEabQualityByFactorId(
			@PathVariable("factorid") int factorid) {

		return hrmsPerformanceEabQualityService.getAllPerformanceEabQualityByFactorId(factorid);

	}

}
