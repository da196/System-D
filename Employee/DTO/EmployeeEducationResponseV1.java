package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//This class returns only institute name and few other details
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEducationResponseV1 {
	private int id;

	private String instituteName;

	private String startYear;

	private String endYear;

	private String attachmentUri;

}
