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

import com.Hrms.Leave.DTO.LeaveWorkflowStepResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflowStep;
import com.Hrms.Leave.Service.HrmsLeaveRecallApprovalWorkflowStepService;

@RestController
@RequestMapping("v1/leaveRecallApprovalWorkflowStep")
public class HrmsLeaveRecallApprovalWorkflowStepController {

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowStepService hrmsLeaveRecallApprovalWorkflowStepService;

	@PostMapping(value = "/addLeaveRecallWorkFlowStep")
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflowStep> addLeaveRecallWorkFlowStep(
			@RequestBody HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveRecallApprovalWorkflowStep) {

		return hrmsLeaveRecallApprovalWorkflowStepService
				.addLeaveRecallWorkFlowStep(hrmsLeaveRecallApprovalWorkflowStep);

	}

	@GetMapping(value = "/getLeaveWorkFlowStepById/{id}")
	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveWorkFlowStepById(@PathVariable("id") int id) {

		return hrmsLeaveRecallApprovalWorkflowStepService.getLeaveRecallWorkFlowStepById(id);

	}

	@GetMapping(value = "/getLeaveRecallWorkFlowStepByWorkFlowId/{workflowId}")
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveRecallWorkFlowStepByWorkFlowId(
			@PathVariable("workflowId") int workflowId) {

		return hrmsLeaveRecallApprovalWorkflowStepService.getLeaveRecallWorkFlowStepByWorkFlowId(workflowId);

	}

	@PutMapping(value = "/updateLeaveRecallWorkFlowStep/{id}")
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflowStep> updateLeaveRecallWorkFlowStep(
			@RequestBody HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveRecallApprovalWorkflowStep,
			@PathVariable("id") int id) {

		return hrmsLeaveRecallApprovalWorkflowStepService
				.updateLeaveRecallWorkFlowStep(hrmsLeaveRecallApprovalWorkflowStep, id);

	}

	@DeleteMapping(value = "/deleteLeaveRecallWorkFlowStep/{id}")
	public ResponseEntity<?> deleteLeaveWorkFlowStep(@PathVariable("id") int id) {

		return hrmsLeaveRecallApprovalWorkflowStepService.deleteLeaveRecallWorkFlowStep(id);

	}

	@GetMapping(value = "/getAllLeaveRecallWorkFlowStep")
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveRecallWorkFlowStep() {

		return hrmsLeaveRecallApprovalWorkflowStepService.getAllLeaveRecallWorkFlowStep();

	}

}
