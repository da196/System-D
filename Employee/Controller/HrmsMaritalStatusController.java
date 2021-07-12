package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsMaritalStatus;
import com.Hrms.Employee.Service.HrmsMaritalStatusService;

@RestController
@RequestMapping("/v1/maritalStatus")
public class HrmsMaritalStatusController {

	@Autowired
	private HrmsMaritalStatusService hrmsMaritalStatusService;

	@GetMapping(value = "/getAllMaritalStatus")
	public ResponseEntity<List<HrmsMaritalStatus>> listMaritalStatus() {
		return hrmsMaritalStatusService.listHrmsMaritalStatus();
	}

}
