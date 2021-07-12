package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollDeductionMandatoryInsuranceResponse {

	private int id;

	private Double amount;

	private Double amountemployer;

	private Double amountemployee;

	private int contributiontypeid;
	private String contributiontype;

	private String datededucted;

	private int payrollid;

	private int employeeid;

	private String fullName;

	private int currencyid;
	private String currency;

	private int approved;

	private int active;

}
