package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollContributionVoluntaryResponse {

	private int id;

	private String description;

	private Double amount;

	private Double rate;

	private int isformularcomputed;

	private int contributiontypeid;

	private String contributiontype;

	private int employeeid;

	private String fullName;

	private int serviceproviderid;
	private String serviceprovider;

	private int contributionmode;

	private int locked;

	private String membershipnumber;

	private String joiningdate;

	private int currencyid;
	private String currency;

	private int approved;

	private int active;

}
