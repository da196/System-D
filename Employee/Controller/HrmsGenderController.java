package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsGender;
import com.Hrms.Employee.Service.HrmsGenderService;

@RestController
@RequestMapping("/v1/gender")
public class HrmsGenderController {
	@Autowired
	private HrmsGenderService hrmsGenderService;

	@GetMapping(value = "/getAllgender")
	public ResponseEntity<List<HrmsGender>> listgender() {
		return hrmsGenderService.listHrmsGender();
	}

}
