package com.Hrms.Perfomance.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmploymentCategory;
import com.Hrms.Employee.Entity.HrmsOrginisationUnit;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmploymentCategoryRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeCategoryRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;
import com.Hrms.Perfomance.DTO.PerformanceEabFactorOprasResponse;
import com.Hrms.Perfomance.DTO.PerformanceEabQualityOprasResponse;
import com.Hrms.Perfomance.DTO.PerformanceOprasForm;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjectiveRevised;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewAnnual;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaReviewMidYear;
import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabFactorRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabQualityRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEabRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaObjectiveRevisedRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaReviewAnnualRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceEppaReviewMidYearRepository;
import com.Hrms.Perfomance.Repository.HrmsPerformanceFinancialYearRepository;

@Service
public class HrmsPerformanceOprasFormServiceImpl implements HrmsPerformanceOprasFormService {

	@Autowired
	private HrmsPerformanceEabFactorRepository hrmsPerformanceEabFactorRepository;

	@Autowired
	private HrmsPerformanceEabQualityRepository hrmsPerformanceEabQualityRepository;

	@Autowired
	private HrmsPerformanceEabRepository hrmsPerformanceEabRepository;

	@Autowired
	private HrmsPerformanceEppaRepository hrmsPerformanceEppaRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired

	private HrmsPerformanceEppaObjectiveRevisedRepository hrmsPerformanceEppaObjectiveRevisedRepository;

	@Autowired
	private HrmsPerformanceEppaObjectiveRepository hrmsPerformanceEppaObjectiveRepository;

	@Autowired
	private HrmsPerformanceEppaReviewAnnualRepository hrmsPerformanceEppaReviewAnnualRepository;

	@Autowired
	private HrmsPerformanceEppaReviewMidYearRepository hrmsPerformanceEppaReviewMidYearRepository;

	@Autowired
	private HrmsPerformanceFinancialYearRepository hrmsPerformanceFinancialYearRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Autowired

	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired

	private HrmsOrganisationOfficeCategoryRepository hrmsOrganisationOfficeCategoryRepository;

	@Autowired

	private HrmsEmploymentCategoryRepository hrmsEmploymentCategoryRepository;

	@Override
	public ResponseEntity<PerformanceOprasForm> getPerformanceOprasFormByFinancialYearAndEmployeeid(int financialyearid,
			int employeeid) {

		List<Integer> approveds = new ArrayList<>();

		approveds.add(1);
		approveds.add(0);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		PerformanceOprasForm performanceOprasForm = new PerformanceOprasForm();

		int eppaid = 0;
		Double sumQualityAtributeApraisee = 0.0;
		Double sumQualityAtributeSupervisor = 0.0;
		Double sumQualityAtributeAgreed = 0.0;
		int totalAttribute = 0;

		if (hrmsPerformanceEppaRepository.existsByEmployeeidAndFinancialyearidAndActive(employeeid, financialyearid,
				1)) {

			HrmsPerformanceEppa hrmsPerformanceEppa = hrmsPerformanceEppaRepository
					.findByEmployeeidAndFinancialyearid(employeeid, financialyearid);
			eppaid = hrmsPerformanceEppa.getId();

			performanceOprasForm.setPerformanceEppa(hrmsPerformanceEppa);

			List<HrmsPerformanceEppaObjective> performanceEppaObjectiveagreedlist = new ArrayList<>();
			List<HrmsPerformanceEppaObjectiveRevised> performanceEppaObjectiveRevisedlist = new ArrayList<>();

			List<HrmsPerformanceEppaObjective> dbms1 = hrmsPerformanceEppaObjectiveRepository
					.findByEppaidAndActive(eppaid, 1);
			for (HrmsPerformanceEppaObjective dbm1 : dbms1) {

				if (hrmsPerformanceEppaObjectiveRevisedRepository.existsByIdAndActive(dbm1.getId(), 1)) {

					List<HrmsPerformanceEppaObjectiveRevised> dbms3 = hrmsPerformanceEppaObjectiveRevisedRepository
							.findByObjectiveidAndActive(dbm1.getId(), 1);
					for (HrmsPerformanceEppaObjectiveRevised dbm3 : dbms3) {
						performanceEppaObjectiveRevisedlist.add(dbm3);
					}

				}

				performanceEppaObjectiveagreedlist.add(dbm1);

			}

			performanceOprasForm.setPerformanceEppaObjectiveagreedlist(performanceEppaObjectiveagreedlist);
			performanceOprasForm.setPerformanceEppaObjectiveRevisedlist(performanceEppaObjectiveRevisedlist);

			List<HrmsPerformanceEppaReviewMidYear> performanceEppaReviewMidYearlist = new ArrayList<>();

			List<HrmsPerformanceEppaReviewMidYear> dbms2 = hrmsPerformanceEppaReviewMidYearRepository
					.findByEppaidAndActiveAndApprovedIn(eppaid, 1, approveds);
			for (HrmsPerformanceEppaReviewMidYear dbm2 : dbms2) {

				performanceEppaReviewMidYearlist.add(dbm2);
			}

			performanceOprasForm.setPerformanceEppaReviewMidYearlist(performanceEppaReviewMidYearlist);
			List<HrmsPerformanceEppaReviewAnnual> performanceEppaReviewAnnuallist = new ArrayList<>();

			List<HrmsPerformanceEppaReviewAnnual> dbms4 = hrmsPerformanceEppaReviewAnnualRepository
					.findByEppaidAndActive(eppaid, 1);

			for (HrmsPerformanceEppaReviewAnnual dbm4 : dbms4) {

				performanceEppaReviewAnnuallist.add(dbm4);

			}

			performanceOprasForm.setPerformanceEppaReviewAnnuallist(performanceEppaReviewAnnuallist);

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

				// totalAttribute = totalAttribute
				// + hrmsPerformanceEabQualityRepository.countByFactoridAndActive(dbm5.getId(),
				// 1);

				for (HrmsPerformanceEabQuality dbm6 : dbms6) {
					PerformanceEabQualityOprasResponse performanceEabQualityOprasResponse = new PerformanceEabQualityOprasResponse();

					performanceEabQualityOprasResponse.setActive(dbm6.getActive());
					performanceEabQualityOprasResponse.setApproved(dbm6.getApproved());
					performanceEabQualityOprasResponse.setDescription(dbm6.getDescription());
					performanceEabQualityOprasResponse.setFactorid(dbm6.getFactorid());
					performanceEabQualityOprasResponse.setId(dbm6.getId());
					performanceEabQualityOprasResponse.setName(dbm6.getName());

					HrmsPerformanceEab performanceEab = new HrmsPerformanceEab();
					if (hrmsPerformanceEabRepository.existsByEppaidAndQualityidAndEmployeeidAndActive(eppaid,
							dbm6.getId(), employeeid, 1)) {
						totalAttribute = totalAttribute + 1;

						performanceEab = hrmsPerformanceEabRepository
								.findByEppaidAndQualityidAndEmployeeidAndActive(eppaid, dbm6.getId(), employeeid, 1);

						if (performanceEab.getRatingappraisee() != null) {
							sumQualityAtributeApraisee = sumQualityAtributeApraisee
									+ performanceEab.getRatingappraisee();
						}

						if (performanceEab.getRatingsupervisor() != null) {
							sumQualityAtributeSupervisor = sumQualityAtributeSupervisor
									+ performanceEab.getRatingsupervisor();
						}

						if (performanceEab.getRatingagreed() != null) {
							sumQualityAtributeAgreed = sumQualityAtributeAgreed + performanceEab.getRatingagreed();
						}

					}

					performanceEabQualityOprasResponse.setPerformanceEab(performanceEab);

					performanceEabQualityOprasResponselist.add(performanceEabQualityOprasResponse);

				}

				performanceEabFactorOprasResponse
						.setPerformanceEabQualityOprasResponselist(performanceEabQualityOprasResponselist);

				performanceEabFactorOprasResponselist.add(performanceEabFactorOprasResponse);
			}

			performanceOprasForm.setPerformanceEabFactorOprasResponselist(performanceEabFactorOprasResponselist);

			performanceOprasForm.setSumQualityAtributeAgreed(sumQualityAtributeAgreed);

			performanceOprasForm.setSumQualityAtributeApraisee(sumQualityAtributeApraisee);

			performanceOprasForm.setSumQualityAtributeSupervisor(sumQualityAtributeSupervisor);

			Double averageQualityAtributeAgreed = 0.0;
			Double averageQualityAtributeApraisee = 0.0;
			Double averageQualityAtributeSupervisor = 0.0;

			if (totalAttribute != 0) {
				averageQualityAtributeAgreed = sumQualityAtributeAgreed / totalAttribute;

				averageQualityAtributeApraisee = sumQualityAtributeApraisee / totalAttribute;

				averageQualityAtributeSupervisor = sumQualityAtributeSupervisor / totalAttribute;
			}

			performanceOprasForm.setAverageQualityAtributeAgreed(averageQualityAtributeAgreed);
			performanceOprasForm.setAverageQualityAtributeApraisee(averageQualityAtributeApraisee);
			performanceOprasForm.setAverageQualityAtributeSupervisor(averageQualityAtributeSupervisor);

			Double overallPerformance = 0.0; //
			performanceOprasForm.setOverallPerformance(overallPerformance);

		}
		if (hrmsEmployeeRepository.existsByIdAndActive(employeeid, 1)) {
			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndActive(employeeid, 1);
			if (hrmsEmployee.getDob() != null) {
				performanceOprasForm.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
			}

			if (hrmsEmployee.getDateofemployment() != null) {
				performanceOprasForm
						.setDateOfFirstAppointment(simpleDateFormat.format(hrmsEmployee.getDateofemployment()));
				performanceOprasForm
						.setDateOfPresentAppointment(simpleDateFormat.format(hrmsEmployee.getDateofemployment()));
			}

			List<Integer> units = new ArrayList<>();
			units.add(hrmsEmployee.getUnitId());
			units.add(hrmsEmployee.getSectionid());
			// int[] units = { hrmsEmployee.getUnitId(), hrmsEmployee.getSectionid() };
			if (hrmsOrginisationUnitRepository.existsByIdIn(units)) {
				HrmsOrginisationUnit hrmsOrginisationUnit = hrmsOrginisationUnitRepository
						.findById(hrmsEmployee.getUnitId()).get();
				HrmsOrginisationUnit sections = hrmsOrginisationUnitRepository.findById(hrmsEmployee.getSectionid())
						.get();

				String drectorateOrUnit = hrmsOrginisationUnit.getName() + "/" + sections.getName();

				performanceOprasForm.setDrectorateOrUnit(drectorateOrUnit);
			}

			if (hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
				HrmsEmploymentCategory hrmsEmploymentCategory = hrmsEmploymentCategoryRepository
						.findById(hrmsEmployee.getEmploymentcategoryId()).get();
				String TermsOfEmployment = hrmsEmploymentCategory.getName();

				performanceOprasForm.setTermsOfEmployment(TermsOfEmployment);
			}

			Double durationserverUnderSupervisor = 0.0;
			performanceOprasForm.setDurationserverUnderSupervisor(durationserverUnderSupervisor);

			if (hrmsEmployee.getDutystationid() != 0
					&& hrmsOrganisationOfficeRepository.existsById(hrmsEmployee.getDutystationid())) {
				String dutystation = hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).get()
						.getName();
				if (hrmsOrganisationOfficeCategoryRepository.existsById(hrmsOrganisationOfficeRepository
						.findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())) {
					String dutystationtype = hrmsOrganisationOfficeCategoryRepository
							.findById(hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).get()
									.getOfficetypeid())
							.get().getName();

					String dutyStation = dutystationtype.toUpperCase() + " - " + dutystation.toUpperCase();
					performanceOprasForm.setDutyStation(dutyStation);
					String dutyPost = dutyStation;
					performanceOprasForm.setDutyPost(dutyPost);
				}
			}

			performanceOprasForm.setEmployeeid(employeeid);

			StringBuilder employeeFullName = new StringBuilder();

			employeeFullName.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				employeeFullName.append(" " + hrmsEmployee.getMiddleName().trim());
			}
			employeeFullName.append(" " + hrmsEmployee.getLastName().trim());

			performanceOprasForm.setEmployeeName(employeeFullName.toString());

			if (hrmsPerformanceFinancialYearRepository.existsByIdAndActive(financialyearid, 1)) {
				HrmsPerformanceFinancialYear hrmsPerformanceFinancialYear = hrmsPerformanceFinancialYearRepository
						.findByIdAndActive(financialyearid, 1);

				int startext = hrmsPerformanceFinancialYear.getYearstarting() + 1;

				String s = String.valueOf(startext);
				if (s.length() > 2) {
					s = s.substring(s.length() - 2);
				}

				int endext = hrmsPerformanceFinancialYear.getYearstarting() + 1;

				String e = String.valueOf(endext);

				if (e.length() > 2) {
					e = e.substring(e.length() - 2);
				}

				String financialyear = hrmsPerformanceFinancialYear.getYearstarting() + "/" + s + " -"
						+ hrmsPerformanceFinancialYear.getYearending() + "/" + e;
				performanceOprasForm.setFinancialYear(financialyear);

				performanceOprasForm.setReviewPeriod(financialyear);
			}

			performanceOprasForm.setFinancialYearId(financialyearid);

			performanceOprasForm.setGender(hrmsEmployee.getGenderid());

			if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(employeeid, 1)) {
				int salarystructureid = hrmsemployeesalaryRepository.findByEmployeeidAndActive(employeeid, 1)
						.getSalarystructureId();

				if (hrmsSalarystructureRepository.existsById(salarystructureid)) {

					int salaryscaleid = hrmsSalarystructureRepository.findById(salarystructureid).get().getScaleId();

					if (hrmsSalaryScaleRepository.existsById(salaryscaleid)) {
						// String salaryscale =
						// hrmsSalaryScaleRepository.findById(salaryscaleid).get().getName();
						if (hrmsSalaryScaleRepository.existsById(salaryscaleid)) {
							// String salaryscale =
							// hrmsSalaryScaleRepository.findById(salaryscaleid).get().getName();

							String salaryScale = hrmsSalaryScaleRepository
									.findById(hrmsSalarystructureRepository.findById(salarystructureid).get()
											.getScaleId())
									.get().getName() + "-"
									+ hrmsSalaryscalenotchRepository.findById(hrmsSalarystructureRepository
											.findById(salarystructureid).get().getNotchId()).get().getName();

							performanceOprasForm.setSalaryScale(salaryScale);

						}

					}
				}
			}

			if (hrmsEmployee.getDesignationId() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
				performanceOprasForm.setSubstantivePost(
						hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
			}
		}

		return ResponseEntity.status(HttpStatus.OK).body(performanceOprasForm);
	}

}
