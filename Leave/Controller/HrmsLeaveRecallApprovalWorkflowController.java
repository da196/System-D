package com.Hrms.Leave.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Leave.DTO.LeaveWorkflowResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflow;
import com.Hrms.Leave.Service.HrmsLeaveRecallApprovalWorkflowService;

@RestController
@RequestMapping("v1/leaveRecallApprovalWorkflow")
public class HrmsLeaveRecallApprovalWorkflowController {

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowService hrmsLeaveRecallApprovalWorkflowService;

	@PostMapping(value = "/addLeaveRecallWorkFlow")
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflow> addLeaveWorkFlow(
			@RequestBody HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow) {

		return hrmsLeaveRecallApprovalWorkflowService.addLeaveRecallWorkFlow(hrmsLeaveRecallApprovalWorkflow);
	}

	@GetMapping(value = "/getLeaveRecallWorkFlowById/{id}")
	public ResponseEntity<LeaveWorkflowResponse> getLeaveRecallWorkFlowById(@PathVariable("id") int id) {

		return hrmsLeaveRecallApprovalWorkflowService.getLeaveRecallWorkFlowById(id);

	}

	@PutMapping(value = "/updateLeaveRecallWorkFlow/{id}")
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflow> updateLeaveRecallWorkFlow(
			@RequestBody HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow, @PathVariable("id") int id) {

		return hrmsLeaveRecallApprovalWorkflowService.updateLeaveRecallWorkFlow(hrmsLeaveRecallApprovalWorkflow, id);

	}

	@DeleteMapping(value = "/deleteLeaveRecallWorkFlow/{id}")
	public ResponseEntity<?> deleteLeaveRecallWorkFlow(@PathVariable("id") int id) {

		return hrmsLeaveRecallApprovalWorkflowService.deleteLeaveRecallWorkFlow(id);

	}

	@GetMapping(value = "/getAllLeaveRecallWorkFlow")
	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveRecallWorkFlow() {

		return hrmsLeaveRecallApprovalWorkflowService.getAllLeaveRecallWorkFlow();

	}

}
