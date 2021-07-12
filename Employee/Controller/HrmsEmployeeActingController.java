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

import com.Hrms.Employee.Entity.HrmsEmployeeActing;
import com.Hrms.Employee.Service.HrmsEmployeeActingService;

@RestController
@RequestMapping("/v1/EmployeeActing")
public class HrmsEmployeeActingController {

	@Autowired
	private HrmsEmployeeActingService hrmsEmployeeActingService;

	@PostMapping(value = "/addEmployeeActing")
	public ResponseEntity<HrmsEmployeeActing> addEmployeeActing(@RequestBody HrmsEmployeeActing hrmsEmployeeActing) {

		return hrmsEmployeeActingService.addEmployeeActing(hrmsEmployeeActing);

	}

	@GetMapping(value = "/getEmployeeActing/{id}")
	public ResponseEntity<Optional<HrmsEmployeeActing>> getEmployeeActing(@PathVariable("id") int id) {

		return hrmsEmployeeActingService.getEmployeeActing(id);

	}

	@PutMapping(value = "/updateEmployeeActing/{id}")
	public ResponseEntity<HrmsEmployeeActing> updateEmployeeActing(@RequestBody HrmsEmployeeActing hrmsEmployeeActing,
			@PathVariable("id") int id) {

		return hrmsEmployeeActingService.updateEmployeeActing(hrmsEmployeeActing, id);

	}

	@DeleteMapping(value = "/deleteEmployeeActing/{id}")
	public ResponseEntity<?> deleteEmployeeActing(@PathVariable("id") int id) {
		return hrmsEmployeeActingService.deleteHrmsEmployeeActing(id);

	}

	@GetMapping(value = "/getAllEmployeeActing")
	public ResponseEntity<List<HrmsEmployeeActing>> getAllEmployeeActing() {

		return hrmsEmployeeActingService.listHrmsEmployeeActing();

	}

}
