package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsContactType;
import com.Hrms.Employee.Repository.HrmsContactTypeRepository;

@Service
public class HrmsContactTypeServiceImpl implements HrmsContactTypeService {
	@Autowired
	private HrmsContactTypeRepository hrmsContactTypeRepository;

	@Override
	public ResponseEntity<List<HrmsContactType>> listContactType() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsContactTypeRepository.findByActive(1));
	}

}
