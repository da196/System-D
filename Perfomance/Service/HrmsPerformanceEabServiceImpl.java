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
import com.Hrms.Perfomance.DTO.PerformanceEabFactorOprasResponse;
import com.Hrms.Perfomance.DTO.PerformanceEabQualityOprasResponse;
import com.Hrms.Perfomance.DTO.PerformanceEabResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabFactorRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabQualityRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaRepository;

@Service
public class HrmsPerformanceEabServiceImpl implements HrmsPerformanceEabService {

	@Autowired
	private HrmsPerformanceEabRepository hrmsPerformanceEabRepository;

	@Autowired
	private HrmsPerformanceEppaRepository hrmsPerformanceEppaRepository;

	@Autowired
	private HrmsPerformanceEabQualityRepository hrmsPerformanceEabQualityRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsPerformanceEabFactorRepository hrmsPerformanceEabFactorRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEab> addPerformanceEab(HrmsPerformanceEab hrmsPerformanceEab) {

		if (hrmsPerformanceEabRepository.existsByEppaidAndQualityidAndEmployeeidAndActive(
				hrmsPerformanceEab.getEppaid(), hrmsPerformanceEab.getQualityid(), hrmsPerformanceEab.getEmployeeid(),
				1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEab);
		} else {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEab.getEppaid(), 1)
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(hrmsPerformanceEab.getQualityid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPerformanceEab.getEmployeeid(), 1)) {
				hrmsPerformanceEab.setActive(1);
				hrmsPerformanceEab.setApproved(0);
				hrmsPerformanceEab.setUnique_id(UUID.randomUUID());

				hrmsPerformanceEab.setDatereviewed(new Date());

				// set supervisor id and supervisor designation id

				HrmsEmployee dbm = hrmsEmployeeRepository.findByIdAndActive(hrmsPerformanceEab.getEmployeeid(), 1);

				if (dbm.getSupervisordesignationid() != 0 && hrmsEmployeeRepository
						.existsByDesignationIdAndIssupervisorAndActive(dbm.getSupervisordesignationid(), 1, 1)) {

					int supervisorid = hrmsEmployeeRepository
							.findFirstByDesignationIdAndIssupervisorAndActive(dbm.getSupervisordesignationid(), 1, 1)
							.getId();

					int supervisordesignationid = dbm.getSupervisordesignationid();

					hrmsPerformanceEab.setSupervisordesignationid(supervisordesignationid);

					hrmsPerformanceEab.setSupervisorid(supervisorid);

				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEabRepository.saveAndFlush(hrmsPerformanceEab));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEab);
			}

		}

	}

	@Override
	public ResponseEntity<PerformanceEabResponse> getPerformanceEabById(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		PerformanceEabResponse performanceEabResponse = new PerformanceEabResponse();

		if (hrmsPerformanceEabRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEab dbm = hrmsPerformanceEabRepository.findById(id).get();

			performanceEabResponse.setActive(dbm.getActive());
			performanceEabResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEabResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEabResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEabResponse.setId(dbm.getId());
			performanceEabResponse.setEppaid(dbm.getEppaid());

			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {

				performanceEabResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findById(dbm.getEppaid()).get());
			}
			performanceEabResponse.setQualityid(dbm.getQualityid());
			if (dbm.getQualityid() != 0
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(dbm.getQualityid(), 1)) {
				performanceEabResponse.setPerformanceEabQuality(
						hrmsPerformanceEabQualityRepository.findByIdAndActive(dbm.getQualityid(), 1));
			}
			performanceEabResponse.setRatingagreed(dbm.getRatingagreed());
			performanceEabResponse.setRatingappraisee(dbm.getRatingappraisee());
			performanceEabResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			performanceEabResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());
			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				String supervisordesignation = hrmsDesignationRepository.findById(dbm.getSupervisordesignationid())
						.get().getName();

				performanceEabResponse.setSupervisordesignation(supervisordesignation);
			}
			performanceEabResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisorFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisorFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisorFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisorFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setSupervisorFullName(supervisorFullName.toString());
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEab> updatePerformanceEab(HrmsPerformanceEab hrmsPerformanceEab, int id) {
		if (hrmsPerformanceEabRepository.existsByIdAndActive(id, 1)) {
			if (hrmsPerformanceEppaRepository.existsByIdAndActive(hrmsPerformanceEab.getEppaid(), 1)
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(hrmsPerformanceEab.getQualityid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsPerformanceEab.getEmployeeid(), 1)) {
				hrmsPerformanceEab.setActive(1);
				hrmsPerformanceEab.setApproved(0);
				hrmsPerformanceEab.setDate_updated(LocalDateTime.now());

				// set supervisor id and supervisor designation id

				HrmsEmployee dbm = hrmsEmployeeRepository.findByIdAndActive(hrmsPerformanceEab.getEmployeeid(), 1);

				if (dbm.getSupervisordesignationid() != 0 && hrmsEmployeeRepository
						.existsByDesignationIdAndIssupervisorAndActive(dbm.getSupervisordesignationid(), 1, 1)) {

					int supervisorid = hrmsEmployeeRepository
							.findFirstByDesignationIdAndIssupervisorAndActive(dbm.getSupervisordesignationid(), 1, 1)
							.getId();

					int supervisordesignationid = dbm.getSupervisordesignationid();

					hrmsPerformanceEab.setSupervisordesignationid(supervisordesignationid);

					hrmsPerformanceEab.setSupervisorid(supervisorid);

				}

				if (hrmsPerformanceEabRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEab
							.setDate_created(hrmsPerformanceEabRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEabRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEab.setUnique_id(hrmsPerformanceEabRepository.findById(id).get().getUnique_id());
				}
				if (hrmsPerformanceEabRepository.findById(id).get().getDatereviewed() != null) {
					hrmsPerformanceEab
							.setDatereviewed(hrmsPerformanceEabRepository.findById(id).get().getDatereviewed());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEabRepository.saveAndFlush(hrmsPerformanceEab));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEab);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEab(int id) {
		if (hrmsPerformanceEabRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEab hrmsPerformanceEab = hrmsPerformanceEabRepository.findByIdAndActive(id, 1);
			hrmsPerformanceEab.setActive(0);
			hrmsPerformanceEab.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEabRepository.saveAndFlush(hrmsPerformanceEab));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEab() {

		List<PerformanceEabResponse> performanceEabResponselist = new ArrayList<>();

		List<HrmsPerformanceEab> dbms = hrmsPerformanceEabRepository.findByActive(1);

		for (HrmsPerformanceEab dbm : dbms) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			PerformanceEabResponse performanceEabResponse = new PerformanceEabResponse();

			performanceEabResponse.setActive(dbm.getActive());
			performanceEabResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEabResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEabResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEabResponse.setId(dbm.getId());
			performanceEabResponse.setEppaid(dbm.getEppaid());

			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {

				performanceEabResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findById(dbm.getEppaid()).get());
			}
			performanceEabResponse.setQualityid(dbm.getQualityid());
			if (dbm.getQualityid() != 0
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(dbm.getQualityid(), 1)) {
				performanceEabResponse.setPerformanceEabQuality(
						hrmsPerformanceEabQualityRepository.findByIdAndActive(dbm.getQualityid(), 1));
			}
			performanceEabResponse.setRatingagreed(dbm.getRatingagreed());
			performanceEabResponse.setRatingappraisee(dbm.getRatingappraisee());
			performanceEabResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			performanceEabResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());
			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				String supervisordesignation = hrmsDesignationRepository.findById(dbm.getSupervisordesignationid())
						.get().getName();

				performanceEabResponse.setSupervisordesignation(supervisordesignation);
			}
			performanceEabResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisorFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisorFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisorFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisorFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setSupervisorFullName(supervisorFullName.toString());
			}

			performanceEabResponselist.add(performanceEabResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByQualityidId(int qualityid) {

		List<PerformanceEabResponse> performanceEabResponselist = new ArrayList<>();

		List<HrmsPerformanceEab> dbms = hrmsPerformanceEabRepository.findByQualityidAndActive(qualityid, 1);

		for (HrmsPerformanceEab dbm : dbms) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			PerformanceEabResponse performanceEabResponse = new PerformanceEabResponse();

			performanceEabResponse.setActive(dbm.getActive());
			performanceEabResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEabResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEabResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEabResponse.setId(dbm.getId());
			performanceEabResponse.setEppaid(dbm.getEppaid());

			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {

				performanceEabResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findById(dbm.getEppaid()).get());
			}
			performanceEabResponse.setQualityid(dbm.getQualityid());
			if (dbm.getQualityid() != 0
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(dbm.getQualityid(), 1)) {
				performanceEabResponse.setPerformanceEabQuality(
						hrmsPerformanceEabQualityRepository.findByIdAndActive(dbm.getQualityid(), 1));
			}
			performanceEabResponse.setRatingagreed(dbm.getRatingagreed());
			performanceEabResponse.setRatingappraisee(dbm.getRatingappraisee());
			performanceEabResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			performanceEabResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());
			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				String supervisordesignation = hrmsDesignationRepository.findById(dbm.getSupervisordesignationid())
						.get().getName();

				performanceEabResponse.setSupervisordesignation(supervisordesignation);
			}
			performanceEabResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisorFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisorFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisorFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisorFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setSupervisorFullName(supervisorFullName.toString());
			}

			performanceEabResponselist.add(performanceEabResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByEppaId(int eppaid) {
		List<PerformanceEabResponse> performanceEabResponselist = new ArrayList<>();

		List<HrmsPerformanceEab> dbms = hrmsPerformanceEabRepository.findByEppaidAndActive(eppaid, 1);

		for (HrmsPerformanceEab dbm : dbms) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			PerformanceEabResponse performanceEabResponse = new PerformanceEabResponse();

			performanceEabResponse.setActive(dbm.getActive());
			performanceEabResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEabResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEabResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEabResponse.setId(dbm.getId());
			performanceEabResponse.setEppaid(dbm.getEppaid());

			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {

				performanceEabResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findById(dbm.getEppaid()).get());
			}
			performanceEabResponse.setQualityid(dbm.getQualityid());
			if (dbm.getQualityid() != 0
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(dbm.getQualityid(), 1)) {
				performanceEabResponse.setPerformanceEabQuality(
						hrmsPerformanceEabQualityRepository.findByIdAndActive(dbm.getQualityid(), 1));
			}
			performanceEabResponse.setRatingagreed(dbm.getRatingagreed());
			performanceEabResponse.setRatingappraisee(dbm.getRatingappraisee());
			performanceEabResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			performanceEabResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());
			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				String supervisordesignation = hrmsDesignationRepository.findById(dbm.getSupervisordesignationid())
						.get().getName();

				performanceEabResponse.setSupervisordesignation(supervisordesignation);
			}
			performanceEabResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisorFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisorFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisorFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisorFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setSupervisorFullName(supervisorFullName.toString());
			}

			performanceEabResponselist.add(performanceEabResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEabResponse>> getAllPerformanceEabByEmployeeId(int employeeid) {
		List<PerformanceEabResponse> performanceEabResponselist = new ArrayList<>();

		List<HrmsPerformanceEab> dbms = hrmsPerformanceEabRepository.findByEmployeeidAndActive(employeeid, 1);

		for (HrmsPerformanceEab dbm : dbms) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			PerformanceEabResponse performanceEabResponse = new PerformanceEabResponse();

			performanceEabResponse.setActive(dbm.getActive());
			performanceEabResponse.setApproved(dbm.getApproved());
			if (dbm.getDatereviewed() != null) {
				performanceEabResponse.setDatereviewed(simpleDateFormat.format(dbm.getDatereviewed()));
			}
			performanceEabResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEabResponse.setId(dbm.getId());
			performanceEabResponse.setEppaid(dbm.getEppaid());

			if (dbm.getEppaid() != 0 && hrmsPerformanceEppaRepository.existsByIdAndActive(dbm.getEppaid(), 1)) {

				performanceEabResponse
						.setPerformanceEppa(hrmsPerformanceEppaRepository.findById(dbm.getEppaid()).get());
			}
			performanceEabResponse.setQualityid(dbm.getQualityid());
			if (dbm.getQualityid() != 0
					&& hrmsPerformanceEabQualityRepository.existsByIdAndActive(dbm.getQualityid(), 1)) {
				performanceEabResponse.setPerformanceEabQuality(
						hrmsPerformanceEabQualityRepository.findByIdAndActive(dbm.getQualityid(), 1));
			}
			performanceEabResponse.setRatingagreed(dbm.getRatingagreed());
			performanceEabResponse.setRatingappraisee(dbm.getRatingappraisee());
			performanceEabResponse.setRatingsupervisor(dbm.getRatingsupervisor());
			performanceEabResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());
			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				String supervisordesignation = hrmsDesignationRepository.findById(dbm.getSupervisordesignationid())
						.get().getName();

				performanceEabResponse.setSupervisordesignation(supervisordesignation);
			}
			performanceEabResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisorid() != 0 && hrmsEmployeeRepository.existsById(dbm.getSupervisorid())) {
				StringBuilder supervisorFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisorFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisorFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				supervisorFullName.append(" " + hrmsEmployee.getLastName().trim());
				performanceEabResponse.setSupervisorFullName(supervisorFullName.toString());
			}

			performanceEabResponselist.add(performanceEabResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEabFactorOprasResponse>> getAllPerformanceEabByEppaIdV2(int eppaid) {

		int totalAttribute = 0;
		Double sumQualityAtributeApraisee = 0.0;
		Double sumQualityAtributeSupervisor = 0.0;

		Double sumQualityAtributeAgreed = 0.0;

		List<PerformanceEabFactorOprasResponse> performanceEabFactorOprasResponselist = new ArrayList<>();

		List<HrmsPerformanceEabFactor> dbms5 = hrmsPerformanceEabFactorRepository.findByActive(1);
		for (HrmsPerformanceEabFactor dbm5 : dbms5) {
			PerformanceEabFactorOprasResponse performanceEabFactorOprasResponse = new PerformanceEabFactorOprasResponse();
			performanceEabFactorOprasResponse.setActive(dbm5.getActive());
			performanceEabFactorOprasResponse.setApproved(dbm5.getApproved());
			performanceEabFactorOprasResponse.setDescription(dbm5.getDescription());
			performanceEabFactorOprasResponse.setId(dbm5.getId());
			performanceEabFactorOprasResponse.setName(dbm5.getName());

			List<PerformanceEabQualityOprasResponse> performanceEabQualityOprasResponselist = new ArrayList<>();

			List<HrmsPerformanceEabQuality> dbms6 = hrmsPerformanceEabQualityRepository
					.findByFactoridAndActive(dbm5.getId(), 1);

			totalAttribute = totalAttribute
					+ hrmsPerformanceEabQualityRepository.countByFactoridAndActive(dbm5.getId(), 1);

			for (HrmsPerformanceEabQuality dbm6 : dbms6) {
				PerformanceEabQualityOprasResponse performanceEabQualityOprasResponse = new PerformanceEabQualityOprasResponse();

				performanceEabQualityOprasResponse.setActive(dbm6.getActive());
				performanceEabQualityOprasResponse.setApproved(dbm6.getApproved());
				performanceEabQualityOprasResponse.setDescription(dbm6.getDescription());
				performanceEabQualityOprasResponse.setFactorid(dbm6.getFactorid());
				performanceEabQualityOprasResponse.setId(dbm6.getId());
				performanceEabQualityOprasResponse.setName(dbm6.getName());

				HrmsPerformanceEab performanceEab = new HrmsPerformanceEab();
				if (hrmsPerformanceEabRepository.existsByEppaidAndQualityidAndActive(eppaid, dbm6.getId(), 1)) {

					performanceEab = hrmsPerformanceEabRepository.findByEppaidAndQualityidAndActive(eppaid,
							dbm6.getId(), 1);

					sumQualityAtributeApraisee = sumQualityAtributeApraisee + performanceEab.getRatingappraisee();

					sumQualityAtributeSupervisor = sumQualityAtributeSupervisor + performanceEab.getRatingsupervisor();

					sumQualityAtributeAgreed = sumQualityAtributeAgreed + performanceEab.getRatingagreed();

				}

				performanceEabQualityOprasResponse.setPerformanceEab(performanceEab);

				performanceEabQualityOprasResponselist.add(performanceEabQualityOprasResponse);

			}

			performanceEabFactorOprasResponse
					.setPerformanceEabQualityOprasResponselist(performanceEabQualityOprasResponselist);

			performanceEabFactorOprasResponselist.add(performanceEabFactorOprasResponse);
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEabFactorOprasResponselist);
	}

}
