package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollResponsev2 {

	private int id;

	private Double amountsalarygross;

	private Double amountsalarybasic;

	private Double amountsalarynet;
	private Double amounttaxable;
	private Double amountotherincome;
	private List<Allowance> allowancelist;
	private Double amountsalaryallowance;

	private Double amounttaxpaye;

	private Double amountTcraSaccos;

	private Double amountPostanaSimuSaccos;

	private Double amounttaxother;

	private Double amounttax;
	private List<DeductionMandatoryPension> deductionMandatoryPensionlist;

	private List<DeductionMandatoryInsurance> deductionMandatoryInsurancelist;

	private Double amountdeductionmandatory;
	private Double amountdeductionmandatoryEmployer;

	private List<DeductionVoluntary> deductionVoluntarylist;
	private Double amountdeductionvoluntary;

	private List<DeductionLoan> deductionLoanlist;

	private Double amountdeductionloan;

	private Double amountdeduction;

	private String datepay;

	private int payrolltypeid;
	private String payrolltype;

	private int employeeid;
	private String fullName;

	private int currencyid;
	private String currency;

	private int designationid;
	private String designation;

	private int officeid;
	private String office;
	private int officetypeid;
	private String officetype;

	private int dutystationcityid;

	private String dutystationcity;

	private int year;

	private int month;

	private int day;

	private int approved;

	private int active;

}
