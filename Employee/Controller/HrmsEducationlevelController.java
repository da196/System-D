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

import com.Hrms.Employee.Entity.HrmsEducationlevel;
import com.Hrms.Employee.Service.HrmsEducationlevelService;

@RestController
@RequestMapping("/v1/educationlevel")
public class HrmsEducationlevelController {

	@Autowired
	private HrmsEducationlevelService hrmsEducationlevelService;

	@PostMapping(value = "/addEducationlevel")
	public ResponseEntity<HrmsEducationlevel> addEducationlevel(@RequestBody HrmsEducationlevel hrmsEducationlevel) {

		return hrmsEducationlevelService.save(hrmsEducationlevel);

	}

	@GetMapping(value = "/getEducationlevel/{id}")
	public ResponseEntity<Optional<HrmsEducationlevel>> getEducationlevel(@PathVariable("id") int id) {

		return hrmsEducationlevelService.viewHrmsEducationlevel(id);

	}

	@PutMapping(value = "/updateEducationlevel/{id}")
	public ResponseEntity<HrmsEducationlevel> updateEducationlevel(@RequestBody HrmsEducationlevel hrmsEducationlevel,
			@PathVariable("id") int id) {

		return hrmsEducationlevelService.update(hrmsEducationlevel, id);

	}

	@DeleteMapping(value = "/deleteEducationlevel/{id}")
	public ResponseEntity<?> deleteEducationlevel(@PathVariable("id") int id) {
		return hrmsEducationlevelService.deleteHrmsEducationlevel(id);

	}

	@GetMapping(value = "/getAllEducationlevel")
	public ResponseEntity<List<HrmsEducationlevel>> listEducationlevel() {
		return hrmsEducationlevelService.listHrmsEducationlevel();

	}

}
