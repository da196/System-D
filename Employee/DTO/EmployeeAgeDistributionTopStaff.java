package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAgeDistributionTopStaff {
	private String agegroup;
	private int directors;

	private int heads;
	private int totalinAgeGroup;

}
