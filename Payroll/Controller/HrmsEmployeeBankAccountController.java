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

import com.Hrms.Payroll.DTO.EmployeeBankAccount;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccount;
import com.Hrms.Payroll.Service.HrmsEmployeeBankAccountService;

@RestController
@RequestMapping("/v1/EmployeeBankAccount")
public class HrmsEmployeeBankAccountController {

	@Autowired
	private HrmsEmployeeBankAccountService hrmsEmployeeBankAccountService;

	@PostMapping(value = "/addEmployeeBankAccount")
	public ResponseEntity<HrmsEmployeeBankAccount> addEmployeeBankAccount(
			@RequestBody HrmsEmployeeBankAccount hrmsEmployeeBankAccount) {
		return hrmsEmployeeBankAccountService.addEmployeeBankAccount(hrmsEmployeeBankAccount);
	}

	@GetMapping(value = "/getEmployeeBankAccountById")
	public ResponseEntity<EmployeeBankAccount> getEmployeeBankAccountById(@PathVariable("id") int id) {

		return hrmsEmployeeBankAccountService.getEmployeeBankAccountById(id);

	}

	@GetMapping(value = "/getEmployeeBankAccountByEmpId/{empId}")
	public ResponseEntity<List<EmployeeBankAccount>> getEmployeeBankAccountByEmpId(@PathVariable("empId") int empId) {
		return hrmsEmployeeBankAccountService.getEmployeeBankAccountByEmpId(empId);

	}

	@PutMapping(value = "/updateEmployeeBankAccount/{id}")
	public ResponseEntity<HrmsEmployeeBankAccount> updateEmployeeBankAccount(
			@RequestBody HrmsEmployeeBankAccount hrmsEmployeeBankAccount, @PathVariable("id") int id) {

		return hrmsEmployeeBankAccountService.updateEmployeeBankAccount(hrmsEmployeeBankAccount, id);

	}

	@DeleteMapping(value = "/deleteEmployeeBankAccount/{id}")
	public ResponseEntity<?> deleteEmployeeBankAccount(@PathVariable("id") int id) {

		return hrmsEmployeeBankAccountService.deleteEmployeeBankAccount(id);

	}

	@GetMapping(value = "/getAllEmployeeBankAccount")
	public ResponseEntity<List<EmployeeBankAccount>> getAllEmployeeBankAccount() {

		return hrmsEmployeeBankAccountService.getAllEmployeeBankAccount();

	}

	@GetMapping(value = "/getAllEmployeeBankAccountNonApproved")
	public ResponseEntity<List<EmployeeBankAccount>> getAllEmployeeBankAccountNonApproved() {
		return hrmsEmployeeBankAccountService.getAllEmployeeBankAccountNonApproved();

	}

	@GetMapping(value = "/ApproveEmployeeBankAccount/{id}/{approverid}/{status}")
	public ResponseEntity<HrmsEmployeeBankAccount> ApproveEmployeeBankAccount(@PathVariable("id") int id,
			@PathVariable("approverid") int approverid, @PathVariable("status") int status, String comment) {
		return hrmsEmployeeBankAccountService.ApproveEmployeeBankAccount(id, approverid, status, comment);

	}

}
