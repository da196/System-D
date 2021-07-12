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
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflowStep;
import com.Hrms.Leave.Service.HrmsLeaveApprovalWorkflowStepService;

@RestController
@RequestMapping("v1/leaveApprovalWorkflowStep")
public class HrmsLeaveApprovalWorkflowStepController {

	@Autowired
	private HrmsLeaveApprovalWorkflowStepService hrmsLeaveApprovalWorkflowStepService;

	@PostMapping(value = "/addLeaveWorkFlowStep")
	public ResponseEntity<HrmsLeaveApprovalWorkflowStep> addLeaveWorkFlowStep(
			@RequestBody HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep) {

		return hrmsLeaveApprovalWorkflowStepService.addLeaveWorkFlowStep(hrmsLeaveApprovalWorkflowStep);

	}

	@GetMapping(value = "/getLeaveWorkFlowStepById/{id}")
	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveWorkFlowStepById(@PathVariable("id") int id) {

		return hrmsLeaveApprovalWorkflowStepService.getLeaveWorkFlowStepById(id);

	}

	@GetMapping(value = "/getLeaveWorkFlowStepByWorkFlowId/{workflowId}")
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveWorkFlowStepByWorkFlowId(
			@PathVariable("workflowId") int workflowId) {

		return hrmsLeaveApprovalWorkflowStepService.getLeaveWorkFlowStepByWorkFlowId(workflowId);

	}

	@PutMapping(value = "/updateLeaveWorkFlowStep/{id}")
	public ResponseEntity<HrmsLeaveApprovalWorkflowStep> updateLeaveWorkFlowStep(
			@RequestBody HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep, @PathVariable("id") int id) {

		return hrmsLeaveApprovalWorkflowStepService.updateLeaveWorkFlowStep(hrmsLeaveApprovalWorkflowStep, id);

	}

	@DeleteMapping(value = "/deleteLeaveWorkFlowStep/{id}")
	public ResponseEntity<?> deleteLeaveWorkFlowStep(@PathVariable("id") int id) {

		return hrmsLeaveApprovalWorkflowStepService.deleteLeaveWorkFlowStep(id);

	}

	@GetMapping(value = "/getAllLeaveWorkFlowStep")
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveWorkFlowStep() {

		return hrmsLeaveApprovalWorkflowStepService.getAllLeaveWorkFlowStep();

	}

}
