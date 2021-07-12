package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;

@Service
public interface HrmsPerformanceObjectiveService {

	public ResponseEntity<HrmsPerformanceObjective> addPerformanceObjective(
			HrmsPerformanceObjective hrmsPerformanceObjective);

	public ResponseEntity<PerformanceObjectiveResponse> getPerformanceObjectiveById(int id);

	public ResponseEntity<HrmsPerformanceObjective> updatePerformanceObjective(
			HrmsPerformanceObjective hrmsPerformanceObjective, int id);

	public ResponseEntity<?> deletePerformanceObjective(int id);

	public ResponseEntity<List<PerformanceObjectiveResponse>> getAllPerformanceObjective();

	public ResponseEntity<List<PerformanceObjectiveResponse>> getAllPerformanceObjectiveByGoalId(int goalid);

}
