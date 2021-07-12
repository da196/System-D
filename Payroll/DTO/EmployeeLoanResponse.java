package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeLoanResponse {

	private int id;

	private Double amountdebt;

	private Double amountprincipal;

	private Double amountoutstanding;
	private Double amountloanbalance;

	private Double amountpaid;

	private Double interestrate;

	private Double duration;

	private String dateissued;

	private String daterepaymentstart;

	private String daterepaymentend;

	private String description;

	private int loantypeid;

	private String loantype;

	private int loanfrequencyid;
	private String loanfrequency;

	private int employeeid;

	private String employeeFullname;

	private int lenderid;

	private String lender;

	private int currencyid;
	private String currency;

	private int status;

	private int approved;

	private int active;

}
