package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRelativeResponse {

	private int id;

	private int employeeid;
	private String employeename;

	private String fullname;

	private String MobileNo;

	private int relativecategoryid;
	private String firstname;
	private String middlename;
	private String lastname;

	private String relativecategoryname;
	private String phoneprimary;

	private String phonesecondary;

	private String address;

	private String dob;

	private String addresspermanent;

	private int nationalityid;

	private String nationality;

	private int countryofbirthid;

	private String countryofbirth;

	private int countryofresidenceid;

	private String countryofresidence;

	private int approved;

	private int active;

	private int attachmentid;

	private String attachmentname;
	private String attachmentdescription;

	private String uri;

	private int attachmenttypeid;

	private String attachmenttypename;
	private String approvalComment;

}
