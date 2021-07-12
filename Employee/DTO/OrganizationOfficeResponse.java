package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationOfficeResponse {

	private int id;

	private String name;

	private String description;

	private int officetypeid;
	private String officetypeName;

	private int cityid;
	private String cityName;

	private int approved;

	private int active;

}
