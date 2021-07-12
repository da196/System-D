package com.Hrms.Training.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Training.DTO.TrainingApprovalWorkflowStep;
import com.Hrms.Training.DTO.TrainingApprovalWorkflowStepRedirection;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStep;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStepRedirection;
import com.Hrms.Training.Repository.HrmsTrainingApprovalRedirectionReasonRepository;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowStepRedirectionRepository;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowStepRepository;

@Service
public class HrmsTrainingApprovalWorkflowStepRedirectionServiceImpl
		implements HrmsTrainingApprovalWorkflowStepRedirectionService {

	@Autowired
	private HrmsTrainingApprovalWorkflowStepRedirectionRepository hrmsTrainingApprovalWorkflowStepRedirectionRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsTrainingApprovalWorkflowStepRepository hrmsTrainingApprovalWorkflowStepRepository;

	@Autowired

	private HrmsTrainingApprovalRedirectionReasonRepository hrmsTrainingApprovalRedirectionReasonRepository;

	@Override
	public ResponseEntity<HrmsTrainingApprovalWorkflowStepRedirection> addTrainingApprovalWorkflowStepRedirection(
			HrmsTrainingApprovalWorkflowStepRedirection hrmsTrainingApprovalWorkflowStepRedirection) {

		hrmsTrainingApprovalWorkflowStepRedirection.setActive(1);
		hrmsTrainingApprovalWorkflowStepRedirection.setApproved(0);
		hrmsTrainingApprovalWorkflowStepRedirection.setUnique_id(UUID.randomUUID());

		if (hrmsTrainingApprovalWorkflowStepRedirectionRepository
				.existsByStepidAndActive(hrmsTrainingApprovalWorkflowStepRedirection.getStepid(), 1)) {

			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTrainingApprovalWorkflowStepRedirection);
		} else {

			if (hrmsDesignationRepository
					.existsByIdAndActive(hrmsTrainingApprovalWorkflowStepRedirection.getFromdesignationid(), 1)
					&& hrmsDesignationRepository
							.existsByIdAndActive(hrmsTrainingApprovalWorkflowStepRedirection.getTodesignationid(), 1)
					&& hrmsEmployeeRepository
							.existsByIdAndActive(hrmsTrainingApprovalWorkflowStepRedirection.getFromuserid(), 1)
					&& hrmsEmployeeRepository
							.existsByIdAndActive(hrmsTrainingApprovalWorkflowStepRedirection.getTouserid(), 1)
					&& hrmsTrainingApprovalWorkflowStepRepository
							.existsByIdAndActive(hrmsTrainingApprovalWorkflowStepRedirection.getStepid(), 1)
					&& hrmsTrainingApprovalRedirectionReasonRepository.existsByIdAndActive(
							hrmsTrainingApprovalWorkflowStepRedirection.getRiderectionreasonid(), 1)) {

				return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingApprovalWorkflowStepRedirectionRepository
						.saveAndFlush(hrmsTrainingApprovalWorkflowStepRedirection));

			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
						.body(hrmsTrainingApprovalWorkflowStepRedirection);
			}
		}
	}

	@Override
	public ResponseEntity<TrainingApprovalWorkflowStepRedirection> getTrainingApprovalWorkflowStepRedirection(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		TrainingApprovalWorkflowStepRedirection trainingApprovalWorkflowStepRedirection = new TrainingApprovalWorkflowStepRedirection();

		if (hrmsTrainingApprovalWorkflowStepRedirectionRepository.existsByIdAndActive(id, 1)) {

			HrmsTrainingApprovalWorkflowStepRedirection dbm = hrmsTrainingApprovalWorkflowStepRedirectionRepository
					.findByIdAndActive(id, 1);

			trainingApprovalWorkflowStepRedirection.setActive(dbm.getActive());
			trainingApprovalWorkflowStepRedirection.setApproved(dbm.getApproved());
			if (dbm.getDateredirected() != null) {
				trainingApprovalWorkflowStepRedirection
						.setDateredirected(simpleDateFormat.format(dbm.getDateredirected()));
			}
			trainingApprovalWorkflowStepRedirection.setDescription(dbm.getDescription());
			if (dbm.getFromdesignationid() != 0 && hrmsDesignationRepository.existsById(dbm.getFromdesignationid())) {
				trainingApprovalWorkflowStepRedirection.setFromdesignation(
						hrmsDesignationRepository.findById(dbm.getFromdesignationid()).get().getName());
			}
			trainingApprovalWorkflowStepRedirection.setFromdesignationid(dbm.getFromdesignationid());

			if (dbm.getFromuserid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getFromuserid(), 1)) {

				StringBuilder fromuser = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getFromuserid()).get();

				fromuser.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fromuser.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				fromuser.append(" " + hrmsEmployee.getLastName().trim());

				trainingApprovalWorkflowStepRedirection.setFromuser(fromuser.toString());

			}

			trainingApprovalWorkflowStepRedirection.setFromuserid(dbm.getFromuserid());
			trainingApprovalWorkflowStepRedirection.setId(dbm.getId());
			if (dbm.getRiderectionreasonid() != 0
					&& hrmsTrainingApprovalRedirectionReasonRepository.existsById(dbm.getRiderectionreasonid())) {
				trainingApprovalWorkflowStepRedirection
						.setRiderectionreason(hrmsTrainingApprovalRedirectionReasonRepository
								.findById(dbm.getRiderectionreasonid()).get().getName());
			}
			trainingApprovalWorkflowStepRedirection.setRiderectionreasonid(dbm.getRiderectionreasonid());
			trainingApprovalWorkflowStepRedirection.setStepid(dbm.getStepid());
			if (dbm.getTodesignationid() != 0 && hrmsDesignationRepository.existsById(dbm.getTodesignationid())) {
				trainingApprovalWorkflowStepRedirection
						.setTodesignation(hrmsDesignationRepository.findById(dbm.getTodesignationid()).get().getName());
			}
			trainingApprovalWorkflowStepRedirection.setTodesignationid(dbm.getTodesignationid());

			if (dbm.getTouserid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getTouserid(), 1)) {

				StringBuilder touser = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getTouserid()).get();

				touser.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					touser.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				touser.append(" " + hrmsEmployee.getLastName().trim());

				trainingApprovalWorkflowStepRedirection.setTouser(touser.toString());

			}

			trainingApprovalWorkflowStepRedirection.setTouserid(dbm.getTouserid());

			HrmsTrainingApprovalWorkflowStep dbm1 = hrmsTrainingApprovalWorkflowStepRepository
					.findByIdAndActive(dbm.getStepid(), 1);

			TrainingApprovalWorkflowStep trainingApprovalWorkflowStep = new TrainingApprovalWorkflowStep();

			trainingApprovalWorkflowStep.setActive(dbm1.getActive());
			trainingApprovalWorkflowStep.setApproved(dbm1.getApproved());
			trainingApprovalWorkflowStep.setApproverdesignationid(dbm1.getApproverdesignationid());
			if (dbm1.getApproverdesignationid() != 0
					&& hrmsDesignationRepository.existsById(dbm1.getApproverdesignationid())) {
				trainingApprovalWorkflowStep.setApproverdesignation(
						hrmsDesignationRepository.findById(dbm1.getApproverdesignationid()).get().getName());
			}
			trainingApprovalWorkflowStep.setApproverdesignationnextid(dbm1.getApproverdesignationnextid());
			if (dbm1.getApproverdesignationnextid() != 0
					&& hrmsDesignationRepository.existsById(dbm1.getApproverdesignationnextid())) {
				trainingApprovalWorkflowStep.setApproverdesignationnext(
						hrmsDesignationRepository.findById(dbm1.getApproverdesignationnextid()).get().getName());
			}
			trainingApprovalWorkflowStep.setApproverdesignationprevid(dbm1.getApproverdesignationprevid());
			if (dbm1.getApproverdesignationprevid() != 0
					&& hrmsDesignationRepository.existsById(dbm1.getApproverdesignationprevid())) {
				trainingApprovalWorkflowStep.setApproverdesignationprev(
						hrmsDesignationRepository.findById(dbm1.getApproverdesignationprevid()).get().getName());
			}
			trainingApprovalWorkflowStep.setDescription(dbm1.getDescription());
			trainingApprovalWorkflowStep.setId(dbm1.getId());
			trainingApprovalWorkflowStep.setStepnumber(dbm1.getStepnumber());

			trainingApprovalWorkflowStepRedirection.setTrainingApprovalWorkflowStep(trainingApprovalWorkflowStep);

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflowStepRedirection);
	}

	@Override
	public ResponseEntity<List<TrainingApprovalWorkflowStepRedirection>> getAllTrainingApprovalWorkflowStepRedirection() {

		List<TrainingApprovalWorkflowStepRedirection> trainingApprovalWorkflowStepRedirectionlist = new ArrayList<>();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<HrmsTrainingApprovalWorkflowStepRedirection> dbms = hrmsTrainingApprovalWorkflowStepRedirectionRepository
				.findByActive(1);

		for (HrmsTrainingApprovalWorkflowStepRedirection dbm : dbms) {
			TrainingApprovalWorkflowStepRedirection trainingApprovalWorkflowStepRedirection = new TrainingApprovalWorkflowStepRedirection();

			trainingApprovalWorkflowStepRedirection.setActive(dbm.getActive());
			trainingApprovalWorkflowStepRedirection.setApproved(dbm.getApproved());
			if (dbm.getDateredirected() != null) {
				trainingApprovalWorkflowStepRedirection
						.setDateredirected(simpleDateFormat.format(dbm.getDateredirected()));
			}
			trainingApprovalWorkflowStepRedirection.setDescription(dbm.getDescription());
			if (dbm.getFromdesignationid() != 0 && hrmsDesignationRepository.existsById(dbm.getFromdesignationid())) {
				trainingApprovalWorkflowStepRedirection.setFromdesignation(
						hrmsDesignationRepository.findById(dbm.getFromdesignationid()).get().getName());
			}
			trainingApprovalWorkflowStepRedirection.setFromdesignationid(dbm.getFromdesignationid());

			if (dbm.getFromuserid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getFromuserid(), 1)) {

				StringBuilder fromuser = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getFromuserid()).get();

				fromuser.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fromuser.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				fromuser.append(" " + hrmsEmployee.getLastName().trim());

				trainingApprovalWorkflowStepRedirection.setFromuser(fromuser.toString());

			}

			trainingApprovalWorkflowStepRedirection.setFromuserid(dbm.getFromuserid());
			trainingApprovalWorkflowStepRedirection.setId(dbm.getId());
			if (dbm.getRiderectionreasonid() != 0
					&& hrmsTrainingApprovalRedirectionReasonRepository.existsById(dbm.getRiderectionreasonid())) {
				trainingApprovalWorkflowStepRedirection
						.setRiderectionreason(hrmsTrainingApprovalRedirectionReasonRepository
								.findById(dbm.getRiderectionreasonid()).get().getName());
			}
			trainingApprovalWorkflowStepRedirection.setRiderectionreasonid(dbm.getRiderectionreasonid());
			trainingApprovalWorkflowStepRedirection.setStepid(dbm.getStepid());
			if (dbm.getTodesignationid() != 0 && hrmsDesignationRepository.existsById(dbm.getTodesignationid())) {
				trainingApprovalWorkflowStepRedirection
						.setTodesignation(hrmsDesignationRepository.findById(dbm.getTodesignationid()).get().getName());
			}
			trainingApprovalWorkflowStepRedirection.setTodesignationid(dbm.getTodesignationid());

			if (dbm.getTouserid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getTouserid(), 1)) {

				StringBuilder touser = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getTouserid()).get();

				touser.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					touser.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				touser.append(" " + hrmsEmployee.getLastName().trim());

				trainingApprovalWorkflowStepRedirection.setTouser(touser.toString());

			}

			trainingApprovalWorkflowStepRedirection.setTouserid(dbm.getTouserid());

			HrmsTrainingApprovalWorkflowStep dbm1 = hrmsTrainingApprovalWorkflowStepRepository
					.findByIdAndActive(dbm.getStepid(), 1);

			TrainingApprovalWorkflowStep trainingApprovalWorkflowStep = new TrainingApprovalWorkflowStep();

			trainingApprovalWorkflowStep.setActive(dbm1.getActive());
			trainingApprovalWorkflowStep.setApproved(dbm1.getApproved());
			trainingApprovalWorkflowStep.setApproverdesignationid(dbm1.getApproverdesignationid());
			if (dbm1.getApproverdesignationid() != 0
					&& hrmsDesignationRepository.existsById(dbm1.getApproverdesignationid())) {
				trainingApprovalWorkflowStep.setApproverdesignation(
						hrmsDesignationRepository.findById(dbm1.getApproverdesignationid()).get().getName());
			}
			trainingApprovalWorkflowStep.setApproverdesignationnextid(dbm1.getApproverdesignationnextid());
			if (dbm1.getApproverdesignationnextid() != 0
					&& hrmsDesignationRepository.existsById(dbm1.getApproverdesignationnextid())) {
				trainingApprovalWorkflowStep.setApproverdesignationnext(
						hrmsDesignationRepository.findById(dbm1.getApproverdesignationnextid()).get().getName());
			}
			trainingApprovalWorkflowStep.setApproverdesignationprevid(dbm1.getApproverdesignationprevid());
			if (dbm1.getApproverdesignationprevid() != 0
					&& hrmsDesignationRepository.existsById(dbm1.getApproverdesignationprevid())) {
				trainingApprovalWorkflowStep.setApproverdesignationprev(
						hrmsDesignationRepository.findById(dbm1.getApproverdesignationprevid()).get().getName());
			}
			trainingApprovalWorkflowStep.setDescription(dbm1.getDescription());
			trainingApprovalWorkflowStep.setId(dbm1.getId());
			trainingApprovalWorkflowStep.setStepnumber(dbm1.getStepnumber());

			trainingApprovalWorkflowStepRedirection.setTrainingApprovalWorkflowStep(trainingApprovalWorkflowStep);

			trainingApprovalWorkflowStepRedirectionlist.add(trainingApprovalWorkflowStepRedirection);

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalWorkflowStepRedirectionlist);
	}

}
