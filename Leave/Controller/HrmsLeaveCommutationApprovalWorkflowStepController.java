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
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflowStep;
import com.Hrms.Leave.Service.HrmsLeaveCommutationApprovalWorkflowStepService;

@RestController
@RequestMapping("v1/leaveCommutationApprovalWorkflowStep")
public class HrmsLeaveCommutationApprovalWorkflowStepController {

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowStepService hrmsLeaveCommutationApprovalWorkflowStepService;

	@PostMapping(value = "/addLeaveCommutationWorkFlowStep")
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflowStep> addLeaveWorkFlowStep(
			@RequestBody HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep) {

		return hrmsLeaveCommutationApprovalWorkflowStepService
				.addLeaveCommutationWorkFlowStep(hrmsLeaveCommutationApprovalWorkflowStep);

	}

	@GetMapping(value = "/getLeaveCommutationWorkFlowStepById/{id}")
	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveWorkFlowStepById(@PathVariable("id") int id) {

		return hrmsLeaveCommutationApprovalWorkflowStepService.getLeaveCommutationWorkFlowStepById(id);

	}

	@GetMapping(value = "/getLeaveCommutationWorkFlowStepByWorkFlowId/{workflowId}")
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveWorkFlowStepByWorkFlowId(
			@PathVariable("workflowId") int workflowId) {

		return hrmsLeaveCommutationApprovalWorkflowStepService.getLeaveCommutationWorkFlowStepByWorkFlowId(workflowId);

	}

	@PutMapping(value = "/updateLeaveCommutationWorkFlowStep/{id}")
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflowStep> updateLeaveWorkFlowStep(
			@RequestBody HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep,
			@PathVariable("id") int id) {

		return hrmsLeaveCommutationApprovalWorkflowStepService
				.updateLeaveCommutationWorkFlowStep(hrmsLeaveCommutationApprovalWorkflowStep, id);

	}

	@DeleteMapping(value = "/deleteLeaveCommutationWorkFlowStep/{id}")
	public ResponseEntity<?> deleteLeaveWorkFlowStep(@PathVariable("id") int id) {

		return hrmsLeaveCommutationApprovalWorkflowStepService.deleteLeaveCommutationWorkFlowStep(id);

	}

	@GetMapping(value = "/getAllLeaveCommutationWorkFlowStep")
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveWorkFlowStep() {

		return hrmsLeaveCommutationApprovalWorkflowStepService.getAllLeaveCommutationWorkFlowStep();

	}

}
