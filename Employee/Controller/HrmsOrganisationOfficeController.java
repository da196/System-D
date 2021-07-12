package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.OrganizationOfficeResponse;
import com.Hrms.Employee.Entity.HrmsOrganisationOffice;
import com.Hrms.Employee.Service.HrmsOrganisationOfficeService;

@RestController
@RequestMapping("v1/organisationOffice")
public class HrmsOrganisationOfficeController {
	@Autowired
	private HrmsOrganisationOfficeService hrmsOrganisationOfficeService;

	@PostMapping(value = "/addOrganisationOffice")
	public ResponseEntity<HrmsOrganisationOffice> addOrganisationOffice(
			@RequestBody HrmsOrganisationOffice hrmsOrganisationOffice) {
		return hrmsOrganisationOfficeService.addOrganisationOffice(hrmsOrganisationOffice);
	}

	@GetMapping(value = "/getOrganisationOffice/{id}")
	public ResponseEntity<OrganizationOfficeResponse> getOrganisationOffice(@PathVariable("id") int id) {
		return hrmsOrganisationOfficeService.getOrganisationOffice(id);
	}

	@PutMapping(value = "/updateOrganisationOffice/{id}")
	public ResponseEntity<HrmsOrganisationOffice> updateOrganisationOffice(
			@RequestBody HrmsOrganisationOffice hrmsOrganisationOffice, @PathVariable("id") int id) {
		return hrmsOrganisationOfficeService.updateOrganisationOffice(hrmsOrganisationOffice, id);
	}

	@DeleteMapping(value = "/deleteOrganisationOffice/{id}")
	public ResponseEntity<?> deleteOrganisationOffice(@PathVariable("id") int id) {
		return hrmsOrganisationOfficeService.deleteOrganisationOffice(id);
	}

	@GetMapping(value = "/getAllOrganisationOffices")
	public ResponseEntity<List<OrganizationOfficeResponse>> listOrganisationOffice() {
		return hrmsOrganisationOfficeService.listOrganisationOffice();
	}

}
