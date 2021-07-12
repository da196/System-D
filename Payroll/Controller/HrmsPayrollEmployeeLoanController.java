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

import com.Hrms.Payroll.DTO.EmployeeLoanResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoan;
import com.Hrms.Payroll.Service.HrmsPayrollEmployeeLoanService;

@RestController
@RequestMapping("v1/payrollEmployeeLoan")
public class HrmsPayrollEmployeeLoanController {

	@Autowired
	private HrmsPayrollEmployeeLoanService hrmsPayrollEmployeeLoanService;

	@PostMapping(value = "/addPayrollEmployeeLoan")
	public ResponseEntity<HrmsPayrollEmployeeLoan> addPayrollEmployeeLoan(
			@RequestBody HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan) {

		return hrmsPayrollEmployeeLoanService.addPayrollEmployeeLoan(hrmsPayrollEmployeeLoan);

	}

	@GetMapping(value = "/getPayrollEmployeeLoanById/{id}")
	public ResponseEntity<EmployeeLoanResponse> getPayrollEmployeeLoanById(@PathVariable("id") int id) {
		return hrmsPayrollEmployeeLoanService.getPayrollEmployeeLoanById(id);

	}

	@GetMapping(value = "/getPayrollEmployeeLoanByEmpId/{EmpId}")
	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByEmpId(@PathVariable("EmpId") int EmpId) {

		return hrmsPayrollEmployeeLoanService.getPayrollEmployeeLoanByEmpId(EmpId);

	}

	@PutMapping(value = "/updatePayrollEmployeeLoan/{id}/{durationAdjust}")
	public ResponseEntity<HrmsPayrollEmployeeLoan> updatePayrollEmployeeLoan(
			@RequestBody HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan, @PathVariable("id") int id,
			@PathVariable("durationAdjust") Double durationAdjust) {

		return hrmsPayrollEmployeeLoanService.updatePayrollEmployeeLoan(hrmsPayrollEmployeeLoan, id, durationAdjust);

	}

	@DeleteMapping(value = "/deletePayrollEmployeeLoan/{id}")
	public ResponseEntity<?> deletePayrollEmployeeLoan(@PathVariable("id") int id) {

		return hrmsPayrollEmployeeLoanService.deletePayrollEmployeeLoan(id);

	}

	@GetMapping(value = "/getAllPayrollEmployeeLoan")
	public ResponseEntity<List<EmployeeLoanResponse>> getAllPayrollEmployeeLoan() {

		return hrmsPayrollEmployeeLoanService.getAllPayrollEmployeeLoan();

	}

	@GetMapping(value = "/getPayrollEmployeeLoanByEmpIdAndStatus/{EmpId}/{status}")
	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByEmpIdAndStatus(
			@PathVariable("EmpId") int EmpId, @PathVariable("status") int status) {

		return hrmsPayrollEmployeeLoanService.getPayrollEmployeeLoanByEmpIdAndStatus(EmpId, status);

	}

	@GetMapping(value = "/getPayrollEmployeeLoanByStatus/{status}")
	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByStatus(
			@PathVariable("status") int status) {
		return hrmsPayrollEmployeeLoanService.getPayrollEmployeeLoanByStatus(status);

	}

}
