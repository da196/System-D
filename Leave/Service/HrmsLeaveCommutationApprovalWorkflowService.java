package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveWorkflowResponse;
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflow;

@Service
public interface HrmsLeaveCommutationApprovalWorkflowService {

	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflow> addLeaveCommutationWorkFlow(
			HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow);

	public ResponseEntity<LeaveWorkflowResponse> getLeaveCommutationWorkFlowById(int id);

	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflow> updateLeaveCommutationWorkFlow(
			HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow, int id);

	public ResponseEntity<?> deleteLeaveCommutationWorkFlow(int id);

	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveCommutationWorkFlow();

}
