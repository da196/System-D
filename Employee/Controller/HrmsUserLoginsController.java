package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.Userloginresponse;
import com.Hrms.Employee.Service.HrmsUserLoginsService;

@RestController
@RequestMapping("/v1/userLogins")
public class HrmsUserLoginsController {

	@Autowired
	private HrmsUserLoginsService hrmsUserLoginsService;

	@GetMapping(value = "/getlog/{empid}")
	public ResponseEntity<List<Userloginresponse>> getlog(@PathVariable("empid") int empid) {

		return hrmsUserLoginsService.viewlog(empid);

	}

	@GetMapping(value = "/logout/{empid}")
	public ResponseEntity<?> logout(@PathVariable("empid") int empid) {
		return hrmsUserLoginsService.logout(empid);
	}

	@GetMapping(value = "/getAlllogs")
	public ResponseEntity<List<Userloginresponse>> listAlllogs() {
		return hrmsUserLoginsService.listAlllogs();

	}

}
