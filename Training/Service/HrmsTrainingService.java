package com.Hrms.Training.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Training.DTO.TrainingApprovalStatus;
import com.Hrms.Training.DTO.TrainingResponse;
import com.Hrms.Training.Entity.HrmsTraining;

@Service
public interface HrmsTrainingService {

	public ResponseEntity<HrmsTraining> addHrmsTraining(HrmsTraining hrmsTraining);

	public ResponseEntity<List<HrmsTraining>> addHrmsTrainingV2(HrmsTraining hrmsTraining, List<Integer> employeeids);

	public ResponseEntity<?> deleteHrmsTraining(int id);

	public ResponseEntity<HrmsTraining> updateHrmsTraining(HrmsTraining hrmsTraining, int id);

	public ResponseEntity<TrainingResponse> getHrmsTrainingById(int id);

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByEmployeeId(int Employeeid);

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingCurrentYear();

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYear(int finyearid);

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYearAndEmployeeId(int finyearid,
			int Employeeid);

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYearAndQuaterIdAndCategoryId(int finyearid,
			int Quaterid, int Categoryid);

	public ResponseEntity<?> ApproveHrmsTraining(int id, int supervisorid, int status, String comment);

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingNotApprovedBySupervisorNext(int supervisorid);

	public ResponseEntity<List<TrainingApprovalStatus>> getHrmsTrainingApprovers(int id);

	public ResponseEntity<?> updateAfterAttendingTraining(int id, int attended, String attendedReason);

	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingApprovedByEmployeeId(int Employeeid);

}
