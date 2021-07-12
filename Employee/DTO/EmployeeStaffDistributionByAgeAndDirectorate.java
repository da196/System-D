package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStaffDistributionByAgeAndDirectorate {
	private String departmentName;
	private Double agegroupDepartmentPercentage;
	private int totalDepartmentNumber;

}
