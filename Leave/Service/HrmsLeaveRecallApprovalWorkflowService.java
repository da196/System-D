package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveWorkflowResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflow;

@Service
public interface HrmsLeaveRecallApprovalWorkflowService {

	public ResponseEntity<HrmsLeaveRecallApprovalWorkflow> addLeaveRecallWorkFlow(
			HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow);

	public ResponseEntity<LeaveWorkflowResponse> getLeaveRecallWorkFlowById(int id);

	public ResponseEntity<HrmsLeaveRecallApprovalWorkflow> updateLeaveRecallWorkFlow(
			HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow, int id);

	public ResponseEntity<?> deleteLeaveRecallWorkFlow(int id);

	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveRecallWorkFlow();

}
