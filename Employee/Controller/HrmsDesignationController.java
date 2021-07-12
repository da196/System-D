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

import com.Hrms.Employee.DTO.DesignationResponse;
import com.Hrms.Employee.Entity.HrmsDesignation;
import com.Hrms.Employee.Service.HrmsDesignationService;

@RestController
@RequestMapping("/v1/designation")
public class HrmsDesignationController {

	@Autowired
	private HrmsDesignationService hrmsDesignationService;

	@PostMapping(value = "/addDesignation")
	public ResponseEntity<HrmsDesignation> addDesignation(@RequestBody HrmsDesignation hrmsDesignation) {
		return hrmsDesignationService.save(hrmsDesignation);

	}

	@GetMapping(value = "/getDesignation/{id}")
	public ResponseEntity<Optional<HrmsDesignation>> getDesignation(@PathVariable("id") int id) {

		return hrmsDesignationService.viewHrmsDesignation(id);

	}

	@PutMapping(value = "/updateDesignation/{id}")
	public ResponseEntity<HrmsDesignation> update(@RequestBody HrmsDesignation hrmsDesignation,
			@PathVariable("id") int id) {
		return hrmsDesignationService.update(hrmsDesignation, id);

	}

	@DeleteMapping(value = "/deleteDesignation/{id}")
	public ResponseEntity<?> deleteHrmsDesignation(@PathVariable("id") int id) {
		return hrmsDesignationService.deleteHrmsDesignation(id);

	}

	@GetMapping(value = "/getAllDesignation")
	public ResponseEntity<List<HrmsDesignation>> listHrmsDesignation() {
		return hrmsDesignationService.listHrmsDesignation();
	}

	@GetMapping(value = "/getAllSupervisorDesignation")
	public ResponseEntity<List<HrmsDesignation>> getAllSupervisorDesignation() {
		return hrmsDesignationService.listHrmsDesignationApprover();
	}

	@GetMapping(value = "/listDesignationResponse")
	public ResponseEntity<List<DesignationResponse>> listDesignationResponse() {
		return hrmsDesignationService.listDesignationResponse();

	}

	@GetMapping(value = "/getAllDesignationSupervisorByDepartmentId/{departmentId}")
	public ResponseEntity<List<HrmsDesignation>> getAllDesignationSupervisorByDepartmentId(
			@PathVariable("departmentId") int departmentId) {
		return hrmsDesignationService.listHrmsDesignationSupervisorByDepartmentId(departmentId);
	}

}
