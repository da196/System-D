package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveWorkflowStepResponse;
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflowStep;

@Service
public interface HrmsLeaveCommutationApprovalWorkflowStepService {

	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflowStep> addLeaveCommutationWorkFlowStep(
			HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep);

	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveCommutationWorkFlowStepById(int id);

	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveCommutationWorkFlowStepByWorkFlowId(int workflowId);

	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflowStep> updateLeaveCommutationWorkFlowStep(
			HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep, int id);

	public ResponseEntity<?> deleteLeaveCommutationWorkFlowStep(int id);

	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveCommutationWorkFlowStep();

}
