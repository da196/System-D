package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollResponse {

	private int id;

	private Double amountsalarygross;

	private Double amountsalarybasic;

	private Double amountsalarynet;
	private Double amounttaxable;

	private Double amountsalaryallowancetransport;

	private Double amountsalaryallowancehousing;

	private Double amountsalaryallowance;

	private Double amounttaxpaye;

	private Double amounttaxother;

	private Double amounttax;

	private Double amountdeductionmandatorypension;

	private Double amountdeductionmandatoryhealth;

	private Double amountdeductionmandatory;

	private Double amountdeductionvoluntary;

	private Double amountdeduction;

	private Double amountdeductionloansalaryadvance;

	private Double amountdeductionloansaccos;

	private Double amountdeductionloaneducationheslb;

	private Double amountdeductionloaneducationother;

	private Double amountdeductionloanother;

	private Double amountdeductionloan;

	private Double amountsalaryservantAndHospitality;

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
