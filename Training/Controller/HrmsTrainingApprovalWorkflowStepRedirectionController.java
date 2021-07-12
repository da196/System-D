package com.Hrms.Training.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Training.DTO.TrainingApprovalWorkflowStepRedirection;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStepRedirection;
import com.Hrms.Training.Service.HrmsTrainingApprovalWorkflowStepRedirectionService;

@RestController
@RequestMapping("v1/trainingApprovalWorkflowStepRedirection")
public class HrmsTrainingApprovalWorkflowStepRedirectionController {

	@Autowired
	private HrmsTrainingApprovalWorkflowStepRedirectionService hrmsTrainingApprovalWorkflowStepRedirectionService;

	@PostMapping(value = "/addTrainingApprovalWorkflowStepRedirection")
	public ResponseEntity<HrmsTrainingApprovalWorkflowStepRedirection> addTrainingApprovalWorkflowStepRedirection(
			@RequestBody HrmsTrainingApprovalWorkflowStepRedirection hrmsTrainingApprovalWorkflowStepRedirection) {

		return hrmsTrainingApprovalWorkflowStepRedirectionService
				.addTrainingApprovalWorkflowStepRedirection(hrmsTrainingApprovalWorkflowStepRedirection);
	}

	@GetMapping(value = "/getTrainingApprovalWorkflowStepRedirection/{id}")
	public ResponseEntity<TrainingApprovalWorkflowStepRedirection> getTrainingApprovalWorkflowStepRedirection(
			@PathVariable("id") int id) {
		return hrmsTrainingApprovalWorkflowStepRedirectionService.getTrainingApprovalWorkflowStepRedirection(id);
	}

	@GetMapping(value = "/getAllTrainingApprovalWorkflowStepRedirection")
	public ResponseEntity<List<TrainingApprovalWorkflowStepRedirection>> getAllTrainingApprovalWorkflowStepRedirection() {

		return hrmsTrainingApprovalWorkflowStepRedirectionService.getAllTrainingApprovalWorkflowStepRedirection();

	}
}
