package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PayrollBankAccountTranserResponse {
	private List<PayrollBankAccountTranser> payrollBankAccountTranserlist;
	private Double totalamount;
	private int totalNumberOfAccount;

}
