package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEppaObjectiveRevisedResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjectiveRevised;

@Service
public interface HrmsPerformanceEppaObjectiveRevisedService {

	public ResponseEntity<HrmsPerformanceEppaObjectiveRevised> addPerformanceEppaObjectiveRevised(
			HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised);

	public ResponseEntity<PerformanceEppaObjectiveRevisedResponse> getPerformanceEppaObjectiveRevisedById(int id);

	public ResponseEntity<HrmsPerformanceEppaObjectiveRevised> updatePerformanceEppaObjectiveRevised(
			HrmsPerformanceEppaObjectiveRevised hrmsPerformanceEppaObjectiveRevised, int id);

	public ResponseEntity<?> deletePerformanceEppaObjectiveRevised(int id);

	public ResponseEntity<List<PerformanceEppaObjectiveRevisedResponse>> getAllPerformanceEppaObjectiveRevised();

	public ResponseEntity<List<PerformanceEppaObjectiveRevisedResponse>> getAllPerformanceEppaObjectiveRevisedByObjectiveId(
			int objectiveid);

}
