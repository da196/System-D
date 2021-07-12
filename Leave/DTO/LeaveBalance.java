package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveBalance {
	private int id;
	private int leavetypeid;

	private String leavetypename;

	private Double daystaken;

	private Double daysremaining;

	private Double daystotal;

	private int approved;

	private int active;

}
