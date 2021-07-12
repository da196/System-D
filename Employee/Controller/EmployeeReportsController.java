package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.DTO.EmployeeAgeAverageperDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeAgeDistributionTopStaffResponse;
import com.Hrms.Employee.DTO.EmployeeExitReasonResponse;
import com.Hrms.Employee.DTO.EmployeeGenderDistributionTopStaffResponse;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeHeadCountDistributionperGenderResponse;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeAndDirectorateResponse;
import com.Hrms.Employee.DTO.EmployeeStaffDistributionByAgeResponse;
import com.Hrms.Employee.DTO.StaffGrossTurnOverResponse;
import com.Hrms.Employee.Service.EmployeeReportsService;

@RestController
@RequestMapping("v1/employeeReports")
public class EmployeeReportsController {
	@Autowired
	private EmployeeReportsService employeeReportsService;

	@GetMapping(value = "/getHeadCountPerLocationAndGender")
	public ResponseEntity<EmployeeHeadCountDistributionperGenderResponse> getHeadCountPerLocationAndGender() {
		return employeeReportsService.findHeadCountPerGender();
	}

	@GetMapping(value = "/getTopStaffCountPerGender")
	public ResponseEntity<EmployeeGenderDistributionTopStaffResponse> getTopStaffCountPerGender() {

		return employeeReportsService.findTopStaffCountPerGender();
	}

	@GetMapping(value = "/getHeadCountPerDirectorate")
	public ResponseEntity<EmployeeHeadCountDistributionperDirectorateResponse> getHeadCountPerDirectorate() {
		return employeeReportsService.findHeadCountPerDirectorate();

	}

	@GetMapping(value = "/getAgeAveragePerDirectorate")
	public ResponseEntity<EmployeeAgeAverageperDirectorateResponse> getAgeAveragePerDirectorate() {

		return employeeReportsService.findAgeAveragePerDirectorate();

	}

	@GetMapping(value = "/getAgeDistributionTopStaff")
	public ResponseEntity<EmployeeAgeDistributionTopStaffResponse> getAgeDistributionTopStaff() {

		return employeeReportsService.findAgeDistributionTopStaff();

	}

	@GetMapping(value = "/getStaffDistributionByAge")
	public ResponseEntity<EmployeeStaffDistributionByAgeResponse> getStaffDistributionByAge() {

		return employeeReportsService.findStaffDistributionByAge();

	}

	@GetMapping(value = "/getStaffDistributionByAgeAndDirectorate")
	public ResponseEntity<List<EmployeeStaffDistributionByAgeAndDirectorateResponse>> getStaffDistributionByAgeAndDirectorate() {
		return employeeReportsService.findStaffDistributionByAgeAndDirectorate();
	}

	@GetMapping(value = "/getReasonForEmployeesExit")
	public ResponseEntity<EmployeeExitReasonResponse> getReasonForEmployeesExit() {

		return employeeReportsService.findReasonForEmployeesExit();

	}

	@GetMapping(value = "/getStaffGrossTurnOver")
	public ResponseEntity<StaffGrossTurnOverResponse> getStaffGrossTurnOver() {
		return employeeReportsService.findStaffGrossTurnOver();

	}

	@GetMapping(value = "/findyearAndMonthtest")
	public ResponseEntity<?> findyearAndMonth() {

		return employeeReportsService.findyearAndMonth();

	}

}
