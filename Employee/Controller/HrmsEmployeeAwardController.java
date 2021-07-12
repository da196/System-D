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

import com.Hrms.Employee.Entity.HrmsEmployeeAward;
import com.Hrms.Employee.Service.HrmsEmployeeAwardService;

@RestController
@RequestMapping("v1/employeeAward")
public class HrmsEmployeeAwardController {
	@Autowired
	private HrmsEmployeeAwardService hrmsEmployeeAwardService;

	@PostMapping(value = "/addEmployeeAward")
	public ResponseEntity<HrmsEmployeeAward> addEmployeeAward(@RequestBody HrmsEmployeeAward hrmsEmployeeAward) {
		return hrmsEmployeeAwardService.addEmployeeAward(hrmsEmployeeAward);

	}

	@GetMapping(value = "/getEmployeeAward/{id}")
	public ResponseEntity<Optional<HrmsEmployeeAward>> getEmployeeAward(@PathVariable("id") int id) {
		return hrmsEmployeeAwardService.getEmployeeAward(id);
	}

	@PutMapping(value = "/updateEmployeeAward/{id}")
	public ResponseEntity<HrmsEmployeeAward> updateEmployeeAward(@RequestBody HrmsEmployeeAward HrmsEmployeeAward,
			@PathVariable("id") int id) {

		return hrmsEmployeeAwardService.updateEmployeeAward(HrmsEmployeeAward, id);

	}

	@DeleteMapping(value = "/deleteEmployeeAward/{id}")
	public ResponseEntity<?> deleteEmployeeAward(@PathVariable("id") int id) {

		return hrmsEmployeeAwardService.deleteEmployeeAward(id);

	}

	@GetMapping(value = "/getAllEmployeeAward")
	public ResponseEntity<List<HrmsEmployeeAward>> listEmployeeAward() {

		return hrmsEmployeeAwardService.listEmployeeAward();

	}
}
