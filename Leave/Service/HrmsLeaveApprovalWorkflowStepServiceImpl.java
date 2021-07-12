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
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflow;
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflowStep;
import com.Hrms.Leave.Repository.HrmsLeaveApprovalWorkflowRepository;
import com.Hrms.Leave.Repository.HrmsLeaveApprovalWorkflowStepRepository;

@Service
public class HrmsLeaveApprovalWorkflowStepServiceImpl implements HrmsLeaveApprovalWorkflowStepService {

	@Autowired
	private HrmsLeaveApprovalWorkflowStepRepository hrmsLeaveApprovalWorkflowStepRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsLeaveApprovalWorkflowRepository hrmsLeaveApprovalWorkflowRepository;

	@Override
	public ResponseEntity<HrmsLeaveApprovalWorkflowStep> addLeaveWorkFlowStep(
			HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep) {
		if (hrmsLeaveApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
				hrmsLeaveApprovalWorkflowStep.getWorkflowid(), hrmsLeaveApprovalWorkflowStep.getApproverdesignationid(),
				1)

		) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveApprovalWorkflowStep);
		} else {
			hrmsLeaveApprovalWorkflowStep.setActive(1);
			hrmsLeaveApprovalWorkflowStep.setApproved(1);

			hrmsLeaveApprovalWorkflowStep.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository.existsByIdAndActive(hrmsLeaveApprovalWorkflowStep.getApproverdesignationid(),
					1)) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsLeaveApprovalWorkflowStepRepository.saveAndFlush(hrmsLeaveApprovalWorkflowStep));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveApprovalWorkflowStep);
			}

		}
	}

	@Override
	public ResponseEntity<LeaveWorkflowStepResponse> getLeaveWorkFlowStepById(int id) {
		LeaveWorkflowStepResponse leaveWorkflowStepResponse = new LeaveWorkflowStepResponse();

		if (hrmsLeaveApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveApprovalWorkflowStep dbm = hrmsLeaveApprovalWorkflowStepRepository.findByIdAndActive(id, 1);

			if (hrmsLeaveApprovalWorkflowStepRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {
				HrmsLeaveApprovalWorkflow Workflow = hrmsLeaveApprovalWorkflowRepository
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
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getLeaveWorkFlowStepByWorkFlowId(int workflowId) {
		List<LeaveWorkflowStepResponse> leaveWorkflowStepResponselist = new ArrayList<>();

		List<HrmsLeaveApprovalWorkflowStep> dbms = hrmsLeaveApprovalWorkflowStepRepository
				.findByWorkflowidAndActive(workflowId, 1);

		for (HrmsLeaveApprovalWorkflowStep dbm : dbms) {

			if (hrmsLeaveApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsLeaveApprovalWorkflow Workflow = hrmsLeaveApprovalWorkflowRepository
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
	public ResponseEntity<HrmsLeaveApprovalWorkflowStep> updateLeaveWorkFlowStep(
			HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep, int id) {
		if (hrmsLeaveApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveApprovalWorkflowStep.setActive(1);
			hrmsLeaveApprovalWorkflowStep.setApproved(0);
			hrmsLeaveApprovalWorkflowStep.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveApprovalWorkflowStepRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveApprovalWorkflowStep
						.setDate_created(hrmsLeaveApprovalWorkflowStepRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveApprovalWorkflowStepRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveApprovalWorkflowStep
						.setUnique_id(hrmsLeaveApprovalWorkflowStepRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveApprovalWorkflowStepRepository.saveAndFlush(hrmsLeaveApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveApprovalWorkflowStep);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveWorkFlowStep(int id) {
		if (hrmsLeaveApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveApprovalWorkflowStep hrmsLeaveApprovalWorkflowStep = hrmsLeaveApprovalWorkflowStepRepository
					.findByIdAndActive(id, 1);
			hrmsLeaveApprovalWorkflowStep.setActive(0);
			hrmsLeaveApprovalWorkflowStep.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveApprovalWorkflowStepRepository.saveAndFlush(hrmsLeaveApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowStepResponse>> getAllLeaveWorkFlowStep() {

		List<LeaveWorkflowStepResponse> leaveWorkflowStepResponselist = new ArrayList<>();

		List<HrmsLeaveApprovalWorkflowStep> dbms = hrmsLeaveApprovalWorkflowStepRepository.findByActive(1);

		for (HrmsLeaveApprovalWorkflowStep dbm : dbms) {

			if (hrmsLeaveApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsLeaveApprovalWorkflow Workflow = hrmsLeaveApprovalWorkflowRepository
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
