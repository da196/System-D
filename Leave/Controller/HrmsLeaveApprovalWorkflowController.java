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
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflow;
import com.Hrms.Leave.Service.HrmsLeaveApprovalWorkflowService;

@RestController
@RequestMapping("v1/leaveApprovalWorkflow")
public class HrmsLeaveApprovalWorkflowController {

	@Autowired
	private HrmsLeaveApprovalWorkflowService hrmsLeaveApprovalWorkflowService;

	@PostMapping(value = "/addLeaveWorkFlow")
	public ResponseEntity<HrmsLeaveApprovalWorkflow> addLeaveWorkFlow(
			@RequestBody HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow) {

		return hrmsLeaveApprovalWorkflowService.addLeaveWorkFlow(hrmsLeaveApprovalWorkflow);
	}

	@GetMapping(value = "/getLeaveWorkFlowById/{id}")
	public ResponseEntity<LeaveWorkflowResponse> getLeaveWorkFlowById(@PathVariable("id") int id) {

		return hrmsLeaveApprovalWorkflowService.getLeaveWorkFlowById(id);

	}

	@PutMapping(value = "/updateLeaveWorkFlow/{id}")
	public ResponseEntity<HrmsLeaveApprovalWorkflow> updateLeaveWorkFlow(
			@RequestBody HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow, @PathVariable("id") int id) {

		return hrmsLeaveApprovalWorkflowService.updateLeaveWorkFlow(hrmsLeaveApprovalWorkflow, id);

	}

	@DeleteMapping(value = "/deleteLeaveWorkFlow/{id}")
	public ResponseEntity<?> deleteLeaveWorkFlow(@PathVariable("id") int id) {

		return hrmsLeaveApprovalWorkflowService.deleteLeaveWorkFlow(id);

	}

	@GetMapping(value = "/getAllLeaveWorkFlow")
	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveWorkFlow() {

		return hrmsLeaveApprovalWorkflowService.getAllLeaveWorkFlow();

	}

}
