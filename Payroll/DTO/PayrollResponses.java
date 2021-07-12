package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollResponses {
	private List<PayrollResponse> PayrollResponselist;
	private Double totalamountsalarygross;

	private Double totalamountsalarybasic;

	private Double totalamountsalarynet;
	private Double totalamounttaxable;

	private Double totalamountsalaryallowancetransport;

	private Double totalamountsalaryallowancehousing;

	private Double totalamountsalaryallowance;

	private Double totalamounttaxpaye;

	private Double totalamounttaxother;

	private Double totalamounttax;

	private Double totalamountdeductionmandatorypension;

	private Double totalamountdeductionmandatoryhealth;

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
