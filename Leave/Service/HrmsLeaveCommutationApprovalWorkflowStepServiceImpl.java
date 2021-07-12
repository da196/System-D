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
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflow;
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflowStep;
import com.Hrms.Leave.Repository.HrmsLeaveCommutationApprovalWorkflowRepository;
import com.Hrms.Leave.Repository.HrmsLeaveCommutationApprovalWorkflowStepRepository;

@Service
public class HrmsLeaveCommutationApprovalWorkflowStepServiceImpl
		implements HrmsLeaveCommutationApprovalWorkflowStepService {

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowStepRepository hrmsLeaveCommutationApprovalWorkflowStepRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowRepository hrmsLeaveCommutationApprovalWorkflowRepository;

	@Override
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflowStep> addLeaveCommutationWorkFlowStep(
			HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep) {
		if (hrmsLeaveCommutationApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
				hrmsLeaveCommutationApprovalWorkflowStep.getWorkflowid(),
				hrmsLeaveCommutationApprovalWorkflowStep.getApproverdesignationid(), 1)

		) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveCommutationApprovalWorkflowStep);
		} else {
			hrmsLeaveCommutationApprovalWorkflowStep.setActive(1);
			hrmsLeaveCommutationApprovalWorkflowStep.setApproved(1);

			hrmsLeaveCommutationApprovalWorkflowStep.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository
					.existsByIdAndActive(hrmsLeaveCommutationApprovalWorkflowStep.getApproverdesignationid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveCommutationApprovalWorkflowStepRepository
						.saveAndFlush(hrmsLeaveCommutationApprovalWorkflowStep));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
						.body(hrmsLeaveCommutationApprovalWorkflowStep);
			}

		}
	}

	@Override
	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveCommutationWorkFlowStepById(int id) {
		LeaveWorkflowStepResponse leaveWorkflowStepResponse = new LeaveWorkflowStepResponse();

		if (hrmsLeaveCommutationApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveCommutationApprovalWorkflowStep dbm = hrmsLeaveCommutationApprovalWorkflowStepRepository
					.findByIdAndActive(id, 1);

			if (hrmsLeaveCommutationApprovalWorkflowStepRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {
				HrmsLeaveCommutationApprovalWorkflow Workflow = hrmsLeaveCommutationApprovalWorkflowRepository
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
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveCommutationWorkFlowStepByWorkFlowId(int workflowId) {
		List<LeaveWorkflowStepResponse> leaveWorkflowStepResponselist = new ArrayList<>();

		List<HrmsLeaveCommutationApprovalWorkflowStep> dbms = hrmsLeaveCommutationApprovalWorkflowStepRepository
				.findByWorkflowidAndActive(workflowId, 1);

		for (HrmsLeaveCommutationApprovalWorkflowStep dbm : dbms) {

			if (hrmsLeaveCommutationApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsLeaveCommutationApprovalWorkflow Workflow = hrmsLeaveCommutationApprovalWorkflowRepository
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
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflowStep> updateLeaveCommutationWorkFlowStep(
			HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep, int id) {
		if (hrmsLeaveCommutationApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveCommutationApprovalWorkflowStep.setActive(1);
			hrmsLeaveCommutationApprovalWorkflowStep.setApproved(0);
			hrmsLeaveCommutationApprovalWorkflowStep.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveCommutationApprovalWorkflowStepRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveCommutationApprovalWorkflowStep.setDate_created(
						hrmsLeaveCommutationApprovalWorkflowStepRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveCommutationApprovalWorkflowStepRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveCommutationApprovalWorkflowStep.setUnique_id(
						hrmsLeaveCommutationApprovalWorkflowStepRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveCommutationApprovalWorkflowStepRepository
					.saveAndFlush(hrmsLeaveCommutationApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveCommutationApprovalWorkflowStep);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveCommutationWorkFlowStep(int id) {
		if (hrmsLeaveCommutationApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveCommutationApprovalWorkflowStep hrmsLeaveCommutationApprovalWorkflowStep = hrmsLeaveCommutationApprovalWorkflowStepRepository
					.findByIdAndActive(id, 1);
			hrmsLeaveCommutationApprovalWorkflowStep.setActive(0);
			hrmsLeaveCommutationApprovalWorkflowStep.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveCommutationApprovalWorkflowStepRepository
					.saveAndFlush(hrmsLeaveCommutationApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveCommutationWorkFlowStep() {

		List<LeaveWorkflowStepResponse> leaveWorkflowStepResponselist = new ArrayList<>();

		List<HrmsLeaveCommutationApprovalWorkflowStep> dbms = hrmsLeaveCommutationApprovalWorkflowStepRepository
				.findByActive(1);

		for (HrmsLeaveCommutationApprovalWorkflowStep dbm : dbms) {

			if (hrmsLeaveCommutationApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsLeaveCommutationApprovalWorkflow Workflow = hrmsLeaveCommutationApprovalWorkflowRepository
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
