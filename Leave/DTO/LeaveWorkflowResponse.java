package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LeaveWorkflowResponse {

	private int id;

	private int code;

	private String name;

	private String description;

	private int supervisordesignationid;

	private String supervisordesignation;

	private int approved;

	private int active;

}
