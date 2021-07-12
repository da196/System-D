package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsPermission;
import com.Hrms.Employee.Service.HrmsPermissionService;

@RestController
@RequestMapping("/v1/permission")
public class HrmsPermissionController {

	@Autowired
	private HrmsPermissionService hrmsPermissionService;

	@GetMapping(value = "/getAllPermission")
	public ResponseEntity<List<HrmsPermission>> listHrmsPermission() {
		return hrmsPermissionService.listHrmsPermission();
	}

}
