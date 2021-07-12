package com.Hrms.Leave.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LeaveBalanceResponse {

	private int employeeid;

	private String firstname;

	private String middlename;

	private String lastname;

	List<LeaveBalance> leaveBalancelist;

}
