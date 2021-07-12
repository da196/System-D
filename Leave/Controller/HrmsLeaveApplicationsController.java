package com.Hrms.Leave.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Leave.DTO.EmployeeOnAnnualLeave;
import com.Hrms.Leave.DTO.LeaveApplicationsResponse;
import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.Entity.HrmsLeaveApplications;
import com.Hrms.Leave.Service.HrmsLeaveApplicationsService;
import com.Hrms.Training.DTO.TrainingUpdate;

@RestController
@RequestMapping("v1/leaveApplications")
public class HrmsLeaveApplicationsController {

	@Autowired
	private HrmsLeaveApplicationsService hrmsLeaveApplicationsService;

	@PostMapping(value = "/applyLeave")
	public ResponseEntity<HrmsLeaveApplications> applyLeave(@RequestBody HrmsLeaveApplications hrmsLeaveApplications) {

		return hrmsLeaveApplicationsService.applyLeave(hrmsLeaveApplications);

	}

	@PutMapping(value = "/approveLeave/{leaveid}/{approverid}/{status}/{comment}")
	public ResponseEntity<?> approveLeave(@PathVariable("leaveid") int leaveid,
			@PathVariable("approverid") int approverid, @PathVariable("status") int status,
			@PathVariable("comment") String comment) {

		return hrmsLeaveApplicationsService.approveLeave(leaveid, approverid, status, comment);
	}

	@GetMapping(value = "/getLeaveApplicationByLeaveId/{leaveid}")
	public ResponseEntity<LeaveApplicationsResponse> getLeaveApplicationByLeaveId(
			@PathVariable("leaveid") int leaveid) {

		return hrmsLeaveApplicationsService.getLeaveApplicationByLeaveId(leaveid);

	}

	@PutMapping(value = "/updateLeave/{leaveid}")
	public ResponseEntity<HrmsLeaveApplications> updateLeave(@RequestBody HrmsLeaveApplications hrmsLeaveApplications,
			@PathVariable("leaveid") int leaveid) {

		return hrmsLeaveApplicationsService.updateLeave(hrmsLeaveApplications, leaveid);

	}

	@DeleteMapping(value = "/deleteLeave/{leaveid}/{requesterid}")
	public ResponseEntity<?> deleteLeave(@PathVariable("leaveid") int leaveid,
			@PathVariable("requesterid") int requesterid) {
		return hrmsLeaveApplicationsService.deleteLeave(leaveid, requesterid);
	}

	@GetMapping(value = "/getAllLeaveApplications")
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplications() {
		return hrmsLeaveApplicationsService.getAllLeaveApplications();
	}

	@GetMapping(value = "/getAllLeaveApplicationsByEmpId/{empid}")
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsByEmpId(
			@PathVariable("empid") int empid) {

		return hrmsLeaveApplicationsService.getAllLeaveApplicationsByEmpId(empid);

	}

	@GetMapping(value = "/getAllLeaveApplicationsNotApproved")
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsNotApproved() {
		return hrmsLeaveApplicationsService.getAllLeaveApplicationsNotApproved();

	}

	@GetMapping(value = "/getAllLeaveApplicationsBySupervisorId/{supervisorid}")
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsBySupervisorId(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsLeaveApplicationsService.getAllLeaveApplicationsBySupervisorId(supervisorid);

	}

	@GetMapping(value = "/verifyLeaveAllowanceEligibility/{empId}")
	public ResponseEntity<?> verifyLeaveAllowanceEligibility(@PathVariable("empId") int empId) {

		return hrmsLeaveApplicationsService.verifyLeaveAllowanceEligibility(empId);

	}

	@GetMapping(value = "/verifyLeaverequestEligibility/{empId}")
	public ResponseEntity<?> verifyLeaverequestEligibility(@PathVariable("empId") int empId) {
		return hrmsLeaveApplicationsService.verifyLeaverequestEligibility(empId);
	}

	@GetMapping(value = "/verifyEmployeeOnAnnualLeave/{employeeid}")
	public ResponseEntity<EmployeeOnAnnualLeave> verifyEmployeeOnAnnualLeave(
			@PathVariable("employeeid") int employeeid) {
		return hrmsLeaveApplicationsService.verifyEmployeeOnAnnualLeave(employeeid);
	}

	@PutMapping(value = "/approveLeaveV2/{leaveid}/{supervisorid}/{status}/{comment}")
	public ResponseEntity<?> ApproveLeaveV2(@PathVariable("leaveid") int leaveid,
			@PathVariable("supervisorid") int supervisorid, @PathVariable("status") int status,
			@PathVariable("comment") String comment) {

		return hrmsLeaveApplicationsService.ApproveLeaveV2(leaveid, supervisorid, status, comment);
	}

	@PutMapping(value = "/approveLeaveV3/{leaveid}/{supervisorid}/{status}")
	public ResponseEntity<?> ApproveHrmsTrainingV2(@RequestBody TrainingUpdate trainingUpdate,
			@PathVariable("leaveid") int leaveid, @PathVariable("supervisorid") int supervisorid,
			@PathVariable("status") int status) {
		String comment = "";
		if (trainingUpdate != null) {
			comment = trainingUpdate.getComment();

		}
		return hrmsLeaveApplicationsService.ApproveLeaveV2(leaveid, supervisorid, status, comment);
	}

	@GetMapping(value = "/getLeaveApprovers/{id}")
	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveApprovers(@PathVariable("id") int id) {

		return hrmsLeaveApplicationsService.getLeaveApprovers(id);
	}

	@GetMapping(value = "/getLeaveNotApprovedBySupervisorNext/{supervisorid}")
	public ResponseEntity<List<LeaveApplicationsResponse>> getHrmsLeaveNotApprovedBySupervisorNext(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsLeaveApplicationsService.getHrmsLeaveNotApprovedBySupervisorNext(supervisorid);

	}

	@GetMapping(value = "/getAllLeaveApplicationsRejected")
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsRejected() {

		return hrmsLeaveApplicationsService.getAllLeaveApplicationsRejected();

	}

	@GetMapping(value = "/getAllLeaveApplicationsApprovedBySupervisorId/{supervisorid}/{startdate}")
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsApprovedBySupervisorId(
			@PathVariable("supervisorid") int supervisorid, @PathVariable("startdate") String startdate) {

		return hrmsLeaveApplicationsService.getAllLeaveApplicationsApprovedBySupervisorId(supervisorid, startdate);

	}

}
