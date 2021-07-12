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
import com.Hrms.Training.DTO.TrainingApprovalWorkflowStep;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflow;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStep;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowRepository;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowStepRepository;

@Service
public class HrmsTrainingApprovalWorkflowStepServiceImpl implements HrmsTrainingApprovalWorkflowStepService {

	@Autowired

	private HrmsTrainingApprovalWorkflowStepRepository hrmsTrainingApprovalWorkflowStepRepository;

	@Autowired

	private HrmsTrainingApprovalWorkflowRepository hrmsTrainingApprovalWorkflowRepository;

	@Autowired

	private HrmsDesignationRepository hrmsDesignationRepository;

	@Override
	public ResponseEntity<List<TrainingApprovalWorkflowStep>> getAllTrainingApprovalWorkflowStep() {

		List<TrainingApprovalWorkflowStep> trainingApprovalWorkflowSteplist = new ArrayList<>();

		List<HrmsTrainingApprovalWorkflowStep> dbms = hrmsTrainingApprovalWorkflowStepRepository.findByActive(1);

		for (HrmsTrainingApprovalWorkflowStep dbm : dbms) {

			if (hrmsTrainingApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsTrainingApprovalWorkflow Workflow = hrmsTrainingApprovalWorkflowRepository
						.findByIdAndActive(dbm.getWorkflowid(), 1);

				TrainingApprovalWorkflowStep trainingApprovalWorkflowStep = new TrainingApprovalWorkflowStep();

				trainingApprovalWorkflowStep.setActive(dbm.getActive());
				trainingApprovalWorkflowStep.setApproved(dbm.getApproved());

				trainingApprovalWorkflowStep.setWorkflowid(Workflow.getId());
				trainingApprovalWorkflowStep.setWorkflow(Workflow.getName());
				trainingApprovalWorkflowStep.setApproverdesignationid(dbm.getApproverdesignationid());
				if (dbm.getApproverdesignationid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationid())) {
					trainingApprovalWorkflowStep.setApproverdesignation(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationid()).get().getName());
				}
				trainingApprovalWorkflowStep.setApproverdesignationnextid(dbm.getApproverdesignationnextid());
				if (dbm.getApproverdesignationnextid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationnextid())) {
					trainingApprovalWorkflowStep.setApproverdesignationnext(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationnextid()).get().getName());
				}
				trainingApprovalWorkflowStep.setApproverdesignationprevid(dbm.getApproverdesignationprevid());
				if (dbm.getApproverdesignationprevid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationprevid())) {
					trainingApprovalWorkflowStep.setApproverdesignationprev(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationprevid()).get().getName());
				}
				trainingApprovalWorkflowStep.setDescription(dbm.getDescription());
				trainingApprovalWorkflowStep.setId(dbm.getId());
				trainingApprovalWorkflowStep.setStepnumber(dbm.getStepnumber());

				trainingApprovalWorkflowSteplist.add(trainingApprovalWorkflowStep);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflowSteplist);
	}

	@Override
	public ResponseEntity<List<TrainingApprovalWorkflowStep>> getAllTrainingApprovalWorkflowStepByWorkflowid(
			int workflowid) {
		List<TrainingApprovalWorkflowStep> trainingApprovalWorkflowSteplist = new ArrayList<>();

		List<HrmsTrainingApprovalWorkflowStep> dbms = hrmsTrainingApprovalWorkflowStepRepository
				.findByWorkflowidAndActive(workflowid, 1);

		for (HrmsTrainingApprovalWorkflowStep dbm : dbms) {

			if (hrmsTrainingApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {

				HrmsTrainingApprovalWorkflow Workflow = hrmsTrainingApprovalWorkflowRepository
						.findByIdAndActive(dbm.getWorkflowid(), 1);

				TrainingApprovalWorkflowStep trainingApprovalWorkflowStep = new TrainingApprovalWorkflowStep();

				trainingApprovalWorkflowStep.setActive(dbm.getActive());
				trainingApprovalWorkflowStep.setApproved(dbm.getApproved());

				trainingApprovalWorkflowStep.setWorkflowid(Workflow.getId());
				trainingApprovalWorkflowStep.setWorkflow(Workflow.getName());
				trainingApprovalWorkflowStep.setApproverdesignationid(dbm.getApproverdesignationid());
				if (dbm.getApproverdesignationid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationid())) {
					trainingApprovalWorkflowStep.setApproverdesignation(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationid()).get().getName());
				}
				trainingApprovalWorkflowStep.setApproverdesignationnextid(dbm.getApproverdesignationnextid());
				if (dbm.getApproverdesignationnextid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationnextid())) {
					trainingApprovalWorkflowStep.setApproverdesignationnext(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationnextid()).get().getName());
				}
				trainingApprovalWorkflowStep.setApproverdesignationprevid(dbm.getApproverdesignationprevid());
				if (dbm.getApproverdesignationprevid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationprevid())) {
					trainingApprovalWorkflowStep.setApproverdesignationprev(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationprevid()).get().getName());
				}
				trainingApprovalWorkflowStep.setDescription(dbm.getDescription());
				trainingApprovalWorkflowStep.setId(dbm.getId());
				trainingApprovalWorkflowStep.setStepnumber(dbm.getStepnumber());

				trainingApprovalWorkflowSteplist.add(trainingApprovalWorkflowStep);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflowSteplist);
	}

	@Override
	public ResponseEntity<HrmsTrainingApprovalWorkflowStep> addTrainingWorkflowStep(
			HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep) {
		if (hrmsTrainingApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
				hrmsTrainingApprovalWorkflowStep.getWorkflowid(),
				hrmsTrainingApprovalWorkflowStep.getApproverdesignationid(), 1)

		) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingApprovalWorkflowStep);
		} else {
			hrmsTrainingApprovalWorkflowStep.setActive(1);
			hrmsTrainingApprovalWorkflowStep.setApproved(1);

			hrmsTrainingApprovalWorkflowStep.setUnique_id(UUID.randomUUID());
			if (hrmsDesignationRepository
					.existsByIdAndActive(hrmsTrainingApprovalWorkflowStep.getApproverdesignationid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK).body(
						hrmsTrainingApprovalWorkflowStepRepository.saveAndFlush(hrmsTrainingApprovalWorkflowStep));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsTrainingApprovalWorkflowStep);
			}

		}
	}

	@Override
	public ResponseEntity<HrmsTrainingApprovalWorkflowStep> updateTrainingWorkflowStep(int id,
			HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep) {
		if (hrmsTrainingApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			hrmsTrainingApprovalWorkflowStep.setActive(1);
			hrmsTrainingApprovalWorkflowStep.setApproved(0);
			hrmsTrainingApprovalWorkflowStep.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingApprovalWorkflowStepRepository.findById(id).get().getDate_created() != null) {
				hrmsTrainingApprovalWorkflowStep.setDate_created(
						hrmsTrainingApprovalWorkflowStepRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingApprovalWorkflowStepRepository.findById(id).get().getUnique_id() != null) {
				hrmsTrainingApprovalWorkflowStep
						.setUnique_id(hrmsTrainingApprovalWorkflowStepRepository.findById(id).get().getUnique_id());
			}

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingApprovalWorkflowStepRepository.saveAndFlush(hrmsTrainingApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsTrainingApprovalWorkflowStep);
		}
	}

	@Override
	public ResponseEntity<TrainingApprovalWorkflowStep> getTrainingApprovalWorkflowStepById(int id) {

		TrainingApprovalWorkflowStep trainingApprovalWorkflowStep = new TrainingApprovalWorkflowStep();

		if (hrmsTrainingApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingApprovalWorkflowStep dbm = hrmsTrainingApprovalWorkflowStepRepository.findByIdAndActive(id, 1);

			if (hrmsTrainingApprovalWorkflowRepository.existsByIdAndActive(dbm.getWorkflowid(), 1)) {
				HrmsTrainingApprovalWorkflow Workflow = hrmsTrainingApprovalWorkflowRepository
						.findByIdAndActive(dbm.getWorkflowid(), 1);

				trainingApprovalWorkflowStep.setActive(dbm.getActive());
				trainingApprovalWorkflowStep.setApproved(dbm.getApproved());

				trainingApprovalWorkflowStep.setWorkflowid(Workflow.getId());
				trainingApprovalWorkflowStep.setWorkflow(Workflow.getName());
				trainingApprovalWorkflowStep.setApproverdesignationid(dbm.getApproverdesignationid());
				if (dbm.getApproverdesignationid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationid())) {
					trainingApprovalWorkflowStep.setApproverdesignation(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationid()).get().getName());
				}
				trainingApprovalWorkflowStep.setApproverdesignationnextid(dbm.getApproverdesignationnextid());
				if (dbm.getApproverdesignationnextid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationnextid())) {
					trainingApprovalWorkflowStep.setApproverdesignationnext(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationnextid()).get().getName());
				}
				trainingApprovalWorkflowStep.setApproverdesignationprevid(dbm.getApproverdesignationprevid());
				if (dbm.getApproverdesignationprevid() != 0
						&& hrmsDesignationRepository.existsById(dbm.getApproverdesignationprevid())) {
					trainingApprovalWorkflowStep.setApproverdesignationprev(
							hrmsDesignationRepository.findById(dbm.getApproverdesignationprevid()).get().getName());
				}
				trainingApprovalWorkflowStep.setDescription(dbm.getDescription());
				trainingApprovalWorkflowStep.setId(dbm.getId());
				trainingApprovalWorkflowStep.setStepnumber(dbm.getStepnumber());

			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflowStep);
	}

	@Override
	public ResponseEntity<?> deleteTrainingWorkflowStep(int id) {
		if (hrmsTrainingApprovalWorkflowStepRepository.existsByIdAndActive(id, 1)) {
			HrmsTrainingApprovalWorkflowStep hrmsTrainingApprovalWorkflowStep = hrmsTrainingApprovalWorkflowStepRepository
					.findByIdAndActive(id, 1);
			hrmsTrainingApprovalWorkflowStep.setActive(0);
			hrmsTrainingApprovalWorkflowStep.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsTrainingApprovalWorkflowStepRepository.saveAndFlush(hrmsTrainingApprovalWorkflowStep));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
