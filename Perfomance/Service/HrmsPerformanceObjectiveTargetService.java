package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceObjectiveTargetResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveTarget;

@Service
public interface HrmsPerformanceObjectiveTargetService {

	public ResponseEntity<HrmsPerformanceObjectiveTarget> addPerformanceObjectiveTarget(
			HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTarget);

	public ResponseEntity<PerformanceObjectiveTargetResponse> getPerformanceObjectiveTargetById(int id);

	public ResponseEntity<HrmsPerformanceObjectiveTarget> updatePerformanceObjectiveTarget(
			HrmsPerformanceObjectiveTarget hrmsPerformanceObjectiveTarget, int id);

	public ResponseEntity<?> deletePerformanceObjectiveTarget(int id);

	public ResponseEntity<List<PerformanceObjectiveTargetResponse>> getAllPerformanceObjectiveTarget();

	public ResponseEntity<List<PerformanceObjectiveTargetResponse>> getAllPerformanceObjectiveTargetByObjectiveId(
			int objectiveid);

}
