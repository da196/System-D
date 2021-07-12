package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStatistics {
	private int empid;
	private String departmentname;
	private int departmentfemalenumber;
	private int departmentmalenumber;
	private int departmenttotalnumber;

}
