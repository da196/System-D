package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollBankAccount {

	private int id;

	private Double amount;

	private int employeeid;
	private String employeeFullName;

	private int payrollid;

	private int bankaccountid;
	private EmployeeBankAccount bankAccount;

	private int currencyid;
	private String currency;

	private int approved;

	private int active;

}
