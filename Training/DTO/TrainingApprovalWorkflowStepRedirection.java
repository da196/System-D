package com.Hrms.Training.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TrainingApprovalWorkflowStepRedirection {

	private int id;

	private String description;

	private String dateredirected;

	private int stepid;

	private TrainingApprovalWorkflowStep trainingApprovalWorkflowStep;

	private int fromdesignationid;

	private String fromdesignation;

	private int todesignationid;

	private String todesignation;

	private int fromuserid;

	private String fromuser;

	private int touserid;

	private String touser;

	private int riderectionreasonid;

	private String riderectionreason;

	private int approved;

	private int active;

}
