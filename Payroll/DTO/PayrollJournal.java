package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollJournal {
	private Double basicpay;

	private Double psssfemployerpay;

	private Double zssfemployerpay;

	private List<Allowance> allowancelist;

	private Double otherincome;

	private List<DeductionLoan> deductionloanlist;
	private List<DeductionVoluntary> deductionvoluntarylist;

	private Double psssfpay;
	private Double zssfpay;
	private Double payepay;
	private Double netpay;

	private Double miscelaneouspay;

	private Double credittotal;

	private Double debttotal;

	private int month;
	private int year;
	private String payday;
	private String payrolldate;

}
