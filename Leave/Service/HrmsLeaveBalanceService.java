package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Leave.DTO.LeaveBalanceResponse;

@Service
public interface HrmsLeaveBalanceService {

	public ResponseEntity<LeaveBalanceResponse> getLeaveBalanceByEmpId(int empid);

	public ResponseEntity<List<LeaveBalanceResponse>> getAllLeaveBalance();

	public ResponseEntity<List<LeaveBalanceResponse>> getLeaveBalanceBySupervisorId(int supervisorId);

}
