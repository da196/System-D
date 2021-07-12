package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeSocialSecuritySchemeResponse {
	private int id;

	private String socialsecurityschemenumber;

	private String datestart;

	private String datend;

	private String descriptionstart;

	private String descriptionend;

	private int employeeid;

	private String fullName;

	private int serviceproviderid;

	private String serviceprovider;

	private int approved;

	private int active;

}
