
package com.Hrms.Employee.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsRoles;
import com.Hrms.Employee.Repository.HrmsRolesRepository;

@Service
public class HrmsRolesServiceImpl implements HrmsRolesService {
	@Autowired
	private HrmsRolesRepository hrmsRolesRepository;

	@Override
	public ResponseEntity<List<HrmsRoles>> listHrmsRoles() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsRolesRepository.findAll());
	}

	@Override
	public ResponseEntity<HrmsRoles> addRole(HrmsRoles hrmsRole) {
		if (hrmsRolesRepository.existsByName(hrmsRole.getName())) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsRole);
		} else {
			hrmsRole.setUnique_id(UUID.randomUUID());
			hrmsRole.setActive(1);
			hrmsRole.setApproved(0);
			return ResponseEntity.status(HttpStatus.OK).body(hrmsRolesRepository.save(hrmsRole));
		}
	}

}
