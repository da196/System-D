package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Configuration.JwtUtill;
import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Service.HrmsOrginisationUnitService;

@RestController
@RequestMapping("/v1/orginisationUnit")
public class HrmsOrginisationUnitController {

	@Autowired
	private HrmsOrginisationUnitService hrmsOrginisationUnitService;

	@Autowired
	private JwtUtill jwtTokenUtil;

	@GetMapping(value = "/getAllOrginisationUnit")
	public ResponseEntity<List<HrmsOrginisationUnit>> listorginisationUnit() {
		return hrmsOrginisationUnitService.listHrmsOrginisationUnit();

	}

	@GetMapping(value = "/getAllDepartment")
	public ResponseEntity<List<HrmsOrginisationUnit>> listDepartment() {

		return hrmsOrginisationUnitService.listDepartment();

	}

	@GetMapping(value = "/getAllSections")
	public ResponseEntity<List<HrmsOrginisationUnit>> listSections() {

		return hrmsOrginisationUnitService.listSections();

	}

	@GetMapping(value = "/getAllUnits")
	public ResponseEntity<List<HrmsOrginisationUnit>> listUnits() {

		return hrmsOrginisationUnitService.listUnits();
	}

	@GetMapping(value = "/getAllInDirectorgeneral")
	public ResponseEntity<List<HrmsOrginisationUnit>> listDirectorgeneral() {
		return hrmsOrginisationUnitService.listDirectorgeneral();
	}

	@GetMapping(value = "/getAllChildUnitByParentId/{parentId}")
	public ResponseEntity<List<HrmsOrginisationUnit>> getAllChildUnitByParentId(
			@PathVariable("parentId") int parentId) {

		return hrmsOrginisationUnitService.listChildUnitByParentId(parentId);

	}

	@GetMapping(value = "/getAllDepartmentsAndUnits")
	public ResponseEntity<List<HrmsOrginisationUnit>> listDepartmentsAndUnits() {

		return hrmsOrginisationUnitService.listDepartmentsAndUnits();
	}

}
