package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.DTO.TrainingApprovalWorkflow;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflow;

@Service
public interface HrmsTrainingApprovalWorkflowService {

	public ResponseEntity<List<TrainingApprovalWorkflow>> getAllTrainingApprovalWorkflow();

	public ResponseEntity<HrmsTrainingApprovalWorkflow> addTrainingApprovalWorkflow(
			HrmsTrainingApprovalWorkflow trainingApprovalWorkflow);

	public ResponseEntity<TrainingApprovalWorkflow> getTrainingWorkflowById(int id);

	public ResponseEntity<HrmsTrainingApprovalWorkflow> updateTrainingWorkflow(
			HrmsTrainingApprovalWorkflow hrmsTrainingApprovalWorkflow, int id);

	public ResponseEntity<?> deleteTrainingWorkflow(int id);

}
