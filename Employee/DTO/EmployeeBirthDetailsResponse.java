package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBirthDetailsResponse {
	private int id;

	private String dob;
	private String description;
	private String additionalinformation;

	private int employeeid;
	private String employeename;

	private int countryid;

	private String countryname;

	private int cityid;
	private String cityname;

	private int districtid;
	private String districtname;

	private int wardid;
	private String wardanme;

	private int Attachmentid;

	private String Attachmentname;
	private String uri;
	private int attachmenttypeid;

	private String attachmenttypename;
	private int approved;
	private String approvalComment;

	private int active;

}
