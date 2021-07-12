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

import com.Hrms.Employee.DTO.EducationinstitutionResponse;
import com.Hrms.Employee.Entity.HrmsEducationinstitution;
import com.Hrms.Employee.Service.HrmsEducationinstitutionService;

@RestController
@RequestMapping("/v1/educatinstitution")
public class HrmsEducationinstitutionController {
	@Autowired
	private HrmsEducationinstitutionService hrmsEducationinstitutionService;

	@PostMapping(value = "/addEducationinstitution")
	public ResponseEntity<HrmsEducationinstitution> addEducationinstitution(
			@RequestBody HrmsEducationinstitution hrmsEducationinstitution) {
		return hrmsEducationinstitutionService.save(hrmsEducationinstitution);

	}

	@GetMapping(value = "/getEducationinstitution/{id}")
	public ResponseEntity<Optional<HrmsEducationinstitution>> getEducationinstitution(@PathVariable("id") int id) {

		return hrmsEducationinstitutionService.viewHrmsEducationinstitution(id);

	}

	@GetMapping(value = "/getEducationinstitutionDetailed/{id}")
	public ResponseEntity<EducationinstitutionResponse> viewHrmsEducationinstitutionDetailed(
			@PathVariable("id") int id) {

		return hrmsEducationinstitutionService.viewHrmsEducationinstitutionDetailed(id);

	}

	@PutMapping(value = "/updateEducationinstitution/{id}")
	public ResponseEntity<HrmsEducationinstitution> updateEducationinstitution(
			@RequestBody HrmsEducationinstitution hrmsEducationinstitution, @PathVariable("id") int id) {
		return hrmsEducationinstitutionService.update(hrmsEducationinstitution, id);

	}

	@DeleteMapping(value = "/deleteEducationinstitution/{id}")
	public ResponseEntity<?> deleteEducationinstitution(@PathVariable("id") int id) {
		return hrmsEducationinstitutionService.deleteHrmsEducationinstitution(id);

	}

	@GetMapping(value = "/getAllEducationinstitution")
	public ResponseEntity<List<HrmsEducationinstitution>> listEducationinstitution() {

		return hrmsEducationinstitutionService.listHrmsEducationinstitution();

	}

	@GetMapping(value = "/getAllEducationinstitutionDetailed")
	public ResponseEntity<List<EducationinstitutionResponse>> listHrmsEducationinstitutionDetailed() {

		return hrmsEducationinstitutionService.listHrmsEducationinstitutionDetailed();

	}

	@GetMapping(value = "/getAllEducationinstitutionByCountryId/{countryId}")
	public ResponseEntity<List<HrmsEducationinstitution>> listEducationinstitutionByCountryId(
			@PathVariable("countryId") int countryId) {
		return hrmsEducationinstitutionService.listEducationinstitutionByCountryId(countryId);
	}

}
