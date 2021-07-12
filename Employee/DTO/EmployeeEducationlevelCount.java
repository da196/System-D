package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEducationlevelCount {

	private int educationlevelid;
	private String educationlevename;
	private int edulevelempnumber;
	private String educationlevelpercentage;

}
