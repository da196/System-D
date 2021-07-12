package com.Hrms.Payroll.Controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Payroll.DTO.PayrollProcessResponse;
import com.Hrms.Payroll.DTO.PayrollResponse;
import com.Hrms.Payroll.DTO.PayrollResponses;
import com.Hrms.Payroll.DTO.PayrollResponsesv2;
import com.Hrms.Payroll.Service.HrmsPayrollService;

@RequestMapping("v1/payroll")
@RestController
public class HrmsPayrollController {
	@Autowired
	private HrmsPayrollService hrmsPayrollService;

	@GetMapping(value = "/processPayrollPerPeriodBackup/{period}")
	public ResponseEntity<?> processPayrollPerPeriodBackup(@PathVariable("period") Date period) {
		return hrmsPayrollService.processPayrollPerPeriod(period);

	}

	@GetMapping(value = "/getPayrollByPeriodAndEmpId/{year}/{month}/{empId}")
	public ResponseEntity<PayrollResponse> getPayrollByPeriodAndEmpId(@PathVariable("year") int year,
			@PathVariable("month") int month, @PathVariable("empId") int empId) {
		return hrmsPayrollService.getPayrollByPeriodAndEmpId(year, month, empId);

	}

	@GetMapping(value = "/getPayrollByPayrollId/{payrollid}")
	public ResponseEntity<PayrollResponse> getPayrollByPayrollId(@PathVariable("payrollid") int payrollid) {
		return hrmsPayrollService.getPayrollByPayrollId(payrollid);

	}

	@GetMapping(value = "/getPayrollByEmpId/{empid}")
	public ResponseEntity<List<PayrollResponse>> getPayrollByEmpId(@PathVariable("empid") int empid) {
		return hrmsPayrollService.getPayrollByEmpId(empid);

	}

	@DeleteMapping(value = "/deletePayrollByPayrollId/{payrollid}")
	public ResponseEntity<?> deletePayrollByPayrollId(@PathVariable("payrollid") int payrollid) {
		return hrmsPayrollService.deletePayrollByPayrollId(payrollid);

	}

	@GetMapping(value = "/getAllPayroll")
	public ResponseEntity<List<PayrollResponse>> getAllPayroll() {

		return hrmsPayrollService.getAllPayroll();

	}

	@GetMapping(value = "/getPayrollByPeriodBackup/{year}/{month}")
	public ResponseEntity<PayrollResponses> getPayrollByPeriodBackup(@PathVariable("year") int year,
			@PathVariable("month") int month) {

		return hrmsPayrollService.getPayrollByPeriod(year, month);

	}

	@GetMapping(value = "/processPayrollPerPeriod/{year}/{month}")
	public ResponseEntity<PayrollProcessResponse> processPayrollPerPeriod(@PathVariable("year") int year,
			@PathVariable("month") int month) {

		return hrmsPayrollService.processPayrollPerPeriodV2(year, month);

	}

	@GetMapping(value = "/getPayrollByPeriod/{year}/{month}")
	public ResponseEntity<PayrollResponsesv2> getPayrollByPeriod(@PathVariable("year") int year,
			@PathVariable("month") int month) {
		return hrmsPayrollService.getPayrollByPeriodV2(year, month);

	}

	@GetMapping(value = "/processPayrollPerPeriodByEmpId/{year}/{month}/{empId}")
	public ResponseEntity<?> processPayrollPerPeriodByEmpId(@PathVariable("year") int year,
			@PathVariable("month") int month, @PathVariable("empId") int empId) {

		return hrmsPayrollService.processPayrollPerPeriodByEmpId(year, month, empId);

	}

}
