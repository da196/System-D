package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveWorkflowStepResponse;
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflowStep;

@Service
public interface HrmsLeaveApprovalWorkflowStepService {

	public ResponseEntity<HrmsLeaveApprovalWorkflowStep> addLeaveWorkFlowStep(
			HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep);

	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveWorkFlowStepById(int id);

	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveWorkFlowStepByWorkFlowId(int workflowId);

	public ResponseEntity<HrmsLeaveApprovalWorkflowStep> updateLeaveWorkFlowStep(
			HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep, int id);

	public ResponseEntity<?> deleteLeaveWorkFlowStep(int id);

	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveWorkFlowStep();

}
