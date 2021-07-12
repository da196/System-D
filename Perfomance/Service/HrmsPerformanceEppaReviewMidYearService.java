package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEppaReviewMidYearResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewMidYear;

@Service
public interface HrmsPerformanceEppaReviewMidYearService {

	public ResponseEntity<HrmsPerformanceEppaReviewMidYear> addPerformanceEppaReviewMidYear(
			HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear);

	public ResponseEntity<PerformanceEppaReviewMidYearResponse> getPerformanceEppaReviewMidYearById(int id);

	public ResponseEntity<HrmsPerformanceEppaReviewMidYear> updatePerformanceEppaReviewMidYear(
			HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear, int id);

	public ResponseEntity<?> deletePerformanceEppaReviewMidYear(int id);

	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYear();

	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearByEppaId(
			int eppaid);

	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearByObjectiveId(
			int objectiveid);

	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearBySupervisorDesigantionId(
			int supervisordesignationid);

}
