package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeStaffDistributionByAge {
	private String agegroup;
	private Double agegrouppercentage;

	private int totalnumber;

}
