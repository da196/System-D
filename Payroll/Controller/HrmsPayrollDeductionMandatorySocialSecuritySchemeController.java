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

import com.Hrms.Payroll.DTO.PayrollDeductionMandatorySocialSecuritySchemeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Service.HrmsPayrollDeductionMandatorySocialSecuritySchemeService;

@RestController
@RequestMapping("v1/payrollDeductionMandatorySocialSecurityScheme")
public class HrmsPayrollDeductionMandatorySocialSecuritySchemeController {

	@Autowired
	private HrmsPayrollDeductionMandatorySocialSecuritySchemeService hrmsPayrollDeductionMandatorySocialSecuritySchemeService;

	@GetMapping(value = "/getDeductionMandatorySocialSecuritySchemeById/{id}")
	public ResponseEntity<PayrollDeductionMandatorySocialSecuritySchemeResponse> getDeductionMandatorySocialSecuritySchemeById(
			@PathVariable("id") int id) {

		return hrmsPayrollDeductionMandatorySocialSecuritySchemeService
				.getDeductionMandatorySocialSecuritySchemeById(id);

	}

	@GetMapping(value = "/getDeductionMandatorySocialSecuritySchemeByPayrollId/{payrollId}")
	public ResponseEntity<PayrollDeductionMandatorySocialSecuritySchemeResponse> getDeductionMandatorySocialSecuritySchemeByPayrollId(
			@PathVariable("payrollId") int payrollId) {

		return hrmsPayrollDeductionMandatorySocialSecuritySchemeService
				.getDeductionMandatorySocialSecuritySchemeByPayrollId(payrollId);

	}

	@GetMapping(value = "/getDeductionMandatorySocialSecuritySchemeByEmpId/{empId}")
	public ResponseEntity<List<PayrollDeductionMandatorySocialSecuritySchemeResponse>> getDeductionMandatorySocialSecuritySchemeByEmpId(
			@PathVariable("empId") int empId) {

		return hrmsPayrollDeductionMandatorySocialSecuritySchemeService
				.getDeductionMandatorySocialSecuritySchemeByEmpId(empId);

	}

	@PutMapping(value = "/updateDeductionMandatorySocialSecurityScheme/{id}")
	public ResponseEntity<HrmsPayrollDeductionMandatorySocialSecurityScheme> updateDeductionMandatorySocialSecurityScheme(
			@RequestBody HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme,
			@PathVariable("id") int id) {

		return hrmsPayrollDeductionMandatorySocialSecuritySchemeService
				.updateDeductionMandatorySocialSecurityScheme(hrmsPayrollDeductionMandatorySocialSecurityScheme, id);

	}

	@DeleteMapping(value = "/deleteDeductionMandatorySocialSecurityScheme/{id}")
	public ResponseEntity<?> deleteDeductionMandatorySocialSecurityScheme(@PathVariable("id") int id) {

		return hrmsPayrollDeductionMandatorySocialSecuritySchemeService
				.deleteDeductionMandatorySocialSecurityScheme(id);

	}

	@GetMapping(value = "/getAllDeductionMandatorySocialSecurityScheme")
	public ResponseEntity<List<PayrollDeductionMandatorySocialSecuritySchemeResponse>> getAllDeductionMandatorySocialSecurityScheme() {

		return hrmsPayrollDeductionMandatorySocialSecuritySchemeService.getAllDeductionMandatorySocialSecurityScheme();
	}

}
