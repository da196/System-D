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

import com.Hrms.Payroll.DTO.BankBranchResponse;
import com.Hrms.Payroll.Entity.HrmsBankBranch;
import com.Hrms.Payroll.Service.HrmsBankBranchService;

@RestController
@RequestMapping("v1/bankBranch")
public class HrmsBankBranchController {

	@Autowired
	private HrmsBankBranchService hrmsBankBranchService;

	@PostMapping(value = "/addBankBranch")
	public ResponseEntity<HrmsBankBranch> addBankBranch(@RequestBody HrmsBankBranch hrmsBankBranch) {
		return hrmsBankBranchService.addBankBranch(hrmsBankBranch);
	}

	@GetMapping(value = "/getBankBranchById/{id}")
	public ResponseEntity<BankBranchResponse> getBankBranchById(@PathVariable("id") int id) {

		return hrmsBankBranchService.getBankBranchById(id);

	}

	@PutMapping(value = "/updateBankBranch/{id}")
	public ResponseEntity<HrmsBankBranch> updateBankBranch(@RequestBody HrmsBankBranch hrmsBankBranch,
			@PathVariable("id") int id) {
		return hrmsBankBranchService.updateBankBranch(hrmsBankBranch, id);

	}

	@DeleteMapping(value = "/deleteBankBranch/{id}")
	public ResponseEntity<?> deleteBankBranch(@PathVariable("id") int id) {
		return hrmsBankBranchService.deleteBankBranch(id);

	}

	@GetMapping(value = "/getAllBankBranch")
	public ResponseEntity<List<BankBranchResponse>> getAllBankBranch() {
		return hrmsBankBranchService.getAllBankBranch();

	}

	@GetMapping(value = "/getBankBranchByBankId/{bankid}")
	public ResponseEntity<List<BankBranchResponse>> getBankBranchByBankId(@PathVariable("bankid") int bankid) {
		return hrmsBankBranchService.getBankBranchByBankId(bankid);

	}

}
