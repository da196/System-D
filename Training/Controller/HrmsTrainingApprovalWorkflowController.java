package com.Hrms.Training.Controller;

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

import com.Hrms.Training.DTO.TrainingApprovalWorkflow;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflow;
import com.Hrms.Training.Service.HrmsTrainingApprovalWorkflowService;

@RestController
@RequestMapping("v1/trainingApprovalWorkflow")
public class HrmsTrainingApprovalWorkflowController {

	@Autowired
	private HrmsTrainingApprovalWorkflowService hrmsTrainingApprovalWorkflowService;

	@GetMapping(value = "/getAllTrainingApprovalWorkflow")
	public ResponseEntity<List<TrainingApprovalWorkflow>> getAllTrainingApprovalWorkflow() {

		return hrmsTrainingApprovalWorkflowService.getAllTrainingApprovalWorkflow();
	}

	@PostMapping(value = "/addTrainingApprovalWorkflow")
	public ResponseEntity<HrmsTrainingApprovalWorkflow> addTrainingApprovalWorkflow(
			@RequestBody HrmsTrainingApprovalWorkflow trainingApprovalWorkflow) {

		return hrmsTrainingApprovalWorkflowService.addTrainingApprovalWorkflow(trainingApprovalWorkflow);

	}

	@GetMapping(value = "/getTrainingWorkflowById/{id}")
	public ResponseEntity<TrainingApprovalWorkflow> getTrainingWorkflowById(@PathVariable("id") int id) {

		return hrmsTrainingApprovalWorkflowService.getTrainingWorkflowById(id);
	}

	@PutMapping(value = "/updateTrainingWorkflow/{id}")
	public ResponseEntity<HrmsTrainingApprovalWorkflow> updateTrainingWorkflow(
			@RequestBody HrmsTrainingApprovalWorkflow hrmsTrainingApprovalWorkflow, @PathVariable("id") int id) {

		return hrmsTrainingApprovalWorkflowService.updateTrainingWorkflow(hrmsTrainingApprovalWorkflow, id);
	}

	@DeleteMapping(value = "/deleteTrainingWorkflow/{id}")
	public ResponseEntity<?> deleteTrainingWorkflow(@PathVariable("id") int id) {

		return hrmsTrainingApprovalWorkflowService.deleteTrainingWorkflow(id);

	}
}
