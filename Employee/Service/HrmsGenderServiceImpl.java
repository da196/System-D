package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsGender;
import com.Hrms.Employee.Repository.HrmsGenderRepository;

@Service
public class HrmsGenderServiceImpl implements HrmsGenderService {
	@Autowired
	private HrmsGenderRepository hrmsGenderRepository;

	@Override
	public ResponseEntity<List<HrmsGender>> listHrmsGender() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsGenderRepository.findAll());
	}

}
