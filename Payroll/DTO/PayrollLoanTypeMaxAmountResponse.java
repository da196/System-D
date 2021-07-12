package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PayrollLoanTypeMaxAmountResponse {
	private int id;

	private int code;

	private String name;

	private String abbreviation;

	private String description;

	private int isinternalsource;

	private int approved;

	private int active;

	private Double interestrate;

	private Double durationmax;

	private Double amountmax;

	private Double amountmin;

	private int restrictioncode;
	private int employeeid;

	private String fullName;

}
