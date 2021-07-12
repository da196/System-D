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

import com.Hrms.Payroll.Entity.HrmsPayrollContributionType;
import com.Hrms.Payroll.Service.HrmsPayrollContributionTypeService;

@RestController
@RequestMapping("v1/payrollContributionType")
public class HrmsPayrollContributionTypeController {

	@Autowired
	private HrmsPayrollContributionTypeService hrmsPayrollContributionTypeService;

	@PostMapping(value = ("/addPayrollContributionType"))
	public ResponseEntity<HrmsPayrollContributionType> addPayrollContributionType(
			@RequestBody HrmsPayrollContributionType hrmsPayrollContributionType) {

		return hrmsPayrollContributionTypeService.addPayrollContributionType(hrmsPayrollContributionType);

	}

	@GetMapping(value = "/getPayrollContributionTypeById/{id}")
	public ResponseEntity<HrmsPayrollContributionType> getPayrollContributionTypeById(@PathVariable("id") int id) {
		return hrmsPayrollContributionTypeService.getPayrollContributionTypeById(id);
	}

	@PutMapping(value = "/updatePayrollContributionType/{id}")
	public ResponseEntity<HrmsPayrollContributionType> updatePayrollContributionType(
			@RequestBody HrmsPayrollContributionType hrmsPayrollContributionType, @PathVariable("id") int id) {

		return hrmsPayrollContributionTypeService.updatePayrollContributionType(hrmsPayrollContributionType, id);
	}

	@DeleteMapping(value = ("/deletePayrollContributionType/{id}"))
	public ResponseEntity<?> deletePayrollContributionType(@PathVariable("id") int id) {
		return hrmsPayrollContributionTypeService.deletePayrollContributionType(id);

	}

	@GetMapping(value = ("/getAllPayrollContributionType"))
	public ResponseEntity<List<HrmsPayrollContributionType>> getAllPayrollContributionType() {

		return hrmsPayrollContributionTypeService.getAllPayrollContributionType();

	}

}
