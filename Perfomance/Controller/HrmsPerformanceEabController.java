package com.Hrms.Perfomance.Controller;

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

import com.Hrms.Perfomance.DTO.PerformanceEabFactorOprasResponse;
import com.Hrms.Perfomance.DTO.PerformanceEabResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;
import com.Hrms.Perfomance.Service.HrmsPerformanceEabService;

@RestController
@RequestMapping("v1/performanceEab")
public class HrmsPerformanceEabController {
	@Autowired
	private HrmsPerformanceEabService hrmsPerformanceEabService;

	@PostMapping(value = "/addPerformanceEab")
	public ResponseEntity<HrmsPerformanceEab> addPerformanceEab(@RequestBody HrmsPerformanceEab hrmsPerformanceEab) {

		return hrmsPerformanceEabService.addPerformanceEab(hrmsPerformanceEab);
	}

	@GetMapping(value = "/getPerformanceEabById/{id}")
	public ResponseEntity<PerformanceEabResponse> getPerformanceEabById(@PathVariable("id") int id) {

		return hrmsPerformanceEabService.getPerformanceEabById(id);

	}

	@PutMapping(value = "/updatePerformanceEab/{id}")
	public ResponseEntity<HrmsPerformanceEab> updatePerformanceEab(@RequestBody HrmsPerformanceEab hrmsPerformanceEab,
			@PathVariable("id") int id) {

		return hrmsPerformanceEabService.updatePerformanceEab(hrmsPerformanceEab, id);

	}

	@DeleteMapping(value = "/deletePerformanceEab/{id}")
	public ResponseEntity<?> deletePerformanceEab(@PathVariable("id") int id) {

		return hrmsPerformanceEabService.deletePerformanceEab(id);

	}

	@GetMapping(value = "/getAllPerformanceEab")
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEab() {
		return hrmsPerformanceEabService.getAllPerformanceEab();

	}

	@GetMapping(value = "/getAllPerformanceEabByQualityidId/{qualityid}")
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByQualityidId(
			@PathVariable("qualityid") int qualityid) {
		return hrmsPerformanceEabService.getAllPerformanceEabByQualityidId(qualityid);

	}

	@GetMapping(value = "/getAllPerformanceEabByEppaId/{eppaid}")
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByEppaId(
			@PathVariable("eppaid") int eppaid) {

		return hrmsPerformanceEabService.getAllPerformanceEabByEppaId(eppaid);

	}

	@GetMapping(value = "/getAllPerformanceEabByEmployeeId/{employeeid}")
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByEmployeeId(
			@PathVariable("employeeid") int employeeid) {

		return hrmsPerformanceEabService.getAllPerformanceEabByEmployeeId(employeeid);

	}

	@GetMapping(value = "/getAllPerformanceEabByEppaIdV2/{eppaid}")
	public ResponseEntity<List<PerformanceEabFactorOprasResponse>> getAllPerformanceEabByEppaIdV2(
			@PathVariable("eppaid") int eppaid) {

		return hrmsPerformanceEabService.getAllPerformanceEabByEppaIdV2(eppaid);
	}

}
