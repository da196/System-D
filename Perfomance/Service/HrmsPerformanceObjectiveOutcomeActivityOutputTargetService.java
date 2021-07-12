package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputTargetResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;

@Service
public interface HrmsPerformanceObjectiveOutcomeActivityOutputTargetService {

	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> addPerformanceObjectiveOutcomeActivityOutputTarget(
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget);

	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputTargetResponse> getPerformanceObjectiveOutcomeActivityOutputTargetById(
			int id);

	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutputTarget> updatePerformanceObjectiveOutcomeActivityOutputTarget(
			HrmsPerformanceObjectiveOutcomeActivityOutputTarget hrmsPerformanceObjectiveOutcomeActivityOutputTarget,
			int id);

	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutputTarget(int id);

	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputTargetResponse>> getAllPerformanceObjectiveOutcomeActivityOutputTarget();

	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputTargetResponse>> getAllPerformanceObjectiveOutcomeActivityOutputByOutputid(
			int outputid);

}
