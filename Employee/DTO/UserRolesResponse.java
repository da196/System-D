package com.Hrms.Employee.DTO;

import java.util.List;

import com.Hrms.Employee.Entity.HrmsRoles;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserRolesResponse {

	private int id;

	private int userId;

	private String user;

	private int roleId;

	private List<HrmsRoles> roles;

	private int approved;

	private int active;

}
