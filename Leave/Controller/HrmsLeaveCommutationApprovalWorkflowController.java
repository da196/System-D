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
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflow;
import com.Hrms.Leave.Service.HrmsLeaveCommutationApprovalWorkflowService;

@RestController
@RequestMapping("v1/leaveCommutationApprovalWorkflow")
public class HrmsLeaveCommutationApprovalWorkflowController {

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowService hrmsLeaveCommutationApprovalWorkflowService;

	@PostMapping(value = "/addLeaveCommutationWorkFlow")
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflow> addLeaveCommutationWorkFlow(
			@RequestBody HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow) {

		return hrmsLeaveCommutationApprovalWorkflowService
				.addLeaveCommutationWorkFlow(hrmsLeaveCommutationApprovalWorkflow);
	}

	@GetMapping(value = "/getLeaveCommutationWorkFlowById/{id}")
	public ResponseEntity<LeaveWorkflowResponse> getLeaveCommutationWorkFlowById(@PathVariable("id") int id) {

		return hrmsLeaveCommutationApprovalWorkflowService.getLeaveCommutationWorkFlowById(id);

	}

	@PutMapping(value = "/updateLeaveCommutationWorkFlow/{id}")
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflow> updateLeaveWorkFlow(
			@RequestBody HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow,
			@PathVariable("id") int id) {

		return hrmsLeaveCommutationApprovalWorkflowService
				.updateLeaveCommutationWorkFlow(hrmsLeaveCommutationApprovalWorkflow, id);

	}

	@DeleteMapping(value = "/deleteLeaveCommutationWorkFlow/{id}")
	public ResponseEntity<?> deleteLeaveCommutationWorkFlow(@PathVariable("id") int id) {

		return hrmsLeaveCommutationApprovalWorkflowService.deleteLeaveCommutationWorkFlow(id);

	}

	@GetMapping(value = "/getAllLeaveCommutationWorkFlow")
	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveComutationWorkFlow() {

		return hrmsLeaveCommutationApprovalWorkflowService.getAllLeaveCommutationWorkFlow();

	}

}
