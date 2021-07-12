package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveApplicationsResponse {
	private int id;

	private int employeeid;

	private String firstname;

	private String middlename;

	private String lastname;

	private String directorate;

	private String section;

	private int leavetypeid;

	private String leavetypename;

	private String startdate;

	private String enddate;

	private String comment;

	private String contactaddress;

	private String phoneNumber;

	private int dependant;

	private int includefamilymember;

	private int kids;

	private int acting;

	private String actingfullname;

	private Double numberofdays;

	private Double leaveallowance;

	private int leaveallowanceapplicable;

	private int requestedby;

	private String requesterfullname;

	private int approvedby;

	private String approverfullname;

	private String approvalcomment;

	private int approved;

	private String approvedStatus;

	private int underApproval;

	private int active;

	private int year;

	private int month;

}
