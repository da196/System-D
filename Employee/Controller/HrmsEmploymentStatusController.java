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

import com.Hrms.Employee.Entity.HrmsEmploymentStatus;
import com.Hrms.Employee.Service.HrmsEmploymentStatusService;

@RestController
@RequestMapping("v1/employmentStatus")
public class HrmsEmploymentStatusController {

	@Autowired
	private HrmsEmploymentStatusService hrmsEmploymentStatusService;

	@PostMapping(value = "/addEmploymentStatus")
	public ResponseEntity<HrmsEmploymentStatus> addEmploymentStatus(
			@RequestBody HrmsEmploymentStatus hrmsEmploymentStatus) {
		return hrmsEmploymentStatusService.addEmploymentStatus(hrmsEmploymentStatus);

	}

	@GetMapping(value = "/getEmploymentStatus/{id}")
	public ResponseEntity<Optional<HrmsEmploymentStatus>> getEmploymentStatus(@PathVariable("id") int id) {
		return hrmsEmploymentStatusService.getEmploymentStatus(id);

	}

	@PutMapping(value = "/updateEmploymentStatus/{id}")
	public ResponseEntity<HrmsEmploymentStatus> updateEmploymentStatus(
			@RequestBody HrmsEmploymentStatus hrmsEmploymentStatus, @PathVariable("id") int id) {
		return hrmsEmploymentStatusService.updateEmploymentStatus(hrmsEmploymentStatus, id);

	}

	@DeleteMapping(value = "/deleteEmploymentStatus/{id}")
	public ResponseEntity<?> deleteEmploymentStatus(@PathVariable("id") int id) {
		return hrmsEmploymentStatusService.deleteEmploymentStatus(id);

	}

	@GetMapping(value = "/getAllEmploymentStatus")
	public ResponseEntity<List<HrmsEmploymentStatus>> listEmploymentStatus() {
		return hrmsEmploymentStatusService.listEmploymentStatus();

	}

}
