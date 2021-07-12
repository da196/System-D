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

import com.Hrms.Employee.Entity.HrmsEdattachmentType;
import com.Hrms.Employee.Service.HrmsEdattachmentTypeService;

@RestController
@RequestMapping("/v1/edattachmentType")
public class HrmsEdattachmentTypeController {

	@Autowired
	private HrmsEdattachmentTypeService hrmsEdattachmentTypeService;

	@PostMapping(value = "/addEdattachmentType")
	public ResponseEntity<HrmsEdattachmentType> addEdattachmentType(
			@RequestBody HrmsEdattachmentType hrmsEdattachmentType) {
		return hrmsEdattachmentTypeService.save(hrmsEdattachmentType);

	}

	@GetMapping(value = "/getEdattachmentType/{id}")
	public ResponseEntity<Optional<HrmsEdattachmentType>> getEdattachmentType(@PathVariable("id") int id) {

		return hrmsEdattachmentTypeService.viewHrmsEdattachmentType(id);

	}

	@PutMapping(value = "/updateEdattachmentType/{id}")
	public ResponseEntity<HrmsEdattachmentType> updateEdattachmentType(
			@RequestBody HrmsEdattachmentType hrmsEdattachmentType, @PathVariable("id") int id) {

		return hrmsEdattachmentTypeService.update(hrmsEdattachmentType, id);

	}

	@DeleteMapping(value = "/deleteEdattachmentType/{id}")
	public ResponseEntity<?> deleteEdattachmentType(@PathVariable("id") int id) {

		return hrmsEdattachmentTypeService.deleteHrmsEdattachmentType(id);

	}

	@GetMapping(value = "/getAllEdattachmentType")
	public ResponseEntity<List<HrmsEdattachmentType>> listEdattachmentType() {

		return hrmsEdattachmentTypeService.listHrmsEdattachmentType();

	}

}
