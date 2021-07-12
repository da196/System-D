package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeEducationResponseV2 {
	private int id;
	private String employeename;
	private int employeeid;
	private String instituteName;
	private int instituteId;
	private String courseName;
	private int courseId;

	private String startYear;

	private String endYear;

	private int attachmentId;
	private String attachmentName;

	private int attachmentTypeId;
	private String attachmentTypeName;

	private String attachmentUri;

	private String levelName;
	private int levelId;

	private int countryId;

	private String countryname;
	private int approved;
	private String approvalComment;

}
