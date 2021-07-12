package com.Hrms.Leave.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LeaveWorkflowStepResponse {

	private int workflowid;

	private String workflow;

	private int id;

	private int stepnumber;

	private String description;

	private int approverdesignationid;

	private String approverdesignation;

	private int approverdesignationnextid;

	private String approverdesignationnext;

	private int approverdesignationprevid;

	private String approverdesignationprev;

	private int approved;

	private int active;

}
