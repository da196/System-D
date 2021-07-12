package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;

@Service
public interface HrmsPerformancePlanService {

	public ResponseEntity<HrmsPerformancePlan> addPerformancePlan(HrmsPerformancePlan hrmsPerformancePlan);

	public ResponseEntity<HrmsPerformancePlan> getPerformancePlanById(int id);

	public ResponseEntity<HrmsPerformancePlan> updatePerformancePlan(HrmsPerformancePlan hrmsPerformancePlan, int id);

	public ResponseEntity<?> deletePerformancePlan(int id);

	public ResponseEntity<List<HrmsPerformancePlan>> getAllPerformancePlan();

}
