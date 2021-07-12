package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRoles;

@Service
public interface HrmsRolesService {

	public ResponseEntity<HrmsRoles> addRole(HrmsRoles hrmsRoles);

	public ResponseEntity<List<HrmsRoles>> listHrmsRoles();

}
