package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserResponse {

	private int id;

	private String userfullname;

	private String email;

	private String user_hash;

	private int approved;

	private int active;

	private int locked;

}
