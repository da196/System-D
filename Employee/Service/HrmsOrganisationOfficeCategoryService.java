package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrganisationOfficeCategory;

@Service
public interface HrmsOrganisationOfficeCategoryService {
	public ResponseEntity<List<HrmsOrganisationOfficeCategory>> listOrganisationOfficeCategory();
}
