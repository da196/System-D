package com.Hrms.Employee.Controller;

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

import com.Hrms.Employee.DTO.EmployeeInsuranceResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeInsurance;
import com.Hrms.Employee.Service.HrmsEmployeeInsuranceService;

@RestController
@RequestMapping("v1/employeeInsurance")
public class HrmsEmployeeInsuranceController {

	@Autowired
	private HrmsEmployeeInsuranceService hrmsEmployeeInsuranceService;

	@PostMapping(value = "/addEmployeeInsurance")
	public ResponseEntity<HrmsEmployeeInsurance> addEmployeeInsurance(
			@RequestBody HrmsEmployeeInsurance hrmsEmployeeInsurance) {

		return hrmsEmployeeInsuranceService.addEmployeeInsurance(hrmsEmployeeInsurance);

	}

	@GetMapping(value = "/getEmployeeInsuranceById/{id}")
	public ResponseEntity<EmployeeInsuranceResponse> getEmployeeInsuranceById(@PathVariable("id") int id) {

		return hrmsEmployeeInsuranceService.getEmployeeInsuranceById(id);

	}

	@PutMapping(value = "/updateEmployeeInsurance/{id}")
	public ResponseEntity<HrmsEmployeeInsurance> updateEmployeeInsurance(
			@RequestBody HrmsEmployeeInsurance hrmsEmployeeInsurance, @PathVariable("id") int id) {

		return hrmsEmployeeInsuranceService.updateEmployeeInsurance(hrmsEmployeeInsurance, id);

	}

	@DeleteMapping(value = "/deleteEmployeeInsurance/{id}")
	public ResponseEntity<?> deleteEmployeeInsurance(@PathVariable("id") int id) {

		return hrmsEmployeeInsuranceService.deleteEmployeeInsurance(id);

	}

	@GetMapping(value = "/getAllEmployeeInsurance")
	public ResponseEntity<List<EmployeeInsuranceResponse>> listEmployeeInsurance() {

		return hrmsEmployeeInsuranceService.listEmployeeInsurance();

	}

	@GetMapping(value = "/getEmployeeInsuranceByEmpId/{EmpId}")
	public ResponseEntity<List<EmployeeInsuranceResponse>> getEmployeeInsuranceByEmpId(
			@PathVariable("EmpId") int EmpId) {
		return hrmsEmployeeInsuranceService.getEmployeeInsuranceByEmpId(EmpId);
	}

	@PostMapping(value = "/addEmployeeInsuranceList")
	public ResponseEntity<List<HrmsEmployeeInsurance>> addEmployeeInsuranceList(
			@RequestBody List<HrmsEmployeeInsurance> hrmsEmployeeInsurancelist) {
		return hrmsEmployeeInsuranceService.addEmployeeInsuranceList(hrmsEmployeeInsurancelist);

	}

}
