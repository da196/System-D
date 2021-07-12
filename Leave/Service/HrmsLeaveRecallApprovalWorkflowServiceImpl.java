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
import com.Hrms.Leave.DTO.LeaveWorkflowResponse;
import com.Hrms.Leave.Entity.HrmsLeaveRecallApprovalWorkflow;
import com.Hrms.Leave.Repository.HrmsLeaveRecallApprovalWorkflowRepository;

@Service
public class HrmsLeaveRecallApprovalWorkflowServiceImpl implements HrmsLeaveRecallApprovalWorkflowService {

	@Autowired
	private HrmsLeaveRecallApprovalWorkflowRepository hrmsLeaveRecallApprovalWorkflowRepository;

	@Autowired

	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflow> addLeaveRecallWorkFlow(
			HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow) {
		if (hrmsLeaveRecallApprovalWorkflowRepository.existsByNameAndActive(hrmsLeaveRecallApprovalWorkflow.getName(),
				1)
				|| hrmsLeaveRecallApprovalWorkflowRepository.existsBySupervisordesignationidAndActive(
						hrmsLeaveRecallApprovalWorkflow.getSupervisordesignationid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveRecallApprovalWorkflow);
		} else {
			hrmsLeaveRecallApprovalWorkflow.setActive(1);
			hrmsLeaveRecallApprovalWorkflow.setApproved(1);

			hrmsLeaveRecallApprovalWorkflow.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository
					.existsByIdAndActive(hrmsLeaveRecallApprovalWorkflow.getSupervisordesignationid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsLeaveRecallApprovalWorkflowRepository.saveAndFlush(hrmsLeaveRecallApprovalWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveRecallApprovalWorkflow);
			}

		}
	}

	@Override
	public ResponseEntity<LeaveWorkflowResponse> getLeaveRecallWorkFlowById(int id) {
		LeaveWorkflowResponse leaveWorkflowResponse = new LeaveWorkflowResponse();
		if (hrmsLeaveRecallApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveRecallApprovalWorkflow dbm = hrmsLeaveRecallApprovalWorkflowRepository.findByIdAndActive(id, 1);

			leaveWorkflowResponse.setActive(dbm.getActive());
			leaveWorkflowResponse.setApproved(dbm.getApproved());
			leaveWorkflowResponse.setCode(dbm.getCode());
			leaveWorkflowResponse.setDescription(dbm.getDescription());
			leaveWorkflowResponse.setId(dbm.getId());
			leaveWorkflowResponse.setName(dbm.getName());
			leaveWorkflowResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsById(dbm.getSupervisordesignationid())) {
				leaveWorkflowResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveWorkflowResponse);
	}

	@Override
	public ResponseEntity<HrmsLeaveRecallApprovalWorkflow> updateLeaveRecallWorkFlow(
			HrmsLeaveRecallApprovalWorkflow hrmsLeaveRecallApprovalWorkflow, int id) {
		if (hrmsLeaveRecallApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveRecallApprovalWorkflow.setActive(1);
			hrmsLeaveRecallApprovalWorkflow.setApproved(0);
			hrmsLeaveRecallApprovalWorkflow.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveRecallApprovalWorkflowRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveRecallApprovalWorkflow.setDate_created(
						hrmsLeaveRecallApprovalWorkflowRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveRecallApprovalWorkflowRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveRecallApprovalWorkflow
						.setUnique_id(hrmsLeaveRecallApprovalWorkflowRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveRecallApprovalWorkflowRepository.saveAndFlush(hrmsLeaveRecallApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveRecallApprovalWorkflow);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveRecallWorkFlow(int id) {
		if (hrmsLeaveRecallApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveRecallApprovalWorkflow hrmsLeaveApprovalWorkflow = hrmsLeaveRecallApprovalWorkflowRepository
					.findByIdAndActive(id, 1);
			hrmsLeaveApprovalWorkflow.setActive(0);
			hrmsLeaveApprovalWorkflow.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveRecallApprovalWorkflowRepository.saveAndFlush(hrmsLeaveApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveRecallWorkFlow() {
		List<LeaveWorkflowResponse> leaveApprovalWorkflowlist = new ArrayList<>();

		List<HrmsLeaveRecallApprovalWorkflow> dbms = hrmsLeaveRecallApprovalWorkflowRepository.findByActive(1);

		for (HrmsLeaveRecallApprovalWorkflow dbm : dbms) {

			LeaveWorkflowResponse leaveWorkflowResponse = new LeaveWorkflowResponse();

			leaveWorkflowResponse.setActive(dbm.getActive());
			leaveWorkflowResponse.setApproved(dbm.getApproved());
			leaveWorkflowResponse.setCode(dbm.getCode());
			leaveWorkflowResponse.setDescription(dbm.getDescription());
			leaveWorkflowResponse.setId(dbm.getId());
			leaveWorkflowResponse.setName(dbm.getName());
			leaveWorkflowResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsById(dbm.getSupervisordesignationid())) {
				leaveWorkflowResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			leaveApprovalWorkflowlist.add(leaveWorkflowResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApprovalWorkflowlist);
	}

}
