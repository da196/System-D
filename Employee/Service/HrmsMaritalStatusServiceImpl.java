package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsMaritalStatus;
import com.Hrms.Employee.Repository.HrmsMaritalStatusRepository;

@Service
public class HrmsMaritalStatusServiceImpl implements HrmsMaritalStatusService {
	@Autowired
	private HrmsMaritalStatusRepository hrmsMaritalStatusRepository;

	@Override
	public ResponseEntity<List<HrmsMaritalStatus>> listHrmsMaritalStatus() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsMaritalStatusRepository.findAll());
	}

}
