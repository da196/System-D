package com.Hrms.Employee.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRelativeRequest {

	private int id;

	private String firstname;

	private String middlename;

	private String lastname;

	private String phoneprimary;

	private String phonesecondary;

	private String address;

	private Date dob;

	private String addresspermanent;

	private int employeeid;

	private int relativecategoryid;

	private int nationalityid;

	private int countryofbirthid;

	private int countryofresidenceid;

	private int approved;

	private int active;

	private int Attachmentid;

	private String Attachmentname;
	private String Attachmentdescription;

	private String uri;

	private int attachmenttypeid;

}
