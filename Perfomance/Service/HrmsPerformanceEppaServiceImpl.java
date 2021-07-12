package com.Hrms.Perfomance.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Perfomance.DTO.PerformanceEppaExtraResponse;
import com.Hrms.Perfomance.DTO.PerformanceEppaPost;
import com.Hrms.Perfomance.DTO.PerformanceEppaResponse;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;
import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabQualityRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaReviewAnnualRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceFinancialYearRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceObjectiveOutcomeActivityOutputRepository;

@Service
public class HrmsPerformanceEppaServiceImpl implements HrmsPerformanceEppaService {
	@Autowired
	private HrmsPerformanceEppaRepository hrmsPerformanceEppaRepository;

	@Autowired
	private HrmsPerformanceObjectiveOutcomeActivityOutputRepository hrmsPerformanceObjectiveOutcomeActivityOutputRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsPerformanceEppaObjectiveRepository hrmsPerformanceEppaObjectiveRepository;

	@Autowired
	private HrmsPerformanceFinancialYearRepository hrmsPerformanceFinancialYearRepository;

	@Autowired
	private HrmsPerformanceEabQualityRepository hrmsPerformanceEabQualityRepository;

	@Autowired
	private HrmsPerformanceEabRepository hrmsPerformanceEabRepository;

	@Autowired

	private HrmsPerformanceEppaReviewAnnualRepository hrmsPerformanceEppaReviewAnnualRepository;

	@Override
	public ResponseEntity<HrmsPerformanceEppa> addPerformanceEppa(HrmsPerformanceEppa hrmsPerformanceEppa) {
		if (hrmsPerformanceEppaRepository.existsByEmployeeidAndFinancialyearidAndActive(
				hrmsPerformanceEppa.getEmployeeid(), hrmsPerformanceEppa.getFinancialyearid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPerformanceEppa);
		} else {
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsPerformanceEppa.getEmployeeid(), 1)
					&& hrmsPerformanceFinancialYearRepository
							.existsByIdAndActive(hrmsPerformanceEppa.getFinancialyearid(), 1)
			/*
			 * && hrmsPerformanceObjectiveOutcomeActivityOutputRepository
			 * .existsByIdAndActive(hrmsPerformanceEppa.getOutputid(), 1)
			 */) {
				hrmsPerformanceEppa.setActive(1);
				hrmsPerformanceEppa.setApproved(0);
				hrmsPerformanceEppa.setUnique_id(UUID.randomUUID());

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaRepository.saveAndFlush(hrmsPerformanceEppa));
			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppa);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceEppaResponse> getPerformanceEppaById(int id) {

		PerformanceEppaResponse performanceEppaResponse = new PerformanceEppaResponse();

		if (hrmsPerformanceEppaRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEppa dbm = hrmsPerformanceEppaRepository.findById(id).get();

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}
			/*
			 * performanceEppaResponse.setOutputid(dbm.getOutputid());
			 * 
			 * if (dbm.getOutputid() != 0 &&
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.
			 * getOutputid())) {
			 * performanceEppaResponse.setPerformanceObjectiveOutcomeActivityOutput(
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(dbm.
			 * getOutputid()).get()); }
			 */

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponse);
	}

	@Override
	public ResponseEntity<HrmsPerformanceEppa> updatePerformanceEppa(HrmsPerformanceEppa hrmsPerformanceEppa, int id) {
		if (hrmsPerformanceEppaRepository.existsByIdAndActive(id, 1)) {
			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsPerformanceEppa.getEmployeeid(), 1)
					&& hrmsPerformanceFinancialYearRepository
							.existsByIdAndActive(hrmsPerformanceEppa.getFinancialyearid(), 1)
			/*
			 * && hrmsPerformanceObjectiveOutcomeActivityOutputRepository
			 * .existsByIdAndActive(hrmsPerformanceEppa.getOutputid(), 1)
			 */) {
				hrmsPerformanceEppa.setActive(1);
				hrmsPerformanceEppa.setApproved(0);
				hrmsPerformanceEppa.setDate_updated(LocalDateTime.now());
				if (hrmsPerformanceEppaRepository.findById(id).get().getDate_created() != null) {
					hrmsPerformanceEppa
							.setDate_created(hrmsPerformanceEppaRepository.findById(id).get().getDate_created());
				}

				if (hrmsPerformanceEppaRepository.findById(id).get().getUnique_id() != null) {
					hrmsPerformanceEppa.setUnique_id(hrmsPerformanceEppaRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPerformanceEppaRepository.saveAndFlush(hrmsPerformanceEppa));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPerformanceEppa);
			}
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePerformanceEppa(int id) {
		if (hrmsPerformanceEppaRepository.existsByIdAndActive(id, 1)) {
			HrmsPerformanceEppa hrmsPerformanceEppa = hrmsPerformanceEppaRepository.findByIdAndActive(id, 1);
			hrmsPerformanceEppa.setActive(0);
			hrmsPerformanceEppa.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPerformanceEppaRepository.saveAndFlush(hrmsPerformanceEppa));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppa() {

		List<PerformanceEppaResponse> performanceEppaResponselist = new ArrayList<>();

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository.findByActive(1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaResponse performanceEppaResponse = new PerformanceEppaResponse();

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}
			/*
			 * performanceEppaResponse.setOutputid(dbm.getOutputid());
			 * 
			 * if (dbm.getOutputid() != 0 &&
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.
			 * getOutputid())) {
			 * performanceEppaResponse.setPerformanceObjectiveOutcomeActivityOutput(
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(dbm.
			 * getOutputid()).get()); }
			 */

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByOutputId(int outputid) {
		List<PerformanceEppaResponse> performanceEppaResponselist = new ArrayList<>();

		/*
		 * List<HrmsPerformanceEppa> dbms =
		 * hrmsPerformanceEppaRepository.findByOutputidAndActive(outputid, 1);
		 * 
		 * for (HrmsPerformanceEppa dbm : dbms) {
		 * 
		 * PerformanceEppaResponse performanceEppaResponse = new
		 * PerformanceEppaResponse();
		 * 
		 * performanceEppaResponse.setActive(dbm.getActive());
		 * performanceEppaResponse.setApproved(dbm.getApproved());
		 * performanceEppaResponse.setComments(dbm.getComments());
		 * performanceEppaResponse.setDescription(dbm.getDescription());
		 * performanceEppaResponse.setId(dbm.getId());
		 * performanceEppaResponse.setScore(dbm.getScore());
		 * performanceEppaResponse.setScoreannual(dbm.getScoreannual());
		 * performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
		 * performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());
		 * 
		 * if (dbm.getEmployeeid() != 0 &&
		 * hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
		 * 
		 * StringBuilder employeeFullName = new StringBuilder();
		 * 
		 * HrmsEmployee hrmsEmployee =
		 * hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
		 * 
		 * employeeFullName.append(hrmsEmployee.getFirstName().trim()); if
		 * (hrmsEmployee.getMiddleName() != null) { employeeFullName.append(" " +
		 * hrmsEmployee.getMiddleName().trim()); } employeeFullName.append(" " +
		 * hrmsEmployee.getLastName().trim());
		 * 
		 * performanceEppaResponse.setEmployeeFullName(employeeFullName.toString()); }
		 * performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid()); if
		 * (dbm.getFinancialyearid() != 0 &&
		 * hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid()))
		 * { performanceEppaResponse.setPerformanceFinancialYear(
		 * hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get
		 * ()); }
		 * 
		 * 
		 * performanceEppaResponse.setOutputid(dbm.getOutputid());
		 * 
		 * if (dbm.getOutputid() != 0 &&
		 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.
		 * getOutputid())) {
		 * performanceEppaResponse.setPerformanceObjectiveOutcomeActivityOutput(
		 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(dbm.
		 * getOutputid()).get()); }
		 * 
		 * 
		 * performanceEppaResponselist.add(performanceEppaResponse);
		 * 
		 * }
		 */

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByFinancialYearId(int financialyearid) {
		List<PerformanceEppaResponse> performanceEppaResponselist = new ArrayList<>();

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository.findByFinancialyearidAndActive(financialyearid,
				1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaResponse performanceEppaResponse = new PerformanceEppaResponse();

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}
			/*
			 * performanceEppaResponse.setOutputid(dbm.getOutputid());
			 * 
			 * if (dbm.getOutputid() != 0 &&
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.
			 * getOutputid())) {
			 * performanceEppaResponse.setPerformanceObjectiveOutcomeActivityOutput(
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(dbm.
			 * getOutputid()).get()); }
			 */

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaResponse>> getAllPerformanceEppaByEmployeeIdAndFinancialYearId(
			int employeeid, int financialyearid) {
		List<PerformanceEppaResponse> performanceEppaResponselist = new ArrayList<>();

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository
				.findByEmployeeidAndFinancialyearidAndActive(employeeid, financialyearid, 1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaResponse performanceEppaResponse = new PerformanceEppaResponse();

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}
			/*
			 * performanceEppaResponse.setOutputid(dbm.getOutputid());
			 * 
			 * if (dbm.getOutputid() != 0 &&
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.existsById(dbm.
			 * getOutputid())) {
			 * performanceEppaResponse.setPerformanceObjectiveOutcomeActivityOutput(
			 * hrmsPerformanceObjectiveOutcomeActivityOutputRepository.findById(dbm.
			 * getOutputid()).get()); }
			 */

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<PerformanceEppaPost> addPerformanceEppaV2(PerformanceEppaPost performanceEppaPost) {
		if (hrmsPerformanceEppaRepository.existsByEmployeeidAndFinancialyearidAndActive(
				performanceEppaPost.getPerformanceEppa().getEmployeeid(),
				performanceEppaPost.getPerformanceEppa().getFinancialyearid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(performanceEppaPost);
		} else {
			if (hrmsEmployeeRepository.existsByIdAndActive(performanceEppaPost.getPerformanceEppa().getEmployeeid(), 1)
					&& hrmsPerformanceFinancialYearRepository
							.existsByIdAndActive(performanceEppaPost.getPerformanceEppa().getFinancialyearid(), 1)
			/*
			 * && hrmsPerformanceObjectiveOutcomeActivityOutputRepository
			 * .existsByIdAndActive(hrmsPerformanceEppa.getOutputid(), 1)
			 */) {
				performanceEppaPost.getPerformanceEppa().setActive(1);
				performanceEppaPost.getPerformanceEppa().setApproved(0);
				performanceEppaPost.getPerformanceEppa().setUnique_id(UUID.randomUUID());
				int eppaid = hrmsPerformanceEppaRepository.saveAndFlush(performanceEppaPost.getPerformanceEppa())
						.getId();

				// now insert list of agreed objectives

				List<HrmsPerformanceEppaObjective> dbms = performanceEppaPost.getPerformanceEppaObjectivelist();

				for (HrmsPerformanceEppaObjective dbm : dbms) {

					if (hrmsPerformanceEppaRepository.existsByIdAndActive(eppaid, 1)) {
						dbm.setActive(1);
						dbm.setApproved(0);
						dbm.setEppaid(eppaid);
						dbm.setUnique_id(UUID.randomUUID());

						hrmsPerformanceEppaObjectiveRepository.saveAndFlush(dbm);
					}

				}

				return ResponseEntity.status(HttpStatus.OK).body(performanceEppaPost);

			} else {

				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(performanceEppaPost);

			}

		}
	}

	@Override
	public ResponseEntity<PerformanceEppaExtraResponse> getPerformanceEppaV2ById(int id) {

		Double sumQualityAtributeAgreed = 0.0;
		int totalAttribute = 0;
		int eppaid = id;
		Double Scorebehaviour = 0.00;

		int totalannual = 0;
		Double annualScoreSum = 0.00;
		Double Scoreannual = 0.00;

		PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

		if (hrmsPerformanceEppaRepository.existsByIdAndActive(id, 1)) {

			HrmsPerformanceEppa dbm = hrmsPerformanceEppaRepository.findById(id).get();

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository.findByEppaidAndActive(id,
					1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());

			List<HrmsPerformanceEppaReviewAnnual> dbms4 = hrmsPerformanceEppaReviewAnnualRepository
					.findByEppaidAndActive(eppaid, 1);

			for (HrmsPerformanceEppaReviewAnnual dbm4 : dbms4) {

				totalannual = totalannual + 1;

				if (dbm4.getScore() != null) {
					annualScoreSum = annualScoreSum + dbm4.getScore();
				}

			}
			if (totalannual != 0) {
				// Scoreannual = annualScoreSum / totalannual;

				Scoreannual = annualScoreSum;
			}

			performanceEppaResponse.setScoreannual(Scoreannual);

			List<HrmsPerformanceEabQuality> dbms6 = hrmsPerformanceEabQualityRepository.findByActive(1);

			for (HrmsPerformanceEabQuality dbm6 : dbms6) {

				if (hrmsPerformanceEabRepository.existsByEppaidAndQualityidAndEmployeeidAndActive(eppaid, dbm6.getId(),
						dbm.getEmployeeid(), 1)) {
					totalAttribute = totalAttribute + 1;

					HrmsPerformanceEab performanceEab = hrmsPerformanceEabRepository
							.findByEppaidAndQualityidAndEmployeeidAndActive(eppaid, dbm6.getId(), dbm.getEmployeeid(),
									1);

					if (performanceEab.getRatingagreed() != null) {
						sumQualityAtributeAgreed = sumQualityAtributeAgreed + performanceEab.getRatingagreed();
					}

				}

			}

			if (totalAttribute != 0) {
				Scorebehaviour = sumQualityAtributeAgreed / totalAttribute;
			}
			performanceEppaResponse.setScorebehaviour(Scorebehaviour);

			// 60% of Score anual + 40% of behavior

			Double score = 0.6 * (Scoreannual) + 0.4 * (Scorebehaviour);

			performanceEppaResponse.setScore(score);
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponse);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2() {
		List<PerformanceEppaExtraResponse> performanceEppaResponselist = new ArrayList<>();

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository.findByActive(1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2ByEmployeeIdAndFinancialYearId(
			int employeeid, int financialyearid) {
		List<PerformanceEppaExtraResponse> performanceEppaResponselist = new ArrayList<>();

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository
				.findByEmployeeidAndFinancialyearidAndActive(employeeid, financialyearid, 1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2BySupervisorIdAndFinancialYearId(
			int supervisorid, int financialyearid) {
		List<PerformanceEppaExtraResponse> performanceEppaResponselist = new ArrayList<>();

		List<Integer> ids = new ArrayList<>();

		if (hrmsEmployeeRepository.existsByIdAndActive(supervisorid, 1)) {
			int supervisordesignationid = hrmsEmployeeRepository.findByIdAndActive(supervisorid, 1).getDesignationId();

			List<HrmsEmployee> dbmx = hrmsEmployeeRepository
					.findBySupervisordesignationidAndEmploymentstatusid(supervisordesignationid, 1);

			for (HrmsEmployee dbmz : dbmx) {

				ids.add(dbmz.getId());

			}

		}

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository
				.findByEmployeeidInAndFinancialyearidAndActive(ids, financialyearid, 1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2BySupervisorId(int supervisorid) {
		List<PerformanceEppaExtraResponse> performanceEppaResponselist = new ArrayList<>();

		List<Integer> ids = new ArrayList<>();

		if (hrmsEmployeeRepository.existsByIdAndActive(supervisorid, 1)) {
			int supervisordesignationid = hrmsEmployeeRepository.findByIdAndActive(supervisorid, 1).getDesignationId();

			List<HrmsEmployee> dbmx = hrmsEmployeeRepository
					.findBySupervisordesignationidAndEmploymentstatusid(supervisordesignationid, 1);

			for (HrmsEmployee dbmz : dbmx) {

				ids.add(dbmz.getId());

			}

		}

		int year = LocalDateTime.now().getYear();

		int month = LocalDateTime.now().getMonthValue();
		int yearstart = year;

		int yearend = year + 1;
		if (month < 7) {

			yearstart = year - 1;

			yearend = year;

		}
		int financialyearid = 0;

		if (hrmsPerformanceFinancialYearRepository.existsByYearstartingAndYearendingAndActive(yearstart, yearend, 1)) {

			financialyearid = hrmsPerformanceFinancialYearRepository
					.findByYearstartingAndYearendingAndActive(yearstart, yearend, 1).getId();

		}

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository
				.findByEmployeeidInAndFinancialyearidAndActive(ids, financialyearid, 1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());
			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<List<PerformanceEppaExtraResponse>> getAllPerformanceEppaV2ByEmployeeIdAndStartYearAndEndYear(
			int employeeid, int startYear, int endYear) {

		List<PerformanceEppaExtraResponse> performanceEppaResponselist = new ArrayList<>();

		List<Integer> ids = new ArrayList<>();

		if (hrmsPerformanceFinancialYearRepository.existsByYearstartingAndYearendingAndActive(startYear, endYear, 1)) {

			List<HrmsPerformanceFinancialYear> dbmx = hrmsPerformanceFinancialYearRepository.findByActive(1);

			for (HrmsPerformanceFinancialYear dbmz : dbmx) {

				if (dbmz.getYearstarting() <= startYear && dbmz.getYearending() <= endYear) {

					ids.add(dbmz.getId());
				}
			}

		}

		List<HrmsPerformanceEppa> dbms = hrmsPerformanceEppaRepository
				.findByEmployeeidAndFinancialyearidInAndActive(employeeid, ids, 1);

		for (HrmsPerformanceEppa dbm : dbms) {

			PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());

			performanceEppaResponse.setScoreannual(dbm.getScoreannual());
			performanceEppaResponse.setScorebehaviour(dbm.getScorebehaviour());

			performanceEppaResponse.setScore(dbm.getScore());
			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

			performanceEppaResponselist.add(performanceEppaResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponselist);
	}

	@Override
	public ResponseEntity<PerformanceEppaExtraResponse> getAllPerformanceEppaV3ByEmployeeIdAndFinancialYearId(
			int employeeid, int financialyearid) {

		Double sumQualityAtributeAgreed = 0.0;
		int totalAttribute = 0;
		int eppaid = 0;
		Double Scorebehaviour = 0.00;

		int totalannual = 0;
		Double annualScoreSum = 0.00;
		Double Scoreannual = 0.00;

		HrmsPerformanceEppa hrmsPerformanceEppa = hrmsPerformanceEppaRepository
				.findByEmployeeidAndFinancialyearid(employeeid, financialyearid);
		if (hrmsPerformanceEppa != null) {
			eppaid = hrmsPerformanceEppa.getId();
		}

		PerformanceEppaExtraResponse performanceEppaResponse = new PerformanceEppaExtraResponse();

		List<Integer> approveds = new ArrayList<>();

		approveds.add(0);
		approveds.add(1);
		HrmsPerformanceEppa dbm = hrmsPerformanceEppaRepository
				.findByEmployeeidAndFinancialyearidAndApprovedInAndActive(employeeid, financialyearid, approveds, 1);

		if (dbm != null) {

			List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(dbm.getId(), 1);

			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				performanceEppaObjectivelist.add(dbm1);

			}

			performanceEppaResponse.setPerformanceEppaObjectivelist(performanceEppaObjectivelist);

			performanceEppaResponse.setActive(dbm.getActive());
			performanceEppaResponse.setApproved(dbm.getApproved());
			performanceEppaResponse.setComments(dbm.getComments());
			performanceEppaResponse.setDescription(dbm.getDescription());
			performanceEppaResponse.setId(dbm.getId());

			List<HrmsPerformanceEppaReviewAnnual> dbms4 = hrmsPerformanceEppaReviewAnnualRepository
					.findByEppaidAndActive(eppaid, 1);

			for (HrmsPerformanceEppaReviewAnnual dbm4 : dbms4) {

				totalannual = totalannual + 1;

				if (dbm4.getScore() != null) {
					annualScoreSum = annualScoreSum + dbm4.getScore();
				}

			}
			if (totalannual != 0) {
				// Scoreannual = annualScoreSum / totalannual;

				Scoreannual = annualScoreSum;
			}

			performanceEppaResponse.setScoreannual(Scoreannual);

			List<HrmsPerformanceEabQuality> dbms6 = hrmsPerformanceEabQualityRepository.findByActive(1);

			for (HrmsPerformanceEabQuality dbm6 : dbms6) {

				if (hrmsPerformanceEabRepository.existsByEppaidAndQualityidAndEmployeeidAndActive(eppaid, dbm6.getId(),
						employeeid, 1)) {
					totalAttribute = totalAttribute + 1;

					HrmsPerformanceEab performanceEab = hrmsPerformanceEabRepository
							.findByEppaidAndQualityidAndEmployeeidAndActive(eppaid, dbm6.getId(), employeeid, 1);

					if (performanceEab.getRatingagreed() != null) {
						sumQualityAtributeAgreed = sumQualityAtributeAgreed + performanceEab.getRatingagreed();
					}

				}

			}

			if (totalAttribute != 0) {
				Scorebehaviour = sumQualityAtributeAgreed / totalAttribute;
			}
			performanceEppaResponse.setScorebehaviour(Scorebehaviour);

			// Double score = Scoreannual + Scorebehaviour;

			Double score = 0.6 * (Scoreannual) + 0.4 * (Scorebehaviour);

			performanceEppaResponse.setScore(score);

			performanceEppaResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				StringBuilder employeeFullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

				performanceEppaResponse.setEmployeeFullName(employeeFullName.toString());
			}
			performanceEppaResponse.setFinancialyearid(dbm.getFinancialyearid());
			if (dbm.getFinancialyearid() != 0
					&& hrmsPerformanceFinancialYearRepository.existsById(dbm.getFinancialyearid())) {
				performanceEppaResponse.setPerformanceFinancialYear(
						hrmsPerformanceFinancialYearRepository.findById(dbm.getFinancialyearid()).get());
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceEppaResponse);
	}

}
