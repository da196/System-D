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
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflow;
import com.Hrms.Leave.Repository.HrmsLeaveApprovalWorkflowRepository;

@Service
public class HrmsLeaveApprovalWorkflowServiceImpl implements HrmsLeaveApprovalWorkflowService {

	@Autowired
	private HrmsLeaveApprovalWorkflowRepository hrmsLeaveApprovalWorkflowRepository;

	@Autowired

	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<HrmsLeaveApprovalWorkflow> addLeaveWorkFlow(
			HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow) {
		if (hrmsLeaveApprovalWorkflowRepository.existsByNameAndActive(hrmsLeaveApprovalWorkflow.getName(), 1)
				|| hrmsLeaveApprovalWorkflowRepository.existsBySupervisordesignationidAndActive(
						hrmsLeaveApprovalWorkflow.getSupervisordesignationid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveApprovalWorkflow);
		} else {
			hrmsLeaveApprovalWorkflow.setActive(1);
			hrmsLeaveApprovalWorkflow.setApproved(1);

			hrmsLeaveApprovalWorkflow.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository.existsByIdAndActive(hrmsLeaveApprovalWorkflow.getSupervisordesignationid(),
					1)) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsLeaveApprovalWorkflowRepository.saveAndFlush(hrmsLeaveApprovalWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveApprovalWorkflow);
			}

		}
	}

	@Override
	public ResponseEntity<LeaveWorkflowResponse> getLeaveWorkFlowById(int id) {
		LeaveWorkflowResponse leaveWorkflowResponse = new LeaveWorkflowResponse();
		if (hrmsLeaveApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveApprovalWorkflow dbm = hrmsLeaveApprovalWorkflowRepository.findByIdAndActive(id, 1);

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
	public ResponseEntity<HrmsLeaveApprovalWorkflow> updateLeaveWorkFlow(
			HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow, int id) {
		if (hrmsLeaveApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			hrmsLeaveApprovalWorkflow.setActive(1);
			hrmsLeaveApprovalWorkflow.setApproved(0);
			hrmsLeaveApprovalWorkflow.setDate_updated(LocalDateTime.now());
			if (hrmsLeaveApprovalWorkflowRepository.findById(id).get().getDate_created() != null) {
				hrmsLeaveApprovalWorkflow
						.setDate_created(hrmsLeaveApprovalWorkflowRepository.findById(id).get().getDate_created());
			}

			if (hrmsLeaveApprovalWorkflowRepository.findById(id).get().getUnique_id() != null) {
				hrmsLeaveApprovalWorkflow
						.setUnique_id(hrmsLeaveApprovalWorkflowRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveApprovalWorkflowRepository.saveAndFlush(hrmsLeaveApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsLeaveApprovalWorkflow);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeaveWorkFlow(int id) {
		if (hrmsLeaveApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow = hrmsLeaveApprovalWorkflowRepository
					.findByIdAndActive(id, 1);
			hrmsLeaveApprovalWorkflow.setActive(0);
			hrmsLeaveApprovalWorkflow.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsLeaveApprovalWorkflowRepository.saveAndFlush(hrmsLeaveApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveWorkflowResponse>> getAllLeaveWorkFlow() {
		List<LeaveWorkflowResponse> leaveApprovalWorkflowlist = new ArrayList<>();

		List<HrmsLeaveApprovalWorkflow> dbms = hrmsLeaveApprovalWorkflowRepository.findByActive(1);

		for (HrmsLeaveApprovalWorkflow dbm : dbms) {

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
