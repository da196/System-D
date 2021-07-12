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

import com.Hrms.Payroll.Entity.HrmsBank;
import com.Hrms.Payroll.Service.HrmsBankService;

@RestController
@RequestMapping("/v1/bank")
public class HrmsBankController {
	@Autowired
	private HrmsBankService hrmsBankService;

	@PostMapping(value = "/addBank")
	public ResponseEntity<HrmsBank> addBank(@RequestBody HrmsBank hrmsBank) {

		return hrmsBankService.addBank(hrmsBank);

	}

	@GetMapping(value = "/getBankById/{id}")
	public ResponseEntity<HrmsBank> getBankById(@PathVariable("id") int id) {
		return hrmsBankService.getBankById(id);

	}

	@PutMapping(value = "/updateBank/{id}")
	public ResponseEntity<HrmsBank> updateBank(@RequestBody HrmsBank hrmsBank, @PathVariable("id") int id) {
		return hrmsBankService.updateBank(hrmsBank, id);

	}

	@DeleteMapping(value = "/deleteBank/{id}")
	public ResponseEntity<?> deleteBank(@PathVariable("id") int id) {

		return hrmsBankService.deleteBank(id);

	}

	@GetMapping(value = "/getAllBank")
	public ResponseEntity<List<HrmsBank>> getAllBank() {

		return hrmsBankService.getAllBank();

	}

}
