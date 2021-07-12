package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsNationality;
import com.Hrms.Employee.Repository.HrmsNationalityRepository;

@Service
public class HrmsNationalityServiceImpl implements HrmsNationalityService {
	@Autowired
	private HrmsNationalityRepository hrmsNationalityRepository;

	@Override
	public ResponseEntity<List<HrmsNationality>> listHrmsNationality() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsNationalityRepository.findByActiveOrderByIdAsc(1));

	}

}
