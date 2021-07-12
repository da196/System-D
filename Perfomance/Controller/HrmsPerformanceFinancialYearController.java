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

import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;
import com.Hrms.Perfomance.Service.HrmsPerformanceFinancialYearService;

@RestController
@RequestMapping("v1/performanceFinancialYear")
public class HrmsPerformanceFinancialYearController {
	@Autowired
	private HrmsPerformanceFinancialYearService hrmsPerformanceFinancialYearService;

	@PostMapping(value = "/addPerformanceFinancialYear")
	public ResponseEntity<HrmsPerformanceFinancialYear> addPerformanceFinancialYear(
			@RequestBody HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear) {

		return hrmsPerformanceFinancialYearService.addPerformanceFinancialYear(hrmsPerformanceFinancialYear);

	}

	@GetMapping(value = "/getPerformanceFinancialYearById/{id}")
	public ResponseEntity<HrmsPerformanceFinancialYear> getPerformanceFinancialYearById(@PathVariable("id") int id) {

		return hrmsPerformanceFinancialYearService.getPerformanceFinancialYearById(id);

	}

	@PutMapping(value = "/updatePerformanceFinancialYear/{id}")
	public ResponseEntity<HrmsPerformanceFinancialYear> updatePerformanceFinancialYear(
			@RequestBody HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear, @PathVariable("id") int id) {

		return hrmsPerformanceFinancialYearService.updatePerformanceFinancialYear(hrmsPerformanceFinancialYear, id);

	}

	@DeleteMapping(value = "/deletePerformanceFinancialYear/{id}")
	public ResponseEntity<?> deletePerformanceFinancialYear(@PathVariable("id") int id) {

		return hrmsPerformanceFinancialYearService.deletePerformanceFinancialYear(id);

	}

	@GetMapping(value = "/getAllPerformanceFinancialYear")
	public ResponseEntity<List<HrmsPerformanceFinancialYear>> getAllPerformanceFinancialYear() {

		return hrmsPerformanceFinancialYearService.getAllPerformanceFinancialYear();

	}

}
