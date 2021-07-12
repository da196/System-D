package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsOrganisationUnitType;

@Service
public interface HrmsOrganisationUnitTypeService {

	public ResponseEntity<List<HrmsOrganisationUnitType>> listOrganisationUnitType();

}
