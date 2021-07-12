package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.UserResponse;
import com.Hrms.Employee.Entity.HrmsUser;

@Service
public interface HrmsUserService {
	public ResponseEntity<HrmsUser> save(HrmsUser hrmsUser);

	public ResponseEntity<UserResponse> getUserById(int id);

	public ResponseEntity<HrmsUser> update(HrmsUser hrmsUser, int id);

	public ResponseEntity<?> deleteHrmsUser(int id);

	public ResponseEntity<List<UserResponse>> getAllUsers();

}
