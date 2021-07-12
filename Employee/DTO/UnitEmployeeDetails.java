package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnitEmployeeDetails {
	private int unitid;
	private String unitname;
	private String unitshortname;
	private int femalenumber;
	private int malenumber;
	private int totalnumber;

	private String globalpercentage;

}
