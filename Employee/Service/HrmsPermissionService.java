package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsPermission;

@Service
public interface HrmsPermissionService {

	public ResponseEntity<List<HrmsPermission>> listHrmsPermission();

}
