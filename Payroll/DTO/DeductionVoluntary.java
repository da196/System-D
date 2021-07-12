package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionVoluntary {
	// private String voluntaryContributionServiceProvider;

	private String voluntaryContributiontype;

	private int voluntaryContributiontypeCode;

	private Double amountdeductionvoluntary;

}
