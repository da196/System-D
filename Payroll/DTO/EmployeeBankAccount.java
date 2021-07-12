package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBankAccount {

	private int id;

	private String accountname;

	private String accountnumber;

	private int bankbranchid;
	private String bankbranchname;

	private int priority;
	private Double amount;

	private String description;

	private String dateopened;

	private String dateclosed;

	private String approveddate;
	private String approvalcomment;

	private int employeeid;

	private String employeeFullName;

	private int bankid;
	private String bankName;

	private int approvedbyuserid;

	private String approvedby;

	private int approvedbydesignationid;

	private String approvedbydesignation;

	private int createdbyuserid;

	private String createdbyuser;

	private int createdbydesignationid;
	private String createdbydesignation;

	private int bankbranchlocationid;
	private String bankbranchlocation;

	private int approved;

	private int active;

}
