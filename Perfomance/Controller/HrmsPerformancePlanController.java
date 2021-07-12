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

import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;
import com.Hrms.Perfomance.Service.HrmsPerformancePlanService;

@RestController
@RequestMapping("v1/performancePlan")
public class HrmsPerformancePlanController {

	@Autowired
	private HrmsPerformancePlanService hrmsPerformancePlanService;

	@PostMapping(value = "/addPerformancePlan")
	public ResponseEntity<HrmsPerformancePlan> addPerformancePlan(
			@RequestBody HrmsPerformancePlan hrmsPerformancePlan) {

		return hrmsPerformancePlanService.addPerformancePlan(hrmsPerformancePlan);

	}

	@GetMapping(value = "/updatePerformancePlan/{id}")
	public ResponseEntity<HrmsPerformancePlan> getPerformancePlanById(@PathVariable("id") int id) {

		return hrmsPerformancePlanService.getPerformancePlanById(id);

	}

	@PutMapping(value = "/updatePerformancePlan/{id}")
	public ResponseEntity<HrmsPerformancePlan> updatePerformancePlan(
			@RequestBody HrmsPerformancePlan hrmsPerformancePlan, @PathVariable("id") int id) {

		return hrmsPerformancePlanService.updatePerformancePlan(hrmsPerformancePlan, id);

	}

	@DeleteMapping(value = "/deletePerformancePlan/{id}")
	public ResponseEntity<?> deletePerformancePlan(@PathVariable("id") int id) {

		return hrmsPerformancePlanService.deletePerformancePlan(id);

	}

	@GetMapping(value = "/getAllPerformancePlan")
	public ResponseEntity<List<HrmsPerformancePlan>> getAllPerformancePlan() {

		return hrmsPerformancePlanService.getAllPerformancePlan();

	}

}
