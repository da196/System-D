package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingApprovalRedirectionReason;
import com.Hrms.Training.Repository.HrmsTrainingApprovalRedirectionReasonRepository;

@Service
public class HrmsTrainingApprovalRedirectionReasonServiceImpl implements HrmsTrainingApprovalRedirectionReasonService {

	@Autowired
	private HrmsTrainingApprovalRedirectionReasonRepository hrmsTrainingApprovalRedirectionReasonRepository;

	@Override
	public ResponseEntity<List<HrmsTrainingApprovalRedirectionReason>> getAllTrainingApprovalRedirectionReason() {

		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsTrainingApprovalRedirectionReasonRepository.findByActive(1));
	}

}
