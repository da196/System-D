package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeSocialSecuritySchemeResponse;
import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;

@Service
public interface HrmsEmployeeSocialSecuritySchemeService {

	public ResponseEntity<HrmsEmployeeSocialSecurityScheme> addEmployeeSocialSecurityScheme(
			HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme);

	public ResponseEntity<EmployeeSocialSecuritySchemeResponse> getEmployeeSocialSecuritySchemeById(int id);

	public ResponseEntity<HrmsEmployeeSocialSecurityScheme> updateEmployeeSocialSecurityScheme(
			HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme, int id);

	public ResponseEntity<?> deleteEmployeeSocialSecurityScheme(int id);

	public ResponseEntity<List<EmployeeSocialSecuritySchemeResponse>> listEmployeeSocialSecurityScheme();

	public ResponseEntity<List<EmployeeSocialSecuritySchemeResponse>> getEmployeeSocialSecuritySchemeByEmpId(int EmpId);

	public ResponseEntity<?> verifyIfEmployeeHasPension(int EmpId);

}
