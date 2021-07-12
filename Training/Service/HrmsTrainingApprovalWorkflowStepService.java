package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.DTO.TrainingApprovalWorkflowStep;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStep;

@Service
public interface HrmsTrainingApprovalWorkflowStepService {

	public ResponseEntity<List<TrainingApprovalWorkflowStep>> getAllTrainingApprovalWorkflowStep();

	public ResponseEntity<List<TrainingApprovalWorkflowStep>> getAllTrainingApprovalWorkflowStepByWorkflowid(
			int workflowid);

	public ResponseEntity<HrmsTrainingApprovalWorkflowStep> addTrainingWorkflowStep(
			HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep);

	public ResponseEntity<HrmsTrainingApprovalWorkflowStep> updateTrainingWorkflowStep(int id,
			HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep);

	public ResponseEntity<TrainingApprovalWorkflowStep> getTrainingApprovalWorkflowStepById(int id);

	public ResponseEntity<?> deleteTrainingWorkflowStep(int id);

}
