package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveSellResponse {
	private int id;

	private int employeeid;

	private int empknowledge;

	private String empknowledgement;

	private String employeeFullName;

	private int leavetypeid;
	private String leavetypeName;

	private Double numberofdaysSold;

	private Double amount;

	private String solddate;

	private int requesterid;

	private String requester;
	private String reason;

	private int approved;

	private String commutationStatus;

	private int active;

	private int year;

	private int month;

}
