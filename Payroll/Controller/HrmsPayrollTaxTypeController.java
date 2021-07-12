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

import com.Hrms.Payroll.Entity.HrmsPayrollTaxType;
import com.Hrms.Payroll.Service.HrmsPayrollTaxTypeService;

@RestController
@RequestMapping("v1/payrollTaxType")
public class HrmsPayrollTaxTypeController {

	@Autowired
	private HrmsPayrollTaxTypeService hrmsPayrollTaxTypeService;

	@PostMapping(value = "/addPayrollTaxType")
	public ResponseEntity<HrmsPayrollTaxType> addPayrollTaxType(@RequestBody HrmsPayrollTaxType hrmsPayrollTaxType) {

		return hrmsPayrollTaxTypeService.addPayrollTaxType(hrmsPayrollTaxType);
	}

	@GetMapping(value = "/getPayrollTaxTypeById/{id}")
	public ResponseEntity<HrmsPayrollTaxType> getPayrollTaxTypeById(@PathVariable("id") int id) {
		return hrmsPayrollTaxTypeService.getPayrollTaxTypeById(id);

	}

	@PutMapping(value = "/updatePayrollTaxType/{id}")
	public ResponseEntity<HrmsPayrollTaxType> updatePayrollTaxType(@RequestBody HrmsPayrollTaxType hrmsPayrollTaxType,
			@PathVariable("id") int id) {
		return hrmsPayrollTaxTypeService.updatePayrollTaxType(hrmsPayrollTaxType, id);

	}

	@DeleteMapping(value = "/deletePayrollTaxType/{id}")
	public ResponseEntity<?> deletePayrollTaxType(@PathVariable("id") int id) {

		return hrmsPayrollTaxTypeService.deletePayrollTaxType(id);

	}

	@GetMapping(value = "/getAllPayrollTaxType")
	public ResponseEntity<List<HrmsPayrollTaxType>> getAllPayrollTaxType() {
		return hrmsPayrollTaxTypeService.getAllPayrollTaxType();

	}

}
