package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeApprovalComment;
import com.Hrms.Employee.DTO.EmployeeBirthDetailsRequest;
import com.Hrms.Employee.DTO.EmployeeBirthDetailsResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeBirthDetails;

@Service
public interface HrmsEmployeeBirthDetailsService {

	public ResponseEntity<HrmsEmployeeBirthDetails> addEmpBirthDetails(
			EmployeeBirthDetailsRequest employeeBirthDetailsRequest);

	public ResponseEntity<HrmsEmployeeBirthDetails> updateEmployeeBirthDetails(
			EmployeeBirthDetailsRequest employeeBirthDetailsRequest, int id);

	public ResponseEntity<EmployeeBirthDetailsResponse> getEmployeeBirthDetailsByEmpId(int empid);

	public ResponseEntity<EmployeeBirthDetailsResponse> getEmployeeBirthDetailsById(int id);

	public ResponseEntity<?> deleteEmployeeBirthDetails(int id);

	public ResponseEntity<List<EmployeeBirthDetailsResponse>> listEmployeeBirthDetails();

	public ResponseEntity<List<EmployeeBirthDetailsResponse>> listEmployeeBirthDetailsNonApproved();

	public ResponseEntity<?> approveOrRejectEmployeeBirthDetails(EmployeeApprovalComment employeeApprovalComment,
			int id, int status);
}
