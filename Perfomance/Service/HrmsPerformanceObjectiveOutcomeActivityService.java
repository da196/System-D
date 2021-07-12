package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;

@Service
public interface HrmsPerformanceObjectiveOutcomeActivityService {

	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivity> addPerformanceObjectiveOutcomeActivity(
			HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity);

	public ResponseEntity<PerformanceObjectiveOutcomeActivityResponse> getPerformanceObjectiveOutcomeActivityById(
			int id);

	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivity> updatePerformanceObjectiveOutcomeActivity(
			HrmsPerformanceObjectiveOutcomeActivity hrmsPerformanceObjectiveOutcomeActivity, int id);

	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivity(int id);

	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityResponse>> getAllPerformanceObjectiveOutcomeActivity();

	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityResponse>> getAllPerformanceObjectiveOutcomeActivityByObjectiveOutcomeId(
			int objectiveOutcomeid);

}
