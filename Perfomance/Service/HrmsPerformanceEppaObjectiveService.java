package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEppaObjectiveResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;

@Service
public interface HrmsPerformanceEppaObjectiveService {

	public ResponseEntity<HrmsPerformanceEppaObjective> addPerformanceEppaObjective(
			HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective);

	public ResponseEntity<PerformanceEppaObjectiveResponse> getPerformanceEppaObjectiveById(int id);

	public ResponseEntity<HrmsPerformanceEppaObjective> updatePerformanceEppaObjective(
			HrmsPerformanceEppaObjective hrmsPerformanceEppaObjective, int id);

	public ResponseEntity<?> deletePerformanceEppaObjective(int id);

	public ResponseEntity<List<PerformanceEppaObjectiveResponse>> getAllPerformanceEppaObjective();

	public ResponseEntity<List<PerformanceEppaObjectiveResponse>> getAllPerformanceEppaObjectiveByEppaId(int eppaid);

}
