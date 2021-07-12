package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.EmployeeRelativeRequest;
import com.Hrms.Employee.DTO.EmployeeRelativeResponse;

@Service
public interface HrmsEmployeeRelativeService {

	public ResponseEntity<EmployeeRelativeRequest> addEmployeeRelative(EmployeeRelativeRequest employeeRelativeRequest);

	public ResponseEntity<List<EmployeeRelativeResponse>> getEmployeeRelativeByEmpId(int id);

	public ResponseEntity<EmployeeRelativeResponse> getEmployeeRelativeById(int id);

	public ResponseEntity<EmployeeRelativeRequest> updateEmployeeRelative(
			EmployeeRelativeRequest employeeRelativeRequest, int id);

	public ResponseEntity<?> deleteEmployeeRelative(int id);

	public ResponseEntity<List<EmployeeRelativeResponse>> listEmployeeRelatives();

	public ResponseEntity<List<EmployeeRelativeResponse>> listEmployeeRelativesNonApproved();

	public ResponseEntity<?> approveOrRejectEmployeeRelative(EmployeeApprovalComment employeeApprovalComment, int id,
			int status);

}
