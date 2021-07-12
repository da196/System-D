package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.DTO.EmployeeAgeAverageperDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeAgeDistributionTopStaffResponse;
import com.Hrms.Employee.DTO.EmployeeExitReasonResponse;
import com.Hrms.Employee.DTO.EmployeeGenderDistributionTopStaffResponse;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperGenderResponse;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeAndDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeResponse;
import com.Hrms.Employee.DTO.StaffGrossTurnOverResponse;

@Service
public interface EmployeeReportsService {

	public ResponseEntity<EmployeeHeadCountDistributionperGenderResponse> findHeadCountPerGender();

	public ResponseEntity<EmployeeGenderDistributionTopStaffResponse> findTopStaffCountPerGender();

	public ResponseEntity<EmployeeHeadCountDistributionperDirectorateResponse> findHeadCountPerDirectorate();

	public ResponseEntity<EmployeeAgeAverageperDirectorateResponse> findAgeAveragePerDirectorate();

	public ResponseEntity<EmployeeAgeDistributionTopStaffResponse> findAgeDistributionTopStaff();

	public ResponseEntity<EmployeeStaffDistributionByAgeResponse> findStaffDistributionByAge();

	public ResponseEntity<List<EmployeeStaffDistributionByAgeAndDirectorateResponse>> findStaffDistributionByAgeAndDirectorate();

	public ResponseEntity<EmployeeExitReasonResponse> findReasonForEmployeesExit();

	public ResponseEntity<StaffGrossTurnOverResponse> findStaffGrossTurnOver();

	public ResponseEntity<?> findyearAndMonth();

}
