package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeductionMandatoryPension {
	private String mandataryContributionServiceProvider;
	private Double amountdeductionmandatoryPension;

	private Double amountdeductionmandatoryPensionEmployer;

}
