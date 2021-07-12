package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAgeAverageperDirectorate {
	private int id;
	private String directorate;
	private Double ageaverage;

}
