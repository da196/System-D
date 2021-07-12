package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeExitReasonResponse {

	// private String inactive;

	private int inactiveNumber;
	// private String individualTraining;

	private int individualTrainingNumber;
	// private String corporateTraining;

	private int corporateTrainingNumber;
	// private String retirement;

	private int retirementNumber;
	// private String resignation;
	private int resignationNumber;
	// private String termination;

	private int terminationNumber;
	// private String transfer;

	private int transferNumber;
	// private String death;

	private int deathNumber;

}
