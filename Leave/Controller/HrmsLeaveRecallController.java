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
import com.Hrms.Leave.DTO.LeaveRecallResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecall;
import com.Hrms.Leave.Service.HrmsLeaveRecallService;
import com.Hrms.Training.DTO.TrainingUpdate;

@RestController
@RequestMapping("v1/leaveRecall")
public class HrmsLeaveRecallController {
	@Autowired
	private HrmsLeaveRecallService hrmsLeaveRecallService;

	@PostMapping(value = "/requestLeaveRecall/{requestedby}")
	public ResponseEntity<HrmsLeaveRecall> requestLeaveRecall(@RequestBody HrmsLeaveRecall hrmsLeaveRecall,
			@PathVariable("requestedby") int requestedby) {

		return hrmsLeaveRecallService.requestLeaveRecall(hrmsLeaveRecall, requestedby);

	}

	@GetMapping(value = "/getLeaveRecallById/{id}")
	public ResponseEntity<LeaveRecallResponse> getLeaveRecallById(@PathVariable("id") int id) {

		return hrmsLeaveRecallService.getLeaveRecallById(id);

	}

	@PutMapping(value = "/updateLeaveRecall/{id}/{requestedby}")
	public ResponseEntity<HrmsLeaveRecall> updateLeaveRecall(@RequestBody HrmsLeaveRecall hrmsLeaveRecall,
			@PathVariable("id") int id, @PathVariable("requestedby") int requestedby) {

		return hrmsLeaveRecallService.updateLeaveRecall(hrmsLeaveRecall, id, requestedby);

	}

	@DeleteMapping(value = "/deleteLeaveRecall/{id}")
	public ResponseEntity<?> deleteLeaveRecall(@PathVariable("id") int id) {

		return hrmsLeaveRecallService.deleteLeaveRecall(id);

	}

	@GetMapping(value = "/getAllLeaveRecall")
	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecall() {

		return hrmsLeaveRecallService.getAllLeaveRecall();
	}

	@GetMapping(value = "/getAllLeaveRecallBySupervisorId/{supervisorId}")
	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecallBySupervisorId(
			@PathVariable("supervisorId") int supervisorId) {

		return hrmsLeaveRecallService.getAllLeaveRecallBySupervisorId(supervisorId);

	}

	@PutMapping(value = "/ApproveLeaveRecall/{leaverecalledid}/{supervisorid}/{status}")
	public ResponseEntity<?> ApproveLeaveRecall(@RequestBody TrainingUpdate trainingUpdate,
			@PathVariable("leaverecalledid") int leaverecalledid, @PathVariable("supervisorid") int supervisorid,
			@PathVariable("status") int status) {

		String comment = "";
		if (trainingUpdate != null) {
			comment = trainingUpdate.getComment();

		}
		return hrmsLeaveRecallService.ApproveLeaveRecall(leaverecalledid, supervisorid, status, comment);
	}

	@GetMapping(value = "/getLeaveRecallApprovers/{id}")
	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveRecallApprovers(@PathVariable("id") int id) {

		return hrmsLeaveRecallService.getLeaveRecallApprovers(id);
	}

	@GetMapping(value = "/getLeaveRecallNotApprovedBySupervisorNext/{supervisorid}")
	public ResponseEntity<List<LeaveRecallResponse>> getHrmsLeaveRecallNotApprovedBySupervisorNext(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsLeaveRecallService.getHrmsLeaveRecallNotApprovedBySupervisorNext(supervisorid);
	}

	@GetMapping(value = "/getAllLeaveRecallByEmployeeId/{employeeid}")
	public ResponseEntity<List<LeaveRecallResponse>> getAllLeaveRecallByEmployeeId(
			@PathVariable("employeeid") int employeeid) {

		return hrmsLeaveRecallService.getAllLeaveRecallByEmployeeId(employeeid);

	}

	@GetMapping(value = "/acknowledgeLeaveRecall/{id}/{empid}/{acknowledge}")
	public ResponseEntity<?> acknowledgeLeaveRecall(@PathVariable("id") int id, @PathVariable("empid") int empid,
			@PathVariable("acknowledge") int acknowledge) {
		return hrmsLeaveRecallService.AcknowledgeLeaveRecall(id, empid, acknowledge);
	}

}
