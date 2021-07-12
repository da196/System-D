package com.Hrms.Employee.Controller;

import java.util.List;
import java.util.Optional;

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

import com.Hrms.Employee.Entity.HrmsEmployeeDesignation;
import com.Hrms.Employee.Service.HrmsEmployeeDesignationService;

@RestController
@RequestMapping("/v1/employeeDesignation")
public class HrmsEmployeeDesignationController {

	@Autowired
	private HrmsEmployeeDesignationService hrmsEmployeeDesignationService;

	@PostMapping(value = "/addEmployeeDesignation")
	public ResponseEntity<HrmsEmployeeDesignation> addEmployeeDesignation(
			@RequestBody HrmsEmployeeDesignation hrmsEmployeeDesignation) {
		return hrmsEmployeeDesignationService.save(hrmsEmployeeDesignation);
	}

	@GetMapping(value = "/getEmployeeDesignation/{id}")
	public ResponseEntity<Optional<HrmsEmployeeDesignation>> getEmployeeDesignation(@PathVariable("id") int id) {
		return hrmsEmployeeDesignationService.viewHrmsEmployeeDesignation(id);
	}

	@PutMapping(value = "/updateEmployeeDesignation/{id}")
	public ResponseEntity<HrmsEmployeeDesignation> updateEmployeeDesignation(
			@RequestBody HrmsEmployeeDesignation hrmsEmployeeDesignation, @PathVariable("id") int id) {
		return hrmsEmployeeDesignationService.update(hrmsEmployeeDesignation, id);
	}

	@DeleteMapping(value = "/deleteEmployeeDesignation/{id}")
	public ResponseEntity<?> deleteEmployeeDesignation(@PathVariable("id") int id) {
		return hrmsEmployeeDesignationService.deleteHrmsEmployeeDesignation(id);

	}

	@GetMapping(value = "/getAllEmployeeDesignation")
	public ResponseEntity<List<HrmsEmployeeDesignation>> listEmployeeDesignation() {
		return hrmsEmployeeDesignationService.listHrmsEmployeeDesignation();

	}

}
