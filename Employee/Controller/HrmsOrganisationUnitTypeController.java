package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsOrganisationUnitType;
import com.Hrms.Employee.Service.HrmsOrganisationUnitTypeService;

@RestController
@RequestMapping("v1/OrganisationUnitType")
public class HrmsOrganisationUnitTypeController {
	@Autowired
	private HrmsOrganisationUnitTypeService hrmsOrganisationUnitTypeService;

	@GetMapping(value = "/getOrganisationUnitType")
	public ResponseEntity<List<HrmsOrganisationUnitType>> listOrganisationUnitType() {

		return hrmsOrganisationUnitTypeService.listOrganisationUnitType();

	}

}
