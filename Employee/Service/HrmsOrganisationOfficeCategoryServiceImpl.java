package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrganisationOfficeCategory;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeCategoryRepository;

@Service
public class HrmsOrganisationOfficeCategoryServiceImpl implements HrmsOrganisationOfficeCategoryService {
	@Autowired
	private HrmsOrganisationOfficeCategoryRepository hrmsOrganisationOfficeCategoryRepository;

	@Override
	public ResponseEntity<List<HrmsOrganisationOfficeCategory>> listOrganisationOfficeCategory() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsOrganisationOfficeCategoryRepository.findAll());
	}

}
