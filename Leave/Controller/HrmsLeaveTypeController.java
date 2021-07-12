package com.Hrms.Leave.Controller;

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

import com.Hrms.Leave.Entity.HrmsLeaveType;
import com.Hrms.Leave.Service.HrmsLeaveTypeService;

@RestController
@RequestMapping("/v1/leaveType")
public class HrmsLeaveTypeController {

	@Autowired
	private HrmsLeaveTypeService hrmsLeaveTypeService;

	@PostMapping(value = "/addLeaveType")
	public ResponseEntity<HrmsLeaveType> addLeaveType(@RequestBody HrmsLeaveType hrmsLeaveType) {
		return hrmsLeaveTypeService.addLeaveType(hrmsLeaveType);

	}

	@GetMapping(value = "/getLeaveTypeById/{id}")
	public ResponseEntity<HrmsLeaveType> getLeaveTypeById(@PathVariable("id") int id) {
		return hrmsLeaveTypeService.getLeaveTypeById(id);

	}

	@PutMapping(value = "/updateLeaveType/{id}")
	public ResponseEntity<HrmsLeaveType> updateLeaveType(@RequestBody HrmsLeaveType hrmsLeaveType,
			@PathVariable("id") int id) {

		return hrmsLeaveTypeService.updateLeaveType(hrmsLeaveType, id);

	}

	@DeleteMapping(value = "/deleteLeaveType/{id}")
	public ResponseEntity<?> deleteLeaveType(@PathVariable("id") int id) {
		return hrmsLeaveTypeService.deleteLeaveType(id);

	}

	@GetMapping(value = "/getAllLeaveType")
	public ResponseEntity<List<HrmsLeaveType>> getAllLeaveType() {

		return hrmsLeaveTypeService.getAllLeaveType();

	}

}
