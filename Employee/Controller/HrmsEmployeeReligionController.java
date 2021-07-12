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

import com.Hrms.Employee.Entity.HrmsEmployeeReligion;
import com.Hrms.Employee.Service.HrmsEmployeeReligionService;

@RestController
@RequestMapping("/v1/employeeReligion")
public class HrmsEmployeeReligionController {

	@Autowired
	private HrmsEmployeeReligionService hrmsEmployeeReligionService;

	@PostMapping(value = "/addEmployeeReligion")
	public ResponseEntity<HrmsEmployeeReligion> addEmployeeReligion(
			@RequestBody HrmsEmployeeReligion hrmsEmployeeReligion) {

		return hrmsEmployeeReligionService.save(hrmsEmployeeReligion);
	}

	@GetMapping(value = "/getEmployeeReligion/{id}")
	public ResponseEntity<Optional<HrmsEmployeeReligion>> getEmployeeReligion(@PathVariable("id") int id) {

		return hrmsEmployeeReligionService.viewHrmsEmployeeReligion(id);
	}

	@PutMapping(value = "/updateEmployeeReligion/{id}")
	public ResponseEntity<HrmsEmployeeReligion> updateEmployeeReligion(
			@RequestBody HrmsEmployeeReligion hrmsEmployeeReligion, @PathVariable("id") int id) {

		return hrmsEmployeeReligionService.update(hrmsEmployeeReligion, id);
	}

	@DeleteMapping(value = "/deleteEmployeeReligion/{id}")
	public ResponseEntity<?> deleteEmployeeReligion(@PathVariable("id") int id) {

		return hrmsEmployeeReligionService.deleteHrmsEmployeeReligion(id);
	}

	@GetMapping(value = "/getAllEmployeeReligion")
	public ResponseEntity<List<HrmsEmployeeReligion>> listEmployeeReligion() {
		return hrmsEmployeeReligionService.listHrmsEmployeeReligion();

	}

}
