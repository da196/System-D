package com.Hrms.Perfomance.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Perfomance.DTO.PerformanceEppaReviewAnnualResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;

@Service
public interface HrmsPerformanceEppaReviewAnnualService {

	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> seilfAnnualReview(
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual);

	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> supervisorAnnualReview(
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual, int id);

	public ResponseEntity<PerformanceEppaReviewAnnualResponse> getAnnualReviewById(int id);

	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> updateAnnualReview(
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual, int id);

	public ResponseEntity<?> deleteAnnualReview(int id);

	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReview();

	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewByEppaId(int eppaid);

	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewByObjectiveId(int objectiveid);

	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewBySupervisorId(int supervisorid);

	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewBySupervisorDesignationId(
			int supervisordesignationid);

}
