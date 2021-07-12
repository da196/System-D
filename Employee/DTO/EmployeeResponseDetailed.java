package com.Hrms.Employee.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDetailed {

	// employee basic information
	private EmployeeResponse employeeResponse;

	// educationsinformations

	private List<EmployeeEducationResponseV2> employeeEducationResponseV2list;

	// Short courses

	private List<HrmsEmployeeShortCoursesR> hrmsEmployeeShortCoursesRlist;

	// Bank information not yet available

	// Family information
	private List<EmployeeRelativeResponse> employeeRelativeResponselist;

	// salary details not yet available

	// Administration information will be fetched from employee response

	// Contact address information

	private List<HrmsEmployeeAddressContactResponse> hrmsEmployeeAddressContactResponselist;

	// employment history

	private List<HrmsEmployeeEmploymentHistoryResponseByEmpId> hrmsEmployeeEmploymentHistoryResponseByEmpIdlist;

	// leave information are not yet available

	// Loan information are not yet available

	// employee certification
	private List<HrmsEmployeeCertificationResponse> hrmsEmployeeCertificationResponselist;

}
