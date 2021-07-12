package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.EmployeeOnAnnualLeave;
import com.Hrms.Leave.DTO.LeaveApplicationsResponse;
import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.Entity.HrmsLeaveApplications;

@Service
public interface HrmsLeaveApplicationsService {

	public ResponseEntity<HrmsLeaveApplications> applyLeave(HrmsLeaveApplications hrmsLeaveApplications);

	public ResponseEntity<HrmsLeaveApplications> updateLeave(HrmsLeaveApplications hrmsLeaveApplications, int leaveid);

	public ResponseEntity<LeaveApplicationsResponse> getLeaveApplicationByLeaveId(int leaveid);

	public ResponseEntity<?> approveLeave(int leaveid, int approverid, int status, String comment);

	public ResponseEntity<?> deleteLeave(int leaveid, int requesterid);

	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplications();

	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsByEmpId(int empid);

	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsNotApproved();

	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsBySupervisorId(int supervisorid);

	public ResponseEntity<?> verifyLeaveAllowanceEligibility(int employeeid);

	public ResponseEntity<?> leaveRecall(int supervisorid, int leaveid);

	public ResponseEntity<?> verifyLeaverequestEligibility(int employeeid);

	public ResponseEntity<EmployeeOnAnnualLeave> verifyEmployeeOnAnnualLeave(int employeeid);

	public ResponseEntity<?> ApproveLeaveV2(int leaveid, int supervisorid, int status, String comment);

	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveApprovers(int id);

	public ResponseEntity<List<LeaveApplicationsResponse>> getHrmsLeaveNotApprovedBySupervisorNext(int supervisorid);

	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsRejected();

	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsApprovedBySupervisorId(
			int supervisorid, String startdate);

}
