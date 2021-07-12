package com.Hrms.Training.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;
import com.Hrms.Perfomance.Repository.HrmsPerformanceFinancialYearRepository;
import com.Hrms.Training.DTO.TrainingApprovalStatus;
import com.Hrms.Training.DTO.TrainingResponse;
import com.Hrms.Training.Entity.HrmsTraining;
import com.Hrms.Training.Entity.HrmsTrainingApproval;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflow;
import com.Hrms.Training.Entity.HrmsTrainingApprovalWorkflowStep;
import com.Hrms.Training.Repository.HrmsTrainingApprovalRepository;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowRepository;
import com.Hrms.Training.Repository.HrmsTrainingApprovalWorkflowStepRepository;
import com.Hrms.Training.Repository.HrmsTrainingCategoryRepository;
import com.Hrms.Training.Repository.HrmsTrainingInitiatorRepository;
import com.Hrms.Training.Repository.HrmsTrainingPurposeRepository;
import com.Hrms.Training.Repository.HrmsTrainingRepository;
import com.Hrms.Training.Repository.HrmsTrainingSponsorRepository;
import com.Hrms.Training.Repository.HrmsTrainingTypeRepository;

@Service
public class HrmsTrainingServiceImpl implements HrmsTrainingService {

	@Autowired
	private HrmsTrainingRepository hrmsTrainingRepository;

	@Autowired
	private HrmsTrainingCategoryRepository hrmsTrainingCategoryRepository;

	@Autowired
	private HrmsTrainingTypeRepository hrmsTrainingTypeRepository;

	@Autowired
	private HrmsTrainingInitiatorRepository hrmsTrainingInitiatorRepository;

	@Autowired
	private HrmsTrainingSponsorRepository hrmsTrainingSponsorRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsPerformanceFinancialYearRepository hrmsPerformanceFinancialYearRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsTrainingPurposeRepository hrmsTrainingPurposeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsTrainingApprovalRepository hrmsTrainingApprovalRepository;

	@Autowired
	private HrmsTrainingApprovalWorkflowRepository hrmsTrainingApprovalWorkflowRepository;

	@Autowired
	private HrmsTrainingApprovalWorkflowStepRepository hrmsTrainingApprovalWorkflowStepRepository;

	@Override
	public ResponseEntity<HrmsTraining> addHrmsTraining(HrmsTraining hrmsTraining) {
		if (hrmsTrainingRepository.existsByEmployeeidAndDescriptionAndInstitutionAndFinancialyearidAndActive(
				hrmsTraining.getEmployeeid(), hrmsTraining.getDescription(), hrmsTraining.getInstitution(),
				hrmsTraining.getFinancialyearid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTraining);
		} else {

			hrmsTraining.setActive(1);
			hrmsTraining.setApproved(0);
			hrmsTraining.setUnique_id(UUID.randomUUID());

			if (hrmsTrainingSponsorRepository.existsByIdAndActive(hrmsTraining.getTrainingsponsorid(), 1)

					&& hrmsTrainingInitiatorRepository.existsByIdAndActive(hrmsTraining.getTraininginitiatorid(), 1)
					&& hrmsTrainingTypeRepository.existsByIdAndActive(hrmsTraining.getTrainingtypeid(), 1)
					&& hrmsTrainingCategoryRepository.existsByIdAndActive(hrmsTraining.getTrainingcategoryid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getEmployeeid(), 1)
					&& hrmsPerformanceFinancialYearRepository.existsByIdAndActive(hrmsTraining.getFinancialyearid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getRequestedby(), 1)) {

				HrmsEmployee emp = hrmsEmployeeRepository.findByIdAndActive(hrmsTraining.getEmployeeid(), 1);

				hrmsTraining.setSupervisorid(emp.getSupervisorId());

				if (hrmsEmployeeRepository.existsById(emp.getSupervisorId())) {
					hrmsTraining.setSupervisordesignationid(
							hrmsEmployeeRepository.findByIdAndActive(emp.getSupervisorId(), 1).getDesignationId());
				}

				if (hrmsTraining.getTraininginitiatorid() == 1) {

					hrmsTraining.setApproved(1);

				}
				return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingRepository.saveAndFlush(hrmsTraining));

			} else {

				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsTraining);
			}

		}
	}

	@Override
	public ResponseEntity<?> deleteHrmsTraining(int id) {
		if (hrmsTrainingRepository.existsByIdAndActive(id, 1)) {
			HrmsTraining hrmsTraining = hrmsTrainingRepository.findByIdAndActive(id, 1);
			hrmsTraining.setActive(0);
			hrmsTraining.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingRepository.saveAndFlush(hrmsTraining));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsTraining> updateHrmsTraining(HrmsTraining hrmsTraining, int id) {
		if (hrmsTrainingRepository.existsByIdAndActive(id, 1)
				&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getRequestedby(), 1)) {
			hrmsTraining.setActive(1);
			hrmsTraining.setApproved(0);
			hrmsTraining.setDate_updated(LocalDateTime.now());
			if (hrmsTrainingRepository.findById(id).get().getDate_created() != null) {
				hrmsTraining.setDate_created(hrmsTrainingRepository.findById(id).get().getDate_created());
			}

			if (hrmsTrainingRepository.findById(id).get().getUnique_id() != null) {
				hrmsTraining.setUnique_id(hrmsTrainingRepository.findById(id).get().getUnique_id());
			}

			if (hrmsTrainingSponsorRepository.existsByIdAndActive(hrmsTraining.getTrainingsponsorid(), 1)

					&& hrmsTrainingInitiatorRepository.existsByIdAndActive(hrmsTraining.getTraininginitiatorid(), 1)
					&& hrmsTrainingTypeRepository.existsByIdAndActive(hrmsTraining.getTrainingtypeid(), 1)
					&& hrmsTrainingCategoryRepository.existsByIdAndActive(hrmsTraining.getTrainingcategoryid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getEmployeeid(), 1)
					&& hrmsPerformanceFinancialYearRepository.existsByIdAndActive(hrmsTraining.getFinancialyearid(),
							1)) {

				HrmsEmployee emp = hrmsEmployeeRepository.findByIdAndActive(hrmsTraining.getEmployeeid(), 1);

				hrmsTraining.setSupervisorid(emp.getSupervisorId());

				if (hrmsEmployeeRepository.existsById(emp.getSupervisorId())) {
					hrmsTraining.setSupervisordesignationid(
							hrmsEmployeeRepository.findByIdAndActive(emp.getSupervisorId(), 1).getDesignationId());
				}

				if (hrmsTraining.getTraininginitiatorid() == 1) {

					hrmsTraining.setApproved(1);

				}
				return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingRepository.saveAndFlush(hrmsTraining));

			} else {

				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsTraining);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<TrainingResponse> getHrmsTrainingById(int id) {

		TrainingResponse trainingResponse = new TrainingResponse();

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		if (hrmsTrainingRepository.existsByIdAndActive(id, 1)) {

			HrmsTraining dbm = hrmsTrainingRepository.findByIdAndActive(id, 1);

			trainingResponse.setActive(dbm.getActive());
			trainingResponse.setApproved(dbm.getApproved());

			trainingResponse.setRequestedby(dbm.getRequestedby());
			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getRequestedby(), 1)) {

				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setRequestedbFullName(employeeFullName.toString());
			}

			if (dbm.getApproved() == 1) {
				trainingResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 0) {
				trainingResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == -1) {
				trainingResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getUnattended() == 1) {
				trainingResponse.setUnattendedname("Attended");
			}

			if (dbm.getUnattended() == 0) {
				trainingResponse.setUnattendedname("UnAttended");
			}

			trainingResponse.setCurrencyid(dbm.getCurrencyid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				trainingResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
			trainingResponse.setTrainingcost(dbm.getTrainingcost());
			trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

			if (dbm.getTrainingpurposeid() != 0
					&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
				trainingResponse.setTrainingpurpose(
						hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
			}
			trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

			if (dbm.getDate_expected_end() != null) {
				trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
			}

			if (dbm.getDateexpectedstart() != null) {
				trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
			}
			trainingResponse.setDelayed(dbm.getDelayed());
			trainingResponse.setDelayedreason(dbm.getDelayedreason());
			trainingResponse.setDescription(dbm.getDescription());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {

				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setEmployeeFullName(employeeFullName.toString());

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
					trainingResponse.setSection(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid()).get().getName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
					trainingResponse.setDepartment(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

				}

			}

			trainingResponse.setEmployeeid(dbm.getEmployeeid());
			if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
				HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
						.findById(dbm.getFinancialyearid()).get();

				String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
				trainingResponse.setFinancialyear(fyear);
			}

			trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
			trainingResponse.setId(dbm.getId());
			trainingResponse.setInstitution(dbm.getInstitution());
			trainingResponse.setName(dbm.getName());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
				StringBuilder supervisor = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				supervisor.append(" " + hrmsEmployee.getLastName().trim());

				trainingResponse.setSupervisor(supervisor.toString());
			}

			trainingResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				trainingResponse.setSupervisordesignation(
						hrmsDesignationRepository.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
			}
			trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getTrainingcategoryid() != 0
					&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
				trainingResponse.setTrainingcategory(
						hrmsTrainingCategoryRepository.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
			}
			trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
			if (dbm.getTraininginitiatorid() != 0
					&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
				trainingResponse.setTraininginitiator(
						hrmsTrainingInitiatorRepository.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

			}
			trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

			if (dbm.getTrainingsponsorid() != 0
					&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
				trainingResponse.setTrainingsponsor(
						hrmsTrainingSponsorRepository.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

			}
			trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
			if (dbm.getTrainingtypeid() != 0
					&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
				trainingResponse.setTrainingtype(
						hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
			}
			trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
			trainingResponse.setUnattended(dbm.getUnattended());
			trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
			trainingResponse.setUnplanned(dbm.getUnplanned());

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponse);
	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByEmployeeId(int Employeeid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingResponse> trainingResponselist = new ArrayList<>();
		int year = LocalDateTime.now().getYear();

		int month = LocalDateTime.now().getMonthValue();
		int yearstart = year;

		int yearend = year + 1;
		if (month < 7) {

			yearstart = year - 1;

			yearend = year;

		}

		if (hrmsPerformanceFinancialYearRepository.existsByYearstartingAndYearendingAndActive(yearstart, yearend, 1)) {

			int finyear = hrmsPerformanceFinancialYearRepository
					.findByYearstartingAndYearendingAndActive(yearstart, yearend, 1).getId();

			List<HrmsTraining> dbms = hrmsTrainingRepository.findByFinancialyearidAndEmployeeidAndActive(finyear,
					Employeeid, 1);

			for (HrmsTraining dbm : dbms) {

				TrainingResponse trainingResponse = new TrainingResponse();

				trainingResponse.setActive(dbm.getActive());
				trainingResponse.setApproved(dbm.getApproved());

				trainingResponse.setRequestedby(dbm.getRequestedby());
				if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getRequestedby(), 1)) {

					StringBuilder employeeFullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

					employeeFullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
					trainingResponse.setRequestedbFullName(employeeFullName.toString());
				}

				if (dbm.getApproved() == 1) {
					trainingResponse.setApprovedStatus("Approved");
				}

				if (dbm.getApproved() == 0) {
					trainingResponse.setApprovedStatus("On Progress");
				}

				if (dbm.getApproved() == -1) {
					trainingResponse.setApprovedStatus("Rejected");
				}

				if (dbm.getUnattended() == 1) {
					trainingResponse.setUnattendedname("Attended");
				}

				if (dbm.getUnattended() == 0) {
					trainingResponse.setUnattendedname("UnAttended");
				}

				trainingResponse.setCurrencyid(dbm.getCurrencyid());

				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					trainingResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}
				trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
				trainingResponse.setTrainingcost(dbm.getTrainingcost());
				trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

				if (dbm.getTrainingpurposeid() != 0
						&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
					trainingResponse.setTrainingpurpose(
							hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
				}
				trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

				if (dbm.getDate_expected_end() != null) {
					trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
				}

				if (dbm.getDateexpectedstart() != null) {
					trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
				}
				trainingResponse.setDelayed(dbm.getDelayed());
				trainingResponse.setDelayedreason(dbm.getDelayedreason());
				trainingResponse.setDescription(dbm.getDescription());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
					StringBuilder employeeFullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					employeeFullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
					trainingResponse.setEmployeeFullName(employeeFullName.toString());

					if (hrmsEmployee.getSectionid() != 0
							&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
						trainingResponse.setSection(
								hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid()).get().getName());
					}

					if (hrmsEmployee.getUnitId() != 0
							&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
						trainingResponse.setDepartment(
								hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

					}
				}

				trainingResponse.setEmployeeid(dbm.getEmployeeid());
				if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
					HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
							.findById(dbm.getFinancialyearid()).get();

					String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
					trainingResponse.setFinancialyear(fyear);
				}

				trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
				trainingResponse.setId(dbm.getId());
				trainingResponse.setInstitution(dbm.getInstitution());
				trainingResponse.setName(dbm.getName());

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
					StringBuilder supervisor = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

					supervisor.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					supervisor.append(" " + hrmsEmployee.getLastName().trim());

					trainingResponse.setSupervisor(supervisor.toString());
				}

				trainingResponse.setSupervisorid(dbm.getSupervisorid());

				if (dbm.getSupervisordesignationid() != 0
						&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
					trainingResponse.setSupervisordesignation(
							hrmsDesignationRepository.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
				}
				trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

				if (dbm.getTrainingcategoryid() != 0
						&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
					trainingResponse.setTrainingcategory(
							hrmsTrainingCategoryRepository.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
				}
				trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
				if (dbm.getTraininginitiatorid() != 0
						&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
					trainingResponse.setTraininginitiator(hrmsTrainingInitiatorRepository
							.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

				}
				trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

				if (dbm.getTrainingsponsorid() != 0
						&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
					trainingResponse.setTrainingsponsor(
							hrmsTrainingSponsorRepository.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

				}
				trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
				if (dbm.getTrainingtypeid() != 0
						&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
					trainingResponse.setTrainingtype(
							hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
				}
				trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
				trainingResponse.setUnattended(dbm.getUnattended());
				trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
				trainingResponse.setUnplanned(dbm.getUnplanned());

				trainingResponselist.add(trainingResponse);

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);
	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingCurrentYear() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingResponse> trainingResponselist = new ArrayList<>();
		int year = LocalDateTime.now().getYear();

		int month = LocalDateTime.now().getMonthValue();
		int yearstart = year;

		int yearend = year + 1;
		if (month < 7) {

			yearstart = year - 1;

			yearend = year;

		}

		if (hrmsPerformanceFinancialYearRepository.existsByYearstartingAndYearendingAndActive(yearstart, yearend, 1)) {

			int finyear = hrmsPerformanceFinancialYearRepository
					.findByYearstartingAndYearendingAndActive(yearstart, yearend, 1).getId();

			List<HrmsTraining> dbms = hrmsTrainingRepository.findByFinancialyearidAndActive(finyear, 1);

			for (HrmsTraining dbm : dbms) {

				TrainingResponse trainingResponse = new TrainingResponse();

				trainingResponse.setActive(dbm.getActive());
				trainingResponse.setApproved(dbm.getApproved());

				trainingResponse.setRequestedby(dbm.getRequestedby());
				if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getRequestedby(), 1)) {

					StringBuilder employeeFullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

					employeeFullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
					trainingResponse.setRequestedbFullName(employeeFullName.toString());
				}

				if (dbm.getApproved() == 1) {
					trainingResponse.setApprovedStatus("Approved");
				}

				if (dbm.getApproved() == 0) {
					trainingResponse.setApprovedStatus("On Progress");
				}

				if (dbm.getApproved() == -1) {
					trainingResponse.setApprovedStatus("Rejected");
				}

				if (dbm.getUnattended() == 1) {
					trainingResponse.setUnattendedname("Attended");
				}

				if (dbm.getUnattended() == 0) {
					trainingResponse.setUnattendedname("UnAttended");
				}

				trainingResponse.setCurrencyid(dbm.getCurrencyid());

				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					trainingResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}
				trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
				trainingResponse.setTrainingcost(dbm.getTrainingcost());
				trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

				if (dbm.getTrainingpurposeid() != 0
						&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
					trainingResponse.setTrainingpurpose(
							hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
				}
				trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

				if (dbm.getDate_expected_end() != null) {
					trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
				}

				if (dbm.getDateexpectedstart() != null) {
					trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
				}
				trainingResponse.setDelayed(dbm.getDelayed());
				trainingResponse.setDelayedreason(dbm.getDelayedreason());
				trainingResponse.setDescription(dbm.getDescription());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
					StringBuilder employeeFullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					employeeFullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
					trainingResponse.setEmployeeFullName(employeeFullName.toString());

					if (hrmsEmployee.getSectionid() != 0
							&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
						trainingResponse.setSection(
								hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid()).get().getName());
					}

					if (hrmsEmployee.getUnitId() != 0
							&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
						trainingResponse.setDepartment(
								hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

					}
				}

				trainingResponse.setEmployeeid(dbm.getEmployeeid());
				if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
					HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
							.findById(dbm.getFinancialyearid()).get();

					String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
					trainingResponse.setFinancialyear(fyear);
				}

				trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
				trainingResponse.setId(dbm.getId());
				trainingResponse.setInstitution(dbm.getInstitution());
				trainingResponse.setName(dbm.getName());

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
					StringBuilder supervisor = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

					supervisor.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					supervisor.append(" " + hrmsEmployee.getLastName().trim());

					trainingResponse.setSupervisor(supervisor.toString());
				}

				trainingResponse.setSupervisorid(dbm.getSupervisorid());

				if (dbm.getSupervisordesignationid() != 0
						&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
					trainingResponse.setSupervisordesignation(
							hrmsDesignationRepository.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
				}
				trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

				if (dbm.getTrainingcategoryid() != 0
						&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
					trainingResponse.setTrainingcategory(
							hrmsTrainingCategoryRepository.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
				}
				trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
				if (dbm.getTraininginitiatorid() != 0
						&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
					trainingResponse.setTraininginitiator(hrmsTrainingInitiatorRepository
							.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

				}
				trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

				if (dbm.getTrainingsponsorid() != 0
						&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
					trainingResponse.setTrainingsponsor(
							hrmsTrainingSponsorRepository.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

				}
				trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
				if (dbm.getTrainingtypeid() != 0
						&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
					trainingResponse.setTrainingtype(
							hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
				}
				trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
				trainingResponse.setUnattended(dbm.getUnattended());
				trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
				trainingResponse.setUnplanned(dbm.getUnplanned());

				trainingResponselist.add(trainingResponse);

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);
	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYear(int finyearid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingResponse> trainingResponselist = new ArrayList<>();

		List<HrmsTraining> dbms = hrmsTrainingRepository.findByFinancialyearidAndActive(finyearid, 1);

		for (HrmsTraining dbm : dbms) {

			TrainingResponse trainingResponse = new TrainingResponse();

			trainingResponse.setActive(dbm.getActive());
			trainingResponse.setApproved(dbm.getApproved());

			trainingResponse.setRequestedby(dbm.getRequestedby());
			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getRequestedby(), 1)) {

				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setRequestedbFullName(employeeFullName.toString());
			}

			if (dbm.getApproved() == 1) {
				trainingResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 0) {
				trainingResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == -1) {
				trainingResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getUnattended() == 1) {
				trainingResponse.setUnattendedname("Attended");
			}

			if (dbm.getUnattended() == 0) {
				trainingResponse.setUnattendedname("UnAttended");
			}

			trainingResponse.setCurrencyid(dbm.getCurrencyid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				trainingResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
			trainingResponse.setTrainingcost(dbm.getTrainingcost());
			trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

			if (dbm.getTrainingpurposeid() != 0
					&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
				trainingResponse.setTrainingpurpose(
						hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
			}
			trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

			if (dbm.getDate_expected_end() != null) {
				trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
			}

			if (dbm.getDateexpectedstart() != null) {
				trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
			}
			trainingResponse.setDelayed(dbm.getDelayed());
			trainingResponse.setDelayedreason(dbm.getDelayedreason());
			trainingResponse.setDescription(dbm.getDescription());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setEmployeeFullName(employeeFullName.toString());

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
					trainingResponse.setSection(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid()).get().getName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
					trainingResponse.setDepartment(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

				}
			}

			trainingResponse.setEmployeeid(dbm.getEmployeeid());
			if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
				HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
						.findById(dbm.getFinancialyearid()).get();

				String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
				trainingResponse.setFinancialyear(fyear);
			}

			trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
			trainingResponse.setId(dbm.getId());
			trainingResponse.setInstitution(dbm.getInstitution());
			trainingResponse.setName(dbm.getName());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
				StringBuilder supervisor = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				supervisor.append(" " + hrmsEmployee.getLastName().trim());

				trainingResponse.setSupervisor(supervisor.toString());
			}

			trainingResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				trainingResponse.setSupervisordesignation(
						hrmsDesignationRepository.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
			}
			trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getTrainingcategoryid() != 0
					&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
				trainingResponse.setTrainingcategory(
						hrmsTrainingCategoryRepository.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
			}
			trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
			if (dbm.getTraininginitiatorid() != 0
					&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
				trainingResponse.setTraininginitiator(
						hrmsTrainingInitiatorRepository.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

			}
			trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

			if (dbm.getTrainingsponsorid() != 0
					&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
				trainingResponse.setTrainingsponsor(
						hrmsTrainingSponsorRepository.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

			}
			trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
			if (dbm.getTrainingtypeid() != 0
					&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
				trainingResponse.setTrainingtype(
						hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
			}
			trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
			trainingResponse.setUnattended(dbm.getUnattended());
			trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
			trainingResponse.setUnplanned(dbm.getUnplanned());

			trainingResponselist.add(trainingResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);
	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYearAndEmployeeId(int finyearid,
			int Employeeid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingResponse> trainingResponselist = new ArrayList<>();

		List<HrmsTraining> dbms = hrmsTrainingRepository.findByFinancialyearidAndEmployeeidAndAndActive(finyearid,
				Employeeid, 1);

		for (HrmsTraining dbm : dbms) {

			TrainingResponse trainingResponse = new TrainingResponse();

			trainingResponse.setActive(dbm.getActive());
			trainingResponse.setApproved(dbm.getApproved());

			trainingResponse.setRequestedby(dbm.getRequestedby());
			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getRequestedby(), 1)) {

				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setRequestedbFullName(employeeFullName.toString());
			}
			if (dbm.getApproved() == 1) {
				trainingResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 0) {
				trainingResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == -1) {
				trainingResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getUnattended() == 1) {
				trainingResponse.setUnattendedname("Attended");
			}

			if (dbm.getUnattended() == 0) {
				trainingResponse.setUnattendedname("UnAttended");
			}

			trainingResponse.setCurrencyid(dbm.getCurrencyid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				trainingResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
			trainingResponse.setTrainingcost(dbm.getTrainingcost());
			trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

			if (dbm.getTrainingpurposeid() != 0
					&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
				trainingResponse.setTrainingpurpose(
						hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
			}
			trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

			if (dbm.getDate_expected_end() != null) {
				trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
			}

			if (dbm.getDateexpectedstart() != null) {
				trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
			}
			trainingResponse.setDelayed(dbm.getDelayed());
			trainingResponse.setDelayedreason(dbm.getDelayedreason());
			trainingResponse.setDescription(dbm.getDescription());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setEmployeeFullName(employeeFullName.toString());

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
					trainingResponse.setSection(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid()).get().getName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
					trainingResponse.setDepartment(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

				}
			}

			trainingResponse.setEmployeeid(dbm.getEmployeeid());
			if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
				HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
						.findById(dbm.getFinancialyearid()).get();

				String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
				trainingResponse.setFinancialyear(fyear);
			}

			trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
			trainingResponse.setId(dbm.getId());
			trainingResponse.setInstitution(dbm.getInstitution());
			trainingResponse.setName(dbm.getName());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
				StringBuilder supervisor = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				supervisor.append(" " + hrmsEmployee.getLastName().trim());

				trainingResponse.setSupervisor(supervisor.toString());
			}

			trainingResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				trainingResponse.setSupervisordesignation(
						hrmsDesignationRepository.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
			}
			trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getTrainingcategoryid() != 0
					&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
				trainingResponse.setTrainingcategory(
						hrmsTrainingCategoryRepository.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
			}
			trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
			if (dbm.getTraininginitiatorid() != 0
					&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
				trainingResponse.setTraininginitiator(
						hrmsTrainingInitiatorRepository.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

			}
			trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

			if (dbm.getTrainingsponsorid() != 0
					&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
				trainingResponse.setTrainingsponsor(
						hrmsTrainingSponsorRepository.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

			}
			trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
			if (dbm.getTrainingtypeid() != 0
					&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
				trainingResponse.setTrainingtype(
						hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
			}
			trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
			trainingResponse.setUnattended(dbm.getUnattended());
			trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
			trainingResponse.setUnplanned(dbm.getUnplanned());

			trainingResponselist.add(trainingResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);
	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingByFinancialYearAndQuaterIdAndCategoryId(int finyearid,
			int Quaterid, int Categoryid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		int startday = 0;
		int startmonth = 0;

		int endmonth = 0;

		int endday = 0;

		if (Quaterid == 1) {

			startday = 1;
			startmonth = 7;

			endmonth = 9;

			endday = 31;

		}

		if (Quaterid == 2) {

			startday = 1;
			startmonth = 10;

			endmonth = 12;

			endday = 31;

		}

		if (Quaterid == 3) {

			startday = 1;
			startmonth = 1;

			endmonth = 3;

			endday = 31;

		}

		if (Quaterid == 4) {

			startday = 1;
			startmonth = 4;

			endmonth = 6;

			endday = 31;

		}

		if (Quaterid == 0) {

			startday = 1;
			startmonth = 1;

			endmonth = 12;

			endday = 31;

		}

		List<TrainingResponse> trainingResponselist = new ArrayList<>();

		List<HrmsTraining> dbms = hrmsTrainingRepository.findByFinancialyearidAndTrainingcategoryidAndActive(finyearid,
				Categoryid, 1);

		if (Categoryid == 0) {

			dbms = hrmsTrainingRepository.findByFinancialyearidAndActive(finyearid, 1);

		}

		for (HrmsTraining dbm : dbms) {
			if (dbm.getDate_expected_end() != null && dbm.getDateexpectedstart() != null) {

				LocalDate startdate = dbm.getDateexpectedstart().toInstant().atZone(ZoneId.systemDefault())
						.toLocalDate();

				LocalDate enddate = dbm.getDate_expected_end().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

				if (startdate.getMonthValue() >= startmonth && startdate.getDayOfMonth() >= startday
						&& enddate.getMonthValue() <= endmonth && enddate.getDayOfMonth() <= endday) {

					TrainingResponse trainingResponse = new TrainingResponse();

					trainingResponse.setActive(dbm.getActive());
					trainingResponse.setApproved(dbm.getApproved());

					trainingResponse.setRequestedby(dbm.getRequestedby());
					if (dbm.getRequestedby() != 0
							&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getRequestedby(), 1)) {

						StringBuilder employeeFullName = new StringBuilder();
						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

						employeeFullName.append(hrmsEmployee.getFirstName().trim());
						if (hrmsEmployee.getMiddleName() != null) {
							employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
						}

						employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
						trainingResponse.setRequestedbFullName(employeeFullName.toString());
					}
					if (dbm.getApproved() == 1) {
						trainingResponse.setApprovedStatus("Approved");
					}

					if (dbm.getApproved() == 0) {
						trainingResponse.setApprovedStatus("On Progress");
					}

					if (dbm.getApproved() == -1) {
						trainingResponse.setApprovedStatus("Rejected");
					}

					if (dbm.getUnattended() == 1) {
						trainingResponse.setUnattendedname("Attended");
					}

					if (dbm.getUnattended() == 0) {
						trainingResponse.setUnattendedname("UnAttended");
					}

					trainingResponse.setCurrencyid(dbm.getCurrencyid());

					if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
						trainingResponse
								.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
					}
					trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
					trainingResponse.setTrainingcost(dbm.getTrainingcost());
					trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

					if (dbm.getTrainingpurposeid() != 0
							&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
						trainingResponse.setTrainingpurpose(
								hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
					}
					trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

					if (dbm.getDate_expected_end() != null) {
						trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
					}

					if (dbm.getDateexpectedstart() != null) {
						trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
					}
					trainingResponse.setDelayed(dbm.getDelayed());
					trainingResponse.setDelayedreason(dbm.getDelayedreason());
					trainingResponse.setDescription(dbm.getDescription());
					if (dbm.getEmployeeid() != 0
							&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
						StringBuilder employeeFullName = new StringBuilder();
						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

						employeeFullName.append(hrmsEmployee.getFirstName().trim());
						if (hrmsEmployee.getMiddleName() != null) {
							employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
						}

						employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
						trainingResponse.setEmployeeFullName(employeeFullName.toString());

						if (hrmsEmployee.getSectionid() != 0
								&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
							trainingResponse.setSection(hrmsOrginisationUnitRepository
									.findById(hrmsEmployee.getSectionid()).get().getName());
						}

						if (hrmsEmployee.getUnitId() != 0
								&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
							trainingResponse.setDepartment(
									hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

						}
					}

					trainingResponse.setEmployeeid(dbm.getEmployeeid());
					if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
						HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
								.findById(dbm.getFinancialyearid()).get();

						String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
						trainingResponse.setFinancialyear(fyear);
					}

					trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
					trainingResponse.setId(dbm.getId());
					trainingResponse.setInstitution(dbm.getInstitution());
					trainingResponse.setName(dbm.getName());

					if (dbm.getEmployeeid() != 0
							&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
						StringBuilder supervisor = new StringBuilder();
						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

						supervisor.append(hrmsEmployee.getFirstName().trim());
						if (hrmsEmployee.getMiddleName() != null) {
							supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
						}

						supervisor.append(" " + hrmsEmployee.getLastName().trim());

						trainingResponse.setSupervisor(supervisor.toString());
					}

					trainingResponse.setSupervisorid(dbm.getSupervisorid());

					if (dbm.getSupervisordesignationid() != 0
							&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
						trainingResponse.setSupervisordesignation(hrmsDesignationRepository
								.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
					}
					trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

					if (dbm.getTrainingcategoryid() != 0
							&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
						trainingResponse.setTrainingcategory(hrmsTrainingCategoryRepository
								.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
					}
					trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
					if (dbm.getTraininginitiatorid() != 0
							&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
						trainingResponse.setTraininginitiator(hrmsTrainingInitiatorRepository
								.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

					}
					trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

					if (dbm.getTrainingsponsorid() != 0
							&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
						trainingResponse.setTrainingsponsor(hrmsTrainingSponsorRepository
								.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

					}
					trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
					if (dbm.getTrainingtypeid() != 0
							&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
						trainingResponse.setTrainingtype(
								hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
					}
					trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
					trainingResponse.setUnattended(dbm.getUnattended());
					trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
					trainingResponse.setUnplanned(dbm.getUnplanned());

					trainingResponselist.add(trainingResponse);

				}
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);
	}

	@Override
	public ResponseEntity<List<HrmsTraining>> addHrmsTrainingV2(HrmsTraining hrmsTraining, List<Integer> employeeids) {

		List<HrmsTraining> hrmsTraininglist = new ArrayList<>();
		Boolean flag = false;

		for (Integer employeeid : employeeids) {
			hrmsTraining.setEmployeeid(employeeid);

			if (hrmsTrainingRepository.existsByEmployeeidAndDescriptionAndInstitutionAndFinancialyearidAndActive(
					hrmsTraining.getEmployeeid(), hrmsTraining.getDescription(), hrmsTraining.getInstitution(),
					hrmsTraining.getFinancialyearid(), 1)) {

				// return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsTraining);
			} else {

				hrmsTraining.setActive(1);
				hrmsTraining.setApproved(0);
				hrmsTraining.setUnique_id(UUID.randomUUID());

				if (hrmsTrainingSponsorRepository.existsByIdAndActive(hrmsTraining.getTrainingsponsorid(), 1)

						&& hrmsTrainingInitiatorRepository.existsByIdAndActive(hrmsTraining.getTraininginitiatorid(), 1)
						&& hrmsTrainingTypeRepository.existsByIdAndActive(hrmsTraining.getTrainingtypeid(), 1)
						&& hrmsTrainingCategoryRepository.existsByIdAndActive(hrmsTraining.getTrainingcategoryid(), 1)
						&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getEmployeeid(), 1)
						&& hrmsPerformanceFinancialYearRepository.existsByIdAndActive(hrmsTraining.getFinancialyearid(),
								1)
						&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getRequestedby(), 1)) {

					HrmsEmployee emp = hrmsEmployeeRepository.findByIdAndActive(hrmsTraining.getEmployeeid(), 1);

					hrmsTraining.setSupervisorid(emp.getSupervisorId());

					if (hrmsEmployeeRepository.existsById(emp.getSupervisorId())) {
						hrmsTraining.setSupervisordesignationid(
								hrmsEmployeeRepository.findByIdAndActive(emp.getSupervisorId(), 1).getDesignationId());
					}

					hrmsTraininglist.add(hrmsTraining);

					// hrmsTrainingRepository.saveAndFlush(hrmsTraining);
					flag = true;

				} else {
					flag = false;

					// return
					// ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsTraining);
				}

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingRepository.saveAll(hrmsTraininglist));
	}

	@Override
	public ResponseEntity<?> ApproveHrmsTraining(int id, int supervisorid, int status, String comment) {

		if (hrmsTrainingRepository.existsByIdAndActiveAndApproved(id, 1, 0)
				&& hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
					.findByIdAndIssupervisorAndActiveAndEmploymentstatusid(supervisorid, 1, 1, 2);

			HrmsTraining hrmsTraining = hrmsTrainingRepository.findByIdAndActive(id, 1);

			int employeeid = hrmsTraining.getEmployeeid();

			int firstsupervisordesignationid = hrmsTraining.getSupervisordesignationid();

			int workflowid = 0;
			if (hrmsTrainingApprovalWorkflowRepository
					.existsBySupervisordesignationidAndActive(firstsupervisordesignationid, 1)) {

				HrmsTrainingApprovalWorkflow hrmsTrainingApprovalWorkflow = hrmsTrainingApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(firstsupervisordesignationid, 1);

				workflowid = hrmsTrainingApprovalWorkflow.getId();

				if (hrmsTrainingApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
						workflowid, hrmsEmployee.getDesignationId(), 1)) {

					HrmsTrainingApprovalWorkflowStep step = hrmsTrainingApprovalWorkflowStepRepository
							.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
									hrmsEmployee.getDesignationId(), 1);

					int stepnext = 0;
					if (step.getApproverdesignationnextid() != 0) {
						HrmsTrainingApprovalWorkflowStep step1 = hrmsTrainingApprovalWorkflowStepRepository
								.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
										step.getApproverdesignationnextid(), 1);

						stepnext = step1.getStepnumber();
					}

					HrmsTrainingApproval hrmsTrainingApproval = new HrmsTrainingApproval();

					hrmsTrainingApproval.setActive(1);
					hrmsTrainingApproval.setApproved(1);

					StringBuilder approvedby = new StringBuilder();

					if (hrmsEmployee.getFirstName() != null) {
						approvedby.append(hrmsEmployee.getFirstName().trim());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						approvedby.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					if (hrmsEmployee.getLastName() != null) {
						approvedby.append(" " + hrmsEmployee.getLastName().trim());
					}

					hrmsTrainingApproval.setApprovedby(approvedby.toString());

					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsByIdAndActive(hrmsEmployee.getDesignationId(), 1)) {
						hrmsTrainingApproval.setApproverdesignationid(hrmsEmployee.getDesignationId());
					}

					hrmsTrainingApproval.setDate_created(LocalDateTime.now());

					hrmsTrainingApproval.setDescription(comment);
					hrmsTrainingApproval.setStepid(step.getId());
					hrmsTrainingApproval.setStepnumber(step.getStepnumber());
					hrmsTrainingApproval.setStepnumbernext(stepnext);
					hrmsTrainingApproval.setTrainingid(id);
					hrmsTrainingApproval.setUnique_id(UUID.randomUUID());
					hrmsTrainingApproval.setWorkflowid(workflowid);
					hrmsTrainingApproval.setApproveruserid(supervisorid);

					hrmsTrainingApproval.setStatus(status);

					hrmsTrainingApprovalRepository.saveAndFlush(hrmsTrainingApproval);

					if (hrmsTrainingApprovalRepository.countByTrainingid(
							id) == hrmsTrainingApprovalWorkflowStepRepository.countByWorkflowid(workflowid)
							&& status == 1) {

						hrmsTraining.setDate_updated(LocalDateTime.now());
						hrmsTraining.setApproved(1);

						hrmsTrainingRepository.saveAndFlush(hrmsTraining);

						return ResponseEntity.status(HttpStatus.OK).body(hrmsTraining);

					} else {
						if (status == 0) {
							hrmsTraining.setDate_updated(LocalDateTime.now());
							hrmsTraining.setApproved(-1);// rejecting

							hrmsTrainingRepository.saveAndFlush(hrmsTraining);

							return ResponseEntity.status(HttpStatus.OK).body(hrmsTraining);

						} else {

							return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingApproval);
						}
					}

				} else {
					return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(workflowid);
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(id);
			}

		} else {

			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(id);
		}
	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingNotApprovedBySupervisorNext(int supervisorid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingResponse> trainingResponselist = new ArrayList<>();

		if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			List<HrmsTraining> dbms = hrmsTrainingRepository.findByApprovedAndActive(0, 1);

			for (HrmsTraining dbm : dbms) {

				if (supervisorid == dbm.getSupervisorid()) {

					if (hrmsTrainingApprovalRepository.existsByApproveruseridAndTrainingidAndActive(supervisorid,
							dbm.getId(), 1)) {
						// do nothing you already approved it
					} else {

						TrainingResponse trainingResponse = new TrainingResponse();

						trainingResponse.setActive(dbm.getActive());
						trainingResponse.setApproved(dbm.getApproved());

						if (dbm.getApproved() == 1) {
							trainingResponse.setApprovedStatus("Approved");
						}

						if (dbm.getApproved() == 0) {
							trainingResponse.setApprovedStatus("On Progress");
						}

						if (dbm.getApproved() == -1) {
							trainingResponse.setApprovedStatus("Rejected");
						}
						if (dbm.getUnattended() == 1) {
							trainingResponse.setUnattendedname("Attended");
						}

						if (dbm.getUnattended() == 0) {
							trainingResponse.setUnattendedname("UnAttended");
						}

						trainingResponse.setCurrencyid(dbm.getCurrencyid());

						if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
							trainingResponse
									.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
						}
						trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
						trainingResponse.setTrainingcost(dbm.getTrainingcost());
						trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

						if (dbm.getTrainingpurposeid() != 0
								&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
							trainingResponse.setTrainingpurpose(
									hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
						}
						trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

						if (dbm.getDate_expected_end() != null) {
							trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
						}

						if (dbm.getDateexpectedstart() != null) {
							trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
						}
						trainingResponse.setDelayed(dbm.getDelayed());
						trainingResponse.setDelayedreason(dbm.getDelayedreason());
						trainingResponse.setDescription(dbm.getDescription());
						if (dbm.getEmployeeid() != 0
								&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
							StringBuilder employeeFullName = new StringBuilder();
							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

							employeeFullName.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
							}

							employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
							trainingResponse.setEmployeeFullName(employeeFullName.toString());

							if (hrmsEmployee.getSectionid() != 0 && hrmsOrginisationUnitRepository
									.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
								trainingResponse.setSection(hrmsOrginisationUnitRepository
										.findById(hrmsEmployee.getSectionid()).get().getName());
							}

							if (hrmsEmployee.getUnitId() != 0 && hrmsOrginisationUnitRepository
									.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
								trainingResponse.setDepartment(hrmsOrginisationUnitRepository
										.findById(hrmsEmployee.getUnitId()).get().getName());

							}
						}

						trainingResponse.setEmployeeid(dbm.getEmployeeid());
						if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
							HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
									.findById(dbm.getFinancialyearid()).get();

							String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
							trainingResponse.setFinancialyear(fyear);
						}

						trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
						trainingResponse.setId(dbm.getId());
						trainingResponse.setInstitution(dbm.getInstitution());
						trainingResponse.setName(dbm.getName());

						if (dbm.getEmployeeid() != 0
								&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
							StringBuilder supervisor = new StringBuilder();
							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

							supervisor.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
							}

							supervisor.append(" " + hrmsEmployee.getLastName().trim());

							trainingResponse.setSupervisor(supervisor.toString());
						}

						trainingResponse.setSupervisorid(dbm.getSupervisorid());

						if (dbm.getSupervisordesignationid() != 0
								&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
							trainingResponse.setSupervisordesignation(hrmsDesignationRepository
									.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
						}
						trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

						if (dbm.getTrainingcategoryid() != 0
								&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
							trainingResponse.setTrainingcategory(hrmsTrainingCategoryRepository
									.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
						}
						trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
						if (dbm.getTraininginitiatorid() != 0 && hrmsTrainingInitiatorRepository
								.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
							trainingResponse.setTraininginitiator(hrmsTrainingInitiatorRepository
									.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

						}
						trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

						if (dbm.getTrainingsponsorid() != 0
								&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
							trainingResponse.setTrainingsponsor(hrmsTrainingSponsorRepository
									.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

						}
						trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
						if (dbm.getTrainingtypeid() != 0
								&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
							trainingResponse.setTrainingtype(
									hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
						}
						trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
						trainingResponse.setUnattended(dbm.getUnattended());
						trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
						trainingResponse.setUnplanned(dbm.getUnplanned());

						trainingResponselist.add(trainingResponse);

					}

				} else {

					// check if first supervisor has approved and this user has not approved

					if (hrmsTrainingApprovalRepository
							.existsByApproveruseridAndTrainingidAndActive(dbm.getSupervisorid(), dbm.getId(), 1)

							&& !hrmsTrainingApprovalRepository
									.existsByApproveruseridAndTrainingidAndActive(supervisorid, dbm.getId(), 1)) {

						// check if this supervisor is the next to approve by verifying if the back to
						// this has approved

						int workflowid = hrmsTrainingApprovalRepository
								.findFirstByApproveruseridAndTrainingidAndActive(dbm.getSupervisorid(), dbm.getId(), 1)
								.getWorkflowid();

						// check if the step number of this supervisor on step

						// get supervisor designation

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(supervisorid).get();

						if (hrmsTrainingApprovalWorkflowStepRepository
								.existsByWorkflowidAndApproverdesignationidAndActive(workflowid,
										hrmsEmployee.getDesignationId(), 1)) {

							HrmsTrainingApprovalWorkflowStep step = hrmsTrainingApprovalWorkflowStepRepository
									.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
											hrmsEmployee.getDesignationId(), 1);

							int stepnow = step.getStepnumber();

							int designationprev = step.getApproverdesignationprevid();

							// check if this user has approved already

							if (hrmsTrainingApprovalRepository
									.existsByApproverdesignationidAndTrainingidAndActiveAndStatus(designationprev,
											dbm.getId(), 1, 1)) {

								// add this training as it deserves to be approved

								TrainingResponse trainingResponse = new TrainingResponse();

								trainingResponse.setActive(dbm.getActive());
								trainingResponse.setApproved(dbm.getApproved());

								if (dbm.getApproved() == 1) {
									trainingResponse.setApprovedStatus("Approved");
								}

								if (dbm.getApproved() == 0) {
									trainingResponse.setApprovedStatus("On Progress");
								}

								if (dbm.getApproved() == -1) {
									trainingResponse.setApprovedStatus("Rejected");
								}

								if (dbm.getUnattended() == 1) {
									trainingResponse.setUnattendedname("Attended");
								}

								if (dbm.getUnattended() == 0) {
									trainingResponse.setUnattendedname("UnAttended");
								}

								trainingResponse.setCurrencyid(dbm.getCurrencyid());

								if (dbm.getCurrencyid() != 0
										&& hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
									trainingResponse.setCurrency(
											hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
								}
								trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
								trainingResponse.setTrainingcost(dbm.getTrainingcost());
								trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

								if (dbm.getTrainingpurposeid() != 0
										&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
									trainingResponse.setTrainingpurpose(hrmsTrainingPurposeRepository
											.findById(dbm.getTrainingpurposeid()).get().getName());
								}
								trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

								if (dbm.getDate_expected_end() != null) {
									trainingResponse
											.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
								}

								if (dbm.getDateexpectedstart() != null) {
									trainingResponse
											.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
								}
								trainingResponse.setDelayed(dbm.getDelayed());
								trainingResponse.setDelayedreason(dbm.getDelayedreason());
								trainingResponse.setDescription(dbm.getDescription());
								if (dbm.getEmployeeid() != 0
										&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
									StringBuilder employeeFullName = new StringBuilder();
									HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository.findById(dbm.getEmployeeid())
											.get();

									employeeFullName.append(hrmsEmployee1.getFirstName().trim());
									if (hrmsEmployee1.getMiddleName() != null) {
										employeeFullName.append(" " + hrmsEmployee1.getMiddleName().trim());
									}

									employeeFullName.append(" " + hrmsEmployee1.getLastName().trim());
									trainingResponse.setEmployeeFullName(employeeFullName.toString());

									if (hrmsEmployee1.getSectionid() != 0 && hrmsOrginisationUnitRepository
											.existsByIdAndActive(hrmsEmployee1.getSectionid(), 1)) {
										trainingResponse.setSection(hrmsOrginisationUnitRepository
												.findById(hrmsEmployee1.getSectionid()).get().getName());
									}

									if (hrmsEmployee1.getUnitId() != 0 && hrmsOrginisationUnitRepository
											.existsByIdAndActive(hrmsEmployee1.getUnitId(), 1)) {
										trainingResponse.setDepartment(hrmsOrginisationUnitRepository
												.findById(hrmsEmployee1.getUnitId()).get().getName());

									}
								}

								trainingResponse.setEmployeeid(dbm.getEmployeeid());
								if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(),
										1)) {
									HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
											.findById(dbm.getFinancialyearid()).get();

									String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
									trainingResponse.setFinancialyear(fyear);
								}

								trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
								trainingResponse.setId(dbm.getId());
								trainingResponse.setInstitution(dbm.getInstitution());
								trainingResponse.setName(dbm.getName());

								if (dbm.getEmployeeid() != 0
										&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
									StringBuilder supervisor = new StringBuilder();
									HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getSupervisorid())
											.get();

									supervisor.append(hrmsEmployee2.getFirstName().trim());
									if (hrmsEmployee2.getMiddleName() != null) {
										supervisor.append(" " + hrmsEmployee2.getMiddleName().trim());
									}

									supervisor.append(" " + hrmsEmployee2.getLastName().trim());

									trainingResponse.setSupervisor(supervisor.toString());
								}

								trainingResponse.setSupervisorid(dbm.getSupervisorid());

								if (dbm.getSupervisordesignationid() != 0 && hrmsDesignationRepository
										.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
									trainingResponse.setSupervisordesignation(hrmsDesignationRepository
											.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
								}
								trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

								if (dbm.getTrainingcategoryid() != 0 && hrmsTrainingCategoryRepository
										.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
									trainingResponse.setTrainingcategory(hrmsTrainingCategoryRepository
											.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
								}
								trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
								if (dbm.getTraininginitiatorid() != 0 && hrmsTrainingInitiatorRepository
										.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
									trainingResponse.setTraininginitiator(hrmsTrainingInitiatorRepository
											.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

								}
								trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

								if (dbm.getTrainingsponsorid() != 0 && hrmsTrainingSponsorRepository
										.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
									trainingResponse.setTrainingsponsor(hrmsTrainingSponsorRepository
											.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

								}
								trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
								if (dbm.getTrainingtypeid() != 0
										&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
									trainingResponse.setTrainingtype(hrmsTrainingTypeRepository
											.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
								}
								trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
								trainingResponse.setUnattended(dbm.getUnattended());
								trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
								trainingResponse.setUnplanned(dbm.getUnplanned());

								trainingResponselist.add(trainingResponse);

							}

						}

					}

				}

			}

			return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);

		} else {

			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(null);
		}
	}

	@Override
	public ResponseEntity<List<TrainingApprovalStatus>> getHrmsTrainingApprovers(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingApprovalStatus> trainingApprovalStatuslist = new ArrayList<>();

		if (hrmsTrainingRepository.existsByIdAndActive(id, 1)) {

			HrmsTraining hrmsTraining = hrmsTrainingRepository.findByIdAndActive(id, 1);
			if (hrmsTraining.getTraininginitiatorid() == 1
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsTraining.getRequestedby(), 1)) { // this is HR

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndActive(hrmsTraining.getRequestedby(), 1);
				TrainingApprovalStatus trainingApprovalStatus = new TrainingApprovalStatus();

				trainingApprovalStatus.setApproved(hrmsTraining.getApproved());

				if (hrmsTraining.getDate_created() != null) {

					String dateapproved = hrmsTraining.getDate_created()
							.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
					trainingApprovalStatus.setDateApproved(dateapproved);
				}
				// do not set description of approval as it does not exist
				trainingApprovalStatus.setDescription("");

				trainingApprovalStatus.setId(id);
				if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

					char initial = hrmsEmployee.getFirstName().charAt(0);
					trainingApprovalStatus.setSupervisorInitial(initial);
				}

				if (hrmsEmployee != null) {

					StringBuilder supervisorName = new StringBuilder();

					supervisorName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					supervisorName.append(" " + hrmsEmployee.getLastName().trim());

					trainingApprovalStatus.setSupervisorName(supervisorName.toString());

				}
				if (hrmsTraining.getApproved() == 1) {
					trainingApprovalStatus.setTrainingStatus("Approved");

					trainingApprovalStatus.setApprovalStatus("Approved");
				}

				if (hrmsTraining.getApproved() == 0) {
					trainingApprovalStatus.setTrainingStatus("Submitted");
					trainingApprovalStatus.setApprovalStatus("Submitted");
				}

				if (hrmsTraining.getApproved() == -1) {
					trainingApprovalStatus.setTrainingStatus("Rejected");
					trainingApprovalStatus.setApprovalStatus("Rejected");
				}

				trainingApprovalStatuslist.add(trainingApprovalStatus);

			} else {

				int supervisordesignationid = hrmsTraining.getSupervisordesignationid();

				if (hrmsTrainingApprovalWorkflowRepository
						.existsBySupervisordesignationidAndActive(supervisordesignationid, 1)) {
					int workflowid = hrmsTrainingApprovalWorkflowRepository
							.findBySupervisordesignationidAndActive(supervisordesignationid, 1).getId();

					if (hrmsTrainingApprovalWorkflowStepRepository.existsByWorkflowidAndActive(workflowid, 1)) {

						List<HrmsTrainingApprovalWorkflowStep> dbms = hrmsTrainingApprovalWorkflowStepRepository
								.findByWorkflowidAndActiveOrderByStepnumberAsc(workflowid, 1);

						for (HrmsTrainingApprovalWorkflowStep dbm : dbms) {

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
									.findFirstByDesignationIdAndIssupervisorAndActive(dbm.getApproverdesignationid(), 1,
											1);

							if (hrmsTrainingApprovalRepository.existsByApproverdesignationidAndTrainingidAndActive(
									dbm.getApproverdesignationid(), id, 1)) {

								HrmsTrainingApproval hrmsTrainingApproval = hrmsTrainingApprovalRepository
										.findByApproverdesignationidAndTrainingidAndActive(
												dbm.getApproverdesignationid(), id, 1);

								TrainingApprovalStatus trainingApprovalStatus = new TrainingApprovalStatus();

								if (hrmsTrainingApproval != null && hrmsTrainingApproval.getStatus() == 1) {
									trainingApprovalStatus.setApprovalStatus("Approved");
								}

								if (hrmsTrainingApproval != null && hrmsTrainingApproval.getStatus() == 0) {
									trainingApprovalStatus.setApprovalStatus("Rejected");
								}

								if (hrmsTrainingApproval == null) {
									trainingApprovalStatus.setApprovalStatus("Pending");
								}

								trainingApprovalStatus.setApproved(hrmsTraining.getApproved());

								if (hrmsTrainingApproval != null && hrmsTrainingApproval.getDate_created() != null) {

									String dateapproved = hrmsTrainingApproval.getDate_created()
											.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

									trainingApprovalStatus.setDateApproved(dateapproved);
								}
								if (hrmsTrainingApproval != null) {
									trainingApprovalStatus.setDescription(hrmsTrainingApproval.getDescription());
								}

								trainingApprovalStatus.setId(id);
								if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

									char initial = hrmsEmployee.getFirstName().charAt(0);
									trainingApprovalStatus.setSupervisorInitial(initial);
								}

								if (hrmsEmployee != null) {

									StringBuilder supervisorName = new StringBuilder();

									supervisorName.append(hrmsEmployee.getFirstName().trim());
									if (hrmsEmployee.getMiddleName() != null) {
										supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
									}

									supervisorName.append(" " + hrmsEmployee.getLastName().trim());

									trainingApprovalStatus.setSupervisorName(supervisorName.toString());

								}
								if (hrmsTraining.getApproved() == 1) {
									trainingApprovalStatus.setTrainingStatus("Approved");
								}

								if (hrmsTraining.getApproved() == 0) {
									trainingApprovalStatus.setTrainingStatus("Submitted");
								}

								if (hrmsTraining.getApproved() == -1) {
									trainingApprovalStatus.setTrainingStatus("Rejected");
								}

								trainingApprovalStatuslist.add(trainingApprovalStatus);

							} else {
								// approver has not yet approved so get his/her details in a way around that
								// checking on approval table

								// confirm or check if previous approver rejected this training then do not
								// return the rest approvers
								if (dbm.getApproverdesignationprevid() != 0 && hrmsTrainingApprovalRepository
										.existsByStatusAndTrainingidAndActive(0, id, 1)) {

								} else {

									TrainingApprovalStatus trainingApprovalStatus = new TrainingApprovalStatus();

									trainingApprovalStatus.setApprovalStatus("Pending");

									trainingApprovalStatus.setApproved(hrmsTraining.getApproved());

									// do not set date approved as it does not exist
									trainingApprovalStatus.setDateApproved("");

									// do not set description of approval as it does not exist
									trainingApprovalStatus.setDescription("");

									trainingApprovalStatus.setId(id);
									if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

										char initial = hrmsEmployee.getFirstName().charAt(0);
										trainingApprovalStatus.setSupervisorInitial(initial);
									}

									if (hrmsEmployee != null) {

										StringBuilder supervisorName = new StringBuilder();

										supervisorName.append(hrmsEmployee.getFirstName().trim());
										if (hrmsEmployee.getMiddleName() != null) {
											supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
										}

										supervisorName.append(" " + hrmsEmployee.getLastName().trim());

										trainingApprovalStatus.setSupervisorName(supervisorName.toString());

									}
									if (hrmsTraining.getApproved() == 1) {
										trainingApprovalStatus.setTrainingStatus("Approved");
									}

									if (hrmsTraining.getApproved() == 0) {
										trainingApprovalStatus.setTrainingStatus("Submitted");
									}

									if (hrmsTraining.getApproved() == -1) {
										trainingApprovalStatus.setTrainingStatus("Rejected");
									}

									trainingApprovalStatuslist.add(trainingApprovalStatus);

								}

							}

						}

					}

				}

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingApprovalStatuslist);
	}

	@Override
	public ResponseEntity<?> updateAfterAttendingTraining(int id, int attended, String attendedReason) {
		if (hrmsTrainingRepository.existsByIdAndActiveAndApproved(id, 1, 0)
				&& !hrmsTrainingApprovalRepository.existsByStatusAndTrainingidAndActive(1, id, 1)) {

			HrmsTraining hrmsTraining = hrmsTrainingRepository.findByIdAndActive(id, 1);

			hrmsTraining.setUnattended(attended);
			hrmsTraining.setUnattendedreason(attendedReason);
			hrmsTraining.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsTrainingRepository.saveAndFlush(hrmsTraining));

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}

	}

	@Override
	public ResponseEntity<List<TrainingResponse>> getHrmsTrainingApprovedByEmployeeId(int Employeeid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<TrainingResponse> trainingResponselist = new ArrayList<>();
		int year = LocalDateTime.now().getYear();

		int month = LocalDateTime.now().getMonthValue();
		int yearstart = year;

		int yearend = year + 1;
		if (month < 7) {

			yearstart = year - 1;

			yearend = year;

		}

		List<HrmsTraining> dbms = hrmsTrainingRepository.findByEmployeeidAndActiveAndApproved(Employeeid, 1, 1);

		for (HrmsTraining dbm : dbms) {

			TrainingResponse trainingResponse = new TrainingResponse();

			trainingResponse.setActive(dbm.getActive());
			trainingResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 1) {
				trainingResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 0) {
				trainingResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == -1) {
				trainingResponse.setApprovedStatus("Rejected");
			}

			trainingResponse.setUnattended(dbm.getUnattended());
			if (dbm.getUnattended() == 1) {
				trainingResponse.setUnattendedname("Attended");
			}

			if (dbm.getUnattended() == 0) {
				trainingResponse.setUnattendedname("UnAttended");
			}

			trainingResponse.setCurrencyid(dbm.getCurrencyid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				trainingResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			trainingResponse.setInstitutionaddress(dbm.getInstitutionaddress());
			trainingResponse.setTrainingcost(dbm.getTrainingcost());
			trainingResponse.setTrainingpurposeid(dbm.getTrainingpurposeid());

			if (dbm.getTrainingpurposeid() != 0
					&& hrmsTrainingPurposeRepository.existsById(dbm.getTrainingpurposeid())) {
				trainingResponse.setTrainingpurpose(
						hrmsTrainingPurposeRepository.findById(dbm.getTrainingpurposeid()).get().getName());
			}
			trainingResponse.setFeestructureattachment(dbm.getFeestructureattachment());

			if (dbm.getDate_expected_end() != null) {
				trainingResponse.setDateexpectedend(simpleDateFormat.format(dbm.getDate_expected_end()));
			}

			if (dbm.getDateexpectedstart() != null) {
				trainingResponse.setDateexpectedstart(simpleDateFormat.format(dbm.getDateexpectedstart()));
			}
			trainingResponse.setDelayed(dbm.getDelayed());
			trainingResponse.setDelayedreason(dbm.getDelayedreason());
			trainingResponse.setDescription(dbm.getDescription());
			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
				StringBuilder employeeFullName = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				employeeFullName.append(" " + hrmsEmployee.getLastName().trim());
				trainingResponse.setEmployeeFullName(employeeFullName.toString());

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {
					trainingResponse.setSection(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid()).get().getName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {
					trainingResponse.setDepartment(
							hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());

				}
			}

			trainingResponse.setEmployeeid(dbm.getEmployeeid());
			if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(dbm.getFinancialyearid(), 1)) {
				HrmsPerformanceFinancialYear dbmx = hrmsPerformanceFinancialYearRepository
						.findById(dbm.getFinancialyearid()).get();

				String fyear = dbmx.getYearstarting() + "/" + dbmx.getYearending();
				trainingResponse.setFinancialyear(fyear);
			}

			trainingResponse.setFinancialyearid(dbm.getFinancialyearid());
			trainingResponse.setId(dbm.getId());
			trainingResponse.setInstitution(dbm.getInstitution());
			trainingResponse.setName(dbm.getName());

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsByIdAndActive(dbm.getSupervisorid(), 1)) {
				StringBuilder supervisor = new StringBuilder();
				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getSupervisorid()).get();

				supervisor.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					supervisor.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				supervisor.append(" " + hrmsEmployee.getLastName().trim());

				trainingResponse.setSupervisor(supervisor.toString());
			}

			trainingResponse.setSupervisorid(dbm.getSupervisorid());

			if (dbm.getSupervisordesignationid() != 0
					&& hrmsDesignationRepository.existsByIdAndActive(dbm.getSupervisordesignationid(), 1)) {
				trainingResponse.setSupervisordesignation(
						hrmsDesignationRepository.findByIdAndActive(dbm.getSupervisordesignationid(), 1).getName());
			}
			trainingResponse.setSupervisordesignationid(dbm.getSupervisordesignationid());

			if (dbm.getTrainingcategoryid() != 0
					&& hrmsTrainingCategoryRepository.existsByIdAndActive(dbm.getTrainingcategoryid(), 1)) {
				trainingResponse.setTrainingcategory(
						hrmsTrainingCategoryRepository.findByIdAndActive(dbm.getTrainingcategoryid(), 1).getName());
			}
			trainingResponse.setTrainingcategoryid(dbm.getTrainingcategoryid());
			if (dbm.getTraininginitiatorid() != 0
					&& hrmsTrainingInitiatorRepository.existsByIdAndActive(dbm.getTraininginitiatorid(), 1)) {
				trainingResponse.setTraininginitiator(
						hrmsTrainingInitiatorRepository.findByIdAndActive(dbm.getTraininginitiatorid(), 1).getName());

			}
			trainingResponse.setTraininginitiatorid(dbm.getTraininginitiatorid());

			if (dbm.getTrainingsponsorid() != 0
					&& hrmsTrainingSponsorRepository.existsByIdAndActive(dbm.getTrainingsponsorid(), 1)) {
				trainingResponse.setTrainingsponsor(
						hrmsTrainingSponsorRepository.findByIdAndActive(dbm.getTrainingsponsorid(), 1).getName());

			}
			trainingResponse.setTrainingsponsorid(dbm.getTrainingsponsorid());
			if (dbm.getTrainingtypeid() != 0
					&& hrmsTrainingTypeRepository.existsByIdAndActive(dbm.getTrainingtypeid(), 1)) {
				trainingResponse.setTrainingtype(
						hrmsTrainingTypeRepository.findByIdAndActive(dbm.getTrainingtypeid(), 1).getName());
			}
			trainingResponse.setTrainingtypeid(dbm.getTrainingtypeid());
			trainingResponse.setUnattended(dbm.getUnattended());
			trainingResponse.setUnattendedreason(dbm.getUnattendedreason());
			trainingResponse.setUnplanned(dbm.getUnplanned());

			trainingResponselist.add(trainingResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(trainingResponselist);
	}

}
