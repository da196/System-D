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

import com.Hrms.Employee.Entity.HrmsAllowancetype;
import com.Hrms.Employee.Service.HrmsAllowancetypeService;

@RestController
@RequestMapping("v1/allowancetype")
public class HrmsAllowancetypeController {

	@Autowired
	private HrmsAllowancetypeService hrmsAllowancetypeService;

	@PostMapping(value = "/addAllowanceType")
	public ResponseEntity<HrmsAllowancetype> addAllowanceType(@RequestBody HrmsAllowancetype hrmsAllowancetype) {
		return hrmsAllowancetypeService.save(hrmsAllowancetype);

	}

	@GetMapping(value = "/getAllowancetype/{id}")
	public ResponseEntity<Optional<HrmsAllowancetype>> getAllowancetype(@PathVariable("id") int id) {

		return hrmsAllowancetypeService.viewHrmsAllowancetype(id);
	}

	@PutMapping(value = "/updateAllowanceType/{id}")
	public ResponseEntity<HrmsAllowancetype> updateAllowanceType(@RequestBody HrmsAllowancetype hrmsAllowancetype,
			@PathVariable("id") int id) {

		return hrmsAllowancetypeService.update(hrmsAllowancetype, id);

	}

	@DeleteMapping(value = "/deleteAllowancetype/{id}")
	public ResponseEntity<?> deleteAllowancetype(@PathVariable("id") int id) {

		return hrmsAllowancetypeService.deleteHrmsAllowancetype(id);

	}

	@GetMapping(value = "/listAllowancetype")
	public ResponseEntity<List<HrmsAllowancetype>> listAllowancetype() {

		return hrmsAllowancetypeService.listHrmsAllowancetype();

	}

}
