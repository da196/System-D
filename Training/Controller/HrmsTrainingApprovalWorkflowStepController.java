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

import com.Hrms.Training.DTO.TrainingApprovalWorkflowStep;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStep;
import com.Hrms.Training.Service.HrmsTrainingApprovalWorkflowStepService;

@RestController
@RequestMapping("v1/trainingApprovalWorkflowStep")
public class HrmsTrainingApprovalWorkflowStepController {

	@Autowired
	private HrmsTrainingApprovalWorkflowStepService hrmsTrainingApprovalWorkflowStepService;

	@GetMapping(value = "/getAllTrainingApprovalWorkflowStep")
	public ResponseEntity<List<TrainingApprovalWorkflowStep>> getAllTrainingApprovalWorkflowStep() {

		return hrmsTrainingApprovalWorkflowStepService.getAllTrainingApprovalWorkflowStep();

	}

	@GetMapping(value = "/getAllTrainingApprovalWorkflowStepByWorkflowid/{workflowid}")
	public ResponseEntity<List<TrainingApprovalWorkflowStep>> getAllTrainingApprovalWorkflowStepByWorkflowid(
			@PathVariable("workflowid") int workflowid) {

		return hrmsTrainingApprovalWorkflowStepService.getAllTrainingApprovalWorkflowStepByWorkflowid(workflowid);
	}

	@PostMapping(value = "/addTrainingWorkflowStep")
	public ResponseEntity<HrmsTrainingApprovalWorkflowStep> addTrainingWorkflowStep(
			@RequestBody HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep) {

		return hrmsTrainingApprovalWorkflowStepService.addTrainingWorkflowStep(hrmsTrainingApprovalWorkflowStep);

	}

	@PutMapping(value = "/updateTrainingWorkflowStep/{id}")
	public ResponseEntity<HrmsTrainingApprovalWorkflowStep> updateTrainingWorkflowStep(@PathVariable("id") int id,
			@RequestBody HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep) {

		return hrmsTrainingApprovalWorkflowStepService.updateTrainingWorkflowStep(id, hrmsTrainingApprovalWorkflowStep);

	}

	@GetMapping(value = "/getTrainingApprovalWorkflowStepById/{id}")
	public ResponseEntity<TrainingApprovalWorkflowStep> getTrainingApprovalWorkflowStepById(
			@PathVariable("id") int id) {

		return hrmsTrainingApprovalWorkflowStepService.getTrainingApprovalWorkflowStepById(id);
	}

	@DeleteMapping(value = "/deleteTrainingWorkflowStep/{id}")
	public ResponseEntity<?> deleteTrainingWorkflowStep(@PathVariable("id") int id) {
		return hrmsTrainingApprovalWorkflowStepService.deleteTrainingWorkflowStep(id);
	}

}
