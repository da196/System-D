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

import com.Hrms.Payroll.DTO.PayrollDeductionMandatoryInsuranceResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryInsurance;
import com.Hrms.Payroll.Service.HrmsPayrollDeductionMandatoryInsuranceService;

@RestController
@RequestMapping("v1/payrollDeductionMandatoryInsurance")
public class HrmsPayrollDeductionMandatoryInsuranceController {
	@Autowired
	private HrmsPayrollDeductionMandatoryInsuranceService hrmsPayrollDeductionMandatoryInsuranceService;

	@GetMapping(value = "/getDeductionMandatoryHealthInsuranceById/{id}")
	public ResponseEntity<PayrollDeductionMandatoryInsuranceResponse> getDeductionMandatoryHealthInsuranceById(
			@PathVariable("id") int id) {

		return hrmsPayrollDeductionMandatoryInsuranceService.getDeductionMandatoryInsuranceById(id);

	}

	@GetMapping(value = "/getDeductionMandatoryHealthInsuranceByPayrollId/{payrollId}")
	public ResponseEntity<PayrollDeductionMandatoryInsuranceResponse> getDeductionMandatoryHealthInsuranceByPayrollId(
			@PathVariable("payrollId") int payrollId) {

		return hrmsPayrollDeductionMandatoryInsuranceService.getDeductionMandatoryInsuranceByPayrollId(payrollId);

	}

	@GetMapping(value = "/getDeductionMandatoryHealthInsuranceByEmpId/{empId}")
	public ResponseEntity<List<PayrollDeductionMandatoryInsuranceResponse>> getDeductionMandatoryHealthInsuranceByEmpId(
			@PathVariable("empId") int empId) {

		return hrmsPayrollDeductionMandatoryInsuranceService.getDeductionMandatoryInsuranceByEmpId(empId);

	}

	@PutMapping(value = "/updateDeductionMandatoryHealthInsurance/{id}")
	public ResponseEntity<HrmsPayrollDeductionMandatoryInsurance> updateDeductionMandatoryHealthInsurance(
			@RequestBody HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance,
			@PathVariable("id") int id) {

		return hrmsPayrollDeductionMandatoryInsuranceService
				.updateDeductionMandatoryInsurance(hrmsPayrollDeductionMandatoryInsurance, id);

	}

	@DeleteMapping(value = "/deleteDeductionMandatoryHealthInsurance/{id}")
	public ResponseEntity<?> deleteDeductionMandatoryHealthInsurance(@PathVariable("id") int id) {

		return hrmsPayrollDeductionMandatoryInsuranceService.deleteDeductionMandatoryInsurance(id);

	}

	@GetMapping(value = "/getAllDeductionMandatoryHealthInsurance")
	public ResponseEntity<List<PayrollDeductionMandatoryInsuranceResponse>> getAllDeductionMandatoryHealthInsurance() {

		return hrmsPayrollDeductionMandatoryInsuranceService.getAllDeductionMandatoryInsurance();

	}

}
