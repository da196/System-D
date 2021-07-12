package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveWorkflowStepResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflowStep;

@Service
public interface HrmsLeaveRecallApprovalWorkflowStepService {

	public ResponseEntity<HrmsLeaveRecallApprovalWorkflowStep> addLeaveRecallWorkFlowStep(
			HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveRecallApprovalWorkflowStep);

	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveRecallWorkFlowStepById(int id);

	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveRecallWorkFlowStepByWorkFlowId(int workflowId);

	public ResponseEntity<HrmsLeaveRecallApprovalWorkflowStep> updateLeaveRecallWorkFlowStep(
			HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveRecallApprovalWorkflowStep, int id);

	public ResponseEntity<?> deleteLeaveRecallWorkFlowStep(int id);

	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveRecallWorkFlowStep();

}
