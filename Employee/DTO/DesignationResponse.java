package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DesignationResponse {
	private int id;

	private int code;

	private String name;

	private String abbreviation;

	private String description;

	private int approved;

	private int active;

	private int issupervisor;

}
