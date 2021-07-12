package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StaffGrossTurnOver {
	private String financialYear;
	private int newEmployees;
	private int voluntaryAndInvoluntaryLeavers;
	private int employeesPresent;
	private Double grossTurnOver;

}
