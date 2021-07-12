package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsNationality;
import com.Hrms.Employee.Service.HrmsNationalityService;

@RestController
@RequestMapping("/v1/nationality")
public class HrmsNationalityController {

	@Autowired
	private HrmsNationalityService hrmsNationalityService;

	@GetMapping(value = "/getAllNationality")
	public ResponseEntity<List<HrmsNationality>> listNationality() {
		return hrmsNationalityService.listHrmsNationality();
	}

}
