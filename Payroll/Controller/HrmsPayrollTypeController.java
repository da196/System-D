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

import com.Hrms.Payroll.Entity.HrmsPayrollType;
import com.Hrms.Payroll.Service.HrmsPayrollTypeService;

@RestController
@RequestMapping("v1/payrollType")
public class HrmsPayrollTypeController {
	@Autowired
	private HrmsPayrollTypeService hrmsPayrollTypeService;

	@PostMapping(value = "/addPayrollType")

	public ResponseEntity<HrmsPayrollType> addPayrollType(@RequestBody HrmsPayrollType hrmsPayrollType) {
		return hrmsPayrollTypeService.addPayrollType(hrmsPayrollType);
	}

	@GetMapping(value = "/getPayrollTypeById/{id}")
	public ResponseEntity<HrmsPayrollType> getPayrollTypeById(@PathVariable("id") int id) {

		return hrmsPayrollTypeService.getPayrollTypeById(id);

	}

	@PutMapping(value = "/updatePayrollType/{id}")
	public ResponseEntity<HrmsPayrollType> updatePayrollType(@RequestBody HrmsPayrollType hrmsPayrollType,
			@PathVariable("id") int id) {
		return hrmsPayrollTypeService.updatePayrollType(hrmsPayrollType, id);
	}

	@DeleteMapping(value = "/deletePayrollType/{id}")
	public ResponseEntity<?> deletePayrollType(@PathVariable("id") int id) {
		return hrmsPayrollTypeService.deletePayrollType(id);
	}

	@GetMapping(value = "/getAllPayrollType")
	public ResponseEntity<List<HrmsPayrollType>> getAllPayrollType() {
		return hrmsPayrollTypeService.getAllPayrollType();

	}

}
