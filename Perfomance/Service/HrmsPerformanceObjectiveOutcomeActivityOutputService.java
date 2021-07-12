package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveOutcomeActivityOutputResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;

@Service
public interface HrmsPerformanceObjectiveOutcomeActivityOutputService {

	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutput> addPerformanceObjectiveOutcomeActivityOutput(
			HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput);

	public ResponseEntity<PerformanceObjectiveOutcomeActivityOutputResponse> getPerformanceObjectiveOutcomeActivityOutputById(
			int id);

	public ResponseEntity<HrmsPerformanceObjectiveOutcomeActivityOutput> updatePerformanceObjectiveOutcomeActivityOutput(
			HrmsPerformanceObjectiveOutcomeActivityOutput hrmsPerformanceObjectiveOutcomeActivityOutput, int id);

	public ResponseEntity<?> deletePerformanceObjectiveOutcomeActivityOutput(int id);

	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponse>> getAllPerformanceObjectiveOutcomeActivityOutput();

	public ResponseEntity<List<PerformanceObjectiveOutcomeActivityOutputResponse>> getAllPerformanceObjectiveOutcomeActivityOutputByActivityid(
			int activityid);

}
