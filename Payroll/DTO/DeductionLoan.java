package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionLoan {
	private String loantype;
	private int loantypeCode;

	private Double amountdeductionloan;

	private Double amountloanbalance;

}
