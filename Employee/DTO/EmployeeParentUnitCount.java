package com.Hrms.Employee.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeParentUnitCount {
	private int totalparentunitnumber;

	private String parentunitpercentage;

	private int parentunitid;

	private String parentunit;
	private String unittype;
	private List<EmployeeChildUnitCount> employeeChildUnitCountlist;

}
