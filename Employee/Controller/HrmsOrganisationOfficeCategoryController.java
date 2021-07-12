package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsOrganisationOfficeCategory;
import com.Hrms.Employee.Service.HrmsOrganisationOfficeCategoryService;

@RestController
@RequestMapping("v1/organisationOfficeCategory")
public class HrmsOrganisationOfficeCategoryController {
	@Autowired
	private HrmsOrganisationOfficeCategoryService hrmsOrganisationOfficeCategoryService;

	@GetMapping(value = "/getAllOrganisationOfficeCategory")
	public ResponseEntity<List<HrmsOrganisationOfficeCategory>> listOrganisationOfficeCategory() {
		return hrmsOrganisationOfficeCategoryService.listOrganisationOfficeCategory();
	}

}
