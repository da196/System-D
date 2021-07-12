package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.DTO.TrainingApprovalWorkflowStepRedirection;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStepRedirection;

@Service
public interface HrmsTrainingApprovalWorkflowStepRedirectionService {

	public ResponseEntity<HrmsTrainingApprovalWorkflowStepRedirection> addTrainingApprovalWorkflowStepRedirection(
			HrmsTrainingApprovalWorkflowStepRedirection hrmsTrainingApprovalWorkflowStepRedirection);

	public ResponseEntity<TrainingApprovalWorkflowStepRedirection> getTrainingApprovalWorkflowStepRedirection(int id);

	public ResponseEntity<List<TrainingApprovalWorkflowStepRedirection>> getAllTrainingApprovalWorkflowStepRedirection();

}
