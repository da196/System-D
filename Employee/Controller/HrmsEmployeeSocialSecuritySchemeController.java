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

import com.Hrms.Employee.DTO.EmployeeSocialSecuritySchemeResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;
import com.Hrms.Employee.Service.HrmsEmployeeSocialSecuritySchemeService;

@RestController
@RequestMapping("v1/employeeSocialSecurityScheme")
public class HrmsEmployeeSocialSecuritySchemeController {

	@Autowired
	private HrmsEmployeeSocialSecuritySchemeService hrmsEmployeeSocialSecuritySchemeService;

	@PostMapping(value = "/addEmployeeSocialSecurityScheme")
	public ResponseEntity<HrmsEmployeeSocialSecurityScheme> addEmployeeSocialSecurityScheme(
			@RequestBody HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme) {

		return hrmsEmployeeSocialSecuritySchemeService
				.addEmployeeSocialSecurityScheme(hrmsEmployeeSocialSecurityScheme);

	}

	@GetMapping(value = "/getEmployeeSocialSecuritySchemeById")
	public ResponseEntity<EmployeeSocialSecuritySchemeResponse> getEmployeeSocialSecuritySchemeById(
			@PathVariable("id") int id) {

		return hrmsEmployeeSocialSecuritySchemeService.getEmployeeSocialSecuritySchemeById(id);

	}

	@PutMapping(value = "/updateEmployeeSocialSecurityScheme/{id}")
	public ResponseEntity<HrmsEmployeeSocialSecurityScheme> updateEmployeeSocialSecurityScheme(
			@RequestBody HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme,
			@PathVariable("id") int id) {

		return hrmsEmployeeSocialSecuritySchemeService
				.updateEmployeeSocialSecurityScheme(hrmsEmployeeSocialSecurityScheme, id);

	}

	@DeleteMapping(value = "/deleteEmployeeSocialSecurityScheme")
	public ResponseEntity<?> deleteEmployeeSocialSecurityScheme(@PathVariable("id") int id) {

		return hrmsEmployeeSocialSecuritySchemeService.deleteEmployeeSocialSecurityScheme(id);

	}

	@GetMapping(value = "/getAllEmployeeSocialSecurityScheme")
	public ResponseEntity<List<EmployeeSocialSecuritySchemeResponse>> getAllEmployeeSocialSecurityScheme() {

		return hrmsEmployeeSocialSecuritySchemeService.listEmployeeSocialSecurityScheme();

	}

	@GetMapping(value = "/getEmployeeSocialSecuritySchemeByEmpId/{EmpId}")
	public ResponseEntity<List<EmployeeSocialSecuritySchemeResponse>> getEmployeeSocialSecuritySchemeByEmpId(
			@PathVariable("EmpId") int EmpId) {
		return hrmsEmployeeSocialSecuritySchemeService.getEmployeeSocialSecuritySchemeByEmpId(EmpId);
	}

	@GetMapping(value = "/verifyIfEmployeeHasPension/{EmpId}")
	public ResponseEntity<?> verifyIfEmployeeHasPension(@PathVariable("EmpId") int EmpId) {
		return hrmsEmployeeSocialSecuritySchemeService.verifyIfEmployeeHasPension(EmpId);

	}

}
