package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PayrollLoanPaymentAmount {
	private int id;

	private String name;

	private String abbreviation;

	private int isinternalsource;

	private int approved;

	private int active;

	private Double interestrate;

	private int duration;
	private Double loanAmountDebt;

	private Double loanAmountMonthly;// payment every Month

	private Double loanAmountInterest;// total loan interest

	private Double loanAmountOutstanding;// total debt plus total interest

	private int restrictioncode;

}
