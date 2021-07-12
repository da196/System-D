package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsPermission;
import com.Hrms.Employee.Repository.HrmsPermissionRepository;

@Service
public class HrmsPermissionServiceImpl implements HrmsPermissionService {

	@Autowired
	private HrmsPermissionRepository hrmsPermissionRepository;

	@Override
	public ResponseEntity<List<HrmsPermission>> listHrmsPermission() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsPermissionRepository.findAll());
	}

}
