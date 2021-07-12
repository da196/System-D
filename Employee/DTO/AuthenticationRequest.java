package com.Hrms.Employee.DTO;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor

@Data
public class AuthenticationRequest {

	@NotEmpty
	@NotNull

	private String username;

	@Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
	private String password;
}
