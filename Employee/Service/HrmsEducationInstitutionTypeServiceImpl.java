package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEducationInstitutionType;
import com.Hrms.Employee.Repository.HrmsEducationInstitutionTypeRepository;

@Service
public class HrmsEducationInstitutionTypeServiceImpl implements HrmsEducationInstitutionTypeService {
	@Autowired
	private HrmsEducationInstitutionTypeRepository hrmsEducationInstitutionTypeRepository;

	@Override
	public ResponseEntity<List<HrmsEducationInstitutionType>> listEducationInstitutionType() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsEducationInstitutionTypeRepository.findByActive(1));
	}

}
