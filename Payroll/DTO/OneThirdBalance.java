package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OneThirdBalance {
	private Double basicSalaryAmount;

	private Double onethirdOfBasicAmount;

	private Double netpayAmount;

	private Double possibleLoanAmount;

}
