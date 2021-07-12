package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeGenderDistributionTopStaffResponse {

	private int maledirectors;
	private int femaledirectors;
	private int maleheads;
	private int femaleheads;

}
