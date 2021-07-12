package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EducationinstitutionResponse {

	private int id;

	private int code;

	private String name;

	private String abbreviation;

	private String description;

	private int categoryId;
	private String category;

	private int countryId;
	private String country;

	private int typeid;
	private String type;

	private int cityId;
	private String city;

	private int approved;

	private int active;

}
