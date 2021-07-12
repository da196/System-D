package com.Hrms.Training.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Training.Entity.HrmsTrainingApprovalRedirectionReason;
import com.Hrms.Training.Service.HrmsTrainingApprovalRedirectionReasonService;

@RestController
@RequestMapping("v1/trainingApprovalRedirectionReason")
public class HrmsTrainingApprovalRedirectionReasonController {

	@Autowired
	private HrmsTrainingApprovalRedirectionReasonService hrmsTrainingApprovalRedirectionReasonService;

	@GetMapping(value = "/getAllTrainingApprovalRedirectionReason")
	public ResponseEntity<List<HrmsTrainingApprovalRedirectionReason>> getAllTrainingApprovalRedirectionReason() {

		return hrmsTrainingApprovalRedirectionReasonService.getAllTrainingApprovalRedirectionReason();

	}
}
