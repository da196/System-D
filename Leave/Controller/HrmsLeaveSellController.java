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

import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.DTO.LeaveSellResponse;
import com.Hrms.Leave.Entity.HrmsLeaveSold;
import com.Hrms.Leave.Service.HrmsLeaveSoldService;
import com.Hrms.Training.DTO.TrainingUpdate;

@RestController
@RequestMapping("v1/leaveSell")
public class HrmsLeaveSellController {

	@Autowired
	private HrmsLeaveSoldService hrmsLeaveSoldService;

	@PostMapping(value = "/sellLeave/{requesterid}")
	public ResponseEntity<HrmsLeaveSold> sellLeave(@RequestBody HrmsLeaveSold hrmsLeaveSold,
			@PathVariable("requesterid") int requesterid) {

		return hrmsLeaveSoldService.sellLeave(hrmsLeaveSold, requesterid);

	}

	@GetMapping(value = "/getLeaveSellById/{id}")
	public ResponseEntity<LeaveSellResponse> getLeaveSellById(@PathVariable("id") int id) {

		return hrmsLeaveSoldService.getLeaveSellById(id);

	}

	@PutMapping(value = "/updateLeaveSell/{id}/{requesterid}")
	public ResponseEntity<HrmsLeaveSold> updateLeaveSell(@RequestBody HrmsLeaveSold hrmsLeaveSold,
			@PathVariable("id") int id, @PathVariable("requesterid") int requesterid) {
		return hrmsLeaveSoldService.updateLeaveSell(hrmsLeaveSold, id, requesterid);

	}

	@DeleteMapping(value = "/deleteLeaveSell/{id}")
	public ResponseEntity<?> deleteLeaveSell(@PathVariable("id") int id) {

		return hrmsLeaveSoldService.deleteLeaveSell(id);

	}

	@GetMapping(value = "/getAllLeaveSell")
	public ResponseEntity<List<LeaveSellResponse>> getAllLeaveSell() {
		return hrmsLeaveSoldService.getAllLeaveSell();

	}

	@GetMapping(value = "/getLeaveSellByEmpId/{empId}")
	public ResponseEntity<List<LeaveSellResponse>> getLeaveSellByEmpId(@PathVariable("empId") int empId) {
		return hrmsLeaveSoldService.getLeaveSellByEmpId(empId);

	}

	@GetMapping(value = "/getCommutableAmount/{empid}/{numberofdays}")
	public ResponseEntity<Double> getCommutableAmount(@PathVariable("empid") int empid,
			@PathVariable("numberofdays") int numberofdays) {

		return hrmsLeaveSoldService.getCommutableAmount(empid, numberofdays);

	}

	@GetMapping(value = "/getCommutableAmountV2/{employeeid}/{days}")
	public ResponseEntity<Double> getCommutableAmountV2(@PathVariable("employeeid") int employeeid,
			@PathVariable("days") int days) {
		return hrmsLeaveSoldService.getCommutableAmountV2(employeeid, days);
	}

	@PutMapping(value = "/ApproveLeaveCommutation/{leavesoldid}/{supervisorid}/{status}/{comment}")
	public ResponseEntity<?> ApproveLeaveCommutation(@PathVariable("leavesoldid") int leavesoldid,
			@PathVariable("supervisorid") int supervisorid, @PathVariable("status") int status,
			@PathVariable("comment") String comment) {

		return hrmsLeaveSoldService.ApproveLeaveCommutation(leavesoldid, supervisorid, status, comment);

	}

	@PutMapping(value = "/ApproveLeaveCommutationV2/{leavesoldid}/{supervisorid}/{status}")
	public ResponseEntity<?> ApproveLeaveCommutationV2(@RequestBody TrainingUpdate trainingUpdate,
			@PathVariable("leavesoldid") int leavesoldid, @PathVariable("supervisorid") int supervisorid,
			@PathVariable("status") int status) {
		String comment = "";
		if (trainingUpdate != null) {
			comment = trainingUpdate.getComment();

		}
		return hrmsLeaveSoldService.ApproveLeaveCommutation(leavesoldid, supervisorid, status, comment);
	}

	@GetMapping(value = "/getLeaveCommutationApprovers/{id}")
	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveCommutationApprovers(@PathVariable("id") int id) {

		return hrmsLeaveSoldService.getLeaveCommutationApprovers(id);
	}

	@GetMapping(value = "/getLeaveCommutationNotApprovedBySupervisorNext/{supervisorid}")
	public ResponseEntity<List<LeaveSellResponse>> getHrmsLeaveCommutationNotApprovedBySupervisorNext(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsLeaveSoldService.getHrmsLeaveCommutationNotApprovedBySupervisorNext(supervisorid);

	}

	@PutMapping(value = "/acknowledgeLeaveCommutation/{id}/{empid}/{acknowledge}")
	public ResponseEntity<?> AcknowledgeLeaveCommutation(@PathVariable("id") int id, @PathVariable("empid") int empid,
			@PathVariable("acknowledge") int acknowledge) {

		return hrmsLeaveSoldService.AcknowledgeLeaveCommutation(id, empid, acknowledge);

	}

	@GetMapping(value = "/getAllLeaveCommutedNotApprovedYet/{supervisorid}")
	public ResponseEntity<List<LeaveSellResponse>> getAllLeaveCommutedNotApprovedYet(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsLeaveSoldService.getAllLeaveCommutedNotApprovedYet(supervisorid);
	}

}
