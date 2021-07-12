package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollContributionMandatoryInsuranceResponse {

	private int id;

	private String description;

	private Double amount;

	private Double amountemployer;

	private Double amountemployee;

	private Double rate;

	private Double rateemployer;

	private Double rateemployee;

	private int isformularcomputed;

	private int contributiontypeid;

	private String contributiontype;

	private int insurancetypeid;

	private String insurancetype;

	private int serviceproviderid;
	private String serviceprovider;

	private int currencyid;

	private String currency;

	private int approved;

	private int active;

}
