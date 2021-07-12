package com.Hrms.Training.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Training.DTO.TrainingApprovalWorkflow;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflow;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowRepository;

@Service
public class HrmsTrainingApprovalWorkflowServiceImpl implements HrmsTrainingApprovalWorkflowService {

	@Autowired

	private HrmsTrainingApprovalWorkflowRepository hrmsTrainingApprovalWorkflowRepository;

	@Autowired

	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired

	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Override
	public ResponseEntity<List<TrainingApprovalWorkflow>> getAllTrainingApprovalWorkflow() {

		List<TrainingApprovalWorkflow> trainingApprovalWorkflowlist = new ArrayList<>();

		List<HrmsTrainingApprovalWorkflow> dbms = hrmsTrainingApprovalWorkflowRepository.findByActive(1);

		for (HrmsTrainingApprovalWorkflow dbm : dbms) {

			TrainingApprovalWorkflow trainingApprovalWorkflow = new TrainingApprovalWorkflow();

			trainingApprovalWorkflow.setActive(dbm.getActive());
			trainingApprovalWorkflow.setApproved(dbm.getApproved());
			trainingApprovalWorkflow.setCode(dbm.getCode());
			trainingApprovalWorkflow.setDescription(dbm.getDescription());
			trainingApprovalWorkflow.setId(dbm.getId());
			trainingApprovalWorkflow.setName(dbm.getName());
			trainingApprovalWorkflow.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsById(dbm.getSupervisordesignationid())) {
				trainingApprovalWorkflow.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			trainingApprovalWorkflowlist.add(trainingApprovalWorkflow);

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflowlist);
	}

	@Override
	public ResponseEntity<HrmsTrainingApprovalWorkflow> addTrainingApprovalWorkflow(
			HrmsTrainingApprovalWorkflow trainingApprovalWorkflow) {
		if (hrmsTrainingApprovalWorkflowRepository.existsByNameAndActive(trainingApprovalWorkflow.getName(), 1)
				|| hrmsTrainingApprovalWorkflowRepository.existsBySupervisordesignationidAndActive(
						trainingApprovalWorkflow.getSupervisordesignationid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(trainingApprovalWorkflow);
		} else {
			trainingApprovalWorkflow.setActive(1);
			trainingApprovalWorkflow.setApproved(1);

			trainingApprovalWorkflow.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository.existsByIdAndActive(trainingApprovalWorkflow.getSupervisordesignationid(),
					1)) {

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsTrainingApprovalWorkflowRepository.saveAndFlush(trainingApprovalWorkflow));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(trainingApprovalWorkflow);
			}

		}
	}

	@Override
	public ResponseEntity<TrainingApprovalWorkflow> getTrainingWorkflowById(int id) {

		TrainingApprovalWorkflow trainingApprovalWorkflow = new TrainingApprovalWorkflow();
		if (hrmsTrainingApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingApprovalWorkflow dbm = hrmsTrainingApprovalWorkflowRepository.findByIdAndActive(id, 1);

			trainingApprovalWorkflow.setActive(dbm.getActive());
			trainingApprovalWorkflow.setApproved(dbm.getApproved());
			trainingApprovalWorkflow.setCode(dbm.getCode());
			trainingApprovalWorkflow.setDescription(dbm.getDescription());
			trainingApprovalWorkflow.setId(dbm.getId());
			trainingApprovalWorkflow.setName(dbm.getName());
			trainingApprovalWorkflow.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsById(dbm.getSupervisordesignationid())) {
				trainingApprovalWorkflow.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflow);
	}

	@Override
	public ResponseEntity<HrmsTrainingApprovalWorkflow> updateTrainingWorkflow(
			HrmsTrainingApprovalWorkflow hrmsTrainingApprovalWorkflow, int id) {
		if (hrmsTrainingApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingApprovalWorkflow.setActive(1);
			hrmsTrainingApprovalWorkflow.setApproved(0);
			hrmsTrainingApprovalWorkflow.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingApprovalWorkflowRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingApprovalWorkflow
						.setDate_created(hrmsTrainingApprovalWorkflowRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingApprovalWorkflowRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingApprovalWorkflow
						.setUnique_id(hrmsTrainingApprovalWorkflowRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingApprovalWorkflowRepository.saveAndFlush(hrmsTrainingApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsTrainingApprovalWorkflow);
		}
	}

	@Override
	public ResponseEntity<?> deleteTrainingWorkflow(int id) {
		if (hrmsTrainingApprovalWorkflowRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingApprovalWorkflow hrmsTrainingApprovalWorkflow = hrmsTrainingApprovalWorkflowRepository
					.findByIdAndActive(id, 1);
			hrmsTrainingApprovalWorkflow.setActive(0);
			hrmsTrainingApprovalWorkflow.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingApprovalWorkflowRepository.saveAndFlush(hrmsTrainingApprovalWorkflow));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
