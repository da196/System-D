package com.Hrms.Perfomance.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import com.Hrms.Perfomance.DTO.PerformanceEppaReviewMidYearResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewMidYear;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaReviewMidYearRepository;

@Service
public class HrmsPerformanceEppaReviewMidYearServiceImpl implements HrmsPerformanceEppaReviewMidYearService {

	@Autowired
	private HrmsPerformanceEppaReviewMidYearRepository hrmsPerformanceEppaReviewMidYearRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsPerformanceEppaRepository hrmsPerformanceEppaRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsPerformanceEppaObjectiveRepository hrmsPerformanceEppaObjectiveRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEppaReviewMidYear> addPerformanceEppaReviewMidYear(
			HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear) {
		if (hrmsPerformanceEppaReviewMidYearRepository.existsByEppaidAndObjectiveidAndSupervisoridAndActive(
				hrmsPerformanceEppaReviewMidYear.getEppaid(), hrmsPerformanceEppaReviewMidYear.getObjectiveid(),
				hrmsPerformanceEppaReviewMidYear.getSupervisorid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEppaReviewMidYear);
		} else {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaReviewMidYear.getEppaid(), 1)
					&& hrmsPerformanceEppaObjectiveRepository
							.existsByIdAndActive(hrmsPerformanceEppaReviewMidYear.getObjectiveid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPerformanceEppaReviewMidYear.getSupervisorid(),
							1)) {
				hrmsPerformanceEppaReviewMidYear.setActive(1);
				hrmsPerformanceEppaReviewMidYear.setApproved(0);
				hrmsPerformanceEppaReviewMidYear.setUnique_id(UUID.randomUUID());

				// set supervisor id and supervisor designation id

				HrmsEmployee dbm = hrmsEmployeeRepository
						.findByIdAndActive(hrmsPerformanceEppaReviewMidYear.getSupervisorid(), 1);

				int supervisordesignationid = dbm.getSupervisordesignationid();

				hrmsPerformanceEppaReviewMidYear.setSupervisordesignationid(supervisordesignationid);

				hrmsPerformanceEppaReviewMidYear.setSupervisorid(hrmsPerformanceEppaReviewMidYear.getSupervisorid());

				return ResponseEntity.status(HttpStatus.OK).body(
						hrmsPerformanceEppaReviewMidYearRepository.saveAndFlush(hrmsPerformanceEppaReviewMidYear));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaReviewMidYear);
			}

		}

	}

	@Override
	public ResponseEntity<PerformanceEppaReviewMidYearResponse> getPerformanceEppaReviewMidYearById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		PerformanceEppaReviewMidYearResponse performanceEppaReviewMidYearResponse = new PerformanceEppaReviewMidYearResponse();

		if (hrmsPerformanceEppaReviewMidYearRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEppaReviewMidYear dbm = hrmsPerformanceEppaReviewMidYearRepository.findByIdAndActive(id, 1);

			performanceEppaReviewMidYearResponse.setActive(dbm.getActive());
			performanceEppaReviewMidYearResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewMidYearResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewMidYearResponse.setFactorsaffectingperformance(dbm.getFactorsaffectingperformance());
			performanceEppaReviewMidYearResponse.setId(dbm.getId());
			performanceEppaReviewMidYearResponse.setProgresstowardstarget(dbm.getProgresstowardstarget());
			performanceEppaReviewMidYearResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1))
				performanceEppaReviewMidYearResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			performanceEppaReviewMidYearResponse.setObjectiveid(dbm.getObjectiveid());
			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1))
				performanceEppaReviewMidYearResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));

			performanceEppaReviewMidYearResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewMidYearResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewMidYearResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewMidYearResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewMidYearResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEppaReviewMidYear> updatePerformanceEppaReviewMidYear(
			HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear, int id) {
		if (hrmsPerformanceEppaReviewMidYearRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEppaReviewMidYear.getEppaid(), 1)
					&& hrmsPerformanceEppaObjectiveRepository
							.existsByIdAndActive(hrmsPerformanceEppaReviewMidYear.getObjectiveid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPerformanceEppaReviewMidYear.getSupervisorid(),
							1)) {
				hrmsPerformanceEppaReviewMidYear.setActive(1);
				hrmsPerformanceEppaReviewMidYear.setApproved(0);
				hrmsPerformanceEppaReviewMidYear.setDate_updated(LocalDateTime.now());

				// set supervisor id and supervisor designation id

				HrmsEmployee dbm = hrmsEmployeeRepository
						.findByIdAndActive(hrmsPerformanceEppaReviewMidYear.getSupervisorid(), 1);

				int supervisordesignationid = dbm.getSupervisordesignationid();

				hrmsPerformanceEppaReviewMidYear.setSupervisordesignationid(supervisordesignationid);

				hrmsPerformanceEppaReviewMidYear.setSupervisorid(hrmsPerformanceEppaReviewMidYear.getSupervisorid());

				if (hrmsPerformanceEppaReviewMidYearRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEppaReviewMidYear.setDate_created(
							hrmsPerformanceEppaReviewMidYearRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEppaReviewMidYearRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEppaReviewMidYear
							.setUnique_id(hrmsPerformanceEppaReviewMidYearRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(
						hrmsPerformanceEppaReviewMidYearRepository.saveAndFlush(hrmsPerformanceEppaReviewMidYear));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppaReviewMidYear);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEppaReviewMidYear(int id) {
		if (hrmsPerformanceEppaReviewMidYearRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEppaReviewMidYear hrmsPerformanceEppaReviewMidYear = hrmsPerformanceEppaReviewMidYearRepository
					.findByIdAndActive(id, 1);
			hrmsPerformanceEppaReviewMidYear.setActive(0);
			hrmsPerformanceEppaReviewMidYear.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEppaReviewMidYearRepository.saveAndFlush(hrmsPerformanceEppaReviewMidYear));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYear() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<PerformanceEppaReviewMidYearResponse> performanceEppaReviewMidYearResponselist = new ArrayList<>();

		List<HrmsPerformanceEppaReviewMidYear> dbms = hrmsPerformanceEppaReviewMidYearRepository.findByActive(1);

		for (HrmsPerformanceEppaReviewMidYear dbm : dbms) {

			PerformanceEppaReviewMidYearResponse performanceEppaReviewMidYearResponse = new PerformanceEppaReviewMidYearResponse();

			performanceEppaReviewMidYearResponse.setActive(dbm.getActive());
			performanceEppaReviewMidYearResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewMidYearResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewMidYearResponse.setFactorsaffectingperformance(dbm.getFactorsaffectingperformance());
			performanceEppaReviewMidYearResponse.setId(dbm.getId());
			performanceEppaReviewMidYearResponse.setProgresstowardstarget(dbm.getProgresstowardstarget());
			performanceEppaReviewMidYearResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {
				performanceEppaReviewMidYearResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			}
			performanceEppaReviewMidYearResponse.setObjectiveid(dbm.getObjectiveid());
			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1)) {
				performanceEppaReviewMidYearResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));
			}
			performanceEppaReviewMidYearResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewMidYearResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewMidYearResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewMidYearResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewMidYearResponselist.add(performanceEppaReviewMidYearResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewMidYearResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearByEppaId(
			int eppaid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<Integer> approveds = new ArrayList<>();

		approveds.add(1);
		approveds.add(0);

		List<PerformanceEppaReviewMidYearResponse> performanceEppaReviewMidYearResponselist = new ArrayList<>();

		List<HrmsPerformanceEppaReviewMidYear> dbms = hrmsPerformanceEppaReviewMidYearRepository
				.findByEppaidAndActiveAndApprovedIn(eppaid, 1, approveds);

		for (HrmsPerformanceEppaReviewMidYear dbm : dbms) {

			PerformanceEppaReviewMidYearResponse performanceEppaReviewMidYearResponse = new PerformanceEppaReviewMidYearResponse();

			performanceEppaReviewMidYearResponse.setActive(dbm.getActive());
			performanceEppaReviewMidYearResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewMidYearResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewMidYearResponse.setFactorsaffectingperformance(dbm.getFactorsaffectingperformance());
			performanceEppaReviewMidYearResponse.setId(dbm.getId());
			performanceEppaReviewMidYearResponse.setProgresstowardstarget(dbm.getProgresstowardstarget());
			performanceEppaReviewMidYearResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1))
				performanceEppaReviewMidYearResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			performanceEppaReviewMidYearResponse.setObjectiveid(dbm.getObjectiveid());
			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1))
				performanceEppaReviewMidYearResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));

			performanceEppaReviewMidYearResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewMidYearResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewMidYearResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewMidYearResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewMidYearResponselist.add(performanceEppaReviewMidYearResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewMidYearResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearByObjectiveId(
			int objectiveid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<PerformanceEppaReviewMidYearResponse> performanceEppaReviewMidYearResponselist = new ArrayList<>();

		List<HrmsPerformanceEppaReviewMidYear> dbms = hrmsPerformanceEppaReviewMidYearRepository
				.findByObjectiveidAndActive(objectiveid, 1);

		for (HrmsPerformanceEppaReviewMidYear dbm : dbms) {

			PerformanceEppaReviewMidYearResponse performanceEppaReviewMidYearResponse = new PerformanceEppaReviewMidYearResponse();

			performanceEppaReviewMidYearResponse.setActive(dbm.getActive());
			performanceEppaReviewMidYearResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewMidYearResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewMidYearResponse.setFactorsaffectingperformance(dbm.getFactorsaffectingperformance());
			performanceEppaReviewMidYearResponse.setId(dbm.getId());
			performanceEppaReviewMidYearResponse.setProgresstowardstarget(dbm.getProgresstowardstarget());
			performanceEppaReviewMidYearResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1))
				performanceEppaReviewMidYearResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			performanceEppaReviewMidYearResponse.setObjectiveid(dbm.getObjectiveid());
			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1))
				performanceEppaReviewMidYearResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));

			performanceEppaReviewMidYearResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewMidYearResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewMidYearResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewMidYearResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewMidYearResponselist.add(performanceEppaReviewMidYearResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewMidYearResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaReviewMidYearResponse>> getAllPerformanceEppaReviewMidYearBySupervisorDesigantionId(
			int supervisordesignationid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<PerformanceEppaReviewMidYearResponse> performanceEppaReviewMidYearResponselist = new ArrayList<>();

		List<HrmsPerformanceEppaReviewMidYear> dbms = hrmsPerformanceEppaReviewMidYearRepository
				.findBySupervisordesignationidAndActive(supervisordesignationid, 1);

		for (HrmsPerformanceEppaReviewMidYear dbm : dbms) {

			PerformanceEppaReviewMidYearResponse performanceEppaReviewMidYearResponse = new PerformanceEppaReviewMidYearResponse();

			performanceEppaReviewMidYearResponse.setActive(dbm.getActive());
			performanceEppaReviewMidYearResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEppaReviewMidYearResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEppaReviewMidYearResponse.setFactorsaffectingperformance(dbm.getFactorsaffectingperformance());
			performanceEppaReviewMidYearResponse.setId(dbm.getId());
			performanceEppaReviewMidYearResponse.setProgresstowardstarget(dbm.getProgresstowardstarget());
			performanceEppaReviewMidYearResponse.setEppaid(dbm.getEppaid());
			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1))
				performanceEppaReviewMidYearResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findByIdAndActive(dbm.getEppaid(), 1));
			performanceEppaReviewMidYearResponse.setObjectiveid(dbm.getObjectiveid());
			if (dbm.getObjectiveid() != 0
					&& hrmsPerformanceEppaObjectiveRepository.existsByIdAndActive(dbm.getObjectiveid(), 1))
				performanceEppaReviewMidYearResponse.setPerformanceEppaObjective(
						hrmsPerformanceEppaObjectiveRepository.findByIdAndActive(dbm.getObjectiveid(), 1));

			performanceEppaReviewMidYearResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisor = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisor.append(" " + hrmsEmployee.getLastName().trim());
				performanceEppaReviewMidYearResponse.setSupervisor(supervisor.toString());
			}

			performanceEppaReviewMidYearResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				performanceEppaReviewMidYearResponse.setSupervisordesignation(
						hrmsDesignationRepository.findById(dbm.getSupervisordesignationid()).get().getName());
			}

			performanceEppaReviewMidYearResponselist.add(performanceEppaReviewMidYearResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaReviewMidYearResponselist);
	}

}
