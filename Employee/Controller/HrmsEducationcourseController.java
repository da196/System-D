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

import com.Hrms.Employee.DTO.EducationcourseDetailed;
import com.Hrms.Employee.Entity.HrmsEducationcourse;
import com.Hrms.Employee.Service.HrmsEducationcourseService;

@RestController
@RequestMapping("/v1/educationcourse")
public class HrmsEducationcourseController {

	@Autowired
	private HrmsEducationcourseService hrmsEducationcourseService;

	@PostMapping(value = "/addEducationcourse")
	public ResponseEntity<HrmsEducationcourse> addEducationcourse(
			@RequestBody HrmsEducationcourse hrmsEducationcourse) {

		return hrmsEducationcourseService.save(hrmsEducationcourse);

	}

	@GetMapping(value = "/getEducationcourse/{id}")
	public ResponseEntity<Optional<HrmsEducationcourse>> getEducationcourse(@PathVariable("id") int id) {

		return hrmsEducationcourseService.viewHrmsEducationcourse(id);

	}

	@PutMapping(value = "/updateEducationcourse/{id}")
	public ResponseEntity<HrmsEducationcourse> updateEducationcourse(
			@RequestBody HrmsEducationcourse hrmsEducationcourse, @PathVariable("id") int id) {

		return hrmsEducationcourseService.update(hrmsEducationcourse, id);

	}

	@DeleteMapping(value = "/deleteEducationcourse/{id}")
	public ResponseEntity<?> deleteEducationcourse(@PathVariable("id") int id) {

		return hrmsEducationcourseService.deleteHrmsEducationcourse(id);

	}

	@GetMapping(value = "/getAllEducationcourse")
	public ResponseEntity<List<HrmsEducationcourse>> listEducationcourse() {

		return hrmsEducationcourseService.listHrmsEducationcourse();

	}

	@GetMapping(value = "/getEducationcourseByLevelCode/{levelCode}")
	public ResponseEntity<List<HrmsEducationcourse>> EducationcourseByLevelCode(
			@PathVariable("levelCode") int levelCode) {
		return hrmsEducationcourseService.EducationcourseByLevelCode(levelCode);
	}

	@GetMapping(value = "/getAllEducationcourseDetailed")
	public ResponseEntity<List<EducationcourseDetailed>> listHrmsEducationcourseDetailed() {

		return hrmsEducationcourseService.listHrmsEducationcourseDetailed();

	}

}
