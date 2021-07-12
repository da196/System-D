package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollResponsesv2 {
	private String datepay;
	private List<PayrollResponsev2> PayrollResponselist;
	private Double totalamountsalarygross;

	private Double totalamountsalarybasic;

	private Double totalamountsalarynet;
	private Double totalamounttaxable;

	private Double totalamountsalaryallowancetransport;

	private Double totalamountsalaryallowancehousing;

	private Double totalamountsalaryallowance;

	private Double totalamounttaxpaye;
	private Double totalamountotherincome;

	private Double totalamountTcraSaccos;

	private Double totalamountPostanaSimuSaccos;

	private Double totalamounttaxother;

	private Double totalamounttax;

	private Double totalamountdeductionmandatorypension;

	private Double totalamountdeductionmandatoryInsurance;
	private Double totalamountdeductionmandatoryEmployer;

	private Double totalamountdeductionmandatory;

	private Double totalamountdeductionvoluntary;

	private Double totalamountdeduction;

	private Double totalamountdeductionloansalaryadvance;

	private Double totalamountdeductionloansaccos;

	private Double totalamountdeductionloaneducationheslb;

	private Double totalamountdeductionloaneducationother;

	private Double totalamountdeductionloanother;

	private Double totalamountdeductionloan;

	private Double totalamountsalaryservantAndHospitality;

}
