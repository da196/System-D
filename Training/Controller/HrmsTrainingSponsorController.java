package com.Hrms.Training.Controller;

import java.util.List;

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

import com.Hrms.Training.Entity.HrmsTrainingSponsor;
import com.Hrms.Training.Service.HrmsTrainingSponsorService;

@RestController
@RequestMapping("v1/trainingSponsor")
public class HrmsTrainingSponsorController {

	@Autowired
	private HrmsTrainingSponsorService hrmsTrainingSponsorService;

	@GetMapping(value = "/getAllTrainingSponsor")
	public ResponseEntity<List<HrmsTrainingSponsor>> getAllTrainingSponsor() {

		return hrmsTrainingSponsorService.getAllTrainingSponsor();

	}

	@PostMapping(value = "/addTrainingSponsor")
	public ResponseEntity<HrmsTrainingSponsor> addTrainingSponsor(
			@RequestBody HrmsTrainingSponsor hrmsTrainingSponsor) {

		return hrmsTrainingSponsorService.addTrainingSponsor(hrmsTrainingSponsor);

	}

	@GetMapping(value = "/getTrainingSponsorById")
	public ResponseEntity<HrmsTrainingSponsor> getTrainingSponsorById(@PathVariable("id") int id) {

		return hrmsTrainingSponsorService.getTrainingSponsorById(id);

	}

	@PutMapping(value = "/updateTrainingSponsor/{id}")
	public ResponseEntity<HrmsTrainingSponsor> updateTrainingSponsor(
			@RequestBody HrmsTrainingSponsor hrmsTrainingSponsor, @PathVariable("id") int id) {

		return hrmsTrainingSponsorService.updateTrainingSponsorr(hrmsTrainingSponsor, id);

	}

	@DeleteMapping(value = "/deleteTrainingSponsor/{id}")
	public ResponseEntity<?> deleteTrainingSponsor(@PathVariable("id") int id) {

		return hrmsTrainingSponsorService.deleteTrainingSponsor(id);

	}

}
