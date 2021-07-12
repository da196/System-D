package com.Hrms.Training.Controller;

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

import com.Hrms.Training.DTO.TrainingApprovalStatus;
import com.Hrms.Training.DTO.TrainingResponse;
import com.Hrms.Training.DTO.TrainingUpdate;
import com.Hrms.Training.Entity.HrmsTraining;
import com.Hrms.Training.Service.HrmsTrainingService;

@RestController
@RequestMapping("v1/training")
public class HrmsTrainingController {

	@Autowired
	private HrmsTrainingService hrmsTrainingService;

	@PostMapping(value = "/addTraining")
	public ResponseEntity<HrmsTraining> addHrmsTraining(@RequestBody HrmsTraining hrmsTraining) {

		return hrmsTrainingService.addHrmsTraining(hrmsTraining);

	}

	@PostMapping(value = "/addHrmsTrainingV2/{employeeids}")
	public ResponseEntity<List<HrmsTraining>> addHrmsTrainingV2(@RequestBody HrmsTraining hrmsTraining,
			@PathVariable("employeeids") List<Integer> employeeids) {
		return hrmsTrainingService.addHrmsTrainingV2(hrmsTraining, employeeids);

	}

	@DeleteMapping(value = "/deleteTraining/{id}")
	public ResponseEntity<?> deleteHrmsTraining(@PathVariable("id") int id) {
		return hrmsTrainingService.deleteHrmsTraining(id);

	}

	@PutMapping(value = "/updateTraining/{id}")
	public ResponseEntity<HrmsTraining> updateHrmsTraining(@RequestBody HrmsTraining hrmsTraining,
			@PathVariable("id") int id) {
		return hrmsTrainingService.updateHrmsTraining(hrmsTraining, id);

	}

	@GetMapping(value = "/getTrainingById/{id}")
	public ResponseEntity<TrainingResponse> getHrmsTrainingById(@PathVariable("id") int id) {

		return hrmsTrainingService.getHrmsTrainingById(id);

	}

	@GetMapping(value = "/getTrainingByEmployeeId/{Employeeid}")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByEmployeeId(
			@PathVariable("Employeeid") int Employeeid) {

		return hrmsTrainingService.getHrmsTrainingByEmployeeId(Employeeid);

	}

	@GetMapping(value = "/getTrainingCurrentYear")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingCurrentYear() {

		return hrmsTrainingService.getHrmsTrainingCurrentYear();

	}

	@GetMapping(value = "/getTrainingByFinancialYear/{financialyearid}")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYear(
			@PathVariable("financialyearid") int finyearid) {

		return hrmsTrainingService.getHrmsTrainingByFinancialYear(finyearid);
	}

	@GetMapping(value = "/getHrmsTrainingByFinancialYearAndEmployeeId/{financialyearid}/{Employeeid}")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYearAndEmployeeId(
			@PathVariable("financialyearid") int finyearid, @PathVariable("Employeeid") int Employeeid) {

		return hrmsTrainingService.getHrmsTrainingByFinancialYearAndEmployeeId(finyearid, Employeeid);

	}

	@GetMapping(value = "/getHrmsTrainingByFinancialYearAndQuaterId/{financialyearid}/{Quaterid}/{Categoryid}")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYearAndQuaterId(
			@PathVariable("financialyearid") int finyearid, @PathVariable("Quaterid") int Quaterid,
			@PathVariable("Categoryid") int Categoryid) {
		return hrmsTrainingService.getHrmsTrainingByFinancialYearAndQuaterIdAndCategoryId(finyearid, Quaterid,
				Categoryid);
	}

	@PutMapping(value = "/ApproveTraining/{id}/{supervisorid}/{status}/{comment}")
	public ResponseEntity<?> ApproveHrmsTraining(@PathVariable("id") int id,
			@PathVariable("supervisorid") int supervisorid, @PathVariable("status") int status,
			@PathVariable("comment") String comment) {

		return hrmsTrainingService.ApproveHrmsTraining(id, supervisorid, status, comment);

	}

	@PutMapping(value = "/ApproveTrainingV2/{id}/{supervisorid}/{status}")
	public ResponseEntity<?> ApproveHrmsTrainingV2(@RequestBody TrainingUpdate trainingUpdate,
			@PathVariable("id") int id, @PathVariable("supervisorid") int supervisorid,
			@PathVariable("status") int status) {
		String comment = "";
		if (trainingUpdate != null) {
			comment = trainingUpdate.getComment();

		}
		return hrmsTrainingService.ApproveHrmsTraining(id, supervisorid, status, comment);

	}

	@GetMapping(value = "/getTrainingNotApprovedBySupervisor/{supervisorid}")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingNotApprovedBySupervisorNext(
			@PathVariable("supervisorid") int supervisorid) {

		return hrmsTrainingService.getHrmsTrainingNotApprovedBySupervisorNext(supervisorid);

	}

	@GetMapping(value = "/getTrainingApprovers/{id}")
	public ResponseEntity<List<TrainingApprovalStatus>> getHrmsTrainingApprovers(@PathVariable("id") int id) {

		return hrmsTrainingService.getHrmsTrainingApprovers(id);

	}

	@PutMapping(value = "/ApproveTraining/{id}/{attended}/{status}/{attendedReason}")
	public ResponseEntity<?> updateAfterAttendingTraining(@PathVariable("id") int id,
			@PathVariable("attended") int attended, @PathVariable("attendedReason") String attendedReason) {

		return hrmsTrainingService.updateAfterAttendingTraining(id, attended, attendedReason);
	}

	@GetMapping(value = "/getTrainingApprovedByEmployeeId/{Employeeid}")
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingApprovedByEmployeeId(
			@PathVariable("Employeeid") int Employeeid) {

		return hrmsTrainingService.getHrmsTrainingApprovedByEmployeeId(Employeeid);

	}

}
