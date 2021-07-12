package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeHeadCountDistribution {
	private int id;
	private String location;
	private int femalenumber;
	private int malenumber;
	private int totallocationnumber;

}
