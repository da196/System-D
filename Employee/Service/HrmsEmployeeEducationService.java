package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.EmployeeEducationResponseV2;
import com.Hrms.Employee.DTO.HrmsEmployeeEducationRequest;

@Service
public interface HrmsEmployeeEducationService {

	public ResponseEntity<?> addEmployeeEducation(HrmsEmployeeEducationRequest hrmsEmployeeEducationRequest);

	public ResponseEntity<EmployeeEducationResponseV2> getEmployeeEducationById(int id);

	public ResponseEntity<List<EmployeeEducationResponseV2>> getEmployeeEducationByempId(int id);

	public ResponseEntity<HrmsEmployeeEducationRequest> updateEmployeeEducation(
			HrmsEmployeeEducationRequest hrmsEmployeeEducationRequest, int id);

	public ResponseEntity<?> deleteEmployeeEducation(int id);

	public ResponseEntity<List<EmployeeEducationResponseV2>> listEmployeeEducation();

	public ResponseEntity<List<EmployeeEducationResponseV2>> listEmployeeEducationNonApproved();

	public ResponseEntity<?> approveOrejectEmployeeEducation(EmployeeApprovalComment employeeApprovalComment, int id,
			int status);

}
