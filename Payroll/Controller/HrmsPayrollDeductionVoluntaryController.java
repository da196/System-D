package com.Hrms.Payroll.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Payroll.DTO.PayrollDeductionVoluntaryResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntary;
import com.Hrms.Payroll.Service.HrmsPayrollDeductionVoluntaryService;

@RestController
@RequestMapping("v1/payrollDeductionVoluntary")
public class HrmsPayrollDeductionVoluntaryController {

	@Autowired
	private HrmsPayrollDeductionVoluntaryService hrmsPayrollDeductionVoluntaryService;

	@GetMapping(value = "/getDeductionVoluntaryById/{id}")
	public ResponseEntity<PayrollDeductionVoluntaryResponse> getDeductionVoluntaryById(@PathVariable("id") int id) {

		return hrmsPayrollDeductionVoluntaryService.getDeductionVoluntaryById(id);

	}

	@GetMapping(value = "/getDeductionVoluntaryByPayrollId/{payrollId}")
	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getDeductionVoluntaryByPayrollId(
			@PathVariable("payrollId") int payrollId) {

		return hrmsPayrollDeductionVoluntaryService.getDeductionVoluntaryByPayrollId(payrollId);

	}

	@GetMapping(value = "/getDeductionVoluntaryByEmpId/{empId}")
	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getDeductionVoluntaryByEmpId(
			@PathVariable("empId") int empId) {

		return hrmsPayrollDeductionVoluntaryService.getDeductionVoluntaryByEmpId(empId);

	}

	@PutMapping(value = "/updatePayrollDeductionVoluntary/{id}")
	public ResponseEntity<HrmsPayrollDeductionVoluntary> updatePayrollDeductionVoluntary(
			@RequestBody HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary, @PathVariable("id") int id) {

		return hrmsPayrollDeductionVoluntaryService.updatePayrollDeductionVoluntary(hrmsPayrollDeductionVoluntary, id);

	}

	@DeleteMapping(value = "/deleteDeductionVoluntary/{id}")
	public ResponseEntity<?> deleteDeductionVoluntary(@PathVariable("id") int id) {

		return hrmsPayrollDeductionVoluntaryService.deleteDeductionVoluntary(id);

	}

	@GetMapping(value = "/getAllDeductionVoluntary")
	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getAllDeductionVoluntary() {

		return hrmsPayrollDeductionVoluntaryService.getAllDeductionVoluntary();

	}

}
