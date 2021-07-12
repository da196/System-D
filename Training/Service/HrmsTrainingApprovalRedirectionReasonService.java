package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.Entity.HrmsTrainingApprovalRedirectionReason;

@Service
public interface HrmsTrainingApprovalRedirectionReasonService {

	public ResponseEntity<List<HrmsTrainingApprovalRedirectionReason>> getAllTrainingApprovalRedirectionReason();

}
