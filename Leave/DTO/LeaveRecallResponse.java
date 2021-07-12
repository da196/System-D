package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LeaveRecallResponse {

	private int id;

	private int employeeid;

	private String employeename;

	private int leavetypeid;

	private String leavetype;

	private String startdate;

	private String enddate;

	private String comment;

	private int leaveid;

	private Double numberofdays;

	private int requestedbyid;

	private String requestedbyname;

	private String approvalcomment;

	private int approved;

	private String recallStatus;

	private int active;

	private int empknowledge;

	private String empknowledgement;

	private Double estimatedcost;

	private int year;

	private int month;

}
