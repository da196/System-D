package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeChildUnitCount {

	private int totalchildnumber;
	private String childunitpercentage;
	private int childunitid;

	private String unittype;
	private String childunit;

}
