package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.OrganizationOfficeResponse;
import com.Hrms.Employee.Entity.HrmsOrganisationOffice;

@Service
public interface HrmsOrganisationOfficeService {
	public ResponseEntity<HrmsOrganisationOffice> addOrganisationOffice(HrmsOrganisationOffice hrmsOrganisationOffice);

	public ResponseEntity<OrganizationOfficeResponse> getOrganisationOffice(int id);

	public ResponseEntity<HrmsOrganisationOffice> updateOrganisationOffice(
			HrmsOrganisationOffice hrmsOrganisationOffice, int id);

	public ResponseEntity<?> deleteOrganisationOffice(int id);

	public ResponseEntity<List<OrganizationOfficeResponse>> listOrganisationOffice();

}
