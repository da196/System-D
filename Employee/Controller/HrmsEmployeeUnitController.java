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

import com.Hrms.Employee.Entity.HrmsEmployeeUnit;
import com.Hrms.Employee.Service.HrmsEmployeeUnitService;

@RestController
@RequestMapping("/v1/employeeUnit")
public class HrmsEmployeeUnitController {

	@Autowired
	private HrmsEmployeeUnitService hrmsEmployeeUnitService;

	@PostMapping(value = "/addEmployeeUnit")
	public ResponseEntity<HrmsEmployeeUnit> addEmployeeUnit(@RequestBody HrmsEmployeeUnit hrmsEmployeeUnit) {

		return hrmsEmployeeUnitService.save(hrmsEmployeeUnit);
	}

	@GetMapping(value = "/getEmployeeUnit/{id}")
	public ResponseEntity<Optional<HrmsEmployeeUnit>> getEmployeeUnit(@PathVariable("id") int id) {

		return hrmsEmployeeUnitService.viewHrmsEmployeeUnit(id);
	}

	@PutMapping(value = "/updateEmployeeUnit/{id}")
	public ResponseEntity<HrmsEmployeeUnit> updateEmployeeUnit(@RequestBody HrmsEmployeeUnit hrmsEmployeeUnit,
			@PathVariable("id") int id) {

		return hrmsEmployeeUnitService.update(hrmsEmployeeUnit, id);

	}

	@DeleteMapping(value = "/deleteEmployeeUnit/{id}")
	public ResponseEntity<?> deleteEmployeeUnit(@PathVariable("id") int id) {

		return hrmsEmployeeUnitService.deleteHrmsEmployeeUnit(id);

	}

	@GetMapping(value = "/getAllEmployeeUnit")
	public ResponseEntity<List<HrmsEmployeeUnit>> listEmployeeUnit() {

		return hrmsEmployeeUnitService.listHrmsEmployeeUnit();

	}

}
