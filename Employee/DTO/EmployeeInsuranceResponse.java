package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInsuranceResponse {

	private int id;

	private String insurancenumber;

	private String datestart;

	private String dateend;

	private String descriptionstart;

	private String descriptionend;

	private int employeeid;

	private String fullName;

	private int insurancecategoryid;

	private String insurancecategory;

	private int insuranceproviderid;

	private String insuranceprovider;

	private int approved;

	private int active;

}
