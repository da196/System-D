package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaySlipResponse {
	private String fileNumber;

	private String employeeFullName;
	private int employeeid;

	private String office;

	private String officetype;

	private String salaryScale;

	private String designation;

	private String department;

	private String dateofEmployment;

	private String payrollPeriod;// eg November 2020

	private String modeofPayment;

	// items

	private Double basicsalaryAmount;

	private Double grosssalaryAmount;

	private Double netsalaryAmount;

	private Double payeAmount;

	private Double amountotherincome;

	private Double amounttaxable;

	// allowance

	private List<Allowance> allowancelist;

	// pension
	private String pensionName;

	private Double pensionAmount;

	// voluntary contribution

	private List<DeductionVoluntary> deductionVoluntarylist;

	private List<DeductionLoan> deductionLoanlist;

	private Double totalDeductionAmount;

	private List<PayrollBankAccountTranser> BankAccountTranserlist;
	private Double totalbankAccountAmountTransfered;

	private int year;
	private int month;
	private int day;

}
