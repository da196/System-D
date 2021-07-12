package com.Hrms.Employee.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeAgeDistributionTopStaffResponse {

	private List<EmployeeAgeDistributionTopStaff> ageGroupdetails;

	private int totaldirectors;

	private int totalheads;

	private int totaltopstaff;

}
