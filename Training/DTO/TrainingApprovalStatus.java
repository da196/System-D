package com.Hrms.Training.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingApprovalStatus {
	private int id;

	private String TrainingStatus;
	private int Approved;

	private char supervisorInitial;

	private String supervisorName;

	private String ApprovalStatus;

	private String DateApproved;

	private String Description;

}
