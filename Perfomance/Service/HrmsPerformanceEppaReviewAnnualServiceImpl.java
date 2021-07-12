package com.Hrms.Perfomance.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Perfomance.DTO.PerformanceEppaReviewAnnualResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaReviewAnnualRepository;

@Service
public class HrmsPerformanceEppaReviewAnnualServiceImpl implements HrmsPerformanceEppaReviewAnnualService {
	@Autowired
	private HrmsPerformanceEppaReviewAnnualRepository hrmsPerformanceEppaReviewAnnualRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsPerformanceEppaRepository hrmsPerformanceEppaRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsPerformanceEppaObjectiveRepository hrmsPerformanceEppaObjectiveRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> seilfAnnualReview(
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual) {
		if (hrmsPerformanceEppaReviewAnnualRepository.existsByEppaidAndObjectiveidAndActive(
				hrmsPerformanceEppaReviewAnnual.getEppaid(), hrmsPerformanceEppaReviewAnnual.getObjectiveid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEppaReviewAnnual);
		} else {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaReviewAnnual.getEppaid(), 1)
					&& hrmsPerformanceEppaObjectiveRepository
							.existsByIdAndActive(hrmsPerformanceEppaReviewAnnual.getObjectiveid(), 1)) {
				hrmsPerformanceEppaReviewAnnual.setActive(1);
				hrmsPerformanceEppaReviewAnnual.setApproved(0);
				hrmsPerformanceEppaReviewAnnual.setUnique_id(UUID.randomUUID());
				hrmsPerformanceEppaReviewAnnual.setDatereviewed(new Date());

				// set supervisor id and supervisor designation id
				// get employee id
				// set supervisor id and supervisor designation id

				HrmsPerformanceEppa hrmsPerformanceEppa = hrmsPerformanceEppaRepository
						.findByIdAndActive(hrmsPerformanceEppaReviewAnnual.getEppaid(), 1);
				if (hrmsPerformanceEppa.getEmployeeid() != 0
						&& hrmsEmployeeRepository.existsById(hrmsPerformanceEppa.getEmployeeid())) {
					HrmsEmployee dbm = hrmsEmployeeRepository.findByIdAndActive(hrmsPerformanceEppa.getEmployeeid(), 1);

					int supervisordesignationid = dbm.getSupervisordesignationid();

					int supervisorid = hrmsEmployeeRepository
							.findFirstByDesignationIdAndIssupervisorAndActive(supervisordesignationid, 1, 1).getId();

					hrmsPerformanceEppaReviewAnnual.setSupervisordesignationid(supervisordesignationid);

					hrmsPerformanceEppaReviewAnnual.setSupervisorid(supervisorid);

				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaReviewAnnualRepository.saveAndFlush(hrmsPerformanceEppaReviewAnnual));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaReviewAnnual);
			}

		}

	}

	@Override
	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> supervisorAnnualReview(
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual, int id) {

		if (hrmsPerformanceEppaReviewAnnualRepository.existsByIdAndActive(id, 1)) {

			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaReviewAnnual.getEppaid(), 1)
					&& hrmsPerformanceEppaObjectiveRepository
							.existsByIdAndActive(hrmsPerformanceEppaReviewAnnual.getObjectiveid(), 1)) {
				hrmsPerformanceEppaReviewAnnual.setActive(1);
				hrmsPerformanceEppaReviewAnnual.setApproved(0);
				hrmsPerformanceEppaReviewAnnual.setDate_updated(LocalDateTime.now());

				// set supervisor id and supervisor designation id
				// get employee id
				// set supervisor id and supervisor designation id
				HrmsPerformanceEppaReviewAnnual dbm = hrmsPerformanceEppaReviewAnnualRepository.findById(id).get();
				if (dbm != null) {
					hrmsPerformanceEppaReviewAnnual.setSupervisordesignationid(dbm.getSupervisordesignationid());
					hrmsPerformanceEppaReviewAnnual.setSupervisorid(dbm.getSupervisorid());
					hrmsPerformanceEppaReviewAnnual.setRatingappraisee(dbm.getRatingappraisee());
					if (dbm.getDatereviewed() != null) {
						hrmsPerformanceEppaReviewAnnual.setDatereviewed(dbm.getDatereviewed());
					}

					if (dbm.getDate_created() != null) {
						hrmsPerformanceEppaReviewAnnual.setDate_created(dbm.getDate_created());
					}
					if (dbm.getUnique_id() != null) {
						hrmsPerformanceEppaReviewAnnual.setUnique_id(dbm.getUnique_id());
					}
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaReviewAnnualRepository.saveAndFlush(hrmsPerformanceEppaReviewAnnual));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaReviewAnnual);
			}

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsPerformanceEppaReviewAnnual);
		}

	}

	@Override
	public ResponseEntity<PerformanceEppaReviewAnnualResponse> getAnnualReviewById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		PerformanceEppaReviewAnnualResponse performanceEppaReviewAnnualResponse = new PerformanceEppaReviewAnnualResponse();

		if (hrmsPerformanceEppaReviewAnnualRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEppaReviewAnnual dbm = hrmsPerformanceEppaReviewAnnualRepository.findByIdAndActive(id, 1);

			performanceEppaReviewAnnualResponse.setActive(dbm.getActive());
			performanceEppaReviewAnnualResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewAnnualResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewAnnualResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewAnnualResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}

			performanceEppaReviewAnnualResponse.setId(dbm.getId());
			performanceEppaReviewAnnualResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewAnnualResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}

			performanceEppaReviewAnnualResponse.setOutputactual(dbm.getOutputactual());
			performanceEppaReviewAnnualResponse.setOutputtarget(dbm.getOutputtarget());

			if (dbm.getRatingagreed() != null) {
				performanceEppaReviewAnnualResponse.setRatingagreed(dbm.getRatingagreed());
			}
			if (dbm.getRatingappraisee() != null) {
				performanceEppaReviewAnnualResponse.setRatingappraisee(dbm.getRatingappraisee());
			}
			if (dbm.getRatingsupervisor() != null) {
				performanceEppaReviewAnnualResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			}
			if (dbm.getScore() != null) {
				performanceEppaReviewAnnualResponse.setScore(dbm.getScore());
			}
			performanceEppaReviewAnnualResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewAnnualResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewAnnualResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewAnnualResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewAnnualResponse.setTargets(dbm.getTargets());
			if (dbm.getWeighting() != null) {
				performanceEppaReviewAnnualResponse.setWeighting(dbm.getWeighting());
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewAnnualResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEppaReviewAnnual> updateAnnualReview(
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual, int id) {
		if (hrmsPerformanceEppaReviewAnnualRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaReviewAnnual.getEppaid(), 1)
					&& hrmsPerformanceEppaObjectiveRepository
							.existsByIdAndActive(hrmsPerformanceEppaReviewAnnual.getObjectiveid(), 1)) {
				hrmsPerformanceEppaReviewAnnual.setActive(1);
				hrmsPerformanceEppaReviewAnnual.setApproved(0);
				hrmsPerformanceEppaReviewAnnual.setDate_updated(LocalDateTime.now());

				// get supervisor id and supervisor designation id from db

				HrmsPerformanceEppaReviewAnnual dbm1 = hrmsPerformanceEppaReviewAnnualRepository.findById(id).get();

				hrmsPerformanceEppaReviewAnnual.setSupervisordesignationid(dbm1.getSupervisordesignationid());

				hrmsPerformanceEppaReviewAnnual.setSupervisorid(dbm1.getSupervisorid());

				if (hrmsPerformanceEppaReviewAnnualRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEppaReviewAnnual.setDate_created(
							hrmsPerformanceEppaReviewAnnualRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEppaReviewAnnualRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEppaReviewAnnual
							.setUnique_id(hrmsPerformanceEppaReviewAnnualRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaReviewAnnualRepository.saveAndFlush(hrmsPerformanceEppaReviewAnnual));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaReviewAnnual);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteAnnualReview(int id) {
		if (hrmsPerformanceEppaReviewAnnualRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEppaReviewAnnual hrmsPerformanceEppaReviewAnnual = hrmsPerformanceEppaReviewAnnualRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceEppaReviewAnnual.setActive(0);
			hrmsPerformanceEppaReviewAnnual.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEppaReviewAnnualRepository.saveAndFlush(hrmsPerformanceEppaReviewAnnual));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReview() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PerformanceEppaReviewAnnualResponse> performanceEppaReviewAnnualResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaReviewAnnual> dbms = hrmsPerformanceEppaReviewAnnualRepository.findByActive(1);
		for (HrmsPerformanceEppaReviewAnnual dbm : dbms) {

			PerformanceEppaReviewAnnualResponse performanceEppaReviewAnnualResponse = new PerformanceEppaReviewAnnualResponse();

			performanceEppaReviewAnnualResponse.setActive(dbm.getActive());
			performanceEppaReviewAnnualResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewAnnualResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewAnnualResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewAnnualResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}

			performanceEppaReviewAnnualResponse.setId(dbm.getId());
			performanceEppaReviewAnnualResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewAnnualResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}

			performanceEppaReviewAnnualResponse.setOutputactual(dbm.getOutputactual());
			performanceEppaReviewAnnualResponse.setOutputtarget(dbm.getOutputtarget());

			if (dbm.getRatingagreed() != null) {
				performanceEppaReviewAnnualResponse.setRatingagreed(dbm.getRatingagreed());
			}
			if (dbm.getRatingappraisee() != null) {
				performanceEppaReviewAnnualResponse.setRatingappraisee(dbm.getRatingappraisee());
			}
			if (dbm.getRatingsupervisor() != null) {
				performanceEppaReviewAnnualResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			}
			if (dbm.getScore() != null) {
				performanceEppaReviewAnnualResponse.setScore(dbm.getScore());
			}
			performanceEppaReviewAnnualResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewAnnualResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewAnnualResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewAnnualResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewAnnualResponse.setTargets(dbm.getTargets());
			if (dbm.getWeighting() != null) {
				performanceEppaReviewAnnualResponse.setWeighting(dbm.getWeighting());
			}

			performanceEppaReviewAnnualResponselist.add(performanceEppaReviewAnnualResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewAnnualResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewByEppaId(int eppaid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PerformanceEppaReviewAnnualResponse> performanceEppaReviewAnnualResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaReviewAnnual> dbms = hrmsPerformanceEppaReviewAnnualRepository
				.findByEppaidAndActive(eppaid, 1);
		for (HrmsPerformanceEppaReviewAnnual dbm : dbms) {

			PerformanceEppaReviewAnnualResponse performanceEppaReviewAnnualResponse = new PerformanceEppaReviewAnnualResponse();

			performanceEppaReviewAnnualResponse.setActive(dbm.getActive());
			performanceEppaReviewAnnualResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewAnnualResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewAnnualResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewAnnualResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}

			performanceEppaReviewAnnualResponse.setId(dbm.getId());
			performanceEppaReviewAnnualResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewAnnualResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}

			performanceEppaReviewAnnualResponse.setOutputactual(dbm.getOutputactual());
			performanceEppaReviewAnnualResponse.setOutputtarget(dbm.getOutputtarget());

			if (dbm.getRatingagreed() != null) {
				performanceEppaReviewAnnualResponse.setRatingagreed(dbm.getRatingagreed());
			}
			if (dbm.getRatingappraisee() != null) {
				performanceEppaReviewAnnualResponse.setRatingappraisee(dbm.getRatingappraisee());
			}
			if (dbm.getRatingsupervisor() != null) {
				performanceEppaReviewAnnualResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			}
			if (dbm.getScore() != null) {
				performanceEppaReviewAnnualResponse.setScore(dbm.getScore());
			}
			performanceEppaReviewAnnualResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewAnnualResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewAnnualResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewAnnualResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewAnnualResponse.setTargets(dbm.getTargets());
			if (dbm.getWeighting() != null) {
				performanceEppaReviewAnnualResponse.setWeighting(dbm.getWeighting());
			}

			performanceEppaReviewAnnualResponselist.add(performanceEppaReviewAnnualResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewAnnualResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewByObjectiveId(int objectiveid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PerformanceEppaReviewAnnualResponse> performanceEppaReviewAnnualResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaReviewAnnual> dbms = hrmsPerformanceEppaReviewAnnualRepository
				.findByObjectiveidAndActive(objectiveid, 1);
		for (HrmsPerformanceEppaReviewAnnual dbm : dbms) {

			PerformanceEppaReviewAnnualResponse performanceEppaReviewAnnualResponse = new PerformanceEppaReviewAnnualResponse();

			performanceEppaReviewAnnualResponse.setActive(dbm.getActive());
			performanceEppaReviewAnnualResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewAnnualResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewAnnualResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewAnnualResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}

			performanceEppaReviewAnnualResponse.setId(dbm.getId());
			performanceEppaReviewAnnualResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewAnnualResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}

			performanceEppaReviewAnnualResponse.setOutputactual(dbm.getOutputactual());
			performanceEppaReviewAnnualResponse.setOutputtarget(dbm.getOutputtarget());

			if (dbm.getRatingagreed() != null) {
				performanceEppaReviewAnnualResponse.setRatingagreed(dbm.getRatingagreed());
			}
			if (dbm.getRatingappraisee() != null) {
				performanceEppaReviewAnnualResponse.setRatingappraisee(dbm.getRatingappraisee());
			}
			if (dbm.getRatingsupervisor() != null) {
				performanceEppaReviewAnnualResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			}
			if (dbm.getScore() != null) {
				performanceEppaReviewAnnualResponse.setScore(dbm.getScore());
			}
			performanceEppaReviewAnnualResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewAnnualResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewAnnualResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewAnnualResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewAnnualResponse.setTargets(dbm.getTargets());
			if (dbm.getWeighting() != null) {
				performanceEppaReviewAnnualResponse.setWeighting(dbm.getWeighting());
			}

			performanceEppaReviewAnnualResponselist.add(performanceEppaReviewAnnualResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewAnnualResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewBySupervisorId(
			int supervisorid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PerformanceEppaReviewAnnualResponse> performanceEppaReviewAnnualResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaReviewAnnual> dbms = hrmsPerformanceEppaReviewAnnualRepository
				.findBySupervisoridAndActive(supervisorid, 1);
		for (HrmsPerformanceEppaReviewAnnual dbm : dbms) {

			PerformanceEppaReviewAnnualResponse performanceEppaReviewAnnualResponse = new PerformanceEppaReviewAnnualResponse();

			performanceEppaReviewAnnualResponse.setActive(dbm.getActive());
			performanceEppaReviewAnnualResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewAnnualResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewAnnualResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewAnnualResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}

			performanceEppaReviewAnnualResponse.setId(dbm.getId());
			performanceEppaReviewAnnualResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewAnnualResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}

			performanceEppaReviewAnnualResponse.setOutputactual(dbm.getOutputactual());
			performanceEppaReviewAnnualResponse.setOutputtarget(dbm.getOutputtarget());

			if (dbm.getRatingagreed() != null) {
				performanceEppaReviewAnnualResponse.setRatingagreed(dbm.getRatingagreed());
			}
			if (dbm.getRatingappraisee() != null) {
				performanceEppaReviewAnnualResponse.setRatingappraisee(dbm.getRatingappraisee());
			}
			if (dbm.getRatingsupervisor() != null) {
				performanceEppaReviewAnnualResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			}
			if (dbm.getScore() != null) {
				performanceEppaReviewAnnualResponse.setScore(dbm.getScore());
			}
			performanceEppaReviewAnnualResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewAnnualResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewAnnualResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewAnnualResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewAnnualResponse.setTargets(dbm.getTargets());
			if (dbm.getWeighting() != null) {
				performanceEppaReviewAnnualResponse.setWeighting(dbm.getWeighting());
			}

			performanceEppaReviewAnnualResponselist.add(performanceEppaReviewAnnualResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewAnnualResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewAnnualResponse>> getAllAnnualReviewBySupervisorDesignationId(
			int supervisordesignationid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PerformanceEppaReviewAnnualResponse> performanceEppaReviewAnnualResponselist = new ArrayList<>();
		List<HrmsPerformanceEppaReviewAnnual> dbms = hrmsPerformanceEppaReviewAnnualRepository
				.findBySupervisordesignationidAndActive(supervisordesignationid, 1);
		for (HrmsPerformanceEppaReviewAnnual dbm : dbms) {

			PerformanceEppaReviewAnnualResponse performanceEppaReviewAnnualResponse = new PerformanceEppaReviewAnnualResponse();

			performanceEppaReviewAnnualResponse.setActive(dbm.getActive());
			performanceEppaReviewAnnualResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewAnnualResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewAnnualResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewAnnualResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}

			performanceEppaReviewAnnualResponse.setId(dbm.getId());
			performanceEppaReviewAnnualResponse.setObjectiveid(dbm.getObjectiveid());

			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewAnnualResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}

			performanceEppaReviewAnnualResponse.setOutputactual(dbm.getOutputactual());
			performanceEppaReviewAnnualResponse.setOutputtarget(dbm.getOutputtarget());

			if (dbm.getRatingagreed() != null) {
				performanceEppaReviewAnnualResponse.setRatingagreed(dbm.getRatingagreed());
			}
			if (dbm.getRatingappraisee() != null) {
				performanceEppaReviewAnnualResponse.setRatingappraisee(dbm.getRatingappraisee());
			}
			if (dbm.getRatingsupervisor() != null) {
				performanceEppaReviewAnnualResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			}
			if (dbm.getScore() != null) {
				performanceEppaReviewAnnualResponse.setScore(dbm.getScore());
			}
			performanceEppaReviewAnnualResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewAnnualResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewAnnualResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewAnnualResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewAnnualResponse.setTargets(dbm.getTargets());
			if (dbm.getWeighting() != null) {
				performanceEppaReviewAnnualResponse.setWeighting(dbm.getWeighting());
			}

			performanceEppaReviewAnnualResponselist.add(performanceEppaReviewAnnualResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewAnnualResponselist);
	}

}
