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

import com.Hrms.Perfomance.DTO.PerformanceEabFactorWithQualitiesResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;
import com.Hrms.Perfomance.Service.HrmsPerformanceEabFactorService;

@RestController
@RequestMapping("v1/performanceEabFactor")
public class HrmsPerformanceEabFactorController {

	@Autowired
	private HrmsPerformanceEabFactorService hrmsPerformanceEabFactorService;

	@PostMapping(value = "/addPerformanceEabFactor")
	public ResponseEntity<HrmsPerformanceEabFactor> addPerformanceEabFactor(
			@RequestBody HrmsPerformanceEabFactor hrmsPerformanceEabFactor) {

		return hrmsPerformanceEabFactorService.addPerformanceEabFactor(hrmsPerformanceEabFactor);

	}

	@GetMapping(value = "/getPerformanceEabFactorById/{id}")
	public ResponseEntity<HrmsPerformanceEabFactor> getPerformanceEabFactorById(@PathVariable("id") int id) {

		return hrmsPerformanceEabFactorService.getPerformanceEabFactorById(id);

	}

	@PutMapping(value = "/updatePerformanceEabFactor/{id}")
	public ResponseEntity<HrmsPerformanceEabFactor> updatePerformanceEabFactor(
			@RequestBody HrmsPerformanceEabFactor hrmsPerformanceEabFactor, @PathVariable("id") int id) {
		return hrmsPerformanceEabFactorService.updatePerformanceEabFactor(hrmsPerformanceEabFactor, id);

	}

	@DeleteMapping(value = "/deletePerformanceEabFactor/{id}")
	public ResponseEntity<?> deletePerformanceEabFactor(@PathVariable("id") int id) {

		return hrmsPerformanceEabFactorService.deletePerformanceEabFactor(id);

	}

	@GetMapping(value = "/getAllPerformanceEabFactor")
	public ResponseEntity<List<HrmsPerformanceEabFactor>> getAllPerformanceEabFactor() {

		return hrmsPerformanceEabFactorService.getAllPerformanceEabFactor();

	}

	@GetMapping(value = "/getAllPerformanceEabFactorWithQualities")
	public ResponseEntity<List<PerformanceEabFactorWithQualitiesResponse>> getAllPerformanceEabFactorWithQualities() {
		return hrmsPerformanceEabFactorService.getAllPerformanceEabFactorWithQualities();

	}

}
