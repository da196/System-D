package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class HrmsAllowanceResponse {

	private int id;

	private Double amount;

	private String description;

	private int employmentcategoryid;

	private String employmentcategoryname;

	private int designationid;

	private String designationname;

	private int allowancetypeid;

	private String allowancetypename;

	private int salarystructureid;
	private String salarystructurename;

	private int currencyid;

	private String currency;

	private int approved;

	private int active;

}
