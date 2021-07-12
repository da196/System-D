package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsContactType;
import com.Hrms.Employee.Service.HrmsContactTypeService;

@RestController
@RequestMapping("v1/contactType")
public class HrmsContactTypeController {
	@Autowired

	private HrmsContactTypeService hrmsContactTypeService;

	@GetMapping(value = "/listAllContactType")
	public ResponseEntity<List<HrmsContactType>> listContactType() {
		return hrmsContactTypeService.listContactType();
	}

}
