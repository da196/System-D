package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeOnAnnualLeave {

	private int employeeid;
	private int onleave;

	private String enddate;

}
