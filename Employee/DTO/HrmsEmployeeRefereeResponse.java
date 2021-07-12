package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeRefereeResponse {

	private int id;
	private int employeeid;
	private String employeename;
	private String fullname;

	private String institution;

	private String position;

	private String phoneprimary;

	private String phonesecondary;

	private String email;

	private String address;

	private int refereecategoryid;

	private String refereecategoryname;

	private int nationalityid;
	private String nationality;

	private int approved;
	private String approvalComment;

}
