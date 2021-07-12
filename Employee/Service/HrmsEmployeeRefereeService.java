package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeRefereeResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeReferee;

@Service
public interface HrmsEmployeeRefereeService {

	public ResponseEntity<HrmsEmployeeReferee> addEmployeeReferee(HrmsEmployeeReferee hrmsEmployeeReferee);

	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> getEmployeeRefereeByEmpId(int id);

	public ResponseEntity<HrmsEmployeeRefereeResponse> getEmployeeRefereeById(int id);

	public ResponseEntity<HrmsEmployeeReferee> updateEmployeeReferee(HrmsEmployeeReferee hrmsEmployeeReferee, int id);

	public ResponseEntity<?> deleteEmployeeReferee(int id);

	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> listEmployeeReferees();

	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> getEmployeeRefereeByEmail(String email);

	public ResponseEntity<List<HrmsEmployeeRefereeResponse>> listEmployeeRefereesNonApproved();

	public ResponseEntity<?> approveOrRejectEmployeeReferee(EmployeeApprovalComment employeeApprovalComment, int id,
			int status);

}
