package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.DTO.LeaveSellResponse;
import com.Hrms.Leave.Entity.HrmsLeaveSold;

@Service
public interface HrmsLeaveSoldService {
	public ResponseEntity<HrmsLeaveSold> sellLeave(HrmsLeaveSold hrmsLeaveSold, int requesterid);

	public ResponseEntity<LeaveSellResponse> getLeaveSellById(int id);

	public ResponseEntity<HrmsLeaveSold> updateLeaveSell(HrmsLeaveSold hrmsLeaveSold, int id, int requesterid);

	public ResponseEntity<?> deleteLeaveSell(int id);

	public ResponseEntity<List<LeaveSellResponse>> getAllLeaveSell();

	public ResponseEntity<List<LeaveSellResponse>> getLeaveSellByEmpId(int empId);

	public ResponseEntity<Double> getCommutableAmount(int empid, int numberofdays);

	public ResponseEntity<Double> getCommutableAmountV2(int empid, int numberofdays);

	public ResponseEntity<?> ApproveLeaveCommutation(int leaveid, int supervisorid, int status, String comment);

	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveCommutationApprovers(int id);

	public ResponseEntity<List<LeaveSellResponse>> getHrmsLeaveCommutationNotApprovedBySupervisorNext(int supervisorid);

	public ResponseEntity<?> AcknowledgeLeaveCommutation(int id, int empid, int acknowledge);

	public ResponseEntity<List<LeaveSellResponse>> getAllLeaveCommutedNotApprovedYet(int supervisorid);

}
