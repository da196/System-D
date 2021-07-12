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

import com.Hrms.Employee.Entity.HrmsEmployeeContact;
import com.Hrms.Employee.Service.HrmsEmployeeContactService;

@RestController
@RequestMapping("v1/employeeContact")
public class HrmsEmployeeContactController {
	@Autowired
	private HrmsEmployeeContactService hrmsEmployeeContactService;

	@PostMapping(value = "/addEmployeeContact")
	public ResponseEntity<HrmsEmployeeContact> addEmployeeContact(
			@RequestBody HrmsEmployeeContact hrmsEmployeeContact) {

		return hrmsEmployeeContactService.addEmployeeContact(hrmsEmployeeContact);

	}

	@GetMapping(value = "/getEmployeeContact/{id}")
	public ResponseEntity<Optional<HrmsEmployeeContact>> getEmployeeContact(@PathVariable("id") int id) {
		return hrmsEmployeeContactService.getEmployeeContact(id);

	}

	@PutMapping(value = "/updateEmployeeContact/{id}")
	public ResponseEntity<HrmsEmployeeContact> updateEmployeeContact(
			@RequestBody HrmsEmployeeContact hrmsEmployeeContact, @PathVariable("id") int id) {

		return hrmsEmployeeContactService.updateEmployeeContact(hrmsEmployeeContact, id);

	}

	@DeleteMapping(value = "/deleteEmployeeContact/{id}")
	public ResponseEntity<?> deleteEmployeeContact(@PathVariable("id") int id) {
		return hrmsEmployeeContactService.deleteEmployeeContact(id);

	}

	@GetMapping(value = "/getAllEmployeeContact()")
	public ResponseEntity<List<HrmsEmployeeContact>> listEmployeeContact() {

		return hrmsEmployeeContactService.listEmployeeContact();

	}

}
