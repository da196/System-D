package com.Hrms.Leave.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Leave.DTO.LeaveBalanceResponse;
import com.Hrms.Leave.Service.HrmsLeaveBalanceService;

@RestController
@RequestMapping("v1/HrmsLeaveBalance")
public class HrmsLeaveBalanceController {
	@Autowired
	private HrmsLeaveBalanceService hrmsLeaveBalanceService;

	@GetMapping(value = "/getLeaveBalanceByEmpId/{empid}")
	public ResponseEntity<LeaveBalanceResponse> getLeaveBalanceByEmpId(@PathVariable("empid") int empid) {

		return hrmsLeaveBalanceService.getLeaveBalanceByEmpId(empid);

	}

	@GetMapping(value = "/getAllLeaveBalance")
	public ResponseEntity<List<LeaveBalanceResponse>> getAllLeaveBalance() {
		return hrmsLeaveBalanceService.getAllLeaveBalance();

	}

	@GetMapping(value = "/getLeaveBalanceBySupervisorId/{supervisorId}")
	public ResponseEntity<List<LeaveBalanceResponse>> getLeaveBalanceBySupervisorId(
			@PathVariable("supervisorId") int supervisorId) {
		return hrmsLeaveBalanceService.getLeaveBalanceBySupervisorId(supervisorId);
	}

}
