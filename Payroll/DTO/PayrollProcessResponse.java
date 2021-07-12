package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollProcessResponse {

	private int employeeTotal;

	private Double grossSalaryTotal;

	private Double taxTotal;

	private Double pensionEmployerTotal;

	private Double pensionEmployeeTotal;
	private Double insuranceEmployerTotal;
	private Double loanTotal;

	private Double netSalaryTotal;
	private Double basicSalaryTotal;

}
