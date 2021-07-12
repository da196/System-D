package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHeadCountDistributionperDirectorate {
	private int id;
	private String departmentname;

	private int totalnumber;

	private Double departmentpercentage;

}
