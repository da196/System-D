package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponseTemp {
	private String jwt;

	private String email;

	private String firstname;

	private String middlename;

	private String lastname;

}
