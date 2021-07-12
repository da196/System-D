package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.DTO.LeaveRecallResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecall;

@Service
public interface HrmsLeaveRecallService {

	public ResponseEntity<HrmsLeaveRecall> requestLeaveRecall(HrmsLeaveRecall hrmsLeaveRecall, int requestedby);

	public ResponseEntity<LeaveRecallResponse> getLeaveRecallById(int id);

	public ResponseEntity<HrmsLeaveRecall> updateLeaveRecall(HrmsLeaveRecall hrmsLeaveRecall, int id, int requestedby);

	public ResponseEntity<?> deleteLeaveRecall(int id);

	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecall();

	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecallBySupervisorId(int supervisorId);

	public ResponseEntity<?> ApproveLeaveRecall(int leaveid, int supervisorid, int status, String comment);

	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveRecallApprovers(int id);

	public ResponseEntity<List<LeaveRecallResponse>> getHrmsLeaveRecallNotApprovedBySupervisorNext(int supervisorid);

	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecallByEmployeeId(int employeeid);

	public ResponseEntity<?> AcknowledgeLeaveRecall(int id, int empid, int acknowledge);

}
