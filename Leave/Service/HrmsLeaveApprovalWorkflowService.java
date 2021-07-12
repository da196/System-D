package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveWorkflowResponse;
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflow;

@Service
public interface HrmsLeaveApprovalWorkflowService {

	public ResponseEntity<HrmsLeaveApprovalWorkflow> addLeaveWorkFlow(
			HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow);

	public ResponseEntity<LeaveWorkflowResponse> getLeaveWorkFlowById(int id);

	public ResponseEntity<HrmsLeaveApprovalWorkflow> updateLeaveWorkFlow(
			HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow, int id);

	public ResponseEntity<?> deleteLeaveWorkFlow(int id);

	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveWorkFlow();

}
