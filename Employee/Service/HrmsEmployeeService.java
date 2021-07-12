package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeForVideoConference;
import com.Hrms.Employee.DTO.EmployeeGeneralRequest;
import com.Hrms.Employee.DTO.EmployeeResponse;
import com.Hrms.Employee.DTO.EmployeeResponseDetailed;
import com.Hrms.Employee.DTO.EmployeeResponseEdms;
import com.Hrms.Employee.Entity.HrmsEmployee;

@Service
public interface HrmsEmployeeService {

	public ResponseEntity<HrmsEmployee> save(HrmsEmployee hrmsEmployee);

	public ResponseEntity<EmployeeGeneralRequest> addEmployeeDetails(EmployeeGeneralRequest employeeGeneralRequest);

	public ResponseEntity<EmployeeGeneralRequest> updateEmployeeDetails(EmployeeGeneralRequest employeeGeneralRequest,
			int empid);

	public ResponseEntity<EmployeeResponse> viewHrmsEmployee(int id);

	public ResponseEntity<HrmsEmployee> update(HrmsEmployee hrmsEmployee, int id);

	public ResponseEntity<?> deleteHrmsEmployee(int id);

	public ResponseEntity<List<EmployeeResponse>> listHrmsEmployee();

	public ResponseEntity<EmployeeResponse> getEmployeeByEmail(String email);

	public ResponseEntity<EmployeeResponseEdms> getEmployeeByEmailEdms(String email);

	public ResponseEntity<EmployeeResponseDetailed> getEmployeeDetailsById(int id);

	public ResponseEntity<HrmsEmployee> updatePhoto(int EmpId, String PhotoUri);

	public ResponseEntity<List<HrmsEmployee>> getallemployeelessdetails();

	public ResponseEntity<List<EmployeeForVideoConference>> getAllEmployeeforVideoConference();

	public ResponseEntity<HrmsEmployee> updateEmployeePhoto(HrmsEmployee hrmsEmployee);

}
