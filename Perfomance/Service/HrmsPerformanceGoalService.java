package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceGoalResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;

@Service
public interface HrmsPerformanceGoalService {

	public ResponseEntity<HrmsPerformanceGoal> addPerformanceGoal(HrmsPerformanceGoal hrmsPerformanceGoal);

	public ResponseEntity<PerformanceGoalResponse> getPerformanceGoalById(int id);

	public ResponseEntity<HrmsPerformanceGoal> updatePerformanceGoal(HrmsPerformanceGoal hrmsPerformanceGoal, int id);

	public ResponseEntity<?> deletePerformanceGoal(int id);

	public ResponseEntity<List<PerformanceGoalResponse>> getAllPerformanceGoal();

	public ResponseEntity<List<PerformanceGoalResponse>> getAllPerformanceGoalByPlanId(int planid);

}
