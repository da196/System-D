package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.UserRolesResponse;
import com.Hrms.Employee.Entity.HrmsUserRoles;

@Service
public interface HrmsUserRolesService {

	public ResponseEntity<HrmsUserRoles> save(HrmsUserRoles hrmsUserRoles);

	public ResponseEntity<List<HrmsUserRoles>> savelist(HrmsUserRoles hrmsUserRoles, List<Integer> roles);

	public ResponseEntity<Optional<HrmsUserRoles>> viewHrmsUserRoles(int id);

	public ResponseEntity<UserRolesResponse> getUserRolesById(int id);

	public ResponseEntity<List<UserRolesResponse>> getAllUserRoles();

	public ResponseEntity<List<UserRolesResponse>> getUserRolesByEmpId(int empid);

	public ResponseEntity<List<UserRolesResponse>> getUserRolesByUserId(int userid);

	public ResponseEntity<HrmsUserRoles> update(HrmsUserRoles hrmsUserRoles, int id);

	public ResponseEntity<?> deleteHrmsUserRoles(int id);

	public ResponseEntity<List<HrmsUserRoles>> listHrmsUserRoles();

}
