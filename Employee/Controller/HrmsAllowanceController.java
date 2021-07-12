package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

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

import com.Hrms.Employee.DTO.HrmsAllowanceResponse;
import com.Hrms.Employee.DTO.SalaryStructureedms;
import com.Hrms.Employee.Entity.HrmsAllowance;
import com.Hrms.Employee.Service.HrmsAllowanceService;

@RestController
@RequestMapping("/v1/allowance")
public class HrmsAllowanceController {

	@Autowired
	private HrmsAllowanceService hrmsAllowanceService;

	@PostMapping(value = "/addAllowance")
	public ResponseEntity<HrmsAllowance> addAllowance(@RequestBody HrmsAllowance hrmsAllowance) {
		return hrmsAllowanceService.save(hrmsAllowance);

	}

	@GetMapping(value = "/getAllowance/{id}")
	public ResponseEntity<Optional<HrmsAllowance>> getAllowance(@PathVariable("id") int id) {
		return hrmsAllowanceService.viewHrmsHrmsAllowance(id);

	}

	@GetMapping(value = "/getByDesignationId/{id}")
	public ResponseEntity<List<HrmsAllowance>> FindByDesignationId(int id) {
		return hrmsAllowanceService.FindByDesignationId(id);

	}

	@PutMapping(value = "/updateAllowance/{id}")
	public ResponseEntity<HrmsAllowance> updateAllowance(@RequestBody HrmsAllowance hrmsAllowance,
			@PathVariable("id") int id) {
		return hrmsAllowanceService.updateHrmsAllowance(hrmsAllowance, id);

	}

	@DeleteMapping(value = "/deleteAllowance/{id}")
	public ResponseEntity<?> deleteAllowance(@PathVariable("id") int id) {

		return hrmsAllowanceService.deleteHrmsAllowance(id);

	}

	@GetMapping(value = "/getAllAllowance")
	public ResponseEntity<List<HrmsAllowance>> listAllowance() {
		return hrmsAllowanceService.listHrmsAllowance();

	}

	@GetMapping(value = "/getAllAllowanceDetailed")
	public ResponseEntity<List<HrmsAllowanceResponse>> listHrmsAllowanceDetailed() {
		return hrmsAllowanceService.listHrmsAllowanceDetailed();

	}

	@GetMapping(value = "/getAllowancesTravel")
	public ResponseEntity<List<HrmsAllowanceResponse>> getAllowancesTravel() {

		return hrmsAllowanceService.getAllowancesTravel();

	}

	@GetMapping(value = "/getAllsalaryStructure")
	public ResponseEntity<List<SalaryStructureedms>> getAllsalaryStructure() {
		return hrmsAllowanceService.getAllsalaryStructure();
	}

}
