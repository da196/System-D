package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeCertificationResponse;

@Service
public interface HrmsEmployeeCertificationService {
	public ResponseEntity<HrmsEmployeeCertificationRequest> addEmployeeCertification(
			HrmsEmployeeCertificationRequest hrmsEmployeeCertificationRequest);

	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> getEmployeeCertificationByEmpId(int empid);

	public ResponseEntity<HrmsEmployeeCertificationResponse> getEmployeeCertificationById(int id);

	public ResponseEntity<HrmsEmployeeCertificationRequest> update(
			HrmsEmployeeCertificationRequest hrmsEmployeeCertificationRequest, int id);

	public ResponseEntity<?> deleteEmployeeCertification(int id);

	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> listEmployeeCertification();

	public ResponseEntity<List<HrmsEmployeeCertificationResponse>> listEmployeeCertificationNonApproved();

	public ResponseEntity<?> approveOrRejectEmployeeCertification(EmployeeApprovalComment employeeApprovalComment,
			int id, int status);
}
