package com.Hrms.Training.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingApprovalWorkflow {

	private int id;

	private int code;

	private String name;

	private String description;

	private int supervisordesignationid;

	private String supervisordesignation;

	private int approved;

	private int active;

}
