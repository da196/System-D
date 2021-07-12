package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsRoles;
import com.Hrms.Employee.Service.HrmsRolesService;

@RestController
@RequestMapping("/v1/roles")
public class HrmsRolesController {
	@Autowired
	private HrmsRolesService hrmsRolesService;

	@GetMapping(value = "/getAllRoles")
	public ResponseEntity<List<HrmsRoles>> listRoles() {
		return hrmsRolesService.listHrmsRoles();
	}

	@PostMapping(value = "/addRole")
	public ResponseEntity<HrmsRoles> addRole(@RequestBody HrmsRoles hrmsRoles) {
		return hrmsRolesService.addRole(hrmsRoles);
	}

}
