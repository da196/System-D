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

import com.Hrms.Payroll.DTO.OneThirdBalance;
import com.Hrms.Payroll.DTO.PayrollLoanPaymentAmount;
import com.Hrms.Payroll.DTO.PayrollLoanTypeMaxAmountResponse;
import com.Hrms.Payroll.DTO.PayrollLoanTypeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;
import com.Hrms.Payroll.Service.HrmsPayrollLoanTypeService;

@RestController
@RequestMapping("v1/payrollLoanType")
public class HrmsPayrollLoanTypeController {
	@Autowired
	private HrmsPayrollLoanTypeService hrmsPayrollLoanTypeService;

	@PostMapping(value = "/addPayrollLoanType")
	public ResponseEntity<HrmsPayrollLoanType> addPayrollLoanType(
			@RequestBody HrmsPayrollLoanType hrmsPayrollLoanType) {
		return hrmsPayrollLoanTypeService.addPayrollLoanType(hrmsPayrollLoanType);
	}

	@GetMapping(value = "/getPayrollLoanTypeById/{id}")
	public ResponseEntity<PayrollLoanTypeResponse> getPayrollLoanTypeById(@PathVariable("id") int id) {
		return hrmsPayrollLoanTypeService.getPayrollLoanTypeById(id);

	}

	@PutMapping(value = "/updatePayrollLoanType/{id}")
	public ResponseEntity<HrmsPayrollLoanType> updatePayrollLoanType(
			@RequestBody HrmsPayrollLoanType hrmsPayrollLoanType, @PathVariable("id") int id) {
		return hrmsPayrollLoanTypeService.updatePayrollLoanType(hrmsPayrollLoanType, id);
	}

	@DeleteMapping(value = "/deletePayrollLoanType/{id}")
	public ResponseEntity<?> deletePayrollLoanType(@PathVariable("id") int id) {
		return hrmsPayrollLoanTypeService.deletePayrollLoanType(id);

	}

	@GetMapping(value = "/getAllPayrollLoanType")
	public ResponseEntity<List<PayrollLoanTypeResponse>> getAllPayrollLoanType() {
		return hrmsPayrollLoanTypeService.getAllPayrollLoanType();

	}

	@GetMapping(value = "/getMaxLoanAmount/{empid}/{loantypeid}")
	public ResponseEntity<PayrollLoanTypeMaxAmountResponse> getMaxLoanAmount(@PathVariable("empid") int empid,
			@PathVariable("loantypeid") int loantypeid) {

		return hrmsPayrollLoanTypeService.getMaxLoanAmount(empid, loantypeid);

	}

	@GetMapping(value = "/getPaymentAmount/{loantypeid}/{loanAmount}/{duration}")
	public ResponseEntity<PayrollLoanPaymentAmount> getPaymentAmount(@PathVariable("loantypeid") int loantypeid,
			@PathVariable("loanAmount") Double loanAmount, @PathVariable("duration") int duration) {
		return hrmsPayrollLoanTypeService.getPaymentAmount(loantypeid, loanAmount, duration);
	}

	@GetMapping(value = "/getPayrollLoanTypeByLenderId/{lenderid}")
	public ResponseEntity<List<PayrollLoanTypeResponse>> getPayrollLoanTypeByLenderId(
			@PathVariable("lenderid") int lenderid) {
		return hrmsPayrollLoanTypeService.getPayrollLoanTypeByLenderId(lenderid);
	}

	@GetMapping(value = "/getOneThirdofBasicAfterDeduction/{empid}")
	public ResponseEntity<OneThirdBalance> getOneThirdofBasicAfterDeduction(@PathVariable("empid") int empid) {

		return hrmsPayrollLoanTypeService.getOneThirdofBasicAfterDeduction(empid);

	}

}
