package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Allowance {
	private String allowancetype;
	private int allowancetypeCode;

	private Double amountsalaryallowance;

}
