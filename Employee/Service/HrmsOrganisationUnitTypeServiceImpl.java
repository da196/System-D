package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrganisationUnitType;
import com.Hrms.Employee.Repository.HrmsOrganisationUnitTypeRepository;

@Service
public class HrmsOrganisationUnitTypeServiceImpl implements HrmsOrganisationUnitTypeService {
	@Autowired
	private HrmsOrganisationUnitTypeRepository hrmsOrganisationUnitTypeRepository;

	@Override
	public ResponseEntity<List<HrmsOrganisationUnitType>> listOrganisationUnitType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsOrganisationUnitTypeRepository.findAll());
	}

}
