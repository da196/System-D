package com.Hrms.Leave.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Leave.DTO.LeaveWorkflowStepResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflow;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflowStep;
import com.Hrms.Leave.Repository.HrmsLeaveRecallApprovalWorkflowRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallApprovalWorkflowStepRepository;

@Service
public class HrmsLeaveRecallApprovalWorkflowStepServiceImpl implements HrmsLeaveRecallApprovalWorkflowStepService {

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowStepRepository hrmsLeaveRecallApprovalWorkflowStepRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowRepository hrmsLeaveRecallApprovalWorkflowRepository;

	@Override
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflowStep> addLeaveRecallWorkFlowStep(
			HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveRecallApprovalWorkflowStep) {
		if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
				hrmsLeaveRecallApprovalWorkflowStep.getWorkflowid(),
				hrmsLeaveRecallApprovalWorkflowStep.getApproverdesignationid(), 1)

		) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveRecallApprovalWorkflowStep);
		} else {
			hrmsLeaveRecallApprovalWorkflowStep.setActive(1);
			hrmsLeaveRecallApprovalWorkflowStep.setApproved(1);

			hrmsLeaveRecallApprovalWorkflowStep.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository
					.existsByIdAndActive(hrmsLeaveRecallApprovalWorkflowStep.getApproverdesignationid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveRecallApprovalWorkflowStepRepository
						.saveAndFlush(hrmsLeaveRecallApprovalWorkflowStep));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveRecallApprovalWorkflowStep);
			}

		}
	}

	@Override
	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveRecallWorkFlowStepById(int id) {
		LeaveWorkflowStepResponse leaveWorkflowStepResponse = new LeaveWorkflowStepResponse();

		if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveRecallApprovalWorkflowStep dbm = hrmsLeaveRecallApprovalWorkflowStepRepository
					.findByIdAndActive(id, 1);

			if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {
				HrmsLeaveRecallApprovalWorkflow Workflow = hrmsLeaveRecallApprovalWorkflowRepository
						.findByIdAndActive(dbm.getWorkflowid(), 1);

				leaveWorkflowStepResponse.setActive(dbm.getActive());
				leaveWorkflowStepResponse.setApproved(dbm.getApproved());

				leaveWorkflowStepResponse.setWorkflowid(Workflow.getId());
				leaveWorkflowStepResponse.setWorkflow(Workflow.getName());
				leaveWorkflowStepResponse.setApproverdesignationid(dbm.getApproverdesignationid());
				if (dbm.getApproverdesignationid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationid())) {
					leaveWorkflowStepResponse.setApproverdesignation(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationid()).get().getName());
				}
				leaveWorkflowStepResponse.setApproverdesignationnextid(dbm.getApproverdesignationnextid());
				if (dbm.getApproverdesignationnextid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationnextid())) {
					leaveWorkflowStepResponse.setApproverdesignationnext(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationnextid()).get().getName());
				}
				leaveWorkflowStepResponse.setApproverdesignationprevid(dbm.getApproverdesignationprevid());
				if (dbm.getApproverdesignationprevid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationprevid())) {
					leaveWorkflowStepResponse.setApproverdesignationprev(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationprevid()).get().getName());
				}
				leaveWorkflowStepResponse.setDescription(dbm.getDescription());
				leaveWorkflowStepResponse.setId(dbm.getId());
				leaveWorkflowStepResponse.setStepnumber(dbm.getStepnumber());

			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(leaveWorkflowStepResponse);
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveRecallWorkFlowStepByWorkFlowId(int workflowId) {
		List<LeaveWorkflowStepResponse> leaveWorkflowStepResponselist = new ArrayList<>();

		List<HrmsLeaveRecallApprovalWorkflowStep> dbms = hrmsLeaveRecallApprovalWorkflowStepRepository
				.findByWorkflowidAndActive(workflowId, 1);

		for (HrmsLeaveRecallApprovalWorkflowStep dbm : dbms) {

			if (hrmsLeaveRecallApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsLeaveRecallApprovalWorkflow Workflow = hrmsLeaveRecallApprovalWorkflowRepository
						.findByIdAndActive(dbm.getWorkflowid(), 1);

				LeaveWorkflowStepResponse leaveWorkflowStepResponse = new LeaveWorkflowStepResponse();

				leaveWorkflowStepResponse.setActive(dbm.getActive());
				leaveWorkflowStepResponse.setApproved(dbm.getApproved());

				leaveWorkflowStepResponse.setWorkflowid(Workflow.getId());
				leaveWorkflowStepResponse.setWorkflow(Workflow.getName());
				leaveWorkflowStepResponse.setApproverdesignationid(dbm.getApproverdesignationid());
				if (dbm.getApproverdesignationid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationid())) {
					leaveWorkflowStepResponse.setApproverdesignation(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationid()).get().getName());
				}
				leaveWorkflowStepResponse.setApproverdesignationnextid(dbm.getApproverdesignationnextid());
				if (dbm.getApproverdesignationnextid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationnextid())) {
					leaveWorkflowStepResponse.setApproverdesignationnext(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationnextid()).get().getName());
				}
				leaveWorkflowStepResponse.setApproverdesignationprevid(dbm.getApproverdesignationprevid());
				if (dbm.getApproverdesignationprevid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationprevid())) {
					leaveWorkflowStepResponse.setApproverdesignationprev(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationprevid()).get().getName());
				}
				leaveWorkflowStepResponse.setDescription(dbm.getDescription());
				leaveWorkflowStepResponse.setId(dbm.getId());
				leaveWorkflowStepResponse.setStepnumber(dbm.getStepnumber());

				leaveWorkflowStepResponselist.add(leaveWorkflowStepResponse);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveWorkflowStepResponselist);
	}

	@Override
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflowStep> updateLeaveRecallWorkFlowStep(
			HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveRecallApprovalWorkflowStep, int id) {
		if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveRecallApprovalWorkflowStep.setActive(1);
			hrmsLeaveRecallApprovalWorkflowStep.setApproved(0);
			hrmsLeaveRecallApprovalWorkflowStep.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveRecallApprovalWorkflowStepRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveRecallApprovalWorkflowStep.setDate_created(
						hrmsLeaveRecallApprovalWorkflowStepRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveRecallApprovalWorkflowStepRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveRecallApprovalWorkflowStep
						.setUnique_id(hrmsLeaveRecallApprovalWorkflowStepRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsLeaveRecallApprovalWorkflowStepRepository.saveAndFlush(hrmsLeaveRecallApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveRecallApprovalWorkflowStep);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveRecallWorkFlowStep(int id) {
		if (hrmsLeaveRecallApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveRecallApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep = hrmsLeaveRecallApprovalWorkflowStepRepository
					.findByIdAndActive(id, 1);
			hrmsLeaveApprovalWorkflowStep.setActive(0);
			hrmsLeaveApprovalWorkflowStep.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveRecallApprovalWorkflowStepRepository.saveAndFlush(hrmsLeaveApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveRecallWorkFlowStep() {

		List<LeaveWorkflowStepResponse> leaveWorkflowStepResponselist = new ArrayList<>();

		List<HrmsLeaveRecallApprovalWorkflowStep> dbms = hrmsLeaveRecallApprovalWorkflowStepRepository.findByActive(1);

		for (HrmsLeaveRecallApprovalWorkflowStep dbm : dbms) {

			if (hrmsLeaveRecallApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsLeaveRecallApprovalWorkflow Workflow = hrmsLeaveRecallApprovalWorkflowRepository
						.findByIdAndActive(dbm.getWorkflowid(), 1);

				LeaveWorkflowStepResponse leaveWorkflowStepResponse = new LeaveWorkflowStepResponse();

				leaveWorkflowStepResponse.setActive(dbm.getActive());
				leaveWorkflowStepResponse.setApproved(dbm.getApproved());

				leaveWorkflowStepResponse.setWorkflowid(Workflow.getId());
				leaveWorkflowStepResponse.setWorkflow(Workflow.getName());
				leaveWorkflowStepResponse.setApproverdesignationid(dbm.getApproverdesignationid());
				if (dbm.getApproverdesignationid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationid())) {
					leaveWorkflowStepResponse.setApproverdesignation(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationid()).get().getName());
				}
				leaveWorkflowStepResponse.setApproverdesignationnextid(dbm.getApproverdesignationnextid());
				if (dbm.getApproverdesignationnextid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationnextid())) {
					leaveWorkflowStepResponse.setApproverdesignationnext(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationnextid()).get().getName());
				}
				leaveWorkflowStepResponse.setApproverdesignationprevid(dbm.getApproverdesignationprevid());
				if (dbm.getApproverdesignationprevid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationprevid())) {
					leaveWorkflowStepResponse.setApproverdesignationprev(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationprevid()).get().getName());
				}
				leaveWorkflowStepResponse.setDescription(dbm.getDescription());
				leaveWorkflowStepResponse.setId(dbm.getId());
				leaveWorkflowStepResponse.setStepnumber(dbm.getStepnumber());

				leaveWorkflowStepResponselist.add(leaveWorkflowStepResponse);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveWorkflowStepResponselist);
	}

}
