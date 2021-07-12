package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HrmsEmployeeShortCoursesR {

	private int id;

	private String datestart;

	private String datend;
	private String coursename;
	private String description;

	private String institution;

	private int expire;

	private int employeeid;

	private String employeename;

	private int countryid;
	private String countryname;

	private int attchmentid;
	private String attachmentname;

	private String attachmentdescription;

	private String uri;

	private int attachmenttypeid;
	private String attachmenttypename;

	private int approved;
	private String approvalComment;

	private int active;

}
