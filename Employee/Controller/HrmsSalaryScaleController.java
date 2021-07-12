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

import com.Hrms.Employee.Entity.HrmsSalaryScale;
import com.Hrms.Employee.Service.HrmsSalaryScaleService;

@RestController
@RequestMapping("/v1/salaryScale")
public class HrmsSalaryScaleController {

	@Autowired
	private HrmsSalaryScaleService hrmsSalaryScaleService;

	@PostMapping(value = "/addSalaryScale")
	public ResponseEntity<HrmsSalaryScale> addSalaryScale(@RequestBody HrmsSalaryScale hrmsSalaryScale) {
		return hrmsSalaryScaleService.save(hrmsSalaryScale);

	}

	@GetMapping(value = "/getSalaryScale/{id}")
	public ResponseEntity<Optional<HrmsSalaryScale>> getSalaryScale(@PathVariable("id") int id) {
		return hrmsSalaryScaleService.viewHrmsSalaryScale(id);

	}

	@PutMapping(value = "/updateSalaryScale/{id}")
	public ResponseEntity<HrmsSalaryScale> updateSalaryScale(@RequestBody HrmsSalaryScale hrmsSalaryScale,
			@PathVariable("id") int id) {
		return hrmsSalaryScaleService.update(hrmsSalaryScale, id);

	}

	@DeleteMapping(value = "/deleteSalaryScale/{id}")
	public ResponseEntity<?> deleteSalaryScale(@PathVariable("id") int id) {
		return hrmsSalaryScaleService.deleteHrmsSalaryScale(id);

	}

	@GetMapping(value = "/getAllSalaryScale")
	public ResponseEntity<List<HrmsSalaryScale>> listSalaryScale() {

		return hrmsSalaryScaleService.listHrmsSalaryScale();

	}

}
