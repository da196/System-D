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
import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalWorkflow;
import com.Hrms.Leave.Repository.HrmsLeaveCommutationApprovalWorkflowRepository;

@Service
public class HrmsLeaveCommutationApprovalWorkflowServiceImpl implements HrmsLeaveCommutationApprovalWorkflowService {

	@Autowired
	private HrmsLeaveCommutationApprovalWorkflowRepository hrmsLeaveCommutationApprovalWorkflowRepository;

	@Autowired

	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflow> addLeaveCommutationWorkFlow(
			HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow) {
		if (hrmsLeaveCommutationApprovalWorkflowRepository
				.existsByNameAndActive(hrmsLeaveCommutationApprovalWorkflow.getName(), 1)
				|| hrmsLeaveCommutationApprovalWorkflowRepository.existsBySupervisordesignationidAndActive(
						hrmsLeaveCommutationApprovalWorkflow.getSupervisordesignationid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveCommutationApprovalWorkflow);
		} else {
			hrmsLeaveCommutationApprovalWorkflow.setActive(1);
			hrmsLeaveCommutationApprovalWorkflow.setApproved(1);

			hrmsLeaveCommutationApprovalWorkflow.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository
					.existsByIdAndActive(hrmsLeaveCommutationApprovalWorkflow.getSupervisordesignationid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveCommutationApprovalWorkflowRepository
						.saveAndFlush(hrmsLeaveCommutationApprovalWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveCommutationApprovalWorkflow);
			}

		}
	}

	@Override
	public ResponseEntity<LeaveWorkflowResponse> getLeaveCommutationWorkFlowById(int id) {
		LeaveWorkflowResponse leaveWorkflowResponse = new LeaveWorkflowResponse();
		if (hrmsLeaveCommutationApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveCommutationApprovalWorkflow dbm = hrmsLeaveCommutationApprovalWorkflowRepository
					.findByIdAndActive(id, 1);

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
	public ResponseEntity<HrmsLeaveCommutationApprovalWorkflow> updateLeaveCommutationWorkFlow(
			HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow, int id) {
		if (hrmsLeaveCommutationApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveCommutationApprovalWorkflow.setActive(1);
			hrmsLeaveCommutationApprovalWorkflow.setApproved(0);
			hrmsLeaveCommutationApprovalWorkflow.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveCommutationApprovalWorkflowRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveCommutationApprovalWorkflow.setDate_created(
						hrmsLeaveCommutationApprovalWorkflowRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveCommutationApprovalWorkflowRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveCommutationApprovalWorkflow
						.setUnique_id(hrmsLeaveCommutationApprovalWorkflowRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsLeaveCommutationApprovalWorkflowRepository.saveAndFlush(hrmsLeaveCommutationApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveCommutationApprovalWorkflow);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveCommutationWorkFlow(int id) {
		if (hrmsLeaveCommutationApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveCommutationApprovalWorkflow hrmsLeaveCommutationApprovalWorkflow = hrmsLeaveCommutationApprovalWorkflowRepository
					.findByIdAndActive(id, 1);
			hrmsLeaveCommutationApprovalWorkflow.setActive(0);
			hrmsLeaveCommutationApprovalWorkflow.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(
					hrmsLeaveCommutationApprovalWorkflowRepository.saveAndFlush(hrmsLeaveCommutationApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveCommutationWorkFlow() {
		List<LeaveWorkflowResponse> leaveApprovalWorkflowlist = new ArrayList<>();

		List<HrmsLeaveCommutationApprovalWorkflow> dbms = hrmsLeaveCommutationApprovalWorkflowRepository
				.findByActive(1);

		for (HrmsLeaveCommutationApprovalWorkflow dbm : dbms) {

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
