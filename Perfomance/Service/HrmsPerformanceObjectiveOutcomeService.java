package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;

@Service
public interface HrmsPerformanceObjectiveOutcomeService {

	public ResponseEntity<HrmsPerformanceObjectiveOutcome> addPerformanceObjectiveOutcome(
			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome);

	public ResponseEntity<PerformanceObjectiveOutcomeResponse> getPerformanceObjectiveOutcomeById(int id);

	public ResponseEntity<HrmsPerformanceObjectiveOutcome> updatePerformanceObjectiveOutcome(
			HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome, int id);

	public ResponseEntity<?> deletePerformanceObjectiveOutcome(int id);

	public ResponseEntity<List<PerformanceObjectiveOutcomeResponse>> getAllPerformanceObjectiveOutcome();

	public ResponseEntity<List<PerformanceObjectiveOutcomeResponse>> getAllPerformanceObjectiveOutcomeByObjectiveId(
			int objectiveid);

}
