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

import com.Hrms.Employee.DTO.HrmsSalarystructureDetails;
import com.Hrms.Employee.Entity.HrmsSalarystructure;
import com.Hrms.Employee.Service.HrmsSalarystructureService;

@RestController
@RequestMapping("/v1/salarystructure")
public class HrmsSalarystructureController {

	@Autowired
	private HrmsSalarystructureService hrmsSalarystructureService;

	@PostMapping(value = "/addSalarystructure")
	public ResponseEntity<HrmsSalarystructure> addSalarystructure(
			@RequestBody HrmsSalarystructure hrmsSalarystructure) {
		return hrmsSalarystructureService.save(hrmsSalarystructure);
	}

	@GetMapping(value = "/getSalarystructure/{id}")
	public ResponseEntity<Optional<HrmsSalarystructure>> getSalarystructure(@PathVariable("id") int id) {
		return hrmsSalarystructureService.viewHrmsSalarystructure(id);

	}

	@PutMapping(value = "/updateSalarystructure/{id}")
	public ResponseEntity<HrmsSalarystructure> updateSalarystructure(
			@RequestBody HrmsSalarystructure hrmsSalarystructure, @PathVariable("id") int id) {

		return hrmsSalarystructureService.update(hrmsSalarystructure, id);

	}

	@DeleteMapping(value = "/deleteSalarystructure/{id}")
	public ResponseEntity<?> deleteSalarystructure(@PathVariable("id") int id) {

		return hrmsSalarystructureService.deleteHrmsSalarystructure(id);

	}

	@GetMapping(value = "/getAllSalarystructure")
	public ResponseEntity<List<HrmsSalarystructureDetails>> listSalarystructure() {
		return hrmsSalarystructureService.listHrmsSalarystructure();

	}

}
