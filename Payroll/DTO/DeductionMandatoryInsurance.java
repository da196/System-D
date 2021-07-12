package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionMandatoryInsurance {
	private String mandataryContributionServiceProvider;

	private Double amountdeductionmandatoryInsurance;

	private Double amountdeductionmandatoryInsuranceemployer;

}
