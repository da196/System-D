package com.Hrms.Payroll.Service;

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

import com.Hrms.Employee.Entity.HrmsAllowance;
import com.Hrms.Employee.Entity.HrmsAllowancetype;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;
import com.Hrms.Employee.Entity.HrmsSalarystructure;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Repository.HrmsAllowanceRepository;
import com.Hrms.Employee.Repository.HrmsAllowancetypeRepository;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeSocialSecuritySchemeRepositoty;
import com.Hrms.Employee.Repository.HrmsInsuranceProviderRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeCategoryRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;
import com.Hrms.Payroll.DTO.Allowance;
import com.Hrms.Payroll.DTO.DeductionLoan;
import com.Hrms.Payroll.DTO.DeductionMandatoryInsurance;
import com.Hrms.Payroll.DTO.DeductionMandatoryPension;
import com.Hrms.Payroll.DTO.DeductionVoluntary;
import com.Hrms.Payroll.DTO.PayrollProcessResponse;
import com.Hrms.Payroll.DTO.PayrollResponse;
import com.Hrms.Payroll.DTO.PayrollResponses;
import com.Hrms.Payroll.DTO.PayrollResponsesv2;
import com.Hrms.Payroll.DTO.PayrollResponsev2;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccount;
import com.Hrms.Payroll.Entity.HrmsPayroll;
import com.Hrms.Payroll.Entity.HrmsPayrollBankAccount;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatoryInsurance;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeVoluntary;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntary;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionLoan;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryInsurance;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionTax;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntary;
import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoan;
import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;
import com.Hrms.Payroll.Entity.HrmsPayrollTaxStructure;
import com.Hrms.Payroll.Repository.HrmsEmployeeBankAccountRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollBankAccountRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatoryInsuranceRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatorySocialSecuritySchemeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryServiceProviderRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionLoanRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionMandatoryInsuranceRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionTaxRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollEmployeeLoanRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanTypeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxStructureRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxTypeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTypeRepository;
import com.Hrms.Payroll.Repository.HrmsSocialSecuritySchemeServiceProviderRepository;

@Service
public class HrmsPayrollServiceImpl implements HrmsPayrollService {
	@Autowired
	private HrmsPayrollRepository hrmsPayrollRepository;
	@Autowired

	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsEmployeeSocialSecuritySchemeRepositoty hrmsEmployeeSocialSecuritySchemeRepositoty;

	@Autowired
	private HrmsPayrollContributionMandatorySocialSecuritySchemeRepository hrmsPayrollContributionMandatorySocialSecuritySchemeRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderRepository hrmsSocialSecuritySchemeServiceProviderRepository;

	@Autowired
	private HrmsPayrollContributionMandatoryInsuranceRepository hrmsPayrollContributionMandatoryInsuranceRepository;

	@Autowired
	private HrmsInsuranceProviderRepository hrmsInsuranceProviderRepository;

	@Autowired
	private HrmsPayrollContributionVoluntaryRepository hrmsPayrollContributionVoluntaryRepository;

	@Autowired
	private HrmsPayrollContributionTypeVoluntaryRepository hrmsPayrollContributionTypeVoluntaryRepository;

	@Autowired
	private HrmsPayrollContributionVoluntaryServiceProviderRepository hrmsPayrollContributionVoluntaryServiceProviderRepository;

	@Autowired
	private HrmsPayrollEmployeeLoanRepository hrmsPayrollEmployeeLoanRepository;

	@Autowired
	private HrmsPayrollLoanTypeRepository hrmsPayrollLoanTypeRepository;

	@Autowired
	private HrmsAllowanceRepository hrmsAllowanceRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsAllowancetypeRepository hrmsAllowancetypeRepository;

	@Autowired
	private HrmsPayrollTaxStructureRepository hrmsPayrollTaxStructureRepository;

	@Autowired
	private HrmsPayrollTaxTypeRepository hrmsPayrollTaxTypeRepository;

	@Autowired
	private HrmsPayrollDeductionTaxRepository hrmsPayrollDeductionTaxRepository;

	@Autowired
	private HrmsPayrollDeductionMandatoryInsuranceRepository hrmsPayrollDeductionMandatoryInsuranceRepository;

	@Autowired
	private HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository;

	@Autowired
	private HrmsPayrollDeductionVoluntaryRepository hrmsPayrollDeductionVoluntaryRepository;

	@Autowired
	private HrmsPayrollDeductionLoanRepository hrmsPayrollDeductionLoanRepository;

	@Autowired
	private HrmsPayrollTypeRepository hrmsPayrollTypeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired

	private HrmsOrganisationOfficeCategoryRepository hrmsOrganisationOfficeCategoryRepository;

	@Autowired

	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired
	private HrmsPayrollBankAccountRepository hrmsPayrollBankAccountRepository;

	@Autowired
	HrmsEmployeeBankAccountRepository hrmsEmployeeBankAccountRepository;

	@Override
	public ResponseEntity<?> processPayrollPerPeriod(Date period) {

		/*
		 * String pattern = "YYYY-MM-DD"; SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat(pattern); String dt = simpleDateFormat.format(period);
		 * 
		 * String[] dateParts = dt.split("-"); int day = Integer.parseInt(dateParts[2]);
		 * int month = Integer.parseInt(dateParts[1]); int year =
		 * Integer.parseInt(dateParts[0]);
		 * 
		 * int year1 = LocalDateTime.now().getYear();
		 * 
		 * int month1 = LocalDateTime.now().getMonthValue();
		 * 
		 * Integer[] status = { 2 };
		 * 
		 * if (hrmsPayrollRepository.countByYearAndMonthAndActive(year, month, 1) ==
		 * hrmsEmployeeRepository .countByEmploymentstatusidIn(status)) {// verify there
		 * no existing processed // payroll // for this month before processing
		 * 
		 * return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(null); } else
		 * {
		 * 
		 * List<HrmsEmployee> hrmsEmployeelist =
		 * hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1); // get
		 * // list // of // all // employee // credible // to // payment
		 * 
		 * for (HrmsEmployee dbm : hrmsEmployeelist) {
		 * 
		 * if
		 * (!hrmsPayrollRepository.existsByEmployeeidAndYearAndMonthAndActive(dbm.getId(
		 * ), year, month, 1)) {
		 * 
		 * HrmsPayroll hrmsPayroll = new HrmsPayroll(); hrmsPayroll.setActive(1);
		 * hrmsPayroll.setYear(year); hrmsPayroll.setMonth(month);
		 * hrmsPayroll.setDay(day); hrmsPayroll.setEmployeeid(dbm.getId());
		 * 
		 * // date pay hrmsPayroll.setDatepay(new Date()); // basic salary Double
		 * bsalary = 0.00; Double amountsalarygross = 0.00; Double amounttaxable = 0.00;
		 * if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1))
		 * { Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
		 * .findByEmployeeidAndActive(dbm.getId(), 1); if
		 * (hrmsSalarystructureRepository.existsById(hrmsemployeesalary.
		 * getSalarystructureId())) { HrmsSalarystructure hrmsSalarystructure =
		 * hrmsSalarystructureRepository
		 * .findById(hrmsemployeesalary.getSalarystructureId()).get(); bsalary =
		 * hrmsSalarystructure.getBasicSalary();
		 * 
		 * hrmsPayroll.setCurrencyid(4);// 4 for TZS
		 * 
		 * } }
		 * 
		 * hrmsPayroll.setAmountsalarybasic(bsalary);
		 * 
		 * Double amountsalaryallowancetransport = 0.00; Double
		 * amountsalaryallowancehousing = 0.00; Double
		 * amountsalaryallowanceservantAndHospitality = 0.00;
		 * 
		 * Double amountsalaryallowance = 0.00; // salarystructure if
		 * (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
		 * Hrmsemployeesalary hrmsemployeesalary1 = hrmsemployeesalaryRepository
		 * .findByEmployeeidAndActive(dbm.getId(), 1); // Allowance , house allowance,
		 * transport etc if ((dbm.getDesignationId() != 0 &&
		 * hrmsemployeesalary1.getSalarystructureId() != 0 &&
		 * dbm.getEmploymentcategoryId() != 0) && (hrmsAllowanceRepository
		 * .existsByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAndActive(
		 * dbm.getDesignationId(), hrmsemployeesalary1.getSalarystructureId(),
		 * dbm.getEmploymentcategoryId(), 1))) {
		 * 
		 * List<HrmsAllowance> hrmsAllowancelist = hrmsAllowanceRepository
		 * .findByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAndActive(
		 * dbm.getDesignationId(), hrmsemployeesalary1.getSalarystructureId(),
		 * dbm.getEmploymentcategoryId(), 1); for (HrmsAllowance allowance :
		 * hrmsAllowancelist) {
		 * 
		 * if
		 * (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().
		 * getName() .toLowerCase().contains("transport")) {
		 * 
		 * amountsalaryallowancetransport = allowance.getAmount();
		 * 
		 * }
		 * 
		 * if
		 * (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().
		 * getName() .toLowerCase().contains("housing")) {
		 * 
		 * amountsalaryallowancehousing = allowance.getAmount();
		 * 
		 * }
		 * 
		 * if
		 * (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().
		 * getName() .toLowerCase().contains("servant")) {
		 * 
		 * amountsalaryallowanceservantAndHospitality = allowance.getAmount();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * amountsalaryallowance = (amountsalaryallowancetransport +
		 * amountsalaryallowancehousing + amountsalaryallowanceservantAndHospitality);
		 * 
		 * hrmsPayroll.setAmountsalaryallowance(amountsalaryallowance);
		 * hrmsPayroll.setAmountsalaryallowancetransport(amountsalaryallowancetransport)
		 * ;
		 * 
		 * hrmsPayroll.setAmountsalaryallowancehousing(amountsalaryallowancehousing);
		 * 
		 * hrmsPayroll.setAmountsalaryservantAndHospitality(
		 * amountsalaryallowanceservantAndHospitality);
		 * 
		 * // Gross salary amountsalarygross = (bsalary + amountsalaryallowance);
		 * hrmsPayroll.setAmountsalarygross(amountsalarygross);
		 * 
		 * // amount_deduction_mandatory_sssf Double psssfemployee = 0.00; Double
		 * psssfemployer = 0.00; Double psssftotal = 0.00; int psssfcontributiontype =
		 * 0;
		 * 
		 * List<HrmsPayrollContributionMandatorySocialSecurityScheme> psssfs =
		 * hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
		 * .findByActive(1); HrmsEmployeeSocialSecurityScheme
		 * hrmsEmployeeSocialSecurityScheme = hrmsEmployeeSocialSecuritySchemeRepositoty
		 * .findFirstByEmployeeidAndActive(dbm.getId(), 1); int ssserviceproviderid = 0;
		 * if
		 * (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByIdAndActive(dbm.getId(),
		 * 1) && hrmsEmployeeSocialSecurityScheme.getServiceproviderid() != 0) {
		 * ssserviceproviderid =
		 * hrmsEmployeeSocialSecurityScheme.getServiceproviderid();
		 * 
		 * for (HrmsPayrollContributionMandatorySocialSecurityScheme pssf : psssfs) { if
		 * (ssserviceproviderid != 0) {
		 * 
		 * if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(
		 * ssserviceproviderid) .get().getName().toLowerCase().contains("psssf") &&
		 * ssserviceproviderid == pssf.getServiceproviderid()) { psssfcontributiontype =
		 * pssf.getContributiontypeid();
		 * 
		 * psssfemployee = (pssf.getAmount() + pssf.getRateemployee() * bsalary);
		 * psssfemployer = (pssf.getAmount() + pssf.getRateemployer() * bsalary);
		 * psssftotal = psssfemployee + psssfemployer;
		 * 
		 * }
		 * 
		 * if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(
		 * ssserviceproviderid) .get().getName().toLowerCase().contains("zss") &&
		 * ssserviceproviderid == pssf.getServiceproviderid()) { psssfcontributiontype =
		 * pssf.getContributiontypeid();
		 * 
		 * psssfemployee = (pssf.getAmount() + pssf.getRateemployee() * bsalary);
		 * psssfemployer = (pssf.getAmount() + pssf.getRateemployer() * bsalary);
		 * psssftotal = psssfemployee + psssfemployer;
		 * 
		 * } }
		 * 
		 * }
		 * 
		 * } hrmsPayroll.setAmountdeductionmandatorysssf(psssfemployee); amounttaxable =
		 * (amountsalarygross - psssfemployee);
		 * hrmsPayroll.setAmounttaxable(amounttaxable);
		 * 
		 * // amount_deduction_mandatory_health Double heathamountemployee = 0.00;
		 * Double heathamountemployer = 0.00; Double heathamounttotal = 0.00; int
		 * heathcontributiontype = 0; int wcfcontributiontype = 0; Double
		 * wcfamountemployer = 0.00; List<HrmsPayrollContributionMandatoryInsurance>
		 * nhifs = hrmsPayrollContributionMandatoryInsuranceRepository .findByActive(1);
		 * 
		 * for (HrmsPayrollContributionMandatoryInsurance nhif : nhifs) {
		 * 
		 * if
		 * (hrmsInsuranceProviderRepository.findById(nhif.getServiceproviderid()).get().
		 * getName() .toLowerCase().contains("nhif")) { heathcontributiontype =
		 * nhif.getContributiontypeid();
		 * 
		 * heathamountemployee = (nhif.getAmount() + nhif.getRateemployee() * bsalary);
		 * heathamountemployer = (nhif.getAmount() + nhif.getRateemployer() * bsalary);
		 * heathamounttotal = heathamountemployee + heathamountemployer;
		 * 
		 * }
		 * 
		 * if
		 * (hrmsInsuranceProviderRepository.findById(nhif.getServiceproviderid()).get().
		 * getName() .toLowerCase().contains("workers")) { wcfcontributiontype =
		 * nhif.getContributiontypeid();
		 * 
		 * wcfamountemployer = (nhif.getAmount() + nhif.getRateemployer() * bsalary);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * hrmsPayroll.setAmountdeductionmandatoryhealth(heathamountemployee);
		 * 
		 * // amount_deduction_mandatory // here we compute total mandatory deductions
		 * Double mandatorydeductions = psssfemployee + heathamountemployee;
		 * hrmsPayroll.setAmountdeductionmandatory(mandatorydeductions);
		 * 
		 * // amount_deduction_voluntary Double amountdeductionvoluntary = 0.00;
		 * 
		 * HrmsPayrollContributionVoluntary HrmsPayrollContributionVoluntaryonce = new
		 * HrmsPayrollContributionVoluntary(); if
		 * (hrmsPayrollContributionVoluntaryRepository.
		 * existsByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0)) {
		 * 
		 * List<HrmsPayrollContributionVoluntary> voluntarycontributionlist =
		 * hrmsPayrollContributionVoluntaryRepository
		 * .findByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0);
		 * 
		 * for (HrmsPayrollContributionVoluntary vcontribtn : voluntarycontributionlist)
		 * { if (vcontribtn.getJoiningdate() != null) {
		 * 
		 * String dt1 = simpleDateFormat.format(vcontribtn.getJoiningdate());
		 * 
		 * String[] dateParts1 = dt1.split("-"); int day2 =
		 * Integer.parseInt(dateParts1[2]); int month2 =
		 * Integer.parseInt(dateParts1[1]); int year2 = Integer.parseInt(dateParts1[0]);
		 * 
		 * if ((vcontribtn.getContributionmode() == 1) && (month2 == month) && (year2 ==
		 * year)) {
		 * 
		 * amountdeductionvoluntary = amountdeductionvoluntary + (vcontribtn.getAmount()
		 * + vcontribtn.getRate() * bsalary); vcontribtn.setLocked(1);
		 * 
		 * vcontribtn.setDate_updated(LocalDateTime.now());
		 * HrmsPayrollContributionVoluntaryonce = vcontribtn;
		 * 
		 * } if (vcontribtn.getContributionmode() != 1) {
		 * 
		 * amountdeductionvoluntary = amountdeductionvoluntary + (vcontribtn.getAmount()
		 * + vcontribtn.getRate() * bsalary);
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } hrmsPayroll.setAmountdeductionvoluntary(amountdeductionvoluntary);
		 * 
		 * // amount_deduction , this calculates total deduction mandatory and voluntary
		 * Double amountdeduction = mandatorydeductions + amountdeductionvoluntary;
		 * 
		 * hrmsPayroll.setAmountdeduction(amountdeduction);
		 * 
		 * // amount_deduction_loan_salary_advance Double
		 * amountdeductionloansalaryadvance = 0.00; Double amountdeductionloansaccos =
		 * 0.00; Double amountdeductionloaneducationheslb = 0.00; Double
		 * amountdeductionloaneducationother = 0.00; Double amountdeductionloanother =
		 * 0.00; Double amountdeductionloan = 0.00; Integer[] loanstatus = { 0, 1 }; if
		 * (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndStatusInAndActive(dbm
		 * .getId(), loanstatus, 1)) {
		 * 
		 * List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist =
		 * hrmsPayrollEmployeeLoanRepository
		 * .findByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus, 1);
		 * 
		 * for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist) {
		 * 
		 * if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("advance")) {
		 * 
		 * if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
		 * amountdeductionloansalaryadvance = emploan.getAmountoutstanding();
		 * 
		 * } else {
		 * 
		 * amountdeductionloansalaryadvance = emploan.getAmountprincipal();
		 * 
		 * } }
		 * 
		 * if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("heslb")) {
		 * 
		 * if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
		 * amountdeductionloaneducationheslb = emploan.getAmountoutstanding();
		 * 
		 * } else {
		 * 
		 * amountdeductionloaneducationheslb = emploan.getAmountprincipal();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("saccos")) {
		 * 
		 * if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
		 * amountdeductionloansaccos = emploan.getAmountoutstanding();
		 * 
		 * } else {
		 * 
		 * amountdeductionloansaccos = emploan.getAmountprincipal();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("educationother")) {
		 * 
		 * if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
		 * amountdeductionloaneducationother = emploan.getAmountoutstanding();
		 * 
		 * } else {
		 * 
		 * amountdeductionloaneducationother = emploan.getAmountprincipal();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * if (!hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("advance") &&
		 * !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("heslb") &&
		 * !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("saccos")
		 * 
		 * && !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().
		 * getName() .toLowerCase().contains("educationother")) {
		 * 
		 * if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
		 * 
		 * amountdeductionloanother = emploan.getAmountoutstanding();
		 * 
		 * } else {
		 * 
		 * amountdeductionloanother = emploan.getAmountprincipal();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * }
		 * 
		 * } amountdeductionloan = (amountdeductionloansalaryadvance +
		 * amountdeductionloaneducationheslb + amountdeductionloansaccos +
		 * amountdeductionloaneducationother + amountdeductionloanother);
		 * hrmsPayroll.setAmountdeductionloan(amountdeductionloan);
		 * 
		 * hrmsPayroll.setAmountdeductionloansalaryadvance(
		 * amountdeductionloansalaryadvance);
		 * hrmsPayroll.setAmountdeductionloaneducationheslb(
		 * amountdeductionloaneducationheslb);
		 * 
		 * hrmsPayroll.setAmountdeductionloansaccos(amountdeductionloansaccos);
		 * hrmsPayroll.setAmountdeductionloaneducationother(
		 * amountdeductionloaneducationother);
		 * hrmsPayroll.setAmountdeductionloanother(amountdeductionloanother); // Tax
		 * deduction
		 * 
		 * // if(hrmsPayrollTaxStructureRepository.) {
		 * 
		 * // } Double amounttaxpaye = 0.00; Double amounttax = 0.00; Double
		 * amounttaxother = 0.00; // this is not yet available so it will remain zero
		 * int taxtypeid = 0; int currencyid = 4; List<HrmsPayrollTaxStructure>
		 * hrmsPayrollTaxStructurelist = hrmsPayrollTaxStructureRepository
		 * .findByActive(1); for (HrmsPayrollTaxStructure tax :
		 * hrmsPayrollTaxStructurelist) { if
		 * (hrmsPayrollTaxTypeRepository.findById(tax.getTaxtypeid()).get().getName().
		 * toLowerCase() .contains("paye") && (amounttaxable >= tax.getAmountmin() &&
		 * amounttaxable <= tax.getAmountmax())) { taxtypeid = tax.getTaxtypeid();
		 * currencyid = tax.getCurrencyid();
		 * 
		 * amounttaxpaye = (tax.getAmount() + (tax.getRate() * (amounttaxable -
		 * (tax.getAmountmin() - 1))));
		 * 
		 * }
		 * 
		 * } hrmsPayroll.setAmounttaxpaye(amounttaxpaye);
		 * hrmsPayroll.setAmounttaxother(0.00);// so far we do not have any other tax ,
		 * thats why we set // default value to 0
		 * 
		 * amounttax = amounttaxpaye + amounttaxother;
		 * hrmsPayroll.setAmounttax(amounttax); // net salary // Gross minus deductions
		 * Double amountsalarynet = amountsalarygross - (amountdeduction +
		 * amountdeductionloan + amounttax);
		 * 
		 * hrmsPayroll.setAmountsalarynet(amountsalarynet);
		 * hrmsPayroll.setUnique_id(UUID.randomUUID()); hrmsPayroll.setPayrolltypeid(1);
		 * int payrollid = hrmsPayrollRepository.saveAndFlush(hrmsPayroll).getId();
		 * 
		 * // now compute transactional tables for loans and other deductions
		 * 
		 * // first tax deduction HrmsPayrollDeductionTax hrmsPayrollDeductionTax = new
		 * HrmsPayrollDeductionTax(); hrmsPayrollDeductionTax.setActive(1);
		 * hrmsPayrollDeductionTax.setApproved(0);
		 * hrmsPayrollDeductionTax.setAmount(amounttaxpaye);
		 * hrmsPayrollDeductionTax.setCurrencyid(currencyid);
		 * hrmsPayrollDeductionTax.setDatededucted(new Date());
		 * hrmsPayrollDeductionTax.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionTax.setPayrollid(payrollid);
		 * hrmsPayrollDeductionTax.setTaxtypeid(taxtypeid);
		 * hrmsPayrollDeductionTax.setUnique_id(UUID.randomUUID());
		 * hrmsPayrollDeductionTax.setYear(year);
		 * hrmsPayrollDeductionTax.setMonth(month);
		 * 
		 * hrmsPayrollDeductionTaxRepository.saveAndFlush(hrmsPayrollDeductionTax);
		 * 
		 * // Health insurance deduction
		 * 
		 * HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance
		 * = new HrmsPayrollDeductionMandatoryInsurance();
		 * 
		 * hrmsPayrollDeductionMandatoryInsurance.setActive(1);
		 * hrmsPayrollDeductionMandatoryInsurance.setApproved(0);
		 * hrmsPayrollDeductionMandatoryInsurance.setAmount(heathamounttotal);
		 * hrmsPayrollDeductionMandatoryInsurance.setAmountemployee(heathamountemployee)
		 * ;
		 * hrmsPayrollDeductionMandatoryInsurance.setAmountemployer(heathamountemployer)
		 * ; hrmsPayrollDeductionMandatoryInsurance.setContributiontypeid(
		 * heathcontributiontype);
		 * hrmsPayrollDeductionMandatoryInsurance.setCurrencyid(currencyid);
		 * hrmsPayrollDeductionMandatoryInsurance.setDatededucted(new Date());
		 * hrmsPayrollDeductionMandatoryInsurance.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionMandatoryInsurance.setPayrollid(payrollid);
		 * hrmsPayrollDeductionMandatoryInsurance.setUnique_id(UUID.randomUUID());
		 * hrmsPayrollDeductionMandatoryInsurance.setYear(year);
		 * hrmsPayrollDeductionMandatoryInsurance.setMonth(month);
		 * 
		 * hrmsPayrollDeductionMandatoryInsuranceRepository
		 * .saveAndFlush(hrmsPayrollDeductionMandatoryInsurance);
		 * 
		 * // Workers compensation fund HrmsPayrollDeductionMandatoryInsurance
		 * hrmsPayrollDeductionMandatoryInsurance1 = new
		 * HrmsPayrollDeductionMandatoryInsurance();
		 * 
		 * hrmsPayrollDeductionMandatoryInsurance1.setActive(1);
		 * hrmsPayrollDeductionMandatoryInsurance1.setApproved(0);
		 * hrmsPayrollDeductionMandatoryInsurance1.setAmount(wcfamountemployer);
		 * hrmsPayrollDeductionMandatoryInsurance1.setAmountemployee(0.00);
		 * hrmsPayrollDeductionMandatoryInsurance1.setAmountemployer(wcfamountemployer);
		 * hrmsPayrollDeductionMandatoryInsurance1.setContributiontypeid(
		 * wcfcontributiontype);
		 * hrmsPayrollDeductionMandatoryInsurance1.setCurrencyid(currencyid);
		 * hrmsPayrollDeductionMandatoryInsurance1.setDatededucted(new Date());
		 * hrmsPayrollDeductionMandatoryInsurance1.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionMandatoryInsurance1.setPayrollid(payrollid);
		 * hrmsPayrollDeductionMandatoryInsurance1.setUnique_id(UUID.randomUUID());
		 * hrmsPayrollDeductionMandatoryInsurance1.setYear(year);
		 * hrmsPayrollDeductionMandatoryInsurance1.setMonth(month);
		 * 
		 * hrmsPayrollDeductionMandatoryInsuranceRepository
		 * .saveAndFlush(hrmsPayrollDeductionMandatoryInsurance1);
		 * 
		 * // public social security fund deductions if (psssfcontributiontype != 0) {
		 * HrmsPayrollDeductionMandatorySocialSecurityScheme
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme = new
		 * HrmsPayrollDeductionMandatorySocialSecurityScheme();
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setActive(1);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmount(psssftotal);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmountemployee(
		 * psssfemployee);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmountemployer(
		 * psssfemployer);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setApproved(0);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setContributiontypeid(
		 * psssfcontributiontype);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setDatededucted(new
		 * Date());
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setPayrollid(payrollid);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setUnique_id(UUID.
		 * randomUUID());
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setYear(year);
		 * hrmsPayrollDeductionMandatorySocialSecurityScheme.setMonth(month);
		 * 
		 * hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
		 * .saveAndFlush(hrmsPayrollDeductionMandatorySocialSecurityScheme); }
		 * 
		 * // Voluntary deduction if (hrmsPayrollContributionVoluntaryRepository.
		 * existsByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0)) {
		 * 
		 * List<HrmsPayrollContributionVoluntary> voluntarycontributionlist1 =
		 * hrmsPayrollContributionVoluntaryRepository
		 * .findByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0);
		 * 
		 * Double contamount = 0.00; if (HrmsPayrollContributionVoluntaryonce != null &&
		 * HrmsPayrollContributionVoluntaryonce.getAmount() != null) { contamount =
		 * HrmsPayrollContributionVoluntaryonce.getAmount();
		 * 
		 * // update the onceoff payment if it was not null if (contamount > 0.00) {
		 * HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = new
		 * HrmsPayrollDeductionVoluntary(); hrmsPayrollDeductionVoluntary.setActive(1);
		 * hrmsPayrollDeductionVoluntary
		 * .setAmount(HrmsPayrollContributionVoluntaryonce.getAmount());
		 * hrmsPayrollDeductionVoluntary.setApproved(0);
		 * hrmsPayrollDeductionVoluntary.setContributiontypeid(
		 * HrmsPayrollContributionVoluntaryonce.getContributiontypeid());
		 * hrmsPayrollDeductionVoluntary
		 * .setCurrencyid(HrmsPayrollContributionVoluntaryonce.getCurrencyid());
		 * hrmsPayrollDeductionVoluntary.setDatededucted(new Date());
		 * hrmsPayrollDeductionVoluntary.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionVoluntary.setPayrollid(payrollid);
		 * hrmsPayrollDeductionVoluntary.setUnique_id(UUID.randomUUID());
		 * hrmsPayrollDeductionVoluntary.setYear(year);
		 * hrmsPayrollDeductionVoluntary.setMonth(month);
		 * hrmsPayrollDeductionVoluntaryRepository.saveAndFlush(
		 * hrmsPayrollDeductionVoluntary); // lock this transaction now as payment is
		 * now full
		 * 
		 * hrmsPayrollContributionVoluntaryRepository
		 * .saveAndFlush(HrmsPayrollContributionVoluntaryonce);
		 * 
		 * } }
		 * 
		 * for (HrmsPayrollContributionVoluntary vcontribtn :
		 * voluntarycontributionlist1) { HrmsPayrollDeductionVoluntary
		 * hrmsPayrollDeductionVoluntary = new HrmsPayrollDeductionVoluntary();
		 * hrmsPayrollDeductionVoluntary.setActive(1);
		 * hrmsPayrollDeductionVoluntary.setAmount(vcontribtn.getAmount());
		 * hrmsPayrollDeductionVoluntary.setApproved(0);
		 * hrmsPayrollDeductionVoluntary.setContributiontypeid(vcontribtn.
		 * getContributiontypeid());
		 * hrmsPayrollDeductionVoluntary.setCurrencyid(vcontribtn.getCurrencyid());
		 * hrmsPayrollDeductionVoluntary.setDatededucted(new Date());
		 * hrmsPayrollDeductionVoluntary.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionVoluntary.setPayrollid(payrollid);
		 * hrmsPayrollDeductionVoluntary.setUnique_id(UUID.randomUUID());
		 * hrmsPayrollDeductionVoluntary.setYear(year);
		 * hrmsPayrollDeductionVoluntary.setMonth(month);
		 * 
		 * hrmsPayrollDeductionVoluntaryRepository.saveAndFlush(
		 * hrmsPayrollDeductionVoluntary); } }
		 * 
		 * // loan deduction
		 * 
		 * Integer[] loanstatus1 = { 0, 1 }; if
		 * (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndStatusInAndActive(dbm
		 * .getId(), loanstatus1, 1)) { List<HrmsPayrollEmployeeLoan>
		 * hrmsPayrollEmployeeLoanlist1 = hrmsPayrollEmployeeLoanRepository
		 * .findByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus1, 1);
		 * 
		 * for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist1) {
		 * HrmsPayrollDeductionLoan hrmsPayrollDeductionLoan = new
		 * HrmsPayrollDeductionLoan(); hrmsPayrollDeductionLoan.setActive(1);
		 * hrmsPayrollDeductionLoan.setAmount(emploan.getAmountprincipal());
		 * 
		 * Double Amountoutstanding = emploan.getAmountoutstanding() -
		 * emploan.getAmountprincipal();
		 * hrmsPayrollDeductionLoan.setAmountoutstanding(Amountoutstanding);
		 * hrmsPayrollDeductionLoan.setAmountpenalty(0.00);
		 * hrmsPayrollDeductionLoan.setApproved(0);
		 * 
		 * hrmsPayrollDeductionLoan.setCurrencyid(emploan.getCurrencyid());
		 * hrmsPayrollDeductionLoan.setDatededucted(new Date());
		 * hrmsPayrollDeductionLoan.setEmployeeid(dbm.getId());
		 * hrmsPayrollDeductionLoan.setLoanid(emploan.getId());
		 * hrmsPayrollDeductionLoan.setLoantypeid(emploan.getLoantypeid());
		 * hrmsPayrollDeductionLoan.setPayrollid(payrollid);
		 * hrmsPayrollDeductionLoan.setUnique_id(UUID.randomUUID());
		 * hrmsPayrollDeductionLoan.setYear(year);
		 * hrmsPayrollDeductionLoan.setMonth(month);
		 * hrmsPayrollDeductionLoanRepository.saveAndFlush(hrmsPayrollDeductionLoan);
		 * 
		 * // update employee loan table HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan
		 * = hrmsPayrollEmployeeLoanRepository .findById(emploan.getId()).get(); if
		 * (Amountoutstanding == 0) { hrmsPayrollEmployeeLoan.setStatus(2); // completed
		 * paying hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now()); } if
		 * (Amountoutstanding > 0) { hrmsPayrollEmployeeLoan.setStatus(1); // still
		 * paying
		 * 
		 * hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());
		 * 
		 * } hrmsPayrollEmployeeLoanRepository.saveAndFlush(hrmsPayrollEmployeeLoan);
		 * 
		 * }
		 * 
		 * } }
		 * 
		 * }
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(null); }
		 */
		return null;
	}

	@Override
	public ResponseEntity<PayrollResponse> getPayrollByPeriodAndEmpId(int year, int month, int empId) {
		/*
		 * if (hrmsPayrollRepository.existsByEmployeeidAndYearAndMonthAndActive(empId,
		 * year, month, 1)) { String pattern = "yyyy-MM-dd"; SimpleDateFormat
		 * simpleDateFormat = new SimpleDateFormat(pattern); HrmsPayroll dbm =
		 * hrmsPayrollRepository.findByEmployeeidAndYearAndMonthAndActive(empId, year,
		 * month, 1);
		 * 
		 * PayrollResponse payrollResponse = new PayrollResponse();
		 * payrollResponse.setActive(dbm.getActive());
		 * payrollResponse.setYear(dbm.getYear());
		 * payrollResponse.setMonth(dbm.getMonth());
		 * payrollResponse.setDay(dbm.getDay());
		 * 
		 * payrollResponse.setAmountdeduction(dbm.getAmountdeduction());
		 * payrollResponse.setAmountdeductionloan(dbm.getAmountdeductionloan());
		 * payrollResponse.setAmountdeductionloaneducationheslb(dbm.
		 * getAmountdeductionloaneducationheslb());
		 * payrollResponse.setAmountdeductionloaneducationother(dbm.
		 * getAmountdeductionloaneducationother());
		 * payrollResponse.setAmountdeductionloanother(dbm.getAmountdeductionloanother()
		 * );
		 * payrollResponse.setAmountdeductionloansaccos(dbm.getAmountdeductionloansaccos
		 * ()); payrollResponse.setAmountdeductionloansalaryadvance(dbm.
		 * getAmountdeductionloansalaryadvance());
		 * payrollResponse.setAmountdeductionmandatory(dbm.getAmountdeductionmandatory()
		 * ); payrollResponse.setAmountdeductionmandatoryhealth(dbm.
		 * getAmountdeductionmandatoryhealth());
		 * payrollResponse.setAmountdeductionmandatorypension(dbm.
		 * getAmountdeductionmandatorysssf());
		 * payrollResponse.setAmountdeductionvoluntary(dbm.getAmountdeductionvoluntary()
		 * ); payrollResponse.setAmountsalaryallowance(dbm.getAmountsalaryallowance());
		 * payrollResponse.setAmountsalaryallowancehousing(dbm.
		 * getAmountsalaryallowancehousing());
		 * payrollResponse.setAmountsalaryallowancetransport(dbm.
		 * getAmountsalaryallowancetransport());
		 * payrollResponse.setAmountsalarybasic(dbm.getAmountsalarybasic());
		 * payrollResponse.setAmountsalarygross(dbm.getAmountsalarygross());
		 * payrollResponse.setAmountsalarynet(dbm.getAmountsalarynet());
		 * payrollResponse.setAmounttax(dbm.getAmounttax());
		 * payrollResponse.setAmounttaxother(dbm.getAmounttaxother());
		 * payrollResponse.setAmounttaxpaye(dbm.getAmounttaxpaye());
		 * payrollResponse.setApproved(dbm.getApproved());
		 * payrollResponse.setAmountsalaryservantAndHospitality(dbm.
		 * getAmountsalaryservantAndHospitality()); if (dbm.getCurrencyid() != 0 &&
		 * hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
		 * payrollResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid
		 * ()).get().getName()); } payrollResponse.setCurrencyid(dbm.getCurrencyid());
		 * if (dbm.getDatepay() != null) {
		 * payrollResponse.setDatepay(simpleDateFormat.format(dbm.getDatepay())); } if
		 * (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) { HrmsEmployee
		 * hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
		 * 
		 * if (hrmsEmployee.getDutystationid() != 0) { payrollResponse.setOffice(
		 * hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).
		 * get().getName());
		 * payrollResponse.setOfficeid(hrmsEmployee.getDutystationid());
		 * payrollResponse.setOfficetypeid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid());
		 * payrollResponse .setOfficetype( hrmsOrganisationOfficeCategoryRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
		 * .get().getName());
		 * payrollResponse.setDutystationcityid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid());
		 * payrollResponse .setDutystationcity( hrmsLocationCityRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid())
		 * .get().getName());
		 * 
		 * }
		 * 
		 * payrollResponse.setDesignationid(hrmsEmployee.getDesignationId()); if
		 * (hrmsEmployee.getDesignationId() != 0 &&
		 * hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
		 * payrollResponse.setDesignation(
		 * hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().
		 * getName());
		 * 
		 * }
		 * 
		 * StringBuilder fullName = new StringBuilder();
		 * 
		 * fullName.append(hrmsEmployee.getFirstName().trim()); if
		 * (hrmsEmployee.getMiddleName() != null) { fullName.append(" " +
		 * hrmsEmployee.getMiddleName().trim()); } fullName.append(" " +
		 * hrmsEmployee.getLastName().trim());
		 * payrollResponse.setFullName(fullName.toString()); }
		 * payrollResponse.setEmployeeid(dbm.getEmployeeid());
		 * payrollResponse.setId(dbm.getId());
		 * 
		 * if (dbm.getPayrolltypeid() != 0 &&
		 * hrmsPayrollTypeRepository.existsById(dbm.getPayrolltypeid())) {
		 * payrollResponse
		 * .setPayrolltype(hrmsPayrollTypeRepository.findById(dbm.getPayrolltypeid()).
		 * get().getName()); } payrollResponse.setPayrolltypeid(dbm.getPayrolltypeid());
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(payrollResponse); } else {
		 * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
		 */

		return null;
	}

	@Override
	public ResponseEntity<PayrollResponse> getPayrollByPayrollId(int payrollid) {
		/*
		 * if (hrmsPayrollRepository.existsByIdAndActive(payrollid, 1)) { String pattern
		 * = "yyyy-MM-dd"; SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat(pattern); HrmsPayroll dbm =
		 * hrmsPayrollRepository.findByIdAndActive(payrollid, 1);
		 * 
		 * PayrollResponse payrollResponse = new PayrollResponse();
		 * payrollResponse.setActive(dbm.getActive());
		 * 
		 * payrollResponse.setAmounttaxable(dbm.getAmounttaxable());
		 * 
		 * payrollResponse.setYear(dbm.getYear());
		 * payrollResponse.setMonth(dbm.getMonth());
		 * payrollResponse.setDay(dbm.getDay());
		 * payrollResponse.setAmountdeduction(dbm.getAmountdeduction());
		 * payrollResponse.setAmountdeductionloan(dbm.getAmountdeductionloan());
		 * payrollResponse.setAmountdeductionloaneducationheslb(dbm.
		 * getAmountdeductionloaneducationheslb());
		 * payrollResponse.setAmountdeductionloaneducationother(dbm.
		 * getAmountdeductionloaneducationother());
		 * payrollResponse.setAmountdeductionloanother(dbm.getAmountdeductionloanother()
		 * );
		 * payrollResponse.setAmountdeductionloansaccos(dbm.getAmountdeductionloansaccos
		 * ()); payrollResponse.setAmountdeductionloansalaryadvance(dbm.
		 * getAmountdeductionloansalaryadvance());
		 * payrollResponse.setAmountdeductionmandatory(dbm.getAmountdeductionmandatory()
		 * ); payrollResponse.setAmountdeductionmandatoryhealth(dbm.
		 * getAmountdeductionmandatoryhealth());
		 * payrollResponse.setAmountdeductionmandatorypension(dbm.
		 * getAmountdeductionmandatorysssf());
		 * payrollResponse.setAmountdeductionvoluntary(dbm.getAmountdeductionvoluntary()
		 * ); payrollResponse.setAmountsalaryallowance(dbm.getAmountsalaryallowance());
		 * payrollResponse.setAmountsalaryallowancehousing(dbm.
		 * getAmountsalaryallowancehousing());
		 * payrollResponse.setAmountsalaryallowancetransport(dbm.
		 * getAmountsalaryallowancetransport());
		 * payrollResponse.setAmountsalarybasic(dbm.getAmountsalarybasic());
		 * payrollResponse.setAmountsalarygross(dbm.getAmountsalarygross());
		 * payrollResponse.setAmountsalarynet(dbm.getAmountsalarynet());
		 * payrollResponse.setAmounttax(dbm.getAmounttax());
		 * payrollResponse.setAmounttaxother(dbm.getAmounttaxother());
		 * payrollResponse.setAmounttaxpaye(dbm.getAmounttaxpaye());
		 * payrollResponse.setApproved(dbm.getApproved());
		 * payrollResponse.setAmountsalaryservantAndHospitality(dbm.
		 * getAmountsalaryservantAndHospitality()); if (dbm.getCurrencyid() != 0 &&
		 * hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
		 * payrollResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid
		 * ()).get().getName()); } payrollResponse.setCurrencyid(dbm.getCurrencyid());
		 * if (dbm.getDatepay() != null) {
		 * payrollResponse.setDatepay(simpleDateFormat.format(dbm.getDatepay())); } if
		 * (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
		 * 
		 * StringBuilder fullName = new StringBuilder();
		 * 
		 * HrmsEmployee hrmsEmployee =
		 * hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
		 * 
		 * if (hrmsEmployee.getDutystationid() != 0) { payrollResponse.setOffice(
		 * hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).
		 * get().getName());
		 * payrollResponse.setOfficeid(hrmsEmployee.getDutystationid());
		 * payrollResponse.setOfficetypeid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid());
		 * payrollResponse .setOfficetype( hrmsOrganisationOfficeCategoryRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
		 * .get().getName());
		 * payrollResponse.setDutystationcityid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid());
		 * payrollResponse .setDutystationcity( hrmsLocationCityRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid())
		 * .get().getName());
		 * 
		 * }
		 * 
		 * payrollResponse.setDesignationid(hrmsEmployee.getDesignationId()); if
		 * (hrmsEmployee.getDesignationId() != 0 &&
		 * hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
		 * payrollResponse.setDesignation(
		 * hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().
		 * getName());
		 * 
		 * } payrollResponse.setDesignationid(hrmsEmployee.getDesignationId()); if
		 * (hrmsEmployee.getDesignationId() != 0 &&
		 * hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
		 * payrollResponse.setDesignation(
		 * hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().
		 * getName());
		 * 
		 * }
		 * 
		 * fullName.append(hrmsEmployee.getFirstName().trim()); if
		 * (hrmsEmployee.getMiddleName() != null) { fullName.append(" " +
		 * hrmsEmployee.getMiddleName().trim()); } fullName.append(" " +
		 * hrmsEmployee.getLastName().trim());
		 * payrollResponse.setFullName(fullName.toString()); }
		 * payrollResponse.setEmployeeid(dbm.getEmployeeid());
		 * payrollResponse.setId(dbm.getId());
		 * 
		 * if (dbm.getPayrolltypeid() != 0 &&
		 * hrmsPayrollTypeRepository.existsById(dbm.getPayrolltypeid())) {
		 * payrollResponse
		 * .setPayrolltype(hrmsPayrollTypeRepository.findById(dbm.getPayrolltypeid()).
		 * get().getName()); } payrollResponse.setPayrolltypeid(dbm.getPayrolltypeid());
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(payrollResponse); } else {
		 * return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
		 */

		return null;
	}

	@Override
	public ResponseEntity<List<PayrollResponse>> getPayrollByEmpId(int empid) {
		/*
		 * if (hrmsPayrollRepository.existsByEmployeeidAndActive(empid, 1)) {
		 * 
		 * List<PayrollResponse> payrollResponselist = new ArrayList<>(); String pattern
		 * = "yyyy-MM-dd"; SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat(pattern); List<HrmsPayroll> dbms =
		 * hrmsPayrollRepository.findByEmployeeidAndActive(empid, 1); dbms.forEach(dbm
		 * -> { PayrollResponse payrollResponse = new PayrollResponse();
		 * payrollResponse.setActive(dbm.getActive());
		 * payrollResponse.setAmounttaxable(dbm.getAmounttaxable());
		 * 
		 * payrollResponse.setYear(dbm.getYear());
		 * payrollResponse.setMonth(dbm.getMonth());
		 * payrollResponse.setDay(dbm.getDay());
		 * payrollResponse.setAmountdeduction(dbm.getAmountdeduction());
		 * payrollResponse.setAmountdeductionloan(dbm.getAmountdeductionloan());
		 * payrollResponse.setAmountdeductionloaneducationheslb(dbm.
		 * getAmountdeductionloaneducationheslb());
		 * payrollResponse.setAmountdeductionloaneducationother(dbm.
		 * getAmountdeductionloaneducationother());
		 * payrollResponse.setAmountdeductionloanother(dbm.getAmountdeductionloanother()
		 * );
		 * payrollResponse.setAmountdeductionloansaccos(dbm.getAmountdeductionloansaccos
		 * ()); payrollResponse.setAmountdeductionloansalaryadvance(dbm.
		 * getAmountdeductionloansalaryadvance());
		 * payrollResponse.setAmountdeductionmandatory(dbm.getAmountdeductionmandatory()
		 * ); payrollResponse.setAmountdeductionmandatoryhealth(dbm.
		 * getAmountdeductionmandatoryhealth());
		 * payrollResponse.setAmountdeductionmandatorypension(dbm.
		 * getAmountdeductionmandatorysssf());
		 * payrollResponse.setAmountdeductionvoluntary(dbm.getAmountdeductionvoluntary()
		 * ); payrollResponse.setAmountsalaryallowance(dbm.getAmountsalaryallowance());
		 * payrollResponse.setAmountsalaryallowancehousing(dbm.
		 * getAmountsalaryallowancehousing());
		 * payrollResponse.setAmountsalaryallowancetransport(dbm.
		 * getAmountsalaryallowancetransport());
		 * payrollResponse.setAmountsalarybasic(dbm.getAmountsalarybasic());
		 * payrollResponse.setAmountsalarygross(dbm.getAmountsalarygross());
		 * payrollResponse.setAmountsalarynet(dbm.getAmountsalarynet());
		 * payrollResponse.setAmounttax(dbm.getAmounttax());
		 * payrollResponse.setAmounttaxother(dbm.getAmounttaxother());
		 * payrollResponse.setAmounttaxpaye(dbm.getAmounttaxpaye());
		 * payrollResponse.setApproved(dbm.getApproved());
		 * payrollResponse.setAmountsalaryservantAndHospitality(dbm.
		 * getAmountsalaryservantAndHospitality()); if (dbm.getCurrencyid() != 0 &&
		 * hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
		 * payrollResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid
		 * ()).get().getName()); } payrollResponse.setCurrencyid(dbm.getCurrencyid());
		 * if (dbm.getDatepay() != null) {
		 * payrollResponse.setDatepay(simpleDateFormat.format(dbm.getDatepay())); } if
		 * (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) { StringBuilder
		 * fullName = new StringBuilder();
		 * 
		 * HrmsEmployee hrmsEmployee =
		 * hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
		 * 
		 * if (hrmsEmployee.getDutystationid() != 0) {
		 * payrollResponse.setOffice(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getName());
		 * payrollResponse.setOfficeid(hrmsEmployee.getDutystationid());
		 * payrollResponse.setOfficetypeid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid());
		 * payrollResponse .setOfficetype(hrmsOrganisationOfficeCategoryRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
		 * .get().getName());
		 * payrollResponse.setDutystationcityid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid());
		 * payrollResponse .setDutystationcity(hrmsLocationCityRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid())
		 * .get().getName());
		 * 
		 * }
		 * 
		 * payrollResponse.setDesignationid(hrmsEmployee.getDesignationId()); if
		 * (hrmsEmployee.getDesignationId() != 0 &&
		 * hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
		 * payrollResponse.setDesignation(
		 * hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().
		 * getName());
		 * 
		 * }
		 * 
		 * fullName.append(hrmsEmployee.getFirstName().trim()); if
		 * (hrmsEmployee.getMiddleName() != null) { fullName.append(" " +
		 * hrmsEmployee.getMiddleName().trim()); } fullName.append(" " +
		 * hrmsEmployee.getLastName().trim());
		 * payrollResponse.setFullName(fullName.toString()); }
		 * payrollResponse.setEmployeeid(dbm.getEmployeeid());
		 * payrollResponse.setId(dbm.getId());
		 * 
		 * if (dbm.getPayrolltypeid() != 0 &&
		 * hrmsPayrollTypeRepository.existsById(dbm.getPayrolltypeid())) {
		 * payrollResponse
		 * .setPayrolltype(hrmsPayrollTypeRepository.findById(dbm.getPayrolltypeid()).
		 * get().getName()); } payrollResponse.setPayrolltypeid(dbm.getPayrolltypeid());
		 * payrollResponselist.add(payrollResponse); });
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(payrollResponselist); } else
		 * { return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); }
		 */

		return null;
	}

	@Override
	public ResponseEntity<?> deletePayrollByPayrollId(int payrollid) {
		if (hrmsPayrollRepository.existsByIdAndActive(payrollid, 1)) {
			HrmsPayroll hrmsPayroll = hrmsPayrollRepository.findById(payrollid).get();
			hrmsPayroll.setActive(0);
			hrmsPayroll.setDate_updated(LocalDateTime.now());
			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollRepository.saveAndFlush(hrmsPayroll));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollResponse>> getAllPayroll() {
		/*
		 * List<PayrollResponse> payrollResponselist = new ArrayList<>(); String pattern
		 * = "yyyy-MM-dd"; SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat(pattern); List<HrmsPayroll> dbms =
		 * hrmsPayrollRepository.findByActive(1); dbms.forEach(dbm -> { PayrollResponse
		 * payrollResponse = new PayrollResponse();
		 * payrollResponse.setActive(dbm.getActive());
		 * payrollResponse.setAmounttaxable(dbm.getAmounttaxable());
		 * 
		 * payrollResponse.setYear(dbm.getYear());
		 * payrollResponse.setMonth(dbm.getMonth());
		 * payrollResponse.setDay(dbm.getDay());
		 * payrollResponse.setAmountdeduction(dbm.getAmountdeduction());
		 * payrollResponse.setAmountdeductionloan(dbm.getAmountdeductionloan());
		 * payrollResponse.setAmountdeductionloaneducationheslb(dbm.
		 * getAmountdeductionloaneducationheslb());
		 * payrollResponse.setAmountdeductionloaneducationother(dbm.
		 * getAmountdeductionloaneducationother());
		 * payrollResponse.setAmountdeductionloanother(dbm.getAmountdeductionloanother()
		 * );
		 * payrollResponse.setAmountdeductionloansaccos(dbm.getAmountdeductionloansaccos
		 * ()); payrollResponse.setAmountdeductionloansalaryadvance(dbm.
		 * getAmountdeductionloansalaryadvance());
		 * payrollResponse.setAmountdeductionmandatory(dbm.getAmountdeductionmandatory()
		 * ); payrollResponse.setAmountdeductionmandatoryhealth(dbm.
		 * getAmountdeductionmandatoryhealth());
		 * payrollResponse.setAmountdeductionmandatorypension(dbm.
		 * getAmountdeductionmandatorysssf());
		 * payrollResponse.setAmountdeductionvoluntary(dbm.getAmountdeductionvoluntary()
		 * ); payrollResponse.setAmountsalaryallowance(dbm.getAmountsalaryallowance());
		 * payrollResponse.setAmountsalaryallowancehousing(dbm.
		 * getAmountsalaryallowancehousing());
		 * payrollResponse.setAmountsalaryallowancetransport(dbm.
		 * getAmountsalaryallowancetransport());
		 * payrollResponse.setAmountsalarybasic(dbm.getAmountsalarybasic());
		 * payrollResponse.setAmountsalarygross(dbm.getAmountsalarygross());
		 * payrollResponse.setAmountsalarynet(dbm.getAmountsalarynet());
		 * payrollResponse.setAmounttax(dbm.getAmounttax());
		 * payrollResponse.setAmounttaxother(dbm.getAmounttaxother());
		 * payrollResponse.setAmounttaxpaye(dbm.getAmounttaxpaye());
		 * payrollResponse.setApproved(dbm.getApproved());
		 * payrollResponse.setAmountsalaryservantAndHospitality(dbm.
		 * getAmountsalaryservantAndHospitality()); if (dbm.getCurrencyid() != 0 &&
		 * hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
		 * payrollResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid
		 * ()).get().getName()); } payrollResponse.setCurrencyid(dbm.getCurrencyid());
		 * if (dbm.getDatepay() != null) {
		 * payrollResponse.setDatepay(simpleDateFormat.format(dbm.getDatepay())); } if
		 * (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) { StringBuilder
		 * fullName = new StringBuilder();
		 * 
		 * HrmsEmployee hrmsEmployee =
		 * hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
		 * 
		 * if (hrmsEmployee.getDutystationid() != 0) { payrollResponse.setOffice(
		 * hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).
		 * get().getName());
		 * payrollResponse.setOfficeid(hrmsEmployee.getDutystationid());
		 * payrollResponse.setOfficetypeid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid());
		 * payrollResponse .setOfficetype( hrmsOrganisationOfficeCategoryRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
		 * .get().getName());
		 * payrollResponse.setDutystationcityid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid());
		 * payrollResponse .setDutystationcity( hrmsLocationCityRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid())
		 * .get().getName());
		 * 
		 * }
		 * 
		 * payrollResponse.setDesignationid(hrmsEmployee.getDesignationId()); if
		 * (hrmsEmployee.getDesignationId() != 0 &&
		 * hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
		 * payrollResponse.setDesignation(
		 * hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().
		 * getName());
		 * 
		 * } fullName.append(hrmsEmployee.getFirstName().trim()); fullName.append(" " +
		 * hrmsEmployee.getMiddleName().trim()); fullName.append(" " +
		 * hrmsEmployee.getLastName().trim());
		 * payrollResponse.setFullName(fullName.toString()); }
		 * payrollResponse.setEmployeeid(dbm.getEmployeeid());
		 * payrollResponse.setId(dbm.getId());
		 * 
		 * if (dbm.getPayrolltypeid() != 0 &&
		 * hrmsPayrollTypeRepository.existsById(dbm.getPayrolltypeid())) {
		 * payrollResponse
		 * .setPayrolltype(hrmsPayrollTypeRepository.findById(dbm.getPayrolltypeid()).
		 * get().getName()); } payrollResponse.setPayrolltypeid(dbm.getPayrolltypeid());
		 * payrollResponselist.add(payrollResponse); });
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(payrollResponselist);
		 */

		return null;
	}

	@Override
	public ResponseEntity<PayrollResponses> getPayrollByPeriod(int year, int month) {
		// if (hrmsPayrollRepository.existsByYearAndMonthAndActive(year, month, 1)) {
		// initialize totalamount of every element needed to have total
		/*
		 * Double totalamountsalarygross = 0.00;
		 * 
		 * Double totalamountsalarybasic = 0.00; Double totalamounttaxable = 0.00;
		 * 
		 * Double totalamountsalarynet = 0.00;
		 * 
		 * Double totalamountsalaryallowancetransport = 0.00;
		 * 
		 * Double totalamountsalaryallowancehousing = 0.00;
		 * 
		 * Double totalamountsalaryallowance = 0.00;
		 * 
		 * Double totalamounttaxpaye = 0.00;
		 * 
		 * Double totalamounttaxother = 0.00;
		 * 
		 * Double totalamounttax = 0.00;
		 * 
		 * Double totalamountdeductionmandatorypension = 0.00;
		 * 
		 * Double totalamountdeductionmandatoryhealth = 0.00;
		 * 
		 * Double totalamountdeductionmandatory = 0.00;
		 * 
		 * Double totalamountdeductionvoluntary = 0.00;
		 * 
		 * Double totalamountdeduction = 0.00;
		 * 
		 * Double totalamountdeductionloansalaryadvance = 0.00;
		 * 
		 * Double totalamountdeductionloansaccos = 0.00;
		 * 
		 * Double totalamountdeductionloaneducationheslb = 0.00;
		 * 
		 * Double totalamountdeductionloaneducationother = 0.00;
		 * 
		 * Double totalamountdeductionloanother = 0.00;
		 * 
		 * Double totalamountdeductionloan = 0.00;
		 * 
		 * Double totalamountsalaryservantAndHospitality = 0.00;
		 * 
		 * PayrollResponses payrollResponses = new PayrollResponses();
		 * List<PayrollResponse> payrollResponselist = new ArrayList<>(); String pattern
		 * = "yyyy-MM-dd"; SimpleDateFormat simpleDateFormat = new
		 * SimpleDateFormat(pattern); List<HrmsPayroll> dbms =
		 * hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1); for
		 * (HrmsPayroll dbm : dbms) { PayrollResponse payrollResponse = new
		 * PayrollResponse(); // aggregate total totalamountsalarygross =
		 * totalamountsalarygross + dbm.getAmountsalarygross();
		 * 
		 * totalamountsalarybasic = totalamountsalarybasic + dbm.getAmountsalarybasic();
		 * totalamounttaxable = totalamounttaxable + dbm.getAmounttaxable();
		 * 
		 * totalamountsalarynet = totalamountsalarynet + dbm.getAmountsalarynet();
		 * 
		 * totalamountsalaryallowancetransport = totalamountsalaryallowancetransport +
		 * dbm.getAmountsalaryallowancetransport();
		 * 
		 * totalamountsalaryallowancehousing = totalamountsalaryallowancehousing +
		 * dbm.getAmountsalaryallowancehousing();
		 * 
		 * totalamountsalaryallowance = totalamountsalaryallowance +
		 * dbm.getAmountsalaryallowance();
		 * 
		 * totalamounttaxpaye = totalamounttaxpaye + dbm.getAmounttaxpaye();
		 * 
		 * totalamounttaxother = totalamounttaxother + dbm.getAmounttaxother();
		 * 
		 * totalamounttax = totalamounttax + dbm.getAmounttax();
		 * 
		 * totalamountdeductionmandatorypension = totalamountdeductionmandatorypension +
		 * dbm.getAmountdeductionmandatorysssf();
		 * 
		 * totalamountdeductionmandatoryhealth = totalamountdeductionmandatoryhealth +
		 * dbm.getAmountdeductionmandatoryhealth();
		 * 
		 * totalamountdeductionmandatory = totalamountdeductionmandatory +
		 * dbm.getAmountdeductionmandatory();
		 * 
		 * totalamountdeductionvoluntary = totalamountdeductionvoluntary +
		 * dbm.getAmountdeductionvoluntary();
		 * 
		 * totalamountdeduction = totalamountdeduction + dbm.getAmountdeduction();
		 * 
		 * totalamountdeductionloansalaryadvance = totalamountdeductionloansalaryadvance
		 * + dbm.getAmountdeductionloansalaryadvance();
		 * 
		 * totalamountdeductionloansaccos = totalamountdeductionloansaccos +
		 * dbm.getAmountdeductionloansaccos();
		 * 
		 * totalamountdeductionloaneducationheslb =
		 * totalamountdeductionloaneducationheslb +
		 * dbm.getAmountdeductionloaneducationheslb();
		 * 
		 * totalamountdeductionloaneducationother =
		 * totalamountdeductionloaneducationother +
		 * dbm.getAmountdeductionloaneducationother();
		 * 
		 * totalamountdeductionloanother = totalamountdeductionloanother +
		 * dbm.getAmountdeductionloanother();
		 * 
		 * totalamountdeductionloan = totalamountdeductionloan +
		 * dbm.getAmountdeductionloan();
		 * 
		 * totalamountsalaryservantAndHospitality =
		 * totalamountsalaryservantAndHospitality +
		 * dbm.getAmountsalaryservantAndHospitality();
		 * 
		 * payrollResponse.setActive(dbm.getActive());
		 * payrollResponse.setYear(dbm.getYear());
		 * payrollResponse.setMonth(dbm.getMonth());
		 * payrollResponse.setDay(dbm.getDay());
		 * payrollResponse.setAmounttaxable(dbm.getAmounttaxable());
		 * payrollResponse.setAmountdeduction(dbm.getAmountdeduction());
		 * payrollResponse.setAmountdeductionloan(dbm.getAmountdeductionloan());
		 * payrollResponse.setAmountdeductionloaneducationheslb(dbm.
		 * getAmountdeductionloaneducationheslb());
		 * payrollResponse.setAmountdeductionloaneducationother(dbm.
		 * getAmountdeductionloaneducationother());
		 * payrollResponse.setAmountdeductionloanother(dbm.getAmountdeductionloanother()
		 * );
		 * payrollResponse.setAmountdeductionloansaccos(dbm.getAmountdeductionloansaccos
		 * ()); payrollResponse.setAmountdeductionloansalaryadvance(dbm.
		 * getAmountdeductionloansalaryadvance());
		 * payrollResponse.setAmountdeductionmandatory(dbm.getAmountdeductionmandatory()
		 * ); payrollResponse.setAmountdeductionmandatoryhealth(dbm.
		 * getAmountdeductionmandatoryhealth());
		 * payrollResponse.setAmountdeductionmandatorypension(dbm.
		 * getAmountdeductionmandatorysssf());
		 * payrollResponse.setAmountdeductionvoluntary(dbm.getAmountdeductionvoluntary()
		 * ); payrollResponse.setAmountsalaryallowance(dbm.getAmountsalaryallowance());
		 * payrollResponse.setAmountsalaryallowancehousing(dbm.
		 * getAmountsalaryallowancehousing());
		 * payrollResponse.setAmountsalaryallowancetransport(dbm.
		 * getAmountsalaryallowancetransport());
		 * payrollResponse.setAmountsalarybasic(dbm.getAmountsalarybasic());
		 * payrollResponse.setAmountsalarygross(dbm.getAmountsalarygross());
		 * payrollResponse.setAmountsalaryservantAndHospitality(dbm.
		 * getAmountsalaryservantAndHospitality());
		 * 
		 * payrollResponse.setAmountsalarynet(dbm.getAmountsalarynet());
		 * payrollResponse.setAmounttax(dbm.getAmounttax());
		 * payrollResponse.setAmounttaxother(dbm.getAmounttaxother());
		 * payrollResponse.setAmounttaxpaye(dbm.getAmounttaxpaye());
		 * payrollResponse.setApproved(dbm.getApproved()); if (dbm.getCurrencyid() != 0
		 * && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
		 * payrollResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid
		 * ()).get().getName()); } payrollResponse.setCurrencyid(dbm.getCurrencyid());
		 * if (dbm.getDatepay() != null) {
		 * payrollResponse.setDatepay(simpleDateFormat.format(dbm.getDatepay())); } if
		 * (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) { StringBuilder
		 * fullName = new StringBuilder();
		 * 
		 * HrmsEmployee hrmsEmployee =
		 * hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
		 * 
		 * if (hrmsEmployee.getDutystationid() != 0) { payrollResponse.setOffice(
		 * hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).
		 * get().getName());
		 * payrollResponse.setOfficeid(hrmsEmployee.getDutystationid());
		 * payrollResponse.setOfficetypeid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid());
		 * payrollResponse .setOfficetype( hrmsOrganisationOfficeCategoryRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
		 * .get().getName());
		 * payrollResponse.setDutystationcityid(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid());
		 * payrollResponse .setDutystationcity( hrmsLocationCityRepository
		 * .findById(hrmsOrganisationOfficeRepository
		 * .findById(hrmsEmployee.getDutystationid()).get().getCityid())
		 * .get().getName());
		 * 
		 * }
		 * 
		 * payrollResponse.setDesignationid(hrmsEmployee.getDesignationId()); if
		 * (hrmsEmployee.getDesignationId() != 0 &&
		 * hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
		 * payrollResponse.setDesignation(
		 * hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().
		 * getName());
		 * 
		 * } fullName.append(hrmsEmployee.getFirstName().trim()); if
		 * (hrmsEmployee.getMiddleName() != null) { fullName.append(" " +
		 * hrmsEmployee.getMiddleName().trim()); } fullName.append(" " +
		 * hrmsEmployee.getLastName().trim());
		 * payrollResponse.setFullName(fullName.toString()); }
		 * payrollResponse.setEmployeeid(dbm.getEmployeeid());
		 * payrollResponse.setId(dbm.getId());
		 * 
		 * if (dbm.getPayrolltypeid() != 0 &&
		 * hrmsPayrollTypeRepository.existsById(dbm.getPayrolltypeid())) {
		 * payrollResponse
		 * .setPayrolltype(hrmsPayrollTypeRepository.findById(dbm.getPayrolltypeid()).
		 * get().getName()); } payrollResponse.setPayrolltypeid(dbm.getPayrolltypeid());
		 * payrollResponselist.add(payrollResponse); }
		 * payrollResponses.setPayrollResponselist(payrollResponselist);
		 * 
		 * // add total of each amount down here
		 * payrollResponses.setTotalamountsalarygross(totalamountsalarygross);
		 * 
		 * payrollResponses.setTotalamountsalarybasic(totalamountsalarybasic);
		 * 
		 * payrollResponses.setTotalamountsalarynet(totalamountsalarynet);
		 * 
		 * payrollResponses.setTotalamountsalaryallowancetransport(
		 * totalamountsalaryallowancetransport);
		 * 
		 * payrollResponses.setTotalamountsalaryallowancehousing(
		 * totalamountsalaryallowancehousing);
		 * 
		 * payrollResponses.setTotalamountsalaryallowance(totalamountsalaryallowance);
		 * 
		 * payrollResponses.setTotalamounttaxpaye(totalamounttaxpaye);
		 * 
		 * payrollResponses.setTotalamounttaxother(totalamounttaxother);
		 * 
		 * payrollResponses.setTotalamounttax(totalamounttax);
		 * 
		 * payrollResponses.setTotalamountdeductionmandatorypension(
		 * totalamountdeductionmandatorypension);
		 * 
		 * payrollResponses.setTotalamountdeductionmandatoryhealth(
		 * totalamountdeductionmandatoryhealth);
		 * 
		 * payrollResponses.setTotalamountdeductionmandatory(
		 * totalamountdeductionmandatory);
		 * 
		 * payrollResponses.setTotalamountdeductionvoluntary(
		 * totalamountdeductionvoluntary);
		 * 
		 * payrollResponses.setTotalamountdeduction(totalamountdeduction);
		 * 
		 * payrollResponses.setTotalamountdeductionloansalaryadvance(
		 * totalamountdeductionloansalaryadvance);
		 * 
		 * payrollResponses.setTotalamountdeductionloansaccos(
		 * totalamountdeductionloansaccos);
		 * 
		 * payrollResponses.setTotalamountdeductionloaneducationheslb(
		 * totalamountdeductionloaneducationheslb);
		 * 
		 * payrollResponses.setTotalamountdeductionloaneducationother(
		 * totalamountdeductionloaneducationother);
		 * 
		 * payrollResponses.setTotalamountdeductionloanother(
		 * totalamountdeductionloanother);
		 * 
		 * payrollResponses.setTotalamountdeductionloan(totalamountdeductionloan);
		 * 
		 * payrollResponses.setTotalamountsalaryservantAndHospitality(
		 * totalamountsalaryservantAndHospitality);
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(payrollResponses);
		 */

		// } else {
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// }

		return null;
	}

	@Override
	public ResponseEntity<PayrollProcessResponse> processPayrollPerPeriodV2(int year, int month) {

		PayrollProcessResponse payrollProcessResponse = new PayrollProcessResponse();

		int employeeTotal = 0;

		Double grossSalaryTotal = 0.00;

		Double taxTotal = 0.00;

		Double pensionEmployerTotal = 0.00;

		Double pensionEmployeeTotal = 0.00;
		Double insuranceEmployerTotal = 0.00;
		Double loanTotal = 0.00;

		Double netSalaryTotal = 0.00;
		Double basicSalaryTotal = 0.00;

		Double amountotherincome = 0.00;

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		/*
		 * String dt = simpleDateFormat.format(period);
		 * 
		 * String[] dateParts = dt.split("-"); int day = Integer.parseInt(dateParts[2]);
		 * int month = Integer.parseInt(dateParts[1]); int year =
		 * Integer.parseInt(dateParts[0]);
		 * 
		 * int year1 = LocalDateTime.now().getYear();
		 */

		int month1 = LocalDateTime.now().getMonthValue();
		int day = LocalDateTime.now().getDayOfMonth();
		Integer[] status = { 2 };

		if (year < 2020 || (month < 1 || month > 12)) {// verify there no existing processed
			// payroll
			// for this month before processing

			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
		} else {

			if (hrmsPayrollRepository.existsByYearAndMonthAndActive(year, month, 1)) {

				List<HrmsPayroll> hrmsPayrolllist = hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1);

				for (HrmsPayroll dbp : hrmsPayrolllist) {

					basicSalaryTotal = basicSalaryTotal + dbp.getAmountsalarybasic();
					grossSalaryTotal = grossSalaryTotal + dbp.getAmountsalarygross();
					loanTotal = loanTotal + dbp.getAmountdeductionloan();
					netSalaryTotal = netSalaryTotal + dbp.getAmountsalarynet();

					taxTotal = taxTotal + dbp.getAmounttax();

					insuranceEmployerTotal = insuranceEmployerTotal + dbp.getAmountsalarybasic() * 0.06;

					pensionEmployeeTotal = pensionEmployeeTotal + dbp.getAmountdeductionmandatorysssf();
					pensionEmployerTotal = pensionEmployerTotal + dbp.getAmountdeductionmandatorysssf()
							+ dbp.getAmountdeductionmandatorysssf() * 3;

				}

				employeeTotal = hrmsPayrollRepository.countByYearAndMonthAndActive(year, month, 1);
				payrollProcessResponse.setBasicSalaryTotal(basicSalaryTotal);
				payrollProcessResponse.setEmployeeTotal(employeeTotal);
				payrollProcessResponse.setGrossSalaryTotal(grossSalaryTotal);
				payrollProcessResponse.setInsuranceEmployerTotal(insuranceEmployerTotal); // compute
				payrollProcessResponse.setLoanTotal(loanTotal);
				payrollProcessResponse.setNetSalaryTotal(netSalaryTotal);
				payrollProcessResponse.setPensionEmployeeTotal(pensionEmployeeTotal);
				payrollProcessResponse.setPensionEmployerTotal(pensionEmployerTotal);
				payrollProcessResponse.setTaxTotal(taxTotal);

				return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(payrollProcessResponse);
			}

			List<HrmsEmployee> hrmsEmployeelist = hrmsEmployeeRepository.findByEmploymentstatusidInAndActive(status, 1); // get
																															// list
			// of
			// all
			// employee
			// credible
			// to
			// payment

			for (HrmsEmployee dbm : hrmsEmployeelist) {

				if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1) && !hrmsPayrollRepository
						.existsByEmployeeidAndYearAndMonthAndActive(dbm.getId(), year, month, 1)) {

					if (!hrmsPayrollRepository.existsByEmployeeidAndYearAndMonthAndActive(dbm.getId(), year, month,
							1)) {

						HrmsPayroll hrmsPayroll = new HrmsPayroll();
						hrmsPayroll.setActive(1);
						hrmsPayroll.setYear(year);
						hrmsPayroll.setMonth(month);
						hrmsPayroll.setDay(day);
						hrmsPayroll.setEmployeeid(dbm.getId());

						// date pay
						hrmsPayroll.setDatepay(new Date());
						// basic salary
						Double bsalary = 0.00;
						int pssserviceproviderid = 0;
						Double amountsalarygross = 0.00;
						Double amounttaxable = 0.00;
						if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
									.findByEmployeeidAndActive(dbm.getId(), 1);
							if (hrmsSalarystructureRepository.existsById(hrmsemployeesalary.getSalarystructureId())) {
								HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
										.findById(hrmsemployeesalary.getSalarystructureId()).get();
								bsalary = hrmsSalarystructure.getBasicSalary();

								hrmsPayroll.setCurrencyid(4);// 4 for TZS

							}
						}

						basicSalaryTotal = basicSalaryTotal + bsalary;
						hrmsPayroll.setAmountsalarybasic(bsalary);

						Double amountsalaryallowancetransport = 0.00;
						Double amountsalaryallowancehousing = 0.00;
						Double amountsalaryallowanceservantAndHospitality = 0.00;

						Double amountsalaryallowance = 0.00;

						Double psssfemployee = 0.00;
						Double psssfemployer = 0.00;
						Double psssftotal = 0.00;
						int psssfcontributiontype = 0;
						// salarystructure
						if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
							Hrmsemployeesalary hrmsemployeesalary1 = hrmsemployeesalaryRepository
									.findByEmployeeidAndActive(dbm.getId(), 1);
							// Allowance , house allowance, transport etc
							if (hrmsemployeesalary1.getSalarystructureId() != 0

									&& (hrmsAllowanceRepository.existsBySalarystructureidAndCurrencyidAndActive(
											hrmsemployeesalary1.getSalarystructureId(),
											hrmsemployeesalary1.getCurrencyId(), 1))) {

								List<HrmsAllowance> hrmsAllowancelist = hrmsAllowanceRepository
										.findBySalarystructureidAndCurrencyidAndActive(
												hrmsemployeesalary1.getSalarystructureId(),
												hrmsemployeesalary1.getCurrencyId(), 1);
								for (HrmsAllowance allowance : hrmsAllowancelist) {

									if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get()
											.getName().toLowerCase().contains("transport")) {

										amountsalaryallowancetransport = allowance.getAmount();

									}

									if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get()
											.getName().toLowerCase().contains("house")) {

										amountsalaryallowancehousing = allowance.getAmount();

									}

									if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get()
											.getName().toLowerCase().contains("servant")) {

										amountsalaryallowanceservantAndHospitality = allowance.getAmount();

									}

								}

							}
						}

						amountsalaryallowance = (amountsalaryallowancetransport + amountsalaryallowancehousing
								+ amountsalaryallowanceservantAndHospitality);

						hrmsPayroll.setAmountsalaryallowance(amountsalaryallowance);
						hrmsPayroll.setAmountsalaryallowancetransport(amountsalaryallowancetransport);

						hrmsPayroll.setAmountsalaryallowancehousing(amountsalaryallowancehousing);

						hrmsPayroll.setAmountsalaryservantAndHospitality(amountsalaryallowanceservantAndHospitality);

						// Gross salary
						amountsalarygross = (bsalary + amountsalaryallowance);
						grossSalaryTotal = grossSalaryTotal + amountsalarygross;
						hrmsPayroll.setAmountsalarygross(amountsalarygross);

						// amount_deduction_mandatory_sssf

						List<HrmsPayrollContributionMandatorySocialSecurityScheme> psssfs = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
								.findByActive(1);
						HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme = hrmsEmployeeSocialSecuritySchemeRepositoty
								.findFirstByEmployeeidAndActive(dbm.getId(), 1);
						int ssserviceproviderid = 0;
						if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndActive(dbm.getId(), 1)
								&& hrmsEmployeeSocialSecurityScheme.getServiceproviderid() != 0) {
							ssserviceproviderid = hrmsEmployeeSocialSecurityScheme.getServiceproviderid();

							for (HrmsPayrollContributionMandatorySocialSecurityScheme pssf : psssfs) {
								if (ssserviceproviderid != 0) {

									if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(ssserviceproviderid)
											.get().getName().toLowerCase().contains("psssf")
											&& ssserviceproviderid == pssf.getServiceproviderid()) {
										psssfcontributiontype = pssf.getContributiontypeid();

										psssfemployee = (pssf.getAmount() + pssf.getRateemployee() * bsalary);
										psssfemployer = (pssf.getAmount() + pssf.getRateemployer() * bsalary);
										psssftotal = psssfemployee + psssfemployer;

										pssserviceproviderid = pssf.getServiceproviderid();

									}

									if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(ssserviceproviderid)
											.get().getName().toLowerCase().contains("zss")
											&& ssserviceproviderid == pssf.getServiceproviderid()) {
										psssfcontributiontype = pssf.getContributiontypeid();

										psssfemployee = (pssf.getAmount() + pssf.getRateemployee() * bsalary);
										psssfemployer = (pssf.getAmount() + pssf.getRateemployer() * bsalary);
										psssftotal = psssfemployee + psssfemployer;

										pssserviceproviderid = pssf.getServiceproviderid();

									}
								}

							}

						}

						pensionEmployerTotal = pensionEmployerTotal + psssfemployer;
						pensionEmployeeTotal = pensionEmployeeTotal + psssfemployee;

						hrmsPayroll.setAmountdeductionmandatorysssf(psssfemployee);
						amounttaxable = (amountsalarygross - psssfemployee);
						hrmsPayroll.setAmounttaxable(amounttaxable);

						// amount_deduction_mandatory_health
						Double heathamountemployee = 0.00;
						Double heathamountemployer = 0.00;
						Double heathamounttotal = 0.00;
						int heathcontributiontype = 0;
						int heathserviceprovider = 0;
						int wcfcontributiontype = 0;
						int wcfserviceprovider = 0;
						Double wcfamountemployer = 0.00;
						List<HrmsPayrollContributionMandatoryInsurance> nhifs = hrmsPayrollContributionMandatoryInsuranceRepository
								.findByActive(1);

						for (HrmsPayrollContributionMandatoryInsurance nhif : nhifs) {

							if (hrmsInsuranceProviderRepository.findById(nhif.getServiceproviderid()).get().getName()
									.toLowerCase().contains("nhif")) {
								heathserviceprovider = nhif.getServiceproviderid();
								heathcontributiontype = nhif.getContributiontypeid();

								heathamountemployee = (nhif.getAmount() + nhif.getRateemployee() * bsalary);
								heathamountemployer = (nhif.getAmount() + nhif.getRateemployer() * bsalary);
								heathamounttotal = heathamountemployee + heathamountemployer;

							}

							if (hrmsInsuranceProviderRepository.findById(nhif.getServiceproviderid()).get().getName()
									.toLowerCase().contains("workers")) {
								wcfserviceprovider = nhif.getServiceproviderid();
								wcfcontributiontype = nhif.getContributiontypeid();

								wcfamountemployer = (nhif.getAmount() + nhif.getRateemployer() * bsalary);

							}

						}

						insuranceEmployerTotal = insuranceEmployerTotal + heathamounttotal;
						hrmsPayroll.setAmountdeductionmandatoryhealth(heathamountemployee);

						// amount_deduction_mandatory
						// here we compute total mandatory deductions
						Double mandatorydeductions = psssfemployee + heathamountemployee;
						hrmsPayroll.setAmountdeductionmandatory(mandatorydeductions);

						// amount_deduction_voluntary
						Double amountdeductionvoluntary = 0.00;

						HrmsPayrollContributionVoluntary HrmsPayrollContributionVoluntaryonce = new HrmsPayrollContributionVoluntary();
						if (hrmsPayrollContributionVoluntaryRepository.existsByEmployeeidAndActiveAndLocked(dbm.getId(),
								1, 0)) {

							List<HrmsPayrollContributionVoluntary> voluntarycontributionlist = hrmsPayrollContributionVoluntaryRepository
									.findByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0);

							for (HrmsPayrollContributionVoluntary vcontribtn : voluntarycontributionlist) {
								if (vcontribtn.getJoiningdate() != null) {

									String dt1 = simpleDateFormat.format(vcontribtn.getJoiningdate());

									String[] dateParts1 = dt1.split("-");
									int day2 = Integer.parseInt(dateParts1[2]);
									int month2 = Integer.parseInt(dateParts1[1]);
									int year2 = Integer.parseInt(dateParts1[0]);

									if ((vcontribtn.getContributionmode() == 1) && (month2 == month)
											&& (year2 == year)) {

										amountdeductionvoluntary = amountdeductionvoluntary
												+ (vcontribtn.getAmount() + vcontribtn.getRate() * bsalary);
										vcontribtn.setLocked(1);

										vcontribtn.setDate_updated(LocalDateTime.now());
										HrmsPayrollContributionVoluntaryonce = vcontribtn;

									}
									if (vcontribtn.getContributionmode() != 1) {

										amountdeductionvoluntary = amountdeductionvoluntary
												+ (vcontribtn.getAmount() + vcontribtn.getRate() * bsalary);

									}

								}

							}

						}
						hrmsPayroll.setAmountdeductionvoluntary(amountdeductionvoluntary);

						// amount_deduction_loan_salary_advance
						Double amountdeductionloansalaryadvance = 0.00;
						Double amountdeductionloansaccos = 0.00;
						Double amountdeductionloaneducationheslb = 0.00;
						Double amountdeductionloaneducationother = 0.00;
						Double amountdeductionloanother = 0.00;
						Double amountdeductionloan = 0.00;
						Integer[] loanstatus = { 0, 1 };
						if (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndStatusInAndActive(dbm.getId(),
								loanstatus, 1)) {

							List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
									.findByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus, 1);

							for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist) {

								if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().getName()
										.toLowerCase().contains("advance")) {

									if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
										amountdeductionloansalaryadvance = emploan.getAmountoutstanding();

									} else {

										amountdeductionloansalaryadvance = emploan.getAmountprincipal();

									}
								}

								if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().getName()
										.toLowerCase().contains("heslb")) {

									if (emploan.getAmountoutstanding() < (emploan.getAmountprincipal()
											+ bsalary * emploan.getRate())) {
										amountdeductionloaneducationheslb = emploan.getAmountoutstanding();

									} else {

										amountdeductionloaneducationheslb = (emploan.getAmountprincipal()
												+ bsalary * emploan.getRate());

									}

								}

								if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().getName()
										.toLowerCase().contains("saccos")
										&& hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getIsinternalsource() == 1) {

									if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
										amountdeductionloansaccos = emploan.getAmountoutstanding();

									} else {

										amountdeductionloansaccos = emploan.getAmountprincipal();

									}

								}

								if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().getName()
										.toLowerCase().contains("educationother")) {

									if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
										amountdeductionloaneducationother = emploan.getAmountoutstanding();

									} else {

										amountdeductionloaneducationother = emploan.getAmountprincipal();

									}

								}

								if (!hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get().getName()
										.toLowerCase().contains("advance")
										&& !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("heslb")
										&& !(hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("saccos")
												&& hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
														.getIsinternalsource() == 1)

										&& !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("educationother")) {

									if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {

										amountdeductionloanother = amountdeductionloanother
												+ emploan.getAmountoutstanding();

									} else {

										amountdeductionloanother = amountdeductionloanother
												+ emploan.getAmountprincipal();

									}

								}

							}

						}
						amountdeductionloan = (amountdeductionloansalaryadvance + amountdeductionloaneducationheslb
								+ amountdeductionloansaccos + amountdeductionloaneducationother
								+ amountdeductionloanother);

						loanTotal = loanTotal + amountdeductionloan;
						hrmsPayroll.setAmountdeductionloan(amountdeductionloan);

						hrmsPayroll.setAmountdeductionloansalaryadvance(amountdeductionloansalaryadvance);
						hrmsPayroll.setAmountdeductionloaneducationheslb(amountdeductionloaneducationheslb);

						hrmsPayroll.setAmountdeductionloansaccos(amountdeductionloansaccos);
						hrmsPayroll.setAmountdeductionloaneducationother(amountdeductionloaneducationother);
						hrmsPayroll.setAmountdeductionloanother(amountdeductionloanother);
						hrmsPayroll.setAmountotherincome(amountotherincome);// other income to be computed once
																			// available
						// Tax deduction

						// if(hrmsPayrollTaxStructureRepository.) {

						// }
						Double amounttaxpaye = 0.00;
						Double amounttax = 0.00;
						Double amounttaxother = 0.00; // this is not yet available so it will remain zero
						int taxtypeid = 0;
						int currencyid = 4;
						List<HrmsPayrollTaxStructure> hrmsPayrollTaxStructurelist = hrmsPayrollTaxStructureRepository
								.findByActive(1);
						for (HrmsPayrollTaxStructure tax : hrmsPayrollTaxStructurelist) {
							if (hrmsPayrollTaxTypeRepository.findById(tax.getTaxtypeid()).get().getName().toLowerCase()
									.contains("paye")
									&& (amounttaxable >= tax.getAmountmin() && amounttaxable <= tax.getAmountmax())) {
								taxtypeid = tax.getTaxtypeid();
								currencyid = tax.getCurrencyid();

								amounttaxpaye = (tax.getAmount()
										+ (tax.getRate() * (amounttaxable - (tax.getAmountmin() - 1))));

							}

						}
						hrmsPayroll.setAmounttaxpaye(amounttaxpaye);
						hrmsPayroll.setAmounttaxother(0.00);// so far we do not have any other tax , thats why we set
															// default value to 0

						amounttax = amounttaxpaye + amounttaxother;

						taxTotal = taxTotal + amounttax;
						hrmsPayroll.setAmounttax(amounttax);

						// amount_deduction , this calculates total deduction mandatory and voluntary
						Double amountdeduction = mandatorydeductions + amountdeductionvoluntary + amounttax
								+ amountdeductionloan;

						hrmsPayroll.setAmountdeduction(amountdeduction);
						// net salary
						// Gross minus deductions
						Double amountsalarynet = amountsalarygross - (amountdeduction);

						netSalaryTotal = netSalaryTotal + amountsalarynet;

						hrmsPayroll.setAmountsalarynet(amountsalarynet);
						hrmsPayroll.setUnique_id(UUID.randomUUID());
						hrmsPayroll.setPayrolltypeid(1);
						int payrollid = hrmsPayrollRepository.saveAndFlush(hrmsPayroll).getId();

						employeeTotal = hrmsPayrollRepository.countByYearAndMonthAndActive(year, month, 1);

						// now compute transactional tables for loans and other deductions

						// first tax deduction
						HrmsPayrollDeductionTax hrmsPayrollDeductionTax = new HrmsPayrollDeductionTax();
						hrmsPayrollDeductionTax.setActive(1);
						hrmsPayrollDeductionTax.setApproved(0);
						hrmsPayrollDeductionTax.setAmount(amounttaxpaye);
						hrmsPayrollDeductionTax.setCurrencyid(currencyid);
						hrmsPayrollDeductionTax.setDatededucted(new Date());
						hrmsPayrollDeductionTax.setEmployeeid(dbm.getId());
						hrmsPayrollDeductionTax.setPayrollid(payrollid);
						hrmsPayrollDeductionTax.setTaxtypeid(taxtypeid);
						hrmsPayrollDeductionTax.setUnique_id(UUID.randomUUID());
						hrmsPayrollDeductionTax.setYear(year);
						hrmsPayrollDeductionTax.setMonth(month);

						hrmsPayrollDeductionTaxRepository.saveAndFlush(hrmsPayrollDeductionTax);

						// Health insurance deduction

						// HrmsPayrollContributionMandatoryInsurance pcmi =
						// hrmsPayrollContributionMandatoryInsuranceRepository
						// .findByContributiontypeid(heathcontributiontype);
						HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance = new HrmsPayrollDeductionMandatoryInsurance();

						hrmsPayrollDeductionMandatoryInsurance.setActive(1);
						hrmsPayrollDeductionMandatoryInsurance.setApproved(0);
						hrmsPayrollDeductionMandatoryInsurance.setAmount(heathamounttotal);
						hrmsPayrollDeductionMandatoryInsurance.setAmountemployee(heathamountemployee);
						hrmsPayrollDeductionMandatoryInsurance.setAmountemployer(heathamountemployer);
						hrmsPayrollDeductionMandatoryInsurance.setContributiontypeid(heathcontributiontype);
						hrmsPayrollDeductionMandatoryInsurance.setCurrencyid(currencyid);
						hrmsPayrollDeductionMandatoryInsurance.setDatededucted(new Date());
						hrmsPayrollDeductionMandatoryInsurance.setEmployeeid(dbm.getId());
						hrmsPayrollDeductionMandatoryInsurance.setPayrollid(payrollid);
						hrmsPayrollDeductionMandatoryInsurance.setUnique_id(UUID.randomUUID());
						hrmsPayrollDeductionMandatoryInsurance.setYear(year);
						hrmsPayrollDeductionMandatoryInsurance.setMonth(month);

						hrmsPayrollDeductionMandatoryInsurance.setServiceproviderid(heathserviceprovider);

						hrmsPayrollDeductionMandatoryInsuranceRepository
								.saveAndFlush(hrmsPayrollDeductionMandatoryInsurance);

						// Workers compensation fund
						// HrmsPayrollContributionMandatoryInsurance pcmiwc =
						// hrmsPayrollContributionMandatoryInsuranceRepository
						// .findByContributiontypeid(wcfcontributiontype);
						HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance1 = new HrmsPayrollDeductionMandatoryInsurance();

						hrmsPayrollDeductionMandatoryInsurance1.setActive(1);
						hrmsPayrollDeductionMandatoryInsurance1.setApproved(0);
						hrmsPayrollDeductionMandatoryInsurance1.setAmount(wcfamountemployer);
						hrmsPayrollDeductionMandatoryInsurance1.setAmountemployee(0.00);
						hrmsPayrollDeductionMandatoryInsurance1.setAmountemployer(wcfamountemployer);
						hrmsPayrollDeductionMandatoryInsurance1.setContributiontypeid(wcfcontributiontype);
						hrmsPayrollDeductionMandatoryInsurance1.setCurrencyid(currencyid);
						hrmsPayrollDeductionMandatoryInsurance1.setDatededucted(new Date());
						hrmsPayrollDeductionMandatoryInsurance1.setEmployeeid(dbm.getId());
						hrmsPayrollDeductionMandatoryInsurance1.setPayrollid(payrollid);
						hrmsPayrollDeductionMandatoryInsurance1.setUnique_id(UUID.randomUUID());
						hrmsPayrollDeductionMandatoryInsurance1.setYear(year);
						hrmsPayrollDeductionMandatoryInsurance1.setMonth(month);

						hrmsPayrollDeductionMandatoryInsurance.setServiceproviderid(wcfserviceprovider);

						hrmsPayrollDeductionMandatoryInsuranceRepository
								.saveAndFlush(hrmsPayrollDeductionMandatoryInsurance1);

						// public social security fund deductions
						if (psssfcontributiontype != 0) {
							HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme = new HrmsPayrollDeductionMandatorySocialSecurityScheme();
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setActive(1);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmount(psssftotal);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmountemployee(psssfemployee);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmountemployer(psssfemployer);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setApproved(0);
							hrmsPayrollDeductionMandatorySocialSecurityScheme
									.setContributiontypeid(psssfcontributiontype);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setDatededucted(new Date());
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setEmployeeid(dbm.getId());
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setPayrollid(payrollid);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setUnique_id(UUID.randomUUID());
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setYear(year);
							hrmsPayrollDeductionMandatorySocialSecurityScheme.setMonth(month);
							hrmsPayrollDeductionMandatorySocialSecurityScheme
									.setServiceproviderid(pssserviceproviderid);

							hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
									.saveAndFlush(hrmsPayrollDeductionMandatorySocialSecurityScheme);
						}

						// Voluntary deduction
						if (hrmsPayrollContributionVoluntaryRepository.existsByEmployeeidAndActiveAndLocked(dbm.getId(),
								1, 0)) {

							List<HrmsPayrollContributionVoluntary> voluntarycontributionlist1 = hrmsPayrollContributionVoluntaryRepository
									.findByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0);

							Double contamount = 0.00;
							if (HrmsPayrollContributionVoluntaryonce != null
									&& HrmsPayrollContributionVoluntaryonce.getAmount() != null) {
								contamount = HrmsPayrollContributionVoluntaryonce.getAmount();

								// update the onceoff payment if it was not null
								if (contamount > 0.00) {
									HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = new HrmsPayrollDeductionVoluntary();
									hrmsPayrollDeductionVoluntary.setActive(1);
									hrmsPayrollDeductionVoluntary
											.setAmount(HrmsPayrollContributionVoluntaryonce.getAmount());
									hrmsPayrollDeductionVoluntary.setApproved(0);
									hrmsPayrollDeductionVoluntary.setContributiontypeid(
											HrmsPayrollContributionVoluntaryonce.getContributiontypeid());
									hrmsPayrollDeductionVoluntary
											.setCurrencyid(HrmsPayrollContributionVoluntaryonce.getCurrencyid());
									hrmsPayrollDeductionVoluntary.setDatededucted(new Date());
									hrmsPayrollDeductionVoluntary.setEmployeeid(dbm.getId());
									hrmsPayrollDeductionVoluntary.setPayrollid(payrollid);
									hrmsPayrollDeductionVoluntary.setUnique_id(UUID.randomUUID());
									hrmsPayrollDeductionVoluntary.setYear(year);
									hrmsPayrollDeductionVoluntary.setMonth(month);
									hrmsPayrollDeductionVoluntaryRepository.saveAndFlush(hrmsPayrollDeductionVoluntary);
									// lock this transaction now as payment is now full

									hrmsPayrollContributionVoluntaryRepository
											.saveAndFlush(HrmsPayrollContributionVoluntaryonce);

								}
							}

							for (HrmsPayrollContributionVoluntary vcontribtn : voluntarycontributionlist1) {
								HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = new HrmsPayrollDeductionVoluntary();
								hrmsPayrollDeductionVoluntary.setActive(1);
								hrmsPayrollDeductionVoluntary.setAmount(vcontribtn.getAmount());
								hrmsPayrollDeductionVoluntary.setApproved(0);
								hrmsPayrollDeductionVoluntary.setContributiontypeid(vcontribtn.getContributiontypeid());
								hrmsPayrollDeductionVoluntary.setCurrencyid(vcontribtn.getCurrencyid());
								hrmsPayrollDeductionVoluntary.setDatededucted(new Date());
								hrmsPayrollDeductionVoluntary.setEmployeeid(dbm.getId());
								hrmsPayrollDeductionVoluntary.setPayrollid(payrollid);
								hrmsPayrollDeductionVoluntary.setUnique_id(UUID.randomUUID());
								hrmsPayrollDeductionVoluntary.setYear(year);
								hrmsPayrollDeductionVoluntary.setMonth(month);

								hrmsPayrollDeductionVoluntaryRepository.saveAndFlush(hrmsPayrollDeductionVoluntary);
							}
						}

						// loan deduction

						Integer[] loanstatus1 = { 0, 1 };
						if (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndStatusInAndActive(dbm.getId(),
								loanstatus1, 1)) {
							List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist1 = hrmsPayrollEmployeeLoanRepository
									.findByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus1, 1);

							for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist1) {
								int loanid = 0;
								Double Amountoutstanding = 0.0;

								HrmsPayrollDeductionLoan hrmsPayrollDeductionLoan = new HrmsPayrollDeductionLoan();
								hrmsPayrollDeductionLoan.setActive(1);

								if (emploan.getAmountloanbalance() < emploan.getAmountprincipal()) {
									hrmsPayrollDeductionLoan.setAmount(emploan.getAmountloanbalance());

								}
								if (emploan.getAmountloanbalance() >= emploan.getAmountprincipal()) {
									hrmsPayrollDeductionLoan.setAmount(emploan.getAmountprincipal());
									Amountoutstanding = emploan.getAmountloanbalance() - emploan.getAmountprincipal();

								}

								hrmsPayrollDeductionLoan.setAmountoutstanding(Amountoutstanding);
								hrmsPayrollDeductionLoan.setAmountpenalty(0.00);
								hrmsPayrollDeductionLoan.setApproved(0);

								hrmsPayrollDeductionLoan.setCurrencyid(emploan.getCurrencyid());
								hrmsPayrollDeductionLoan.setDatededucted(new Date());
								hrmsPayrollDeductionLoan.setEmployeeid(dbm.getId());
								loanid = emploan.getId();
								hrmsPayrollDeductionLoan.setLoanid(loanid);
								hrmsPayrollDeductionLoan.setLoantypeid(emploan.getLoantypeid());
								hrmsPayrollDeductionLoan.setPayrollid(payrollid);
								hrmsPayrollDeductionLoan.setUnique_id(UUID.randomUUID());
								hrmsPayrollDeductionLoan.setYear(year);
								hrmsPayrollDeductionLoan.setMonth(month);
								hrmsPayrollDeductionLoanRepository.saveAndFlush(hrmsPayrollDeductionLoan);

								// update employee loan table
								HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan = hrmsPayrollEmployeeLoanRepository
										.findById(emploan.getId()).get();
								if (Amountoutstanding == 0) {
									hrmsPayrollEmployeeLoan.setStatus(2); // completed paying
									hrmsPayrollEmployeeLoan.setAmountloanbalance(Amountoutstanding);
									hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());
								}
								if (Amountoutstanding > 0) {
									hrmsPayrollEmployeeLoan.setStatus(1); // still paying
									hrmsPayrollEmployeeLoan.setAmountloanbalance(Amountoutstanding);

									hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());

								}
								hrmsPayrollEmployeeLoanRepository.saveAndFlush(hrmsPayrollEmployeeLoan);

							}

						}

						// Payroll Bank Account processing

						processPayrollBankAccount(amountsalarynet, payrollid, dbm.getId(), currencyid, year, month,
								day);

					}
				}
			}
			payrollProcessResponse.setBasicSalaryTotal(basicSalaryTotal);
			payrollProcessResponse.setEmployeeTotal(employeeTotal);
			payrollProcessResponse.setGrossSalaryTotal(grossSalaryTotal);
			payrollProcessResponse.setInsuranceEmployerTotal(insuranceEmployerTotal);
			payrollProcessResponse.setLoanTotal(loanTotal);
			payrollProcessResponse.setNetSalaryTotal(netSalaryTotal);
			payrollProcessResponse.setPensionEmployeeTotal(pensionEmployeeTotal);
			payrollProcessResponse.setPensionEmployerTotal(pensionEmployerTotal);
			payrollProcessResponse.setTaxTotal(taxTotal);
			return ResponseEntity.status(HttpStatus.OK).body(payrollProcessResponse);
		}
	}

	@Override
	public ResponseEntity<PayrollResponsesv2> getPayrollByPeriodV2(int year, int month) {

		// if (hrmsPayrollRepository.existsByYearAndMonthAndActive(year, month, 1)) {
		// initialize totalamount of every element needed to have total
		Double totalamountsalarygross = 0.00;

		Double totalamountsalarybasic = 0.00;
		Double totalamounttaxable = 0.00;

		Double totalamountsalarynet = 0.00;

		Double totalamountsalaryallowancetransport = 0.00;

		Double totalamountsalaryallowancehousing = 0.00;

		Double totalamountsalaryallowance = 0.00;

		Double totalamounttaxpaye = 0.00;

		Double totalamounttaxother = 0.00;

		Double totalamounttax = 0.00;

		Double totalamountdeductionmandatorypension = 0.00;

		Double totalamountdeductionmandatoryhealth = 0.00;

		Double totalamountdeductionmandatory = 0.00;
		Double totalamountdeductionmandatoryEmployer = 0.00;

		Double totalamountdeductionvoluntary = 0.00;

		Double totalamountdeduction = 0.00;

		Double totalamountdeductionloansalaryadvance = 0.00;

		Double totalamountdeductionloansaccos = 0.00;

		Double totalamountdeductionloaneducationheslb = 0.00;

		Double totalamountdeductionloaneducationother = 0.00;

		Double totalamountdeductionloanother = 0.00;

		Double totalamountdeductionloan = 0.00;

		Double totalamountsalaryservantAndHospitality = 0.00;

		Double totalamountTcraSaccos = 0.00;

		Double totalamountPostanaSimuSaccos = 0.00;
		Double totalamountotherincome = 0.00;
		String datepay = year + "-" + month + "-" + 15;

		PayrollResponsesv2 payrollResponses = new PayrollResponsesv2();
		List<PayrollResponsev2> payrollResponselist = new ArrayList<>();
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1);
		for (HrmsPayroll dbm : dbms) {
			Double amountTcraSaccos = 0.00;

			Double amountPostanaSimuSaccos = 0.00;

			PayrollResponsev2 payrollResponse = new PayrollResponsev2();
			// aggregate total
			totalamountsalarygross = totalamountsalarygross + dbm.getAmountsalarygross();

			totalamountsalarybasic = totalamountsalarybasic + dbm.getAmountsalarybasic();
			totalamounttaxable = totalamounttaxable + dbm.getAmounttaxable();

			totalamountsalarynet = totalamountsalarynet + dbm.getAmountsalarynet();

			totalamountsalaryallowancetransport = totalamountsalaryallowancetransport
					+ dbm.getAmountsalaryallowancetransport();

			totalamountsalaryallowancehousing = totalamountsalaryallowancehousing
					+ dbm.getAmountsalaryallowancehousing();

			totalamountsalaryallowance = totalamountsalaryallowance + dbm.getAmountsalaryallowance();

			totalamounttaxpaye = totalamounttaxpaye + dbm.getAmounttaxpaye();

			totalamounttaxother = totalamounttaxother + dbm.getAmounttaxother();

			totalamounttax = totalamounttax + dbm.getAmounttax();

			totalamountdeductionmandatorypension = totalamountdeductionmandatorypension
					+ dbm.getAmountdeductionmandatorysssf();

			totalamountdeductionmandatoryhealth = totalamountdeductionmandatoryhealth
					+ dbm.getAmountdeductionmandatoryhealth();

			totalamountdeductionmandatory = totalamountdeductionmandatory + dbm.getAmountdeductionmandatory();

			totalamountdeductionvoluntary = totalamountdeductionvoluntary + dbm.getAmountdeductionvoluntary();

			totalamountdeduction = totalamountdeduction + dbm.getAmountdeduction();

			totalamountdeductionloansalaryadvance = totalamountdeductionloansalaryadvance
					+ dbm.getAmountdeductionloansalaryadvance();

			totalamountdeductionloansaccos = totalamountdeductionloansaccos + dbm.getAmountdeductionloansaccos();

			totalamountdeductionloaneducationheslb = totalamountdeductionloaneducationheslb
					+ dbm.getAmountdeductionloaneducationheslb();

			totalamountdeductionloaneducationother = totalamountdeductionloaneducationother
					+ dbm.getAmountdeductionloaneducationother();

			totalamountdeductionloanother = totalamountdeductionloanother + dbm.getAmountdeductionloanother();

			totalamountdeductionloan = totalamountdeductionloan + dbm.getAmountdeductionloan();

			totalamountsalaryservantAndHospitality = totalamountsalaryservantAndHospitality
					+ dbm.getAmountsalaryservantAndHospitality();

			// get allowances
			List<Allowance> allowancelist = new ArrayList<>();

			List<HrmsAllowancetype> hrmsAllowancetypelist = hrmsAllowancetypeRepository.findByActive(1);

			HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

			for (HrmsAllowancetype allowancetype : hrmsAllowancetypelist) {
				if (allowancetype.getCode() == 1 || allowancetype.getCode() == 2 || allowancetype.getCode() == 3) {// exclude
																													// travell
																													// allowance
																													// ,
																													// this
																													// is
																													// not
																													// included
																													// on
																													// payroll
					// payment
					Allowance allowance = new Allowance();

					if (allowancetype.getCode() == 1) { // servant and hospitality allowance

						allowance.setAllowancetype(allowancetype.getName());
						allowance.setAllowancetypeCode(allowancetype.getCode());
						allowance.setAmountsalaryallowance(dbm.getAmountsalaryservantAndHospitality());

					}

					if (allowancetype.getCode() == 2) {// Housing allowance

						allowance.setAllowancetype(allowancetype.getName());
						allowance.setAllowancetypeCode(allowancetype.getCode());
						allowance.setAmountsalaryallowance(dbm.getAmountsalaryallowancehousing());

					}

					if (allowancetype.getCode() == 3) {// transport

						allowance.setAllowancetype(allowancetype.getName());
						allowance.setAllowancetypeCode(allowancetype.getCode());
						allowance.setAmountsalaryallowance(dbm.getAmountsalaryallowancetransport());

					}

					allowancelist.add(allowance);

				}
			}
			payrollResponse.setAllowancelist(allowancelist);
			// populate loan deduction now and their balances
			List<DeductionLoan> deductionLoanlist = new ArrayList<>();
			List<HrmsPayrollLoanType> hrmsPayrollLoanTypelist = hrmsPayrollLoanTypeRepository.findByActive(1);
			for (HrmsPayrollLoanType loantype : hrmsPayrollLoanTypelist) {
				DeductionLoan deductionLoan = new DeductionLoan();
				if (hrmsPayrollDeductionLoanRepository.existsByLoantypeidAndEmployeeidAndPayrollidAndActive(
						loantype.getId(), dbm.getEmployeeid(), dbm.getId(), 1)) {
					HrmsPayrollDeductionLoan hrmsPayrollDeductionLoan = hrmsPayrollDeductionLoanRepository
							.findByLoantypeidAndEmployeeidAndPayrollidAndActive(loantype.getId(), dbm.getEmployeeid(),
									dbm.getId(), 1);

					deductionLoan.setAmountdeductionloan(hrmsPayrollDeductionLoan.getAmount());
					deductionLoan.setLoantype(loantype.getName());
					deductionLoan.setLoantypeCode(loantype.getCode());
					deductionLoan.setAmountloanbalance(hrmsPayrollDeductionLoan.getAmountoutstanding());
					if (loantype.getName().toLowerCase().contains("saccos") && loantype.getIsinternalsource() == 1) {
						amountTcraSaccos = amountTcraSaccos + hrmsPayrollDeductionLoan.getAmount();
					}

					if (loantype.getName().toLowerCase().contains("saccos") && loantype.getIsinternalsource() == 0) {
						amountPostanaSimuSaccos = amountPostanaSimuSaccos + hrmsPayrollDeductionLoan.getAmount();
						;
					}

				} else {
					deductionLoan.setAmountdeductionloan(0.00);
					deductionLoan.setLoantype(loantype.getName());
					deductionLoan.setLoantypeCode(loantype.getCode());
					deductionLoan.setAmountloanbalance(0.00);
				}
				deductionLoanlist.add(deductionLoan);

			}

			payrollResponse.setDeductionLoanlist(deductionLoanlist);

			// populate health insurance

			Double amountdeductionmandatoryEmployer = 0.00;
			List<DeductionMandatoryInsurance> deductionMandatoryHealthlist = new ArrayList<>();

			List<HrmsPayrollContributionMandatoryInsurance> hrmsPayrollContributionMandatoryInsurancelist = hrmsPayrollContributionMandatoryInsuranceRepository
					.findByActive(1);
			for (HrmsPayrollContributionMandatoryInsurance insurance : hrmsPayrollContributionMandatoryInsurancelist) {
				DeductionMandatoryInsurance deductionMandatoryHealth = new DeductionMandatoryInsurance();
				Double amountdeductionmandatoryhealth = dbm.getAmountsalarybasic() * insurance.getRateemployee();
				Double amountdeductionmandatoryhealthemployer = dbm.getAmountsalarybasic()
						* insurance.getRateemployer();
				amountdeductionmandatoryEmployer = amountdeductionmandatoryEmployer
						+ amountdeductionmandatoryhealthemployer;
				deductionMandatoryHealth.setAmountdeductionmandatoryInsurance(amountdeductionmandatoryhealth);
				deductionMandatoryHealth
						.setAmountdeductionmandatoryInsuranceemployer(amountdeductionmandatoryhealthemployer);

				deductionMandatoryHealth.setMandataryContributionServiceProvider(
						hrmsInsuranceProviderRepository.findById(insurance.getServiceproviderid()).get().getName());

				deductionMandatoryHealthlist.add(deductionMandatoryHealth);

			}

			payrollResponse.setDeductionMandatoryInsurancelist(deductionMandatoryHealthlist);

			// populate pension now
			List<DeductionMandatoryPension> deductionMandatoryPensionlist = new ArrayList<>();

			List<HrmsPayrollContributionMandatorySocialSecurityScheme> hrmsPayrollContributionMandatorySocialSecuritySchemelist = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
					.findByActive(1);

			for (HrmsPayrollContributionMandatorySocialSecurityScheme pension : hrmsPayrollContributionMandatorySocialSecuritySchemelist) {
				DeductionMandatoryPension deductionMandatoryPension = new DeductionMandatoryPension();
				if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndServiceprovideridAndActive(
						dbm.getEmployeeid(), pension.getServiceproviderid(), 1)) {
					// Double amountdeductionmandatoryPensionEmployer = dbm.getAmountsalarybasic()
					// * pension.getRateemployer();

					HrmsPayrollDeductionMandatorySocialSecurityScheme pensiondeducted = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
							.findByEmployeeidAndYearAndMonthAndActive(dbm.getEmployeeid(), year, month, 1);
					Double amountdeductionmandatoryPensionEmployer = pensiondeducted.getAmountemployer();

					amountdeductionmandatoryEmployer = amountdeductionmandatoryEmployer
							+ amountdeductionmandatoryPensionEmployer;
					Double amountdeductionmandatoryPension = dbm.getAmountdeductionmandatorysssf();
					deductionMandatoryPension.setAmountdeductionmandatoryPension(amountdeductionmandatoryPension);
					deductionMandatoryPension
							.setAmountdeductionmandatoryPensionEmployer(amountdeductionmandatoryPensionEmployer);

					deductionMandatoryPension
							.setMandataryContributionServiceProvider(hrmsSocialSecuritySchemeServiceProviderRepository
									.findById(pension.getServiceproviderid()).get().getName());
				} else {
					Double amountdeductionmandatoryPensionEmployer = 0.00;
					Double amountdeductionmandatoryPension = 0.00;
					deductionMandatoryPension.setAmountdeductionmandatoryPension(amountdeductionmandatoryPension);
					deductionMandatoryPension
							.setAmountdeductionmandatoryPensionEmployer(amountdeductionmandatoryPensionEmployer);

					deductionMandatoryPension
							.setMandataryContributionServiceProvider(hrmsSocialSecuritySchemeServiceProviderRepository
									.findById(pension.getServiceproviderid()).get().getName());
				}
				deductionMandatoryPensionlist.add(deductionMandatoryPension);
			}

			payrollResponse.setDeductionMandatoryPensionlist(deductionMandatoryPensionlist);

			payrollResponse.setAmountdeductionmandatoryEmployer(amountdeductionmandatoryEmployer);// set mandatory
																									// deduction for
																									// employer
			totalamountdeductionmandatoryEmployer = totalamountdeductionmandatoryEmployer
					+ amountdeductionmandatoryEmployer;
			// populate voluntary contributions
			List<DeductionVoluntary> deductionVoluntarylist = new ArrayList<>();

			List<HrmsPayrollContributionTypeVoluntary> hrmsPayrollContributionTypeVoluntarylist = hrmsPayrollContributionTypeVoluntaryRepository
					.findByActive(1);
			for (HrmsPayrollContributionTypeVoluntary cvoluntarytype : hrmsPayrollContributionTypeVoluntarylist) {

				DeductionVoluntary deductionVoluntary = new DeductionVoluntary();
				if (hrmsPayrollDeductionVoluntaryRepository
						.existsByEmployeeidAndPayrollidAndContributiontypeidAndActive(dbm.getEmployeeid(), dbm.getId(),
								cvoluntarytype.getId(), 1)) {
					HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = hrmsPayrollDeductionVoluntaryRepository
							.findByEmployeeidAndPayrollidAndContributiontypeidAndActive(dbm.getEmployeeid(),
									dbm.getId(), cvoluntarytype.getId(), 1);

					deductionVoluntary.setAmountdeductionvoluntary(hrmsPayrollDeductionVoluntary.getAmount());

					deductionVoluntary.setVoluntaryContributiontype(cvoluntarytype.getName());
					deductionVoluntary.setVoluntaryContributiontypeCode(cvoluntarytype.getCode());

					if (cvoluntarytype.getName().toLowerCase().contains("tcra")
							&& cvoluntarytype.getName().toLowerCase().contains("saccos")) {

						amountTcraSaccos = amountTcraSaccos + hrmsPayrollDeductionVoluntary.getAmount();
					}

					if (cvoluntarytype.getName().toLowerCase().contains("posta")
							&& cvoluntarytype.getName().toLowerCase().contains("simu")) {
						amountPostanaSimuSaccos = amountPostanaSimuSaccos + hrmsPayrollDeductionVoluntary.getAmount();
					}

				} else {
					deductionVoluntary.setAmountdeductionvoluntary(0.00);

					deductionVoluntary.setVoluntaryContributiontype(cvoluntarytype.getName());
					deductionVoluntary.setVoluntaryContributiontypeCode(cvoluntarytype.getCode());
				}
				deductionVoluntarylist.add(deductionVoluntary);
			}

			payrollResponse.setDeductionVoluntarylist(deductionVoluntarylist);
			payrollResponse.setAmountTcraSaccos(amountTcraSaccos);
			totalamountTcraSaccos = totalamountTcraSaccos + amountTcraSaccos;

			payrollResponse.setAmountPostanaSimuSaccos(amountPostanaSimuSaccos);

			totalamountPostanaSimuSaccos = totalamountPostanaSimuSaccos + amountPostanaSimuSaccos;

			payrollResponse.setActive(dbm.getActive());
			payrollResponse.setAmountotherincome(dbm.getAmountotherincome());
			totalamountotherincome = totalamountotherincome + dbm.getAmountotherincome();
			payrollResponse.setYear(dbm.getYear());
			payrollResponse.setMonth(dbm.getMonth());
			payrollResponse.setDay(dbm.getDay());
			payrollResponse.setAmounttaxable(dbm.getAmounttaxable());
			payrollResponse.setAmountdeduction(dbm.getAmountdeduction());
			payrollResponse.setAmountdeductionloan(dbm.getAmountdeductionloan());
			payrollResponse.setAmountdeductionmandatory(dbm.getAmountdeductionmandatory());
			payrollResponse.setAmountdeductionvoluntary(dbm.getAmountdeductionvoluntary());
			payrollResponse.setAmountsalaryallowance(dbm.getAmountsalaryallowance());
			payrollResponse.setAmountsalarybasic(dbm.getAmountsalarybasic());
			payrollResponse.setAmountsalarygross(dbm.getAmountsalarygross());

			payrollResponse.setAmountsalarynet(dbm.getAmountsalarynet());
			payrollResponse.setAmounttax(dbm.getAmounttax());
			payrollResponse.setAmounttaxother(dbm.getAmounttaxother());
			payrollResponse.setAmounttaxpaye(dbm.getAmounttaxpaye());
			payrollResponse.setApproved(dbm.getApproved());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollResponse.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			payrollResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatepay() != null) {
				payrollResponse.setDatepay(simpleDateFormat.format(dbm.getDatepay()));
				// datepay = simpleDateFormat.format(dbm.getDatepay());
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				if (hrmsEmployee.getDutystationid() != 0) {
					payrollResponse.setOffice(
							hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).get().getName());
					payrollResponse.setOfficeid(hrmsEmployee.getDutystationid());
					payrollResponse.setOfficetypeid(hrmsOrganisationOfficeRepository
							.findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid());
					payrollResponse
							.setOfficetype(
									hrmsOrganisationOfficeCategoryRepository
											.findById(hrmsOrganisationOfficeRepository
													.findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
											.get().getName());
					payrollResponse.setDutystationcityid(hrmsOrganisationOfficeRepository
							.findById(hrmsEmployee.getDutystationid()).get().getCityid());
					payrollResponse
							.setDutystationcity(
									hrmsLocationCityRepository
											.findById(hrmsOrganisationOfficeRepository
													.findById(hrmsEmployee.getDutystationid()).get().getCityid())
											.get().getName());

				}

				payrollResponse.setDesignationid(hrmsEmployee.getDesignationId());
				if (hrmsEmployee.getDesignationId() != 0
						&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
					payrollResponse.setDesignation(
							hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());

				}
				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}

				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollResponse.setFullName(fullName.toString());
			}
			payrollResponse.setEmployeeid(dbm.getEmployeeid());
			payrollResponse.setId(dbm.getId());

			if (dbm.getPayrolltypeid() != 0 && hrmsPayrollTypeRepository.existsById(dbm.getPayrolltypeid())) {
				payrollResponse
						.setPayrolltype(hrmsPayrollTypeRepository.findById(dbm.getPayrolltypeid()).get().getName());
			}
			payrollResponse.setPayrolltypeid(dbm.getPayrolltypeid());
			payrollResponselist.add(payrollResponse);
		}
		payrollResponses.setPayrollResponselist(payrollResponselist);

		// add total of each amount down here

		payrollResponses.setTotalamountTcraSaccos(totalamountTcraSaccos);
		payrollResponses.setTotalamountotherincome(totalamountotherincome);

		payrollResponses.setTotalamountPostanaSimuSaccos(totalamountPostanaSimuSaccos);

		payrollResponses.setTotalamountsalarygross(totalamountsalarygross);

		payrollResponses.setTotalamountsalarybasic(totalamountsalarybasic);

		payrollResponses.setTotalamountsalarynet(totalamountsalarynet);

		payrollResponses.setTotalamountsalaryallowancetransport(totalamountsalaryallowancetransport);

		payrollResponses.setTotalamountsalaryallowancehousing(totalamountsalaryallowancehousing);

		payrollResponses.setTotalamountsalaryallowance(totalamountsalaryallowance);

		payrollResponses.setTotalamounttaxpaye(totalamounttaxpaye);

		payrollResponses.setTotalamounttaxother(totalamounttaxother);

		payrollResponses.setTotalamounttax(totalamounttax);
		payrollResponses.setTotalamounttaxable(totalamounttaxable);

		payrollResponses.setTotalamountdeductionmandatorypension(totalamountdeductionmandatorypension);

		payrollResponses.setTotalamountdeductionmandatoryInsurance(totalamountdeductionmandatoryhealth);

		payrollResponses.setTotalamountdeductionmandatory(totalamountdeductionmandatory);

		payrollResponses.setTotalamountdeductionvoluntary(totalamountdeductionvoluntary);

		payrollResponses.setTotalamountdeduction(totalamountdeduction);

		payrollResponses.setTotalamountdeductionloansalaryadvance(totalamountdeductionloansalaryadvance);

		payrollResponses.setTotalamountdeductionloansaccos(totalamountdeductionloansaccos);

		payrollResponses.setTotalamountdeductionloaneducationheslb(totalamountdeductionloaneducationheslb);

		payrollResponses.setTotalamountdeductionloaneducationother(totalamountdeductionloaneducationother);

		payrollResponses.setTotalamountdeductionloanother(totalamountdeductionloanother);

		payrollResponses.setTotalamountdeductionloan(totalamountdeductionloan);

		payrollResponses.setTotalamountsalaryservantAndHospitality(totalamountsalaryservantAndHospitality);

		payrollResponses.setTotalamountdeductionmandatoryEmployer(totalamountdeductionmandatoryEmployer);
		if (datepay != null) {
			payrollResponses.setDatepay(datepay);
		}

		return ResponseEntity.status(HttpStatus.OK).body(payrollResponses);

		// } else {
		// return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		// }
	}

	@Override
	public ResponseEntity<PayrollProcessResponse> processPayrollPerPeriodByEmpId(int year, int month, int EmpId) {

		Integer[] status = { 2 };

		PayrollProcessResponse payrollProcessResponse = new PayrollProcessResponse();
		int employeeTotal = 0;

		Double grossSalaryTotal = 0.00;

		Double taxTotal = 0.00;

		Double pensionEmployerTotal = 0.00;

		Double pensionEmployeeTotal = 0.00;
		Double insuranceEmployerTotal = 0.00;
		Double loanTotal = 0.00;

		Double netSalaryTotal = 0.00;
		Double basicSalaryTotal = 0.00;
		if (hrmsEmployeeRepository.existsByIdAndEmploymentstatusidInAndActive(EmpId, status, 1)
				&& hrmsemployeesalaryRepository.existsByEmployeeidAndActive(EmpId, 1)) {

			String pattern = "YYYY-MM-DD";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			/*
			 * String dt = simpleDateFormat.format(period);
			 * 
			 * String[] dateParts = dt.split("-"); int day = Integer.parseInt(dateParts[2]);
			 * int month = Integer.parseInt(dateParts[1]); int year =
			 * Integer.parseInt(dateParts[0]);
			 * 
			 * int year1 = LocalDateTime.now().getYear();
			 */

			int month1 = LocalDateTime.now().getMonthValue();
			int day = LocalDateTime.now().getDayOfMonth();
			// Integer[] status = { 2 };

			if (hrmsPayrollRepository.existsByEmployeeidAndYearAndMonthAndActive(EmpId, year, month, 1)) {// verify

				/*
				 * String dt = simpleDateFormat.format(period);
				 * 
				 * String[] dateParts = dt.split("-"); int day = Integer.parseInt(dateParts[2]);
				 * int month = Integer.parseInt(dateParts[1]); int year =
				 * Integer.parseInt(dateParts[0]);
				 * 
				 * int year1 = LocalDateTime.now().getYear();
				 */

				if (year < 2020 || (month < 1 || month > 12)) {// verify there no existing processed
					// payroll
					// for this month before processing

					return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
				} else {
					if (hrmsPayrollRepository.existsByYearAndMonthAndActive(year, month, 1)) {

						List<HrmsPayroll> hrmsPayrolllist = hrmsPayrollRepository.findByYearAndMonthAndActive(year,
								month, 1);

						for (HrmsPayroll dbp : hrmsPayrolllist) {

							basicSalaryTotal = basicSalaryTotal + dbp.getAmountsalarybasic();
							grossSalaryTotal = grossSalaryTotal + dbp.getAmountsalarygross();
							loanTotal = loanTotal + dbp.getAmountdeductionloan();
							netSalaryTotal = netSalaryTotal + dbp.getAmountsalarynet();
							taxTotal = taxTotal + dbp.getAmounttax();

							insuranceEmployerTotal = insuranceEmployerTotal + dbp.getAmountsalarybasic() * 0.06;

							pensionEmployeeTotal = pensionEmployeeTotal + dbp.getAmountdeductionmandatorysssf();
							pensionEmployerTotal = pensionEmployerTotal + dbp.getAmountdeductionmandatorysssf()
									+ dbp.getAmountdeductionmandatorysssf() * 3;

						}

						employeeTotal = hrmsPayrollRepository.countByYearAndMonthAndActive(year, month, 1);
						payrollProcessResponse.setBasicSalaryTotal(basicSalaryTotal);
						payrollProcessResponse.setEmployeeTotal(employeeTotal);
						payrollProcessResponse.setGrossSalaryTotal(grossSalaryTotal);
						payrollProcessResponse.setInsuranceEmployerTotal(insuranceEmployerTotal); // compute
						payrollProcessResponse.setLoanTotal(loanTotal);
						payrollProcessResponse.setNetSalaryTotal(netSalaryTotal);
						payrollProcessResponse.setPensionEmployeeTotal(pensionEmployeeTotal);
						payrollProcessResponse.setPensionEmployerTotal(pensionEmployerTotal);
						payrollProcessResponse.setTaxTotal(taxTotal);

						return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(payrollProcessResponse);
					}

					List<HrmsEmployee> hrmsEmployeelist = hrmsEmployeeRepository
							.findByEmploymentstatusidInAndActive(status, 1); // get
																				// list
					// of
					// all
					// employee
					// credible
					// to
					// payment

					for (HrmsEmployee dbm : hrmsEmployeelist) {

						if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1)
								&& !hrmsPayrollRepository.existsByEmployeeidAndYearAndMonthAndActive(dbm.getId(), year,
										month, 1)) {

							if (!hrmsPayrollRepository.existsByEmployeeidAndYearAndMonthAndActive(dbm.getId(), year,
									month, 1)) {

								HrmsPayroll hrmsPayroll = new HrmsPayroll();
								hrmsPayroll.setActive(1);
								hrmsPayroll.setYear(year);
								hrmsPayroll.setMonth(month);
								hrmsPayroll.setDay(day);
								hrmsPayroll.setEmployeeid(dbm.getId());

								// date pay
								hrmsPayroll.setDatepay(new Date());
								// basic salary
								Double bsalary = 0.00;
								Double amountsalarygross = 0.00;
								Double amounttaxable = 0.00;
								if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
									Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
											.findByEmployeeidAndActive(dbm.getId(), 1);
									if (hrmsSalarystructureRepository
											.existsById(hrmsemployeesalary.getSalarystructureId())) {
										HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
												.findById(hrmsemployeesalary.getSalarystructureId()).get();
										bsalary = hrmsSalarystructure.getBasicSalary();

										hrmsPayroll.setCurrencyid(4);// 4 for TZS

									}
								}

								basicSalaryTotal = basicSalaryTotal + bsalary;
								hrmsPayroll.setAmountsalarybasic(bsalary);

								Double amountsalaryallowancetransport = 0.00;
								Double amountsalaryallowancehousing = 0.00;
								Double amountsalaryallowanceservantAndHospitality = 0.00;

								Double amountsalaryallowance = 0.00;

								Double psssfemployee = 0.00;
								Double psssfemployer = 0.00;
								Double psssftotal = 0.00;
								int psssfcontributiontype = 0;
								// salarystructure
								if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getId(), 1)) {
									Hrmsemployeesalary hrmsemployeesalary1 = hrmsemployeesalaryRepository
											.findByEmployeeidAndActive(dbm.getId(), 1);
									// Allowance , house allowance, transport etc
									if ((dbm.getDesignationId() != 0 && hrmsemployeesalary1.getSalarystructureId() != 0
											&& dbm.getEmploymentcategoryId() != 0)
											&& (hrmsAllowanceRepository
													.existsByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAndActive(
															dbm.getDesignationId(),
															hrmsemployeesalary1.getSalarystructureId(),
															dbm.getEmploymentcategoryId(), 1))) {

										List<HrmsAllowance> hrmsAllowancelist = hrmsAllowanceRepository
												.findByDesignationidAndSalarystructureidAndEmploymentcategoryidAndAndActive(
														dbm.getDesignationId(),
														hrmsemployeesalary1.getSalarystructureId(),
														dbm.getEmploymentcategoryId(), 1);
										for (HrmsAllowance allowance : hrmsAllowancelist) {

											if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid())
													.get().getName().toLowerCase().contains("transport")) {

												amountsalaryallowancetransport = allowance.getAmount();

											}

											if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid())
													.get().getName().toLowerCase().contains("house")) {

												amountsalaryallowancehousing = allowance.getAmount();

											}

											if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid())
													.get().getName().toLowerCase().contains("servant")) {

												amountsalaryallowanceservantAndHospitality = allowance.getAmount();

											}

										}

									}
								}

								amountsalaryallowance = (amountsalaryallowancetransport + amountsalaryallowancehousing
										+ amountsalaryallowanceservantAndHospitality);

								hrmsPayroll.setAmountsalaryallowance(amountsalaryallowance);
								hrmsPayroll.setAmountsalaryallowancetransport(amountsalaryallowancetransport);

								hrmsPayroll.setAmountsalaryallowancehousing(amountsalaryallowancehousing);

								hrmsPayroll.setAmountsalaryservantAndHospitality(
										amountsalaryallowanceservantAndHospitality);

								// Gross salary
								amountsalarygross = (bsalary + amountsalaryallowance);
								grossSalaryTotal = grossSalaryTotal + amountsalarygross;
								hrmsPayroll.setAmountsalarygross(amountsalarygross);

								// amount_deduction_mandatory_sssf

								List<HrmsPayrollContributionMandatorySocialSecurityScheme> psssfs = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
										.findByActive(1);
								HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme = hrmsEmployeeSocialSecuritySchemeRepositoty
										.findFirstByEmployeeidAndActive(dbm.getId(), 1);
								int ssserviceproviderid = 0;
								if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndActive(dbm.getId(),
										1) && hrmsEmployeeSocialSecurityScheme.getServiceproviderid() != 0) {
									ssserviceproviderid = hrmsEmployeeSocialSecurityScheme.getServiceproviderid();

									for (HrmsPayrollContributionMandatorySocialSecurityScheme pssf : psssfs) {
										if (ssserviceproviderid != 0) {

											if (hrmsSocialSecuritySchemeServiceProviderRepository
													.findById(ssserviceproviderid).get().getName().toLowerCase()
													.contains("psssf")
													&& ssserviceproviderid == pssf.getServiceproviderid()) {
												psssfcontributiontype = pssf.getContributiontypeid();

												psssfemployee = (pssf.getAmount() + pssf.getRateemployee() * bsalary);
												psssfemployer = (pssf.getAmount() + pssf.getRateemployer() * bsalary);
												psssftotal = psssfemployee + psssfemployer;

											}

											if (hrmsSocialSecuritySchemeServiceProviderRepository
													.findById(ssserviceproviderid).get().getName().toLowerCase()
													.contains("zss")
													&& ssserviceproviderid == pssf.getServiceproviderid()) {
												psssfcontributiontype = pssf.getContributiontypeid();

												psssfemployee = (pssf.getAmount() + pssf.getRateemployee() * bsalary);
												psssfemployer = (pssf.getAmount() + pssf.getRateemployer() * bsalary);
												psssftotal = psssfemployee + psssfemployer;

											}
										}

									}

								}

								pensionEmployerTotal = pensionEmployerTotal + psssfemployer;
								pensionEmployeeTotal = pensionEmployeeTotal + psssfemployee;

								hrmsPayroll.setAmountdeductionmandatorysssf(psssfemployee);
								amounttaxable = (amountsalarygross - psssfemployee);
								hrmsPayroll.setAmounttaxable(amounttaxable);

								// amount_deduction_mandatory_health
								Double heathamountemployee = 0.00;
								Double heathamountemployer = 0.00;
								Double heathamounttotal = 0.00;
								int heathcontributiontype = 0;
								int wcfcontributiontype = 0;
								Double wcfamountemployer = 0.00;
								List<HrmsPayrollContributionMandatoryInsurance> nhifs = hrmsPayrollContributionMandatoryInsuranceRepository
										.findByActive(1);

								for (HrmsPayrollContributionMandatoryInsurance nhif : nhifs) {

									if (hrmsInsuranceProviderRepository.findById(nhif.getServiceproviderid()).get()
											.getName().toLowerCase().contains("nhif")) {
										heathcontributiontype = nhif.getContributiontypeid();

										heathamountemployee = (nhif.getAmount() + nhif.getRateemployee() * bsalary);
										heathamountemployer = (nhif.getAmount() + nhif.getRateemployer() * bsalary);
										heathamounttotal = heathamountemployee + heathamountemployer;

									}

									if (hrmsInsuranceProviderRepository.findById(nhif.getServiceproviderid()).get()
											.getName().toLowerCase().contains("workers")) {
										wcfcontributiontype = nhif.getContributiontypeid();

										wcfamountemployer = (nhif.getAmount() + nhif.getRateemployer() * bsalary);

									}

								}

								insuranceEmployerTotal = insuranceEmployerTotal + heathamounttotal;
								hrmsPayroll.setAmountdeductionmandatoryhealth(heathamountemployee);

								// amount_deduction_mandatory
								// here we compute total mandatory deductions
								Double mandatorydeductions = psssfemployee + heathamountemployee;
								hrmsPayroll.setAmountdeductionmandatory(mandatorydeductions);

								// amount_deduction_voluntary
								Double amountdeductionvoluntary = 0.00;

								HrmsPayrollContributionVoluntary HrmsPayrollContributionVoluntaryonce = new HrmsPayrollContributionVoluntary();
								if (hrmsPayrollContributionVoluntaryRepository
										.existsByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0)) {

									List<HrmsPayrollContributionVoluntary> voluntarycontributionlist = hrmsPayrollContributionVoluntaryRepository
											.findByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0);

									for (HrmsPayrollContributionVoluntary vcontribtn : voluntarycontributionlist) {
										if (vcontribtn.getJoiningdate() != null) {

											String dt1 = simpleDateFormat.format(vcontribtn.getJoiningdate());

											String[] dateParts1 = dt1.split("-");
											int day2 = Integer.parseInt(dateParts1[2]);
											int month2 = Integer.parseInt(dateParts1[1]);
											int year2 = Integer.parseInt(dateParts1[0]);

											if ((vcontribtn.getContributionmode() == 1) && (month2 == month)
													&& (year2 == year)) {

												amountdeductionvoluntary = amountdeductionvoluntary
														+ (vcontribtn.getAmount() + vcontribtn.getRate() * bsalary);
												vcontribtn.setLocked(1);

												vcontribtn.setDate_updated(LocalDateTime.now());
												HrmsPayrollContributionVoluntaryonce = vcontribtn;

											}
											if (vcontribtn.getContributionmode() != 1) {

												amountdeductionvoluntary = amountdeductionvoluntary
														+ (vcontribtn.getAmount() + vcontribtn.getRate() * bsalary);

											}

										}

									}

								}
								hrmsPayroll.setAmountdeductionvoluntary(amountdeductionvoluntary);

								// amount_deduction , this calculates total deduction mandatory and voluntary
								Double amountdeduction = mandatorydeductions + amountdeductionvoluntary;

								hrmsPayroll.setAmountdeduction(amountdeduction);

								// amount_deduction_loan_salary_advance
								Double amountdeductionloansalaryadvance = 0.00;
								Double amountdeductionloansaccos = 0.00;
								Double amountdeductionloaneducationheslb = 0.00;
								Double amountdeductionloaneducationother = 0.00;
								Double amountdeductionloanother = 0.00;
								Double amountdeductionloan = 0.00;
								Integer[] loanstatus = { 0, 1 };
								if (hrmsPayrollEmployeeLoanRepository
										.existsByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus, 1)) {

									List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
											.findByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus, 1);

									for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist) {

										if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("advance")) {

											if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
												amountdeductionloansalaryadvance = emploan.getAmountoutstanding();

											} else {

												amountdeductionloansalaryadvance = emploan.getAmountprincipal();

											}
										}

										if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("heslb")) {

											if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
												amountdeductionloaneducationheslb = emploan.getAmountoutstanding();

											} else {

												amountdeductionloaneducationheslb = emploan.getAmountprincipal();

											}

										}

										if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("saccos")) {

											if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
												amountdeductionloansaccos = emploan.getAmountoutstanding();

											} else {

												amountdeductionloansaccos = emploan.getAmountprincipal();

											}

										}

										if (hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("educationother")) {

											if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
												amountdeductionloaneducationother = emploan.getAmountoutstanding();

											} else {

												amountdeductionloaneducationother = emploan.getAmountprincipal();

											}

										}

										if (!hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid()).get()
												.getName().toLowerCase().contains("advance")
												&& !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid())
														.get().getName().toLowerCase().contains("heslb")
												&& !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid())
														.get().getName().toLowerCase().contains("saccos")

												&& !hrmsPayrollLoanTypeRepository.findById(emploan.getLoantypeid())
														.get().getName().toLowerCase().contains("educationother")) {

											if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {

												amountdeductionloanother = emploan.getAmountoutstanding();

											} else {

												amountdeductionloanother = emploan.getAmountprincipal();

											}

										}

									}

								}
								amountdeductionloan = (amountdeductionloansalaryadvance
										+ amountdeductionloaneducationheslb + amountdeductionloansaccos
										+ amountdeductionloaneducationother + amountdeductionloanother);

								loanTotal = loanTotal + amountdeductionloan;
								hrmsPayroll.setAmountdeductionloan(amountdeductionloan);

								hrmsPayroll.setAmountdeductionloansalaryadvance(amountdeductionloansalaryadvance);
								hrmsPayroll.setAmountdeductionloaneducationheslb(amountdeductionloaneducationheslb);

								hrmsPayroll.setAmountdeductionloansaccos(amountdeductionloansaccos);
								hrmsPayroll.setAmountdeductionloaneducationother(amountdeductionloaneducationother);
								hrmsPayroll.setAmountdeductionloanother(amountdeductionloanother);
								// Tax deduction

								// if(hrmsPayrollTaxStructureRepository.) {

								// }
								Double amounttaxpaye = 0.00;
								Double amounttax = 0.00;
								Double amounttaxother = 0.00; // this is not yet available so it will remain zero
								int taxtypeid = 0;
								int currencyid = 4;
								List<HrmsPayrollTaxStructure> hrmsPayrollTaxStructurelist = hrmsPayrollTaxStructureRepository
										.findByActive(1);
								for (HrmsPayrollTaxStructure tax : hrmsPayrollTaxStructurelist) {
									if (hrmsPayrollTaxTypeRepository.findById(tax.getTaxtypeid()).get().getName()
											.toLowerCase().contains("paye")
											&& (amounttaxable >= tax.getAmountmin()
													&& amounttaxable <= tax.getAmountmax())) {
										taxtypeid = tax.getTaxtypeid();
										currencyid = tax.getCurrencyid();

										amounttaxpaye = (tax.getAmount()
												+ (tax.getRate() * (amounttaxable - (tax.getAmountmin() - 1))));

									}

								}
								hrmsPayroll.setAmounttaxpaye(amounttaxpaye);
								hrmsPayroll.setAmounttaxother(0.00);// so far we do not have any other tax , thats why
																	// we set
																	// default value to 0

								amounttax = amounttaxpaye + amounttaxother;

								taxTotal = taxTotal + amounttax;
								hrmsPayroll.setAmounttax(amounttax);
								// net salary
								// Gross minus deductions
								Double amountsalarynet = amountsalarygross
										- (amountdeduction + amountdeductionloan + amounttax);

								netSalaryTotal = netSalaryTotal + amountsalarynet;

								hrmsPayroll.setAmountsalarynet(amountsalarynet);
								hrmsPayroll.setUnique_id(UUID.randomUUID());
								hrmsPayroll.setPayrolltypeid(1);
								int payrollid = hrmsPayrollRepository.saveAndFlush(hrmsPayroll).getId();

								employeeTotal = hrmsPayrollRepository.countByYearAndMonthAndActive(year, month, 1);

								// now compute transactional tables for loans and other deductions

								// first tax deduction
								HrmsPayrollDeductionTax hrmsPayrollDeductionTax = new HrmsPayrollDeductionTax();
								hrmsPayrollDeductionTax.setActive(1);
								hrmsPayrollDeductionTax.setApproved(0);
								hrmsPayrollDeductionTax.setAmount(amounttaxpaye);
								hrmsPayrollDeductionTax.setCurrencyid(currencyid);
								hrmsPayrollDeductionTax.setDatededucted(new Date());
								hrmsPayrollDeductionTax.setEmployeeid(dbm.getId());
								hrmsPayrollDeductionTax.setPayrollid(payrollid);
								hrmsPayrollDeductionTax.setTaxtypeid(taxtypeid);
								hrmsPayrollDeductionTax.setUnique_id(UUID.randomUUID());
								hrmsPayrollDeductionTax.setYear(year);
								hrmsPayrollDeductionTax.setMonth(month);

								hrmsPayrollDeductionTaxRepository.saveAndFlush(hrmsPayrollDeductionTax);

								// Health insurance deduction

								HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance = new HrmsPayrollDeductionMandatoryInsurance();

								hrmsPayrollDeductionMandatoryInsurance.setActive(1);
								hrmsPayrollDeductionMandatoryInsurance.setApproved(0);
								hrmsPayrollDeductionMandatoryInsurance.setAmount(heathamounttotal);
								hrmsPayrollDeductionMandatoryInsurance.setAmountemployee(heathamountemployee);
								hrmsPayrollDeductionMandatoryInsurance.setAmountemployer(heathamountemployer);
								hrmsPayrollDeductionMandatoryInsurance.setContributiontypeid(heathcontributiontype);
								hrmsPayrollDeductionMandatoryInsurance.setCurrencyid(currencyid);
								hrmsPayrollDeductionMandatoryInsurance.setDatededucted(new Date());
								hrmsPayrollDeductionMandatoryInsurance.setEmployeeid(dbm.getId());
								hrmsPayrollDeductionMandatoryInsurance.setPayrollid(payrollid);
								hrmsPayrollDeductionMandatoryInsurance.setUnique_id(UUID.randomUUID());
								hrmsPayrollDeductionMandatoryInsurance.setYear(year);
								hrmsPayrollDeductionMandatoryInsurance.setMonth(month);

								hrmsPayrollDeductionMandatoryInsuranceRepository
										.saveAndFlush(hrmsPayrollDeductionMandatoryInsurance);

								// Workers compensation fund
								HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance1 = new HrmsPayrollDeductionMandatoryInsurance();

								hrmsPayrollDeductionMandatoryInsurance1.setActive(1);
								hrmsPayrollDeductionMandatoryInsurance1.setApproved(0);
								hrmsPayrollDeductionMandatoryInsurance1.setAmount(wcfamountemployer);
								hrmsPayrollDeductionMandatoryInsurance1.setAmountemployee(0.00);
								hrmsPayrollDeductionMandatoryInsurance1.setAmountemployer(wcfamountemployer);
								hrmsPayrollDeductionMandatoryInsurance1.setContributiontypeid(wcfcontributiontype);
								hrmsPayrollDeductionMandatoryInsurance1.setCurrencyid(currencyid);
								hrmsPayrollDeductionMandatoryInsurance1.setDatededucted(new Date());
								hrmsPayrollDeductionMandatoryInsurance1.setEmployeeid(dbm.getId());
								hrmsPayrollDeductionMandatoryInsurance1.setPayrollid(payrollid);
								hrmsPayrollDeductionMandatoryInsurance1.setUnique_id(UUID.randomUUID());
								hrmsPayrollDeductionMandatoryInsurance1.setYear(year);
								hrmsPayrollDeductionMandatoryInsurance1.setMonth(month);

								hrmsPayrollDeductionMandatoryInsuranceRepository
										.saveAndFlush(hrmsPayrollDeductionMandatoryInsurance1);

								// public social security fund deductions
								if (psssfcontributiontype != 0) {
									HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme = new HrmsPayrollDeductionMandatorySocialSecurityScheme();
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setActive(1);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmount(psssftotal);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmountemployee(psssfemployee);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setAmountemployer(psssfemployer);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setApproved(0);
									hrmsPayrollDeductionMandatorySocialSecurityScheme
											.setContributiontypeid(psssfcontributiontype);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setDatededucted(new Date());
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setEmployeeid(dbm.getId());
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setPayrollid(payrollid);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setUnique_id(UUID.randomUUID());
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setYear(year);
									hrmsPayrollDeductionMandatorySocialSecurityScheme.setMonth(month);

									hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
											.saveAndFlush(hrmsPayrollDeductionMandatorySocialSecurityScheme);
								}

								// Voluntary deduction
								if (hrmsPayrollContributionVoluntaryRepository
										.existsByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0)) {

									List<HrmsPayrollContributionVoluntary> voluntarycontributionlist1 = hrmsPayrollContributionVoluntaryRepository
											.findByEmployeeidAndActiveAndLocked(dbm.getId(), 1, 0);

									Double contamount = 0.00;
									if (HrmsPayrollContributionVoluntaryonce != null
											&& HrmsPayrollContributionVoluntaryonce.getAmount() != null) {
										contamount = HrmsPayrollContributionVoluntaryonce.getAmount();

										// update the onceoff payment if it was not null
										if (contamount > 0.00) {
											HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = new HrmsPayrollDeductionVoluntary();
											hrmsPayrollDeductionVoluntary.setActive(1);
											hrmsPayrollDeductionVoluntary
													.setAmount(HrmsPayrollContributionVoluntaryonce.getAmount());
											hrmsPayrollDeductionVoluntary.setApproved(0);
											hrmsPayrollDeductionVoluntary.setContributiontypeid(
													HrmsPayrollContributionVoluntaryonce.getContributiontypeid());
											hrmsPayrollDeductionVoluntary.setCurrencyid(
													HrmsPayrollContributionVoluntaryonce.getCurrencyid());
											hrmsPayrollDeductionVoluntary.setDatededucted(new Date());
											hrmsPayrollDeductionVoluntary.setEmployeeid(dbm.getId());
											hrmsPayrollDeductionVoluntary.setPayrollid(payrollid);
											hrmsPayrollDeductionVoluntary.setUnique_id(UUID.randomUUID());
											hrmsPayrollDeductionVoluntary.setYear(year);
											hrmsPayrollDeductionVoluntary.setMonth(month);
											hrmsPayrollDeductionVoluntaryRepository
													.saveAndFlush(hrmsPayrollDeductionVoluntary);
											// lock this transaction now as payment is now full

											hrmsPayrollContributionVoluntaryRepository
													.saveAndFlush(HrmsPayrollContributionVoluntaryonce);

										}
									}

									for (HrmsPayrollContributionVoluntary vcontribtn : voluntarycontributionlist1) {
										HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = new HrmsPayrollDeductionVoluntary();
										hrmsPayrollDeductionVoluntary.setActive(1);
										hrmsPayrollDeductionVoluntary.setAmount(vcontribtn.getAmount());
										hrmsPayrollDeductionVoluntary.setApproved(0);
										hrmsPayrollDeductionVoluntary
												.setContributiontypeid(vcontribtn.getContributiontypeid());
										hrmsPayrollDeductionVoluntary.setCurrencyid(vcontribtn.getCurrencyid());
										hrmsPayrollDeductionVoluntary.setDatededucted(new Date());
										hrmsPayrollDeductionVoluntary.setEmployeeid(dbm.getId());
										hrmsPayrollDeductionVoluntary.setPayrollid(payrollid);
										hrmsPayrollDeductionVoluntary.setUnique_id(UUID.randomUUID());
										hrmsPayrollDeductionVoluntary.setYear(year);
										hrmsPayrollDeductionVoluntary.setMonth(month);

										hrmsPayrollDeductionVoluntaryRepository
												.saveAndFlush(hrmsPayrollDeductionVoluntary);
									}
								}

								// loan deduction

								Integer[] loanstatus1 = { 0, 1 };
								if (hrmsPayrollEmployeeLoanRepository
										.existsByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus1, 1)) {
									List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist1 = hrmsPayrollEmployeeLoanRepository
											.findByEmployeeidAndStatusInAndActive(dbm.getId(), loanstatus1, 1);

									for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist1) {
										int loanid = 0;
										HrmsPayrollDeductionLoan hrmsPayrollDeductionLoan = new HrmsPayrollDeductionLoan();
										hrmsPayrollDeductionLoan.setActive(1);
										hrmsPayrollDeductionLoan.setAmount(emploan.getAmountprincipal());

										Double Amountoutstanding = emploan.getAmountoutstanding()
												- emploan.getAmountprincipal();
										hrmsPayrollDeductionLoan.setAmountoutstanding(Amountoutstanding);
										hrmsPayrollDeductionLoan.setAmountpenalty(0.00);
										hrmsPayrollDeductionLoan.setApproved(0);

										hrmsPayrollDeductionLoan.setCurrencyid(emploan.getCurrencyid());
										hrmsPayrollDeductionLoan.setDatededucted(new Date());
										hrmsPayrollDeductionLoan.setEmployeeid(dbm.getId());
										loanid = emploan.getId();
										hrmsPayrollDeductionLoan.setLoanid(loanid);
										hrmsPayrollDeductionLoan.setLoantypeid(emploan.getLoantypeid());
										hrmsPayrollDeductionLoan.setPayrollid(payrollid);
										hrmsPayrollDeductionLoan.setUnique_id(UUID.randomUUID());
										hrmsPayrollDeductionLoan.setYear(year);
										hrmsPayrollDeductionLoan.setMonth(month);
										hrmsPayrollDeductionLoanRepository.saveAndFlush(hrmsPayrollDeductionLoan);

										// update employee loan table
										HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan = hrmsPayrollEmployeeLoanRepository
												.findById(emploan.getId()).get();
										if (Amountoutstanding == 0) {
											hrmsPayrollEmployeeLoan.setStatus(2); // completed paying
											hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());
										}
										if (Amountoutstanding > 0) {
											hrmsPayrollEmployeeLoan.setStatus(1); // still paying

											hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());

										}
										hrmsPayrollEmployeeLoanRepository.saveAndFlush(hrmsPayrollEmployeeLoan);

									}

								}
							}
						}
					}

				}

			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
			}

			payrollProcessResponse.setBasicSalaryTotal(basicSalaryTotal);
			payrollProcessResponse.setEmployeeTotal(employeeTotal);
			payrollProcessResponse.setGrossSalaryTotal(grossSalaryTotal);
			payrollProcessResponse.setInsuranceEmployerTotal(insuranceEmployerTotal);
			payrollProcessResponse.setLoanTotal(loanTotal);
			payrollProcessResponse.setNetSalaryTotal(netSalaryTotal);
			payrollProcessResponse.setPensionEmployeeTotal(pensionEmployeeTotal);
			payrollProcessResponse.setPensionEmployerTotal(pensionEmployerTotal);
			payrollProcessResponse.setTaxTotal(taxTotal);
			return ResponseEntity.status(HttpStatus.OK).body(payrollProcessResponse);

		} else {
			return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
		}
		// return null;
	}

	public Boolean processPayrollBankAccount(Double netpay, int payrollid, int empid, int currencyid, int year,
			int month, int day) {
		Boolean response = true;
		if (hrmsEmployeeBankAccountRepository.existsByEmployeeidAndActive(empid, 1) && netpay > 0) {

			List<HrmsEmployeeBankAccount> dbms = hrmsEmployeeBankAccountRepository
					.findByEmployeeidAndActiveOrderByPriorityAsc(empid, 1);
			int empaccountNo = hrmsEmployeeBankAccountRepository.countByEmployeeidAndActive(empid, 1);
			Double amountToPay = netpay;
			HrmsEmployeeBankAccount defaultaccount = new HrmsEmployeeBankAccount();

			for (HrmsEmployeeBankAccount dbm : dbms) {
				HrmsPayrollBankAccount hrmsPayrollBankAccount = new HrmsPayrollBankAccount();
				if (dbm.getPriority() == 0) {
					defaultaccount = dbm;
				}

				if (empaccountNo >= 2 && dbm.getPriority() != 0) {
					if (amountToPay <= dbm.getAmount()) {

						hrmsPayrollBankAccount.setActive(1);
						hrmsPayrollBankAccount.setAmount(amountToPay);
						hrmsPayrollBankAccount.setApproved(0);
						hrmsPayrollBankAccount.setBankaccountid(dbm.getId());
						hrmsPayrollBankAccount.setCurrencyid(currencyid);
						hrmsPayrollBankAccount.setDatepay(new Date());
						hrmsPayrollBankAccount.setDay(day);
						hrmsPayrollBankAccount.setEmployeeid(empid);
						hrmsPayrollBankAccount.setMonth(month);
						hrmsPayrollBankAccount.setPayrollid(payrollid);
						hrmsPayrollBankAccount.setUnique_id(UUID.randomUUID());
						hrmsPayrollBankAccount.setYear(year);

						hrmsPayrollBankAccountRepository.saveAndFlush(hrmsPayrollBankAccount);
						amountToPay = 0.00;

					} else {

						hrmsPayrollBankAccount.setActive(1);
						hrmsPayrollBankAccount.setAmount(dbm.getAmount());
						hrmsPayrollBankAccount.setApproved(0);
						hrmsPayrollBankAccount.setBankaccountid(dbm.getId());
						hrmsPayrollBankAccount.setCurrencyid(currencyid);
						hrmsPayrollBankAccount.setDatepay(new Date());
						hrmsPayrollBankAccount.setDay(day);
						hrmsPayrollBankAccount.setEmployeeid(empid);
						hrmsPayrollBankAccount.setMonth(month);
						hrmsPayrollBankAccount.setPayrollid(payrollid);
						hrmsPayrollBankAccount.setUnique_id(UUID.randomUUID());
						hrmsPayrollBankAccount.setYear(year);

						hrmsPayrollBankAccountRepository.saveAndFlush(hrmsPayrollBankAccount);
						amountToPay = amountToPay - dbm.getAmount();

					}

				}

			}

			if (empaccountNo >= 2 && defaultaccount != null) {
				HrmsPayrollBankAccount hrmsPayrollBankAccount = new HrmsPayrollBankAccount();

				hrmsPayrollBankAccount.setActive(1);
				hrmsPayrollBankAccount.setAmount(amountToPay);
				hrmsPayrollBankAccount.setApproved(0);
				hrmsPayrollBankAccount.setBankaccountid(defaultaccount.getId());
				hrmsPayrollBankAccount.setCurrencyid(currencyid);
				hrmsPayrollBankAccount.setDatepay(new Date());
				hrmsPayrollBankAccount.setDay(day);
				hrmsPayrollBankAccount.setEmployeeid(empid);
				hrmsPayrollBankAccount.setMonth(month);
				hrmsPayrollBankAccount.setPayrollid(payrollid);
				hrmsPayrollBankAccount.setUnique_id(UUID.randomUUID());
				hrmsPayrollBankAccount.setYear(year);

				hrmsPayrollBankAccountRepository.saveAndFlush(hrmsPayrollBankAccount);

			}

			response = true;

		} else {
			response = false;

		}

		return response;

	}

}
