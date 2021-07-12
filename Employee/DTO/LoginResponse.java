package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
	private String token;
	private String email;
	private String roles;
	private String photo;
	private String fullname;
	private int id;
	private int empId;
	private String filenumber;
	private String designation;

	private int leaveApproval;

	private int leaveCommutationApproval;

	private int leaveRecallApproval;
	private int trainingApproval;

	private int totalApproval;

}
