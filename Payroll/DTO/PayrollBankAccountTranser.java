package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollBankAccountTranser {

	private int id;

	private Double amount;

	private int employeeid;
	private String employeeFullName;

	private int payrollid;

	private int employeebankaccountid;

	private int bankid;

	private String bankName;

	private String accountNumber;

	private String accountName;

	private String sortCode;

	private int currencyid;
	private String currency;

	private int approved;

	private int active;

	private int year;

	private int month;

	private int day;

	private String datepay;

}
