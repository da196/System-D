package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryRequest;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseByEmpId;
import com.Hrms.Employee.DTO.HrmsEmployeeEmploymentHistoryResponseById;

@Service
public interface HrmsEmployeeEmploymentHistoryService {

	public ResponseEntity<HrmsEmployeeEmploymentHistoryRequest> save(
			HrmsEmployeeEmploymentHistoryRequest hrmsEmployeeEmploymentHistoryRequest);

	public ResponseEntity<HrmsEmployeeEmploymentHistoryResponseById> getEmployeeEmploymentHistoryById(int id);

	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseByEmpId>> getEmployeeEmploymentHistoryByEmpId(
			int empId);

	public ResponseEntity<HrmsEmployeeEmploymentHistoryRequest> updateEmployeeEmploymentHistory(
			HrmsEmployeeEmploymentHistoryRequest hrmsEmployeeEmploymentHistoryRequest, int id);

	public ResponseEntity<?> deleteEmployeeEmploymentHistory(int id);

	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseById>> listEmployeeEmploymentHistory();

	public ResponseEntity<List<HrmsEmployeeEmploymentHistoryResponseById>> listEmployeeEmploymentHistoryNonApproved();

	public ResponseEntity<?> approveOrRejectEmployeeEmploymentHistory(EmployeeApprovalComment employeeApprovalComment,
			int id, int status);

}
