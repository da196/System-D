package com.Hrms.Payroll.Controller;

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

import com.Hrms.Payroll.DTO.PayrollTaxStructureResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollTaxStructure;
import com.Hrms.Payroll.Service.HrmsPayrollTaxStructureService;

@RestController
@RequestMapping("v1/payrollTaxStructure")
public class HrmsPayrollTaxStructureController {

	@Autowired
	private HrmsPayrollTaxStructureService hrmsPayrollTaxStructureService;

	@PostMapping(value = "/addPayrollTaxStructure")
	public ResponseEntity<HrmsPayrollTaxStructure> addPayrollTaxStructure(
			@RequestBody HrmsPayrollTaxStructure hrmsPayrollTaxStructure) {

		return hrmsPayrollTaxStructureService.addPayrollTaxStructure(hrmsPayrollTaxStructure);

	}

	@GetMapping(value = "/getPayrollTaxStructureById/{id}")
	public ResponseEntity<PayrollTaxStructureResponse> getPayrollTaxStructureById(@PathVariable("id") int id) {

		return hrmsPayrollTaxStructureService.getPayrollTaxStructureById(id);

	}

	@PutMapping(value = "/updatePayrollTaxStructure/{id}")
	public ResponseEntity<HrmsPayrollTaxStructure> updatePayrollTaxStructure(
			@RequestBody HrmsPayrollTaxStructure hrmsPayrollTaxStructure, @PathVariable("id") int id) {

		return hrmsPayrollTaxStructureService.updatePayrollTaxStructure(hrmsPayrollTaxStructure, id);

	}

	@DeleteMapping(value = "/deletePayrollTaxStructure/{id}")
	public ResponseEntity<?> deletePayrollTaxStructure(@PathVariable("id") int id) {

		return hrmsPayrollTaxStructureService.deletePayrollTaxStructure(id);

	}

	@GetMapping(value = "/getAllPayrollTaxStructure")
	public ResponseEntity<List<PayrollTaxStructureResponse>> getAllPayrollTaxStructure() {

		return hrmsPayrollTaxStructureService.getAllPayrollTaxStructure();

	}

	@GetMapping(value = "/getPayrollTaxStructureByAmount/{amount}")
	public ResponseEntity<PayrollTaxStructureResponse> getPayrollTaxStructureByAmount(
			@PathVariable("amount") Double amount) {
		return hrmsPayrollTaxStructureService.getPayrollTaxStructureByAmount(amount);
	}

}
