package com.Hrms.Employee.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHeadCountDistributionperGenderResponse {
	private List<EmployeeHeadCountDistribution> employeeHeadCountDistributionlist;
	private int totalfemale;
	private int totalmale;
	private int totalnumber;

}
