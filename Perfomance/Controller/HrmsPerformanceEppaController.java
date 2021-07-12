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

import com.Hrms.Perfomance.DTO.PerformanceEppaExtraResponse;
import com.Hrms.Perfomance.DTO.PerformanceEppaPost;
import com.Hrms.Perfomance.DTO.PerformanceEppaResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;
import com.Hrms.Perfomance.Service.HrmsPerformanceEppaService;

@RestController
@RequestMapping("v1/performanceEppa")
public class HrmsPerformanceEppaController {

	@Autowired
	private HrmsPerformanceEppaService hrmsPerformanceEppaService;

	@PostMapping(value = "/addPerformanceEppa")
	public ResponseEntity<HrmsPerformanceEppa> addPerformanceEppa(
			@RequestBody HrmsPerformanceEppa hrmsPerformanceEppa) {

		return hrmsPerformanceEppaService.addPerformanceEppa(hrmsPerformanceEppa);

	}

	@GetMapping(value = "/getPerformanceEppaById/{id}")
	public ResponseEntity<PerformanceEppaResponse> getPerformanceEppaById(@PathVariable("id") int id) {

		return hrmsPerformanceEppaService.getPerformanceEppaById(id);

	}

	@PutMapping(value = "/updatePerformanceEppa/{id}")
	public ResponseEntity<HrmsPerformanceEppa> updatePerformanceEppa(
			@RequestBody HrmsPerformanceEppa hrmsPerformanceEppa, @PathVariable("id") int id) {

		return hrmsPerformanceEppaService.updatePerformanceEppa(hrmsPerformanceEppa, id);

	}

	@DeleteMapping(value = "/deletePerformanceEppa/{id}")
	public ResponseEntity<?> deletePerformanceEppa(@PathVariable("id") int id) {

		return hrmsPerformanceEppaService.deletePerformanceEppa(id);

	}

	@GetMapping(value = "/getAllPerformanceEppa")
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppa() {

		return hrmsPerformanceEppaService.getAllPerformanceEppa();

	}

	@GetMapping(value = "/getAllPerformanceEppaByOutputId/{outputid}")
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByOutputId(
			@PathVariable("outputid") int outputid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaByOutputId(outputid);

	}

	@GetMapping(value = "/getAllPerformanceEppaByFinancialYearId/{financialyearid}")
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByFinancialYearId(
			@PathVariable("financialyearid") int financialyearid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaByFinancialYearId(financialyearid);

	}

	@GetMapping(value = "/getAllPerformanceEppaByEmployeeIdAndFinancialYearId/{employeeid}/{financialyearid}")
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByEmployeeIdAndFinancialYearId(
			@PathVariable("employeeid") int employeeid, @PathVariable("financialyearid") int financialyearid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaByEmployeeIdAndFinancialYearId(employeeid,
				financialyearid);

	}

	@PostMapping(value = "/addPerformanceEppaV2")
	public ResponseEntity<PerformanceEppaPost> addPerformanceEppaV2(
			@RequestBody PerformanceEppaPost performanceEppaPost) {

		return hrmsPerformanceEppaService.addPerformanceEppaV2(performanceEppaPost);

	}

	@GetMapping(value = "/getPerformanceEppaV2ById/{id}")
	public ResponseEntity<PerformanceEppaExtraResponse> getPerformanceEppaV2ById(@PathVariable("id") int id) {

		return hrmsPerformanceEppaService.getPerformanceEppaV2ById(id);

	}

	@GetMapping(value = "/getAllPerformanceEppaV2")
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2() {

		return hrmsPerformanceEppaService.getAllPerformanceEppaV2();

	}

	@GetMapping(value = "/getAllPerformanceEppaV2ByEmployeeIdAndFinancialYearId/{employeeid}/{financialyearid}")

	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2ByEmployeeIdAndFinancialYearId(
			@PathVariable("employeeid") int employeeid, @PathVariable("financialyearid") int financialyearid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaV2ByEmployeeIdAndFinancialYearId(employeeid,
				financialyearid);
	}

	@GetMapping(value = "/getAllPerformanceEppaV2BySupervisorIdAndFinancialYearId/{supervisorid}/{financialyearid}")
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2BySupervisorIdAndFinancialYearId(
			@PathVariable("supervisorid") int supervisorid, @PathVariable("financialyearid") int financialyearid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaV2BySupervisorIdAndFinancialYearId(supervisorid,
				financialyearid);

	}

	@GetMapping(value = "/getAllPerformanceEppaV2BySupervisorId/{supervisorid}")
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2BySupervisorId(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaV2BySupervisorId(supervisorid);

	}

	@GetMapping(value = "/getAllPerformanceEppaV2ByEmployeeIdAndStartYearAndEndYear/{employeeid}/{startYear}/{endYear}")
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2ByEmployeeIdAndStartYearAndEndYear(
			@PathVariable("employeeid") int employeeid, @PathVariable("startYear") int startYear,
			@PathVariable("endYear") int endYear) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaV2ByEmployeeIdAndStartYearAndEndYear(employeeid,
				startYear, endYear);
	}

	@GetMapping(value = "/getAllPerformanceEppaV3ByEmployeeIdAndFinancialYearId/{employeeid}/{financialyearid}")
	public ResponseEntity<PerformanceEppaExtraResponse> getAllPerformanceEppaV3ByEmployeeIdAndFinancialYearId(
			@PathVariable("employeeid") int employeeid, @PathVariable("financialyearid") int financialyearid) {

		return hrmsPerformanceEppaService.getAllPerformanceEppaV3ByEmployeeIdAndFinancialYearId(employeeid,
				financialyearid);
	}

}
