package com.Hrms.Employee.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class EducationcourseDetailed {

	private int id;

	private int code;

	private String name;

	private String abbreviation;

	private String description;

	private int approved;

	private int active;

	private int educationlevelid;

	private String educationlevel;

}
