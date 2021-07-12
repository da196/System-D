package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsEducationInstitutionType;
import com.Hrms.Employee.Service.HrmsEducationInstitutionTypeService;

@RestController

@RequestMapping("v1/educationInstitutionType")
public class HrmsEducationInstitutionTypeController {

	@Autowired

	private HrmsEducationInstitutionTypeService hrmsEducationInstitutionTypeService;

	@GetMapping(value = "/getAllEducationInstitutionType")
	public ResponseEntity<List<HrmsEducationInstitutionType>> listEducationInstitutionType() {

		return hrmsEducationInstitutionTypeService.listEducationInstitutionType();

	}

}
