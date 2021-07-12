package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class employeesalaryResponse {

	private int id;

	private String descriptionAssigned;

	private String descriptionApproved;

	private int employeeid;

	private String employeefullname;

	private int salarystructureId;
	private String SalaryScale;

	private int assignedbyId;

	private String assignedby;

	private int approvedbyId;

	private String approvedby;

	private int currencyId;

	private String currency;

	private int approved;

	private int active;

}
