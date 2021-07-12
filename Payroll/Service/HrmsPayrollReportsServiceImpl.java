package com.Hrms.Payroll.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAllowancetype;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Repository.HrmsAllowanceRepository;
import com.Hrms.Employee.Repository.HrmsAllowancetypeRepository;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeSocialSecuritySchemeRepositoty;
import com.Hrms.Employee.Repository.HrmsEmploymentCategoryRepository;
import com.Hrms.Employee.Repository.HrmsGenderRepository;
import com.Hrms.Employee.Repository.HrmsInsuranceProviderRepository;
import com.Hrms.Employee.Repository.HrmsLocationCityRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeCategoryRepository;
import com.Hrms.Employee.Repository.HrmsOrganisationOfficeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Employee.Repository.HrmsSalaryScaleRepository;
import com.Hrms.Employee.Repository.HrmsSalaryscalenotchRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;
import com.Hrms.Payroll.DTO.Allowance;
import com.Hrms.Payroll.DTO.DeductionLoan;
import com.Hrms.Payroll.DTO.DeductionMandatoryPension;
import com.Hrms.Payroll.DTO.DeductionVoluntary;
import com.Hrms.Payroll.DTO.HealthInsuarance;
import com.Hrms.Payroll.DTO.HealthInsuranceResponse;
import com.Hrms.Payroll.DTO.HeslbReport;
import com.Hrms.Payroll.DTO.HeslbReportResponse;
import com.Hrms.Payroll.DTO.PAYE;
import com.Hrms.Payroll.DTO.PAYEResponse;
import com.Hrms.Payroll.DTO.PaySlipResponse;
import com.Hrms.Payroll.DTO.PayrollBankAccountTranser;
import com.Hrms.Payroll.DTO.PayrollBankAccountTranserResponse;
import com.Hrms.Payroll.DTO.PayrollJournal;
import com.Hrms.Payroll.DTO.PublicSocialSecurityFund;
import com.Hrms.Payroll.DTO.PublicSocialSecurityFundResponse;
import com.Hrms.Payroll.DTO.WorkersCompensationFund;
import com.Hrms.Payroll.DTO.WorkersCompensationFundResponse;
import com.Hrms.Payroll.Entity.HrmsEmployeeBankAccount;
import com.Hrms.Payroll.Entity.HrmsPayroll;
import com.Hrms.Payroll.Entity.HrmsPayrollBankAccount;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionTypeVoluntary;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionLoan;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryInsurance;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntary;
import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoanDetailsHeslb;
import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;
import com.Hrms.Payroll.Repository.HrmsBankBranchRepository;
import com.Hrms.Payroll.Repository.HrmsBankRepository;
import com.Hrms.Payroll.Repository.HrmsEmployeeBankAccountRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollBankAccountRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatoryInsuranceRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatorySocialSecuritySchemeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionLoanRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionMandatoryInsuranceRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollEmployeeLoanDetailsHeslbRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanTypeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTypeRepository;
import com.Hrms.Payroll.Repository.HrmsSocialSecuritySchemeServiceProviderRepository;

@Service
public class HrmsPayrollReportsServiceImpl implements HrmsPayrollReportsService {
	@Autowired
	private HrmsPayrollBankAccountRepository hrmsPayrollBankAccountRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired

	private HrmsOrganisationOfficeRepository hrmsOrganisationOfficeRepository;

	@Autowired
	private HrmsSalaryScaleRepository hrmsSalaryScaleRepository;

	@Autowired
	private HrmsSalaryscalenotchRepository hrmsSalaryscalenotchRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsLocationCityRepository hrmsLocationCityRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsPayrollTypeRepository hrmsPayrollTypeRepository;

	@Autowired
	private HrmsPayrollContributionMandatorySocialSecuritySchemeRepository hrmsPayrollContributionMandatorySocialSecuritySchemeRepository;

	@Autowired
	private HrmsInsuranceProviderRepository hrmsInsuranceProviderRepository;

	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderRepository hrmsSocialSecuritySchemeServiceProviderRepository;

	@Autowired
	private HrmsOrganisationOfficeCategoryRepository hrmsOrganisationOfficeCategoryRepository;

	@Autowired
	private HrmsPayrollContributionMandatoryInsuranceRepository hrmsPayrollContributionMandatoryInsuranceRepository;

	@Autowired
	private HrmsEmployeeBankAccountRepository hrmsEmployeeBankAccountRepository;

	@Autowired
	private HrmsBankRepository hrmsBankRepository;

	@Autowired
	private HrmsEmployeeSocialSecuritySchemeRepositoty hrmsEmployeeSocialSecuritySchemeRepositoty;

	@Autowired
	private HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository;

	@Autowired
	private HrmsPayrollDeductionMandatoryInsuranceRepository hrmsPayrollDeductionMandatoryInsuranceRepository;

	@Autowired
	private HrmsGenderRepository hrmsGenderRepository;

	@Autowired
	private HrmsEmploymentCategoryRepository hrmsEmploymentCategoryRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private HrmsBankBranchRepository hrmsBankBranchRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsAllowancetypeRepository hrmsAllowancetypeRepository;

	@Autowired
	private HrmsAllowanceRepository hrmsAllowanceRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsPayrollLoanTypeRepository hrmsPayrollLoanTypeRepository;

	@Autowired
	private HrmsPayrollDeductionLoanRepository hrmsPayrollDeductionLoanRepository;

	@Autowired
	private HrmsPayrollDeductionVoluntaryRepository hrmsPayrollDeductionVoluntaryRepository;

	@Autowired
	private HrmsPayrollEmployeeLoanDetailsHeslbRepository hrmsPayrollEmployeeLoanDetailsHeslbRepository;

	@Autowired
	private HrmsPayrollContributionTypeVoluntaryRepository hrmsPayrollContributionTypeVoluntaryRepository;

	@Autowired
	private HrmsPayrollRepository hrmsPayrollRepository;

	@Override
	public ResponseEntity<HeslbReportResponse> getHeslbReportByMonthAndYear(int year, int month) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Double totalAmountDeducted = 0.00;
		Double totalbalanceRemaining = 0.00;
		HeslbReportResponse heslbReportResponse = new HeslbReportResponse();

		List<HeslbReport> heslbReportlist = new ArrayList<>();
		int heslbloantypeid = 2; // this is HESLB loantypeid
		if (month == 0) {
			List<HrmsPayrollDeductionLoan> dbms = hrmsPayrollDeductionLoanRepository
					.findByLoantypeidAndYearAndActive(heslbloantypeid, year, 1);

			for (HrmsPayrollDeductionLoan dbm : dbms) {
				HeslbReport heslbReport = new HeslbReport();

				heslbReport.setAmountDeducted(dbm.getAmount());

				totalAmountDeducted = totalAmountDeducted + dbm.getAmount();
				heslbReport.setBalanceRemaining(dbm.getAmountoutstanding());

				totalbalanceRemaining = totalbalanceRemaining + dbm.getAmountoutstanding();
				if (heslbReport.getDeductiondate() != null) {
					heslbReport.setDeductiondate(simpleDateFormat.format(dbm.getDatededucted()));
				}
				if (dbm.getEmployeeid() != 0) {
					StringBuilder fullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					heslbReport.setFileNumber(hrmsEmployee.getFileNumber());
					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					fullName.append(" " + hrmsEmployee.getLastName().trim());

					heslbReport.setFullEmployeeName(fullName.toString());
				}
				if (dbm.getLoanid() != 0
						&& hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByLoanid(dbm.getLoanid())) {
					HrmsPayrollEmployeeLoanDetailsHeslb heslbdetails = hrmsPayrollEmployeeLoanDetailsHeslbRepository
							.findByLoanid(dbm.getLoanid());
					heslbReport.setFormFourIndexNumber(heslbdetails.getCseeindexnumber());
					StringBuilder fullnameAsperHeslb = new StringBuilder();

					fullnameAsperHeslb.append(heslbdetails.getFirstname().trim());
					if (heslbdetails.getMiddlename() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getMiddlename().trim());
					}

					if (heslbdetails.getLastname() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getLastname().trim());
					}

					heslbReport.setFullnameAsperHeslb(fullnameAsperHeslb.toString());
				}
				heslbReport.setMonth(dbm.getMonth());
				heslbReport.setYear(dbm.getYear());

				heslbReportlist.add(heslbReport);

			}

			heslbReportResponse.setHeslbReportlist(heslbReportlist);
			heslbReportResponse.setTotalAmountDeducted(totalAmountDeducted);
			// heslbReportResponse.setTotalbalanceRemaining(totalbalanceRemaining);

		} else {

			List<HrmsPayrollDeductionLoan> dbms = hrmsPayrollDeductionLoanRepository
					.findByLoantypeidAndYearAndMonthAndActive(heslbloantypeid, year, month, 1);

			for (HrmsPayrollDeductionLoan dbm : dbms) {
				HeslbReport heslbReport = new HeslbReport();

				heslbReport.setAmountDeducted(dbm.getAmount());

				totalAmountDeducted = totalAmountDeducted + dbm.getAmount();
				heslbReport.setBalanceRemaining(dbm.getAmountoutstanding());

				totalbalanceRemaining = totalbalanceRemaining + dbm.getAmountoutstanding();
				if (heslbReport.getDeductiondate() != null) {
					heslbReport.setDeductiondate(simpleDateFormat.format(dbm.getDatededucted()));
				}
				if (dbm.getEmployeeid() != 0) {
					StringBuilder fullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					heslbReport.setFileNumber(hrmsEmployee.getFileNumber());
					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					fullName.append(" " + hrmsEmployee.getLastName().trim());

					heslbReport.setFullEmployeeName(fullName.toString());
				}
				if (dbm.getLoanid() != 0
						&& hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByLoanid(dbm.getLoanid())) {
					HrmsPayrollEmployeeLoanDetailsHeslb heslbdetails = hrmsPayrollEmployeeLoanDetailsHeslbRepository
							.findByLoanid(dbm.getLoanid());
					heslbReport.setFormFourIndexNumber(heslbdetails.getCseeindexnumber());
					StringBuilder fullnameAsperHeslb = new StringBuilder();

					fullnameAsperHeslb.append(heslbdetails.getFirstname().trim());
					if (heslbdetails.getMiddlename() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getMiddlename().trim());
					}

					if (heslbdetails.getLastname() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getLastname().trim());
					}

					heslbReport.setFullnameAsperHeslb(fullnameAsperHeslb.toString());
				}
				heslbReport.setMonth(dbm.getMonth());
				heslbReport.setYear(dbm.getYear());

				heslbReportlist.add(heslbReport);

			}

			heslbReportResponse.setHeslbReportlist(heslbReportlist);
			heslbReportResponse.setTotalAmountDeducted(totalAmountDeducted);
			// heslbReportResponse.setTotalbalanceRemaining(totalbalanceRemaining);
		}

		return ResponseEntity.status(HttpStatus.OK).body(heslbReportResponse);
	}

	@Override
	public ResponseEntity<PayrollBankAccountTranserResponse> getBankTransferByYearAndMonth(int year, int month) {
		PayrollBankAccountTranserResponse payrollBankAccountTranserResponse = new PayrollBankAccountTranserResponse();

		List<PayrollBankAccountTranser> payrollBankAccountTranserlist = new ArrayList<>();
		List<HrmsPayrollBankAccount> dbms = hrmsPayrollBankAccountRepository.findByYearAndMonthAndActive(year, month,
				1);
		Double totalamount = 0.00;
		int totalemployeeNumber = 0;
		// totalemployeeNumber =
		// hrmsPayrollBankAccountRepository.countDistinctEmployeeidByYearAndMonthAndActive(year,
		// month, 1);

		for (HrmsPayrollBankAccount dbm : dbms) {
			totalemployeeNumber = totalemployeeNumber + 1;

			PayrollBankAccountTranser payrollBankAccountTranser = new PayrollBankAccountTranser();
			if (hrmsEmployeeBankAccountRepository.existsById(dbm.getBankaccountid())) {

				HrmsEmployeeBankAccount hrmsEmployeeBankAccount = hrmsEmployeeBankAccountRepository
						.findById(dbm.getBankaccountid()).get();
				payrollBankAccountTranser.setAccountNumber(hrmsEmployeeBankAccount.getAccountnumber());

				payrollBankAccountTranser.setBankid(hrmsEmployeeBankAccount.getBankid());
				if (hrmsEmployeeBankAccount.getBankid() != 0
						&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
					payrollBankAccountTranser.setBankName(
							hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
				}

				if (hrmsEmployeeBankAccount.getBankbranchid() != 0
						&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
					payrollBankAccountTranser.setSortCode(hrmsBankBranchRepository
							.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getSortcode());
				}

			}

			payrollBankAccountTranser.setActive(dbm.getActive());
			payrollBankAccountTranser.setAmount(dbm.getAmount());
			totalamount = totalamount + dbm.getAmount();// aggregate sum amount paid to all accounts
			payrollBankAccountTranser.setApproved(dbm.getApproved());

			payrollBankAccountTranser.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollBankAccountTranser
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			String datepay = year + "-" + month + "-" + dbm.getDay();
			payrollBankAccountTranser.setDatepay(datepay);
			payrollBankAccountTranser.setDay(dbm.getDay());
			if (dbm.getBankaccountid() != 0) {
				payrollBankAccountTranser.setEmployeebankaccountid(dbm.getBankaccountid());
			}

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

				payrollBankAccountTranser.setEmployeeFullName(employeeFullname.toString());
			}

			payrollBankAccountTranser.setEmployeeid(dbm.getEmployeeid());
			payrollBankAccountTranser.setId(dbm.getId());
			payrollBankAccountTranser.setMonth(month);
			payrollBankAccountTranser.setPayrollid(dbm.getPayrollid());
			payrollBankAccountTranser.setYear(year);

			payrollBankAccountTranserlist.add(payrollBankAccountTranser);
		}

		payrollBankAccountTranserResponse.setPayrollBankAccountTranserlist(payrollBankAccountTranserlist);
		payrollBankAccountTranserResponse.setTotalamount(totalamount);
		payrollBankAccountTranserResponse.setTotalNumberOfAccount(totalemployeeNumber);

		return ResponseEntity.status(HttpStatus.OK).body(payrollBankAccountTranserResponse);
	}

	@Override
	public ResponseEntity<PayrollBankAccountTranserResponse> getBankTransferByEmpIdAndYearAndMonth(int empid, int year,
			int month) {
		PayrollBankAccountTranserResponse payrollBankAccountTranserResponse = new PayrollBankAccountTranserResponse();

		List<PayrollBankAccountTranser> payrollBankAccountTranserlist = new ArrayList<>();
		List<HrmsPayrollBankAccount> dbms = hrmsPayrollBankAccountRepository
				.findByEmployeeidAndYearAndMonthAndActive(empid, year, month, 1);
		Double totalamount = 0.00;
		int totalNumberOfAccount = 0;
		// totalemployeeNumber = hrmsPayrollBankAccountRepository
		// .countDistinctEmployeeidByEmployeeidAndYearAndMonthAndActive(empid, year,
		// month, 1);

		for (HrmsPayrollBankAccount dbm : dbms) {
			totalNumberOfAccount = totalNumberOfAccount + 1;

			PayrollBankAccountTranser payrollBankAccountTranser = new PayrollBankAccountTranser();
			if (hrmsEmployeeBankAccountRepository.existsById(dbm.getBankaccountid())) {

				HrmsEmployeeBankAccount hrmsEmployeeBankAccount = hrmsEmployeeBankAccountRepository
						.findById(dbm.getBankaccountid()).get();
				payrollBankAccountTranser.setAccountNumber(hrmsEmployeeBankAccount.getAccountnumber());

				payrollBankAccountTranser.setBankid(hrmsEmployeeBankAccount.getBankid());
				if (hrmsEmployeeBankAccount.getBankid() != 0
						&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
					payrollBankAccountTranser.setBankName(
							hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
				}

				if (hrmsEmployeeBankAccount.getBankbranchid() != 0
						&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
					payrollBankAccountTranser.setSortCode(hrmsBankBranchRepository
							.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getSortcode());
				}

			}

			payrollBankAccountTranser.setActive(dbm.getActive());
			payrollBankAccountTranser.setAmount(dbm.getAmount());
			totalamount = totalamount + dbm.getAmount();// aggregate sum amount paid to all accounts
			payrollBankAccountTranser.setApproved(dbm.getApproved());

			payrollBankAccountTranser.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollBankAccountTranser
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}
			String datepay = year + "-" + month + "-" + dbm.getDay();
			payrollBankAccountTranser.setDatepay(datepay);
			payrollBankAccountTranser.setDay(dbm.getDay());
			if (dbm.getBankaccountid() != 0) {
				payrollBankAccountTranser.setEmployeebankaccountid(dbm.getBankaccountid());
			}

			if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder employeeFullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

				payrollBankAccountTranser.setEmployeeFullName(employeeFullname.toString());
			}

			payrollBankAccountTranser.setEmployeeid(dbm.getEmployeeid());
			payrollBankAccountTranser.setId(dbm.getId());
			payrollBankAccountTranser.setMonth(month);
			payrollBankAccountTranser.setPayrollid(dbm.getPayrollid());
			payrollBankAccountTranser.setYear(year);

			payrollBankAccountTranserlist.add(payrollBankAccountTranser);
		}

		payrollBankAccountTranserResponse.setPayrollBankAccountTranserlist(payrollBankAccountTranserlist);
		payrollBankAccountTranserResponse.setTotalamount(totalamount);
		payrollBankAccountTranserResponse.setTotalNumberOfAccount(totalNumberOfAccount);

		return ResponseEntity.status(HttpStatus.OK).body(payrollBankAccountTranserResponse);
	}

	@Override
	public ResponseEntity<HeslbReportResponse> getHeslbReportByMonthAndYearAndEmpId(int year, int month, int empid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		Double totalAmountDeducted = 0.00;
		Double totalbalanceRemaining = 0.00;
		HeslbReportResponse heslbReportResponse = new HeslbReportResponse();

		List<HeslbReport> heslbReportlist = new ArrayList<>();
		int heslbloantypeid = 2; // this is HESLB loan type id

		if (month == 0) {
			List<HrmsPayrollDeductionLoan> dbms = hrmsPayrollDeductionLoanRepository
					.findByLoantypeidAndYearAndEmployeeidAndActive(heslbloantypeid, year, empid, 1);

			for (HrmsPayrollDeductionLoan dbm : dbms) {
				HeslbReport heslbReport = new HeslbReport();

				heslbReport.setAmountDeducted(dbm.getAmount());

				totalAmountDeducted = totalAmountDeducted + dbm.getAmount();
				heslbReport.setBalanceRemaining(dbm.getAmountoutstanding());

				totalbalanceRemaining = totalbalanceRemaining + dbm.getAmountoutstanding();
				if (heslbReport.getDeductiondate() != null) {
					heslbReport.setDeductiondate(simpleDateFormat.format(dbm.getDatededucted()));
				}
				if (dbm.getEmployeeid() != 0) {
					StringBuilder fullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					heslbReport.setFileNumber(hrmsEmployee.getFileNumber());
					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					fullName.append(" " + hrmsEmployee.getLastName().trim());

					heslbReport.setFullEmployeeName(fullName.toString());
				}
				if (dbm.getLoanid() != 0
						&& hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByLoanid(dbm.getLoanid())) {
					HrmsPayrollEmployeeLoanDetailsHeslb heslbdetails = hrmsPayrollEmployeeLoanDetailsHeslbRepository
							.findByLoanid(dbm.getLoanid());
					heslbReport.setFormFourIndexNumber(heslbdetails.getCseeindexnumber());
					StringBuilder fullnameAsperHeslb = new StringBuilder();

					fullnameAsperHeslb.append(heslbdetails.getFirstname().trim());
					if (heslbdetails.getMiddlename() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getMiddlename().trim());
					}

					if (heslbdetails.getLastname() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getLastname().trim());
					}

					heslbReport.setFullnameAsperHeslb(fullnameAsperHeslb.toString());
				}
				heslbReport.setMonth(dbm.getMonth());
				heslbReport.setYear(dbm.getYear());

				heslbReportlist.add(heslbReport);

			}

			heslbReportResponse.setHeslbReportlist(heslbReportlist);
			heslbReportResponse.setTotalAmountDeducted(totalAmountDeducted);
			// heslbReportResponse.setTotalbalanceRemaining(totalbalanceRemaining);

		} else {

			List<HrmsPayrollDeductionLoan> dbms = hrmsPayrollDeductionLoanRepository
					.findByLoantypeidAndYearAndMonthAndEmployeeidAndActive(heslbloantypeid, year, month, empid, 1);

			for (HrmsPayrollDeductionLoan dbm : dbms) {
				HeslbReport heslbReport = new HeslbReport();

				heslbReport.setAmountDeducted(dbm.getAmount());

				totalAmountDeducted = totalAmountDeducted + dbm.getAmount();
				heslbReport.setBalanceRemaining(dbm.getAmountoutstanding());

				totalbalanceRemaining = totalbalanceRemaining + dbm.getAmountoutstanding();
				if (heslbReport.getDeductiondate() != null) {
					heslbReport.setDeductiondate(simpleDateFormat.format(dbm.getDatededucted()));
				}
				if (dbm.getEmployeeid() != 0) {
					StringBuilder fullName = new StringBuilder();
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					heslbReport.setFileNumber(hrmsEmployee.getFileNumber());
					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}

					fullName.append(" " + hrmsEmployee.getLastName().trim());

					heslbReport.setFullEmployeeName(fullName.toString());
				}
				if (dbm.getLoanid() != 0
						&& hrmsPayrollEmployeeLoanDetailsHeslbRepository.existsByLoanid(dbm.getLoanid())) {
					HrmsPayrollEmployeeLoanDetailsHeslb heslbdetails = hrmsPayrollEmployeeLoanDetailsHeslbRepository
							.findByLoanid(dbm.getLoanid());
					heslbReport.setFormFourIndexNumber(heslbdetails.getCseeindexnumber());
					StringBuilder fullnameAsperHeslb = new StringBuilder();

					fullnameAsperHeslb.append(heslbdetails.getFirstname().trim());
					if (heslbdetails.getMiddlename() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getMiddlename().trim());
					}

					if (heslbdetails.getLastname() != null) {
						fullnameAsperHeslb.append(" " + heslbdetails.getLastname().trim());
					}

					heslbReport.setFullnameAsperHeslb(fullnameAsperHeslb.toString());
				}
				heslbReport.setMonth(dbm.getMonth());
				heslbReport.setYear(dbm.getYear());

				heslbReportlist.add(heslbReport);

			}

			heslbReportResponse.setHeslbReportlist(heslbReportlist);
			heslbReportResponse.setTotalAmountDeducted(totalAmountDeducted);
			// heslbReportResponse.setTotalbalanceRemaining(totalbalanceRemaining);
		}

		return ResponseEntity.status(HttpStatus.OK).body(heslbReportResponse);
	}

	@Override
	public ResponseEntity<WorkersCompensationFundResponse> getWCFByMonthAndYear(int year, int month) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		WorkersCompensationFundResponse workersCompensationFundResponse = new WorkersCompensationFundResponse();

		List<WorkersCompensationFund> workersCompensationFundlist = new ArrayList<>();
		Double totalwcfPay = 0.00;

		if (month == 0) {

			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndActive(year, 1);
			int wcfcontributiontypeid = 3;
			for (HrmsPayroll dbm : dbms) {
				WorkersCompensationFund workersCompensationFund = new WorkersCompensationFund();

				workersCompensationFund.setBasicPay(dbm.getAmountsalarybasic());
				if (hrmsPayrollDeductionMandatoryInsuranceRepository
						.existsByPayrollidAndContributiontypeidAndActive(dbm.getId(), wcfcontributiontypeid, 1)) {
					HrmsPayrollDeductionMandatoryInsurance wcfdeductn = hrmsPayrollDeductionMandatoryInsuranceRepository
							.findByPayrollidAndContributiontypeidAndActive(dbm.getId(), wcfcontributiontypeid, 1);
					workersCompensationFund.setWcfPay(wcfdeductn.getAmountemployer());
					totalwcfPay = totalwcfPay + wcfdeductn.getAmountemployer();
					if (wcfdeductn.getDatededucted() != null) {
						workersCompensationFund.setDatepay(simpleDateFormat.format(wcfdeductn.getDatededucted()));
					}
					workersCompensationFund.setDatepayroll(wcfdeductn.getYear() + "-" + wcfdeductn.getMonth());

				}
				workersCompensationFund.setGrossPay(dbm.getAmountsalarygross());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						workersCompensationFund.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}
					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						workersCompensationFund.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}
					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						workersCompensationFund.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						workersCompensationFund
								.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}
					// StringBuilder firstName = new StringBuilder();
					if (hrmsEmployee.getFirstName() != null) {
						workersCompensationFund.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						workersCompensationFund.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						workersCompensationFund.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getNationalId() != null) {
						workersCompensationFund.setNationalId(hrmsEmployee.getNationalId());
					}
				}

				workersCompensationFundlist.add(workersCompensationFund);
			}

		} else {

			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1);
			int wcfcontributiontypeid = 3;
			for (HrmsPayroll dbm : dbms) {
				WorkersCompensationFund workersCompensationFund = new WorkersCompensationFund();

				workersCompensationFund.setBasicPay(dbm.getAmountsalarybasic());
				if (hrmsPayrollDeductionMandatoryInsuranceRepository
						.existsByPayrollidAndContributiontypeidAndActive(dbm.getId(), wcfcontributiontypeid, 1)) {
					HrmsPayrollDeductionMandatoryInsurance wcfdeductn = hrmsPayrollDeductionMandatoryInsuranceRepository
							.findByPayrollidAndContributiontypeidAndActive(dbm.getId(), wcfcontributiontypeid, 1);
					workersCompensationFund.setWcfPay(wcfdeductn.getAmountemployer());
					totalwcfPay = totalwcfPay + wcfdeductn.getAmountemployer();
					if (wcfdeductn.getDatededucted() != null) {
						workersCompensationFund.setDatepay(simpleDateFormat.format(wcfdeductn.getDatededucted()));
					}
					workersCompensationFund.setDatepayroll(wcfdeductn.getYear() + "-" + wcfdeductn.getMonth());
				}
				workersCompensationFund.setGrossPay(dbm.getAmountsalarygross());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						workersCompensationFund.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}
					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						workersCompensationFund.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}
					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						workersCompensationFund.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						workersCompensationFund
								.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}
					// StringBuilder firstName = new StringBuilder();
					if (hrmsEmployee.getFirstName() != null) {
						workersCompensationFund.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						workersCompensationFund.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						workersCompensationFund.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getNationalId() != null) {
						workersCompensationFund.setNationalId(hrmsEmployee.getNationalId());
					}
				}

				workersCompensationFundlist.add(workersCompensationFund);
			}
		}
		workersCompensationFundResponse.setWorkersCompensationFundlist(workersCompensationFundlist);
		workersCompensationFundResponse.setTotalwcfPay(totalwcfPay);

		return ResponseEntity.status(HttpStatus.OK).body(workersCompensationFundResponse);
	}

	@Override
	public ResponseEntity<PublicSocialSecurityFundResponse> getPsssfPaymentByMonthAndYear(int year, int month) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		PublicSocialSecurityFundResponse publicSocialSecurityFundResponse = new PublicSocialSecurityFundResponse();
		int serviceproviderid = 1; // for PSSSF
		Double totalamountemployee = 0.00;

		Double totalamountemployer = 0.00;

		List<Integer> zssfmember = new ArrayList<>();
		List<HrmsEmployeeSocialSecurityScheme> zssfbeneficiaries = hrmsEmployeeSocialSecuritySchemeRepositoty
				.findByActive(1);
		zssfbeneficiaries.forEach(dbmz -> {
			if (dbmz.getServiceproviderid() == 1) {
				zssfmember.add(dbmz.getEmployeeid());
			}

		});

		if (month == 0) {
			List<PublicSocialSecurityFund> publicSocialSecurityFundlist = new ArrayList<>();
			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndEmployeeidInAndActive(year, zssfmember, 1);

			for (HrmsPayroll dbm : dbms) {
				HrmsPayrollDeductionMandatorySocialSecurityScheme pensiondeduction = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
						.findByPayrollidAndServiceprovideridAndActive(dbm.getId(), serviceproviderid, 1);

				PublicSocialSecurityFund publicSocialSecurityFund = new PublicSocialSecurityFund();

				publicSocialSecurityFund.setBasicPay(dbm.getAmountsalarybasic());

				publicSocialSecurityFund.setDatepayroll(year + "-" + dbm.getMonth());

				if (pensiondeduction != null) {
					publicSocialSecurityFund.setAmountemployee(pensiondeduction.getAmountemployee());
					totalamountemployee = totalamountemployee + pensiondeduction.getAmountemployee();

					publicSocialSecurityFund.setAmountemployer(pensiondeduction.getAmountemployer());
					totalamountemployer = totalamountemployer + pensiondeduction.getAmountemployer();
					if (pensiondeduction.getDatededucted() != null) {
						publicSocialSecurityFund
								.setDatepay(simpleDateFormat.format(pensiondeduction.getDatededucted()));
					}
				}

				if (pensiondeduction == null) {
					publicSocialSecurityFund.setAmountemployee(0.00);

					publicSocialSecurityFund.setAmountemployer(0.00);

				}
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						publicSocialSecurityFund.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}

					if (hrmsEmployee.getFileNumber() != null) {
						publicSocialSecurityFund.setPersonalFileNumber(hrmsEmployee.getFileNumber());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						publicSocialSecurityFund
								.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}

					publicSocialSecurityFund.setNationalId(hrmsEmployee.getNationalId());

					if (hrmsEmployee.getFirstName() != null) {
						publicSocialSecurityFund.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						publicSocialSecurityFund.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						publicSocialSecurityFund.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						publicSocialSecurityFund.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						publicSocialSecurityFund.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}

				}
				publicSocialSecurityFundlist.add(publicSocialSecurityFund);
			}
			publicSocialSecurityFundResponse.setPublicSocialSecurityFundlist(publicSocialSecurityFundlist);
			publicSocialSecurityFundResponse.setTotalamountemployee(totalamountemployee);
			publicSocialSecurityFundResponse.setTotalamountemployer(totalamountemployer);

		} else {

			List<PublicSocialSecurityFund> publicSocialSecurityFundlist = new ArrayList<>();
			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndEmployeeidInAndActive(year, month,
					zssfmember, 1);

			for (HrmsPayroll dbm : dbms) {
				HrmsPayrollDeductionMandatorySocialSecurityScheme pensiondeduction = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
						.findByPayrollidAndServiceprovideridAndActive(dbm.getId(), serviceproviderid, 1);

				PublicSocialSecurityFund publicSocialSecurityFund = new PublicSocialSecurityFund();

				publicSocialSecurityFund.setBasicPay(dbm.getAmountsalarybasic());

				publicSocialSecurityFund.setDatepayroll(year + "-" + dbm.getMonth());

				publicSocialSecurityFund.setGrossPay(dbm.getAmountsalarygross());
				if (pensiondeduction != null) {
					publicSocialSecurityFund.setAmountemployee(pensiondeduction.getAmountemployee());
					totalamountemployee = totalamountemployee + pensiondeduction.getAmountemployee();

					publicSocialSecurityFund.setAmountemployer(pensiondeduction.getAmountemployer());
					totalamountemployer = totalamountemployer + pensiondeduction.getAmountemployer();
					if (pensiondeduction.getDatededucted() != null) {
						publicSocialSecurityFund
								.setDatepay(simpleDateFormat.format(pensiondeduction.getDatededucted()));
					}
				}
				if (pensiondeduction == null) {
					publicSocialSecurityFund.setAmountemployee(0.00);

					publicSocialSecurityFund.setAmountemployer(0.00);

				}

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						publicSocialSecurityFund.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}

					if (hrmsEmployee.getFileNumber() != null) {
						publicSocialSecurityFund.setPersonalFileNumber(hrmsEmployee.getFileNumber());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						publicSocialSecurityFund
								.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}

					publicSocialSecurityFund.setNationalId(hrmsEmployee.getNationalId());

					if (hrmsEmployee.getFirstName() != null) {
						publicSocialSecurityFund.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						publicSocialSecurityFund.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						publicSocialSecurityFund.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						publicSocialSecurityFund.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						publicSocialSecurityFund.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}

				}
				publicSocialSecurityFundlist.add(publicSocialSecurityFund);
			}
			publicSocialSecurityFundResponse.setPublicSocialSecurityFundlist(publicSocialSecurityFundlist);
			publicSocialSecurityFundResponse.setTotalamountemployee(totalamountemployee);
			publicSocialSecurityFundResponse.setTotalamountemployer(totalamountemployer);
		}

		return ResponseEntity.status(HttpStatus.OK).body(publicSocialSecurityFundResponse);
	}

	@Override
	public ResponseEntity<PublicSocialSecurityFundResponse> getZssfPaymentByMonthAndYear(int year, int month) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		PublicSocialSecurityFundResponse publicSocialSecurityFundResponse = new PublicSocialSecurityFundResponse();
		int serviceproviderid = 3; // for ZSSF
		Double totalamountemployee = 0.00;

		Double totalamountemployer = 0.00;
		List<Integer> zssfmember = new ArrayList<>();
		List<HrmsEmployeeSocialSecurityScheme> zssfbeneficiaries = hrmsEmployeeSocialSecuritySchemeRepositoty
				.findByActive(1);
		zssfbeneficiaries.forEach(dbmz -> {
			if (dbmz.getServiceproviderid() == 3) {
				zssfmember.add(dbmz.getEmployeeid());
			}

		});

		if (month == 0) {
			List<PublicSocialSecurityFund> publicSocialSecurityFundlist = new ArrayList<>();
			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndEmployeeidInAndActive(year, zssfmember, 1);

			for (HrmsPayroll dbm : dbms) {
				PublicSocialSecurityFund publicSocialSecurityFund = new PublicSocialSecurityFund();

				if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
						.existsByPayrollidAndEmployeeidAndActive(dbm.getId(), dbm.getEmployeeid(), 1)) {
					HrmsPayrollDeductionMandatorySocialSecurityScheme pensiondeduction = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
							.findByPayrollidAndServiceprovideridAndActive(dbm.getId(), serviceproviderid, 1);

					publicSocialSecurityFund.setBasicPay(dbm.getAmountsalarybasic());

					publicSocialSecurityFund.setDatepayroll(year + "-" + dbm.getMonth());

					publicSocialSecurityFund.setGrossPay(dbm.getAmountsalarygross());

					if (pensiondeduction != null) {
						publicSocialSecurityFund.setAmountemployee(pensiondeduction.getAmountemployee());
						totalamountemployee = totalamountemployee + pensiondeduction.getAmountemployee();

						publicSocialSecurityFund.setAmountemployer(pensiondeduction.getAmountemployer());
						totalamountemployer = totalamountemployer + pensiondeduction.getAmountemployer();
						if (pensiondeduction.getDatededucted() != null) {
							publicSocialSecurityFund
									.setDatepay(simpleDateFormat.format(pensiondeduction.getDatededucted()));
						}
					}

					if (pensiondeduction == null) {
						publicSocialSecurityFund.setAmountemployee(0.00);
						publicSocialSecurityFund.setAmountemployer(0.00);

					}
					if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
						if (hrmsEmployee.getDob() != null) {
							publicSocialSecurityFund.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
						}

						if (hrmsEmployee.getGenderid() != 0
								&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
							publicSocialSecurityFund.setGender(
									hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
						}

						publicSocialSecurityFund.setNationalId(hrmsEmployee.getNationalId());

						if (hrmsEmployee.getFirstName() != null) {
							publicSocialSecurityFund.setFirstName(hrmsEmployee.getFirstName());
						}
						if (hrmsEmployee.getLastName() != null) {
							publicSocialSecurityFund.setLastName(hrmsEmployee.getLastName());
						}

						if (hrmsEmployee.getMiddleName() != null) {
							publicSocialSecurityFund.setMiddleName(hrmsEmployee.getMiddleName());
						}

						if (hrmsEmployee.getEmploymentcategoryId() != 0 && hrmsEmploymentCategoryRepository
								.existsById(hrmsEmployee.getEmploymentcategoryId())) {
							publicSocialSecurityFund.setEmploymentCategory(hrmsEmploymentCategoryRepository
									.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
						}

						if (hrmsEmployee.getDesignationId() != 0
								&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
							publicSocialSecurityFund.setDesignation(hrmsDesignationRepository
									.findById(hrmsEmployee.getDesignationId()).get().getName());
						}

					}
				}
				publicSocialSecurityFundlist.add(publicSocialSecurityFund);
			}
			publicSocialSecurityFundResponse.setPublicSocialSecurityFundlist(publicSocialSecurityFundlist);
			publicSocialSecurityFundResponse.setTotalamountemployee(totalamountemployee);
			publicSocialSecurityFundResponse.setTotalamountemployer(totalamountemployer);

		} else {

			List<PublicSocialSecurityFund> publicSocialSecurityFundlist = new ArrayList<>();
			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndEmployeeidInAndActive(year, month,
					zssfmember, 1);

			for (HrmsPayroll dbm : dbms) {
				PublicSocialSecurityFund publicSocialSecurityFund = new PublicSocialSecurityFund();
				if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
						.existsByPayrollidAndEmployeeidAndActive(dbm.getId(), dbm.getEmployeeid(), 1)) {
					HrmsPayrollDeductionMandatorySocialSecurityScheme pensiondeduction = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
							.findByPayrollidAndServiceprovideridAndActive(dbm.getId(), serviceproviderid, 1);

					publicSocialSecurityFund.setBasicPay(dbm.getAmountsalarybasic());

					publicSocialSecurityFund.setDatepayroll(year + "-" + dbm.getMonth());

					publicSocialSecurityFund.setGrossPay(dbm.getAmountsalarygross());

					if (pensiondeduction != null) {
						publicSocialSecurityFund.setAmountemployee(pensiondeduction.getAmountemployee());
						totalamountemployee = totalamountemployee + pensiondeduction.getAmountemployee();

						publicSocialSecurityFund.setAmountemployer(pensiondeduction.getAmountemployer());
						totalamountemployer = totalamountemployer + pensiondeduction.getAmountemployer();
						if (pensiondeduction.getDatededucted() != null) {
							publicSocialSecurityFund
									.setDatepay(simpleDateFormat.format(pensiondeduction.getDatededucted()));
						}
					}
					if (pensiondeduction == null) {
						publicSocialSecurityFund.setAmountemployee(0.00);
						publicSocialSecurityFund.setAmountemployer(0.00);

					}

					if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
						if (hrmsEmployee.getDob() != null) {
							publicSocialSecurityFund.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
						}

						if (hrmsEmployee.getGenderid() != 0
								&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
							publicSocialSecurityFund.setGender(
									hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
						}

						publicSocialSecurityFund.setNationalId(hrmsEmployee.getNationalId());

						if (hrmsEmployee.getFirstName() != null) {
							publicSocialSecurityFund.setFirstName(hrmsEmployee.getFirstName());
						}
						if (hrmsEmployee.getLastName() != null) {
							publicSocialSecurityFund.setLastName(hrmsEmployee.getLastName());
						}

						if (hrmsEmployee.getMiddleName() != null) {
							publicSocialSecurityFund.setMiddleName(hrmsEmployee.getMiddleName());
						}

						if (hrmsEmployee.getEmploymentcategoryId() != 0 && hrmsEmploymentCategoryRepository
								.existsById(hrmsEmployee.getEmploymentcategoryId())) {
							publicSocialSecurityFund.setEmploymentCategory(hrmsEmploymentCategoryRepository
									.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
						}

						if (hrmsEmployee.getDesignationId() != 0
								&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
							publicSocialSecurityFund.setDesignation(hrmsDesignationRepository
									.findById(hrmsEmployee.getDesignationId()).get().getName());
						}

					}
				}
				publicSocialSecurityFundlist.add(publicSocialSecurityFund);
			}
			publicSocialSecurityFundResponse.setPublicSocialSecurityFundlist(publicSocialSecurityFundlist);
			publicSocialSecurityFundResponse.setTotalamountemployee(totalamountemployee);
			publicSocialSecurityFundResponse.setTotalamountemployer(totalamountemployer);
		}

		return ResponseEntity.status(HttpStatus.OK).body(publicSocialSecurityFundResponse);
	}

	@Override
	public ResponseEntity<HealthInsuranceResponse> getNHIFByMonthAndYear(int year, int month) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		HealthInsuranceResponse healthInsuranceResponse = new HealthInsuranceResponse();

		List<HealthInsuarance> healthInsuarancelist = new ArrayList<>();
		Double totalnhifPay = 0.00;

		if (month == 0) {

			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndActive(year, 1);
			int contributiontypeid = 2;// insuarance
			int serviceproviderid = 1;// nhif
			for (HrmsPayroll dbm : dbms) {
				HealthInsuarance healthInsuarance = new HealthInsuarance();

				healthInsuarance.setBasicPay(dbm.getAmountsalarybasic());
				if (hrmsPayrollDeductionMandatoryInsuranceRepository
						.existsByPayrollidAndContributiontypeidAndActive(dbm.getId(), contributiontypeid, 1)) {
					HrmsPayrollDeductionMandatoryInsurance wcfdeductn = hrmsPayrollDeductionMandatoryInsuranceRepository
							.findByPayrollidAndContributiontypeidAndServiceprovideridAndActive(dbm.getId(),
									contributiontypeid, serviceproviderid, 1);
					if (wcfdeductn != null && wcfdeductn.getAmountemployer() != null
							&& wcfdeductn.getAmountemployer() != null) {
						healthInsuarance.setNhifPay(wcfdeductn.getAmountemployer());
						totalnhifPay = totalnhifPay + wcfdeductn.getAmountemployer();
					}
					if (wcfdeductn != null && wcfdeductn.getDatededucted() != null) {
						healthInsuarance.setDatepay(simpleDateFormat.format(wcfdeductn.getDatededucted()));
					}
					if (wcfdeductn != null) {
						healthInsuarance.setDatepayroll(wcfdeductn.getYear() + "-" + wcfdeductn.getMonth());
					}

				}
				healthInsuarance.setGrossPay(dbm.getAmountsalarygross());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						healthInsuarance.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}
					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						healthInsuarance.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}
					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						healthInsuarance.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						healthInsuarance
								.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}
					// StringBuilder firstName = new StringBuilder();
					if (hrmsEmployee.getFirstName() != null) {
						healthInsuarance.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						healthInsuarance.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						healthInsuarance.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getNationalId() != null) {
						healthInsuarance.setNationalId(hrmsEmployee.getNationalId());
					}
				}

				healthInsuarancelist.add(healthInsuarance);
			}

		} else {

			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1);
			int contributiontypeid = 2;// health insurance
			int serviceproviderid = 1;// nhif
			for (HrmsPayroll dbm : dbms) {
				HealthInsuarance healthInsuarance = new HealthInsuarance();

				healthInsuarance.setBasicPay(dbm.getAmountsalarybasic());
				if (hrmsPayrollDeductionMandatoryInsuranceRepository
						.existsByPayrollidAndContributiontypeidAndActive(dbm.getId(), contributiontypeid, 1)) {
					HrmsPayrollDeductionMandatoryInsurance wcfdeductn = hrmsPayrollDeductionMandatoryInsuranceRepository
							.findByPayrollidAndContributiontypeidAndServiceprovideridAndActive(dbm.getId(),
									contributiontypeid, serviceproviderid, 1);
					if (wcfdeductn != null && wcfdeductn.getAmountemployer() != null
							&& wcfdeductn.getAmountemployer() != null) {
						healthInsuarance.setNhifPay(wcfdeductn.getAmountemployer());
						totalnhifPay = totalnhifPay + wcfdeductn.getAmountemployer();
					}
					if (wcfdeductn != null && wcfdeductn.getDatededucted() != null) {
						healthInsuarance.setDatepay(simpleDateFormat.format(wcfdeductn.getDatededucted()));
					}
					if (wcfdeductn != null) {
						healthInsuarance.setDatepayroll(wcfdeductn.getYear() + "-" + wcfdeductn.getMonth());
					}

				}
				healthInsuarance.setGrossPay(dbm.getAmountsalarygross());
				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						healthInsuarance.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}
					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						healthInsuarance.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}
					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						healthInsuarance.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						healthInsuarance
								.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}
					// StringBuilder firstName = new StringBuilder();
					if (hrmsEmployee.getFirstName() != null) {
						healthInsuarance.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						healthInsuarance.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						healthInsuarance.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getNationalId() != null) {
						healthInsuarance.setNationalId(hrmsEmployee.getNationalId());
					}
				}

				healthInsuarancelist.add(healthInsuarance);
			}
		}
		healthInsuranceResponse.setHealthInsuarancelist(healthInsuarancelist);
		healthInsuranceResponse.setTotalnhifPay(totalnhifPay);

		return ResponseEntity.status(HttpStatus.OK).body(healthInsuranceResponse);
	}

	@Override
	public ResponseEntity<PAYEResponse> getPAYEByMonthAndYear(int year, int month) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		PAYEResponse payeResponse = new PAYEResponse();

		List<PAYE> payelist = new ArrayList<>();
		Double totalpaye = 0.00;

		if (month == 0) {

			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndActive(year, 1);
			for (HrmsPayroll dbm : dbms) {
				PAYE paye = new PAYE();

				paye.setBasicPay(dbm.getAmountsalarybasic());
				paye.setGrossPay(dbm.getAmountsalarygross());
				paye.setDatepay(simpleDateFormat.format(dbm.getDatepay()));
				paye.setDatepayroll(dbm.getYear() + "-" + dbm.getMonth() + "-" + dbm.getDay());
				paye.setPayepay(dbm.getAmounttaxpaye());
				totalpaye = totalpaye + dbm.getAmounttaxpaye();

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						paye.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}
					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						paye.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}
					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						paye.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						paye.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}
					// StringBuilder firstName = new StringBuilder();
					if (hrmsEmployee.getFirstName() != null) {
						paye.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						paye.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						paye.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getNationalId() != null) {
						paye.setNationalId(hrmsEmployee.getNationalId());
					}
				}

				payelist.add(paye);

			}

		} else {

			List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1);
			for (HrmsPayroll dbm : dbms) {
				PAYE paye = new PAYE();

				paye.setBasicPay(dbm.getAmountsalarybasic());
				paye.setGrossPay(dbm.getAmountsalarygross());
				paye.setDatepay(simpleDateFormat.format(dbm.getDatepay()));
				paye.setDatepayroll(dbm.getYear() + "-" + dbm.getMonth() + "-" + dbm.getDay());
				paye.setPayepay(dbm.getAmounttaxpaye());
				totalpaye = totalpaye + dbm.getAmounttaxpaye();

				if (dbm.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
					if (hrmsEmployee.getDob() != null) {
						paye.setDateOfBirth(simpleDateFormat.format(hrmsEmployee.getDob()));
					}
					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
						paye.setDesignation(
								hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());
					}
					if (hrmsEmployee.getEmploymentcategoryId() != 0
							&& hrmsEmploymentCategoryRepository.existsById(hrmsEmployee.getEmploymentcategoryId())) {
						paye.setEmploymentCategory(hrmsEmploymentCategoryRepository
								.findById(hrmsEmployee.getEmploymentcategoryId()).get().getName());
					}

					if (hrmsEmployee.getGenderid() != 0
							&& hrmsGenderRepository.existsById(hrmsEmployee.getGenderid())) {
						paye.setGender(hrmsGenderRepository.findById(hrmsEmployee.getGenderid()).get().getName());
					}
					// StringBuilder firstName = new StringBuilder();
					if (hrmsEmployee.getFirstName() != null) {
						paye.setFirstName(hrmsEmployee.getFirstName());
					}
					if (hrmsEmployee.getLastName() != null) {
						paye.setLastName(hrmsEmployee.getLastName());
					}

					if (hrmsEmployee.getMiddleName() != null) {
						paye.setMiddleName(hrmsEmployee.getMiddleName());
					}

					if (hrmsEmployee.getNationalId() != null) {
						paye.setNationalId(hrmsEmployee.getNationalId());
					}
				}

				payelist.add(paye);

			}

		}

		payeResponse.setPayelist(payelist);
		payeResponse.setTotalpaye(totalpaye);
		return ResponseEntity.status(HttpStatus.OK).body(payeResponse);
	}

	@Override
	public ResponseEntity<PayrollJournal> getPayrollJournal(int year, int month) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String payday = null;

		Double basicpay = 0.0;

		Double psssfemployerpay = 0.0;

		Double zssfemployerpay = 0.0;

		Double otherincome = 0.0;

		Double psssfpay = 0.0;
		Double zssfpay = 0.0;
		Double payepay = 0.0;
		Double netpay = 0.0;

		Double credittotal = 0.0;

		Double debttotal = 0.0;
		Double miscelaneouspay = 0.0;
		Double amountdeductionloan = 0.0;

		PayrollJournal payrollJournal = new PayrollJournal();

		List<HrmsPayroll> dbms = hrmsPayrollRepository.findByYearAndMonthAndActive(year, month, 1);

		List<Integer> payrollids = new ArrayList<>();

		for (HrmsPayroll dbm : dbms) {
			payrollids.add(dbm.getId());
			if (dbm.getDatepay() != null) {
				payday = simpleDateFormat.format(dbm.getDatepay());

			}
			basicpay = basicpay + dbm.getAmountsalarybasic();

			netpay = netpay + dbm.getAmountsalarynet();
			otherincome = otherincome + dbm.getAmountotherincome();

			payepay = payepay + dbm.getAmounttaxpaye();
			if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.existsByPayrollidAndEmployeeidAndActive(dbm.getId(), dbm.getEmployeeid(), 1)) {

				HrmsPayrollDeductionMandatorySocialSecurityScheme pensiondeduction = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
						.findByPayrollidAndEmployeeidAndActive(dbm.getId(), dbm.getEmployeeid(), 1);
				if (pensiondeduction.getServiceproviderid() == 1) {// PSSSF

					psssfemployerpay = psssfemployerpay + pensiondeduction.getAmountemployer();// employer contribution

					psssfpay = psssfpay + (pensiondeduction.getAmountemployee() + pensiondeduction.getAmountemployer());// employer
																														// and
																														// contribution

				}

				if (pensiondeduction.getServiceproviderid() == 3) {// ZSSF

					zssfemployerpay = zssfemployerpay + pensiondeduction.getAmountemployer();// employer contribution

					zssfpay = zssfpay + (pensiondeduction.getAmountemployee() + pensiondeduction.getAmountemployer());// employer
																														// and
																														// contribution

				}

			}

		}
		// loan deduction calculation
		List<HrmsPayrollLoanType> loanTypelist = hrmsPayrollLoanTypeRepository.findByActive(1);
		List<DeductionLoan> deductionloanlist = new ArrayList<>();

		for (HrmsPayrollLoanType dbm : loanTypelist) {
			Double amountdeductionloansub = 0.0;
			DeductionLoan deductionLoan = new DeductionLoan();
			deductionLoan.setLoantype(dbm.getName());
			deductionLoan.setLoantypeCode(dbm.getCode());

			// get into loan deduction now

			List<HrmsPayrollDeductionLoan> dbmloans = hrmsPayrollDeductionLoanRepository
					.findByLoantypeidAndYearAndMonthAndActiveAndPayrollidIn(dbm.getId(), year, month, 1, payrollids);
			for (HrmsPayrollDeductionLoan dbmloan : dbmloans) {

				amountdeductionloan = amountdeductionloan + dbmloan.getAmount();

				amountdeductionloansub = amountdeductionloansub + dbmloan.getAmount();
			}

			deductionLoan.setAmountdeductionloan(amountdeductionloansub);

			deductionloanlist.add(deductionLoan);

		}

		// voluntary deduction

		List<HrmsPayrollContributionTypeVoluntary> dbmsvc = hrmsPayrollContributionTypeVoluntaryRepository
				.findByActive(1);
		Double amountdeductionvoluntary = 0.00;
		List<DeductionVoluntary> deductionvoluntarylist = new ArrayList<>();
		for (HrmsPayrollContributionTypeVoluntary dbmvc : dbmsvc) {
			Double amountdeductionvoluntarysub = 0.00;
			DeductionVoluntary deductionVoluntary = new DeductionVoluntary();
			deductionVoluntary.setVoluntaryContributiontypeCode(dbmvc.getCode());
			deductionVoluntary.setVoluntaryContributiontype(dbmvc.getName());

			// get deduction voluntary

			List<HrmsPayrollDeductionVoluntary> deductionvl = hrmsPayrollDeductionVoluntaryRepository
					.findByContributiontypeidAndYearAndMonthAndActiveAndPayrollidIn(dbmvc.getId(), year, month, 1,
							payrollids);
			for (HrmsPayrollDeductionVoluntary deductionv : deductionvl) {

				amountdeductionvoluntary = amountdeductionvoluntary + deductionv.getAmount();

				amountdeductionvoluntarysub = amountdeductionvoluntarysub + deductionv.getAmount();

			}

			deductionVoluntary.setAmountdeductionvoluntary(amountdeductionvoluntarysub);

			deductionvoluntarylist.add(deductionVoluntary);
		}

		// allowance added

		List<HrmsAllowancetype> allowancetype = hrmsAllowancetypeRepository.findByActive(1);
		List<Allowance> allowancelist = new ArrayList<>();
		Double amountsalaryallowance = 0.00;
		for (HrmsAllowancetype dbmallowancetpe : allowancetype) {
			Double amountsalaryallowancesub = 0.00;
			if (dbmallowancetpe.getCode() == 1 || dbmallowancetpe.getCode() == 2 || dbmallowancetpe.getCode() == 3) {// exclude
																														// travel
																														// allowance
				Allowance allowance = new Allowance();

				allowance.setAllowancetype(dbmallowancetpe.getName());
				allowance.setAllowancetypeCode(dbmallowancetpe.getCode());
				for (HrmsPayroll payrolres : dbms) {
					// if
					// (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(payrolres.getEmployeeid(),
					// 1)) {
					// Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
					// .findByEmployeeidAndActive(payrolres.getEmployeeid(), 1);

					// HrmsAllowance hrmsAllowance = hrmsAllowanceRepository
					// .findBySalarystructureidAndAllowancetypeidAndCurrencyidAndActive(
					// hrmsemployeesalary.getSalarystructureId(), dbmallowancetpe.getId(),
					// hrmsemployeesalary.getCurrencyId(), 1); // this is not used now as it may
					// change
					// the actual value on payroll
					if (dbmallowancetpe.getCode() == 1) { // servant and hospitality allowance
						// amountsalaryallowance = amountsalaryallowance
						// + payrolres.getAmountsalaryservantAndHospitality();

						amountsalaryallowancesub = amountsalaryallowancesub
								+ payrolres.getAmountsalaryservantAndHospitality();
					}

					if (dbmallowancetpe.getCode() == 2) {// Housing allowance
						// amountsalaryallowance = amountsalaryallowance +
						// payrolres.getAmountsalaryallowancehousing();

						amountsalaryallowancesub = amountsalaryallowancesub
								+ payrolres.getAmountsalaryallowancehousing();
					}

					if (dbmallowancetpe.getCode() == 3) {// transport
						// amountsalaryallowance = amountsalaryallowance
						// + payrolres.getAmountsalaryallowancetransport();

						amountsalaryallowancesub = amountsalaryallowancesub
								+ payrolres.getAmountsalaryallowancetransport();
					}

					// }
				}
				allowance.setAmountsalaryallowance(amountsalaryallowancesub);

				amountsalaryallowance = amountsalaryallowance + amountsalaryallowancesub;

				allowancelist.add(allowance);
			}

		}
		DecimalFormat df = new DecimalFormat("###.####");
		credittotal = credittotal + amountdeductionloan + netpay + psssfpay + zssfpay + amountdeductionvoluntary
				+ payepay;

		debttotal = debttotal + psssfemployerpay + zssfemployerpay + otherincome + basicpay + amountsalaryallowance;

		String credittotals = df.format(credittotal);

		credittotal = Double.parseDouble(credittotals);

		String debttotals = df.format(debttotal);

		debttotal = Double.parseDouble(debttotals);

		miscelaneouspay = debttotal - credittotal;

		payrollJournal.setAllowancelist(allowancelist);

		payrollJournal.setBasicpay(basicpay);

		payrollJournal.setDeductionloanlist(deductionloanlist);

		payrollJournal.setDeductionvoluntarylist(deductionvoluntarylist);
		payrollJournal.setMonth(month);
		payrollJournal.setNetpay(netpay);
		payrollJournal.setOtherincome(otherincome);
		payrollJournal.setPayday(payday);
		payrollJournal.setPayepay(payepay);
		payrollJournal.setPayrolldate(year + "-" + month);
		payrollJournal.setPsssfemployerpay(psssfemployerpay);
		payrollJournal.setPsssfpay(psssfpay);
		payrollJournal.setYear(year);
		payrollJournal.setZssfemployerpay(zssfemployerpay);
		payrollJournal.setZssfpay(zssfpay);
		payrollJournal.setMiscelaneouspay(miscelaneouspay);

		payrollJournal.setCredittotal(credittotal);

		payrollJournal.setDebttotal(debttotal);

		return ResponseEntity.status(HttpStatus.OK).body(payrollJournal);
	}

	@Override
	public ResponseEntity<PaySlipResponse> getPayrollPaySlip(int employeeid, int year, int month) {
		String datepay = year + "-" + month + "-" + 15;

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (!hrmsPayrollRepository.existsByYearAndMonthAndActive(year, month, 1)) {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

		}
		HrmsPayroll dbm = hrmsPayrollRepository.findByEmployeeidAndYearAndMonthAndActive(employeeid, year, month, 1);

		PaySlipResponse PaySlipResponse = new PaySlipResponse();

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

					if (dbm.getAmountsalaryservantAndHospitality() != 0.00) {
						allowance.setAllowancetype(allowancetype.getName());
						allowance.setAllowancetypeCode(allowancetype.getCode());
						allowance.setAmountsalaryallowance(dbm.getAmountsalaryservantAndHospitality());
					}

				}

				if (allowancetype.getCode() == 2) {// Housing allowance
					if (dbm.getAmountsalaryallowancehousing() != 0.0) {
						allowance.setAllowancetype(allowancetype.getName());
						allowance.setAllowancetypeCode(allowancetype.getCode());
						allowance.setAmountsalaryallowance(dbm.getAmountsalaryallowancehousing());
					}

				}

				if (allowancetype.getCode() == 3) {// transport
					if (dbm.getAmountsalaryallowancetransport() != 0.00) {
						allowance.setAllowancetype(allowancetype.getName());
						allowance.setAllowancetypeCode(allowancetype.getCode());
						allowance.setAmountsalaryallowance(dbm.getAmountsalaryallowancetransport());
					}
				}

				allowancelist.add(allowance);

			}
		}
		PaySlipResponse.setAllowancelist(allowancelist);
		// populate loan deduction now and their balances
		List<DeductionLoan> deductionLoanlist = new ArrayList<>();
		List<HrmsPayrollLoanType> hrmsPayrollLoanTypelist = hrmsPayrollLoanTypeRepository.findByActive(1);
		for (HrmsPayrollLoanType loantype : hrmsPayrollLoanTypelist) {

			if (hrmsPayrollDeductionLoanRepository.existsByLoantypeidAndEmployeeidAndPayrollidAndActive(
					loantype.getId(), dbm.getEmployeeid(), dbm.getId(), 1)) {

				DeductionLoan deductionLoan = new DeductionLoan();
				HrmsPayrollDeductionLoan hrmsPayrollDeductionLoan = hrmsPayrollDeductionLoanRepository
						.findByLoantypeidAndEmployeeidAndPayrollidAndActive(loantype.getId(), dbm.getEmployeeid(),
								dbm.getId(), 1);

				deductionLoan.setAmountdeductionloan(hrmsPayrollDeductionLoan.getAmount());
				deductionLoan.setLoantype(loantype.getName());
				deductionLoan.setLoantypeCode(loantype.getCode());
				deductionLoan.setAmountloanbalance(hrmsPayrollDeductionLoan.getAmountoutstanding());

				deductionLoanlist.add(deductionLoan);
			}

		}

		PaySlipResponse.setDeductionLoanlist(deductionLoanlist);

		// populate pension now
		List<DeductionMandatoryPension> deductionMandatoryPensionlist = new ArrayList<>();

		List<HrmsPayrollContributionMandatorySocialSecurityScheme> hrmsPayrollContributionMandatorySocialSecuritySchemelist = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
				.findByActive(1);

		for (HrmsPayrollContributionMandatorySocialSecurityScheme pension : hrmsPayrollContributionMandatorySocialSecuritySchemelist) {
			DeductionMandatoryPension deductionMandatoryPension = new DeductionMandatoryPension();
			if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndServiceprovideridAndActive(
					dbm.getEmployeeid(), pension.getServiceproviderid(), 1)) {

				PaySlipResponse.setPensionAmount(dbm.getAmountdeductionmandatorysssf());

				PaySlipResponse.setPensionName(hrmsSocialSecuritySchemeServiceProviderRepository
						.findById(pension.getServiceproviderid()).get().getName());
			}
		}

		// populate voluntary contributions
		List<DeductionVoluntary> deductionVoluntarylist = new ArrayList<>();

		List<HrmsPayrollContributionTypeVoluntary> hrmsPayrollContributionTypeVoluntarylist = hrmsPayrollContributionTypeVoluntaryRepository
				.findByActive(1);
		for (HrmsPayrollContributionTypeVoluntary cvoluntarytype : hrmsPayrollContributionTypeVoluntarylist) {

			if (hrmsPayrollDeductionVoluntaryRepository.existsByEmployeeidAndPayrollidAndContributiontypeidAndActive(
					dbm.getEmployeeid(), dbm.getId(), cvoluntarytype.getId(), 1)) {
				DeductionVoluntary deductionVoluntary = new DeductionVoluntary();
				HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = hrmsPayrollDeductionVoluntaryRepository
						.findByEmployeeidAndPayrollidAndContributiontypeidAndActive(dbm.getEmployeeid(), dbm.getId(),
								cvoluntarytype.getId(), 1);

				deductionVoluntary.setAmountdeductionvoluntary(hrmsPayrollDeductionVoluntary.getAmount());

				deductionVoluntary.setVoluntaryContributiontype(cvoluntarytype.getName());
				deductionVoluntary.setVoluntaryContributiontypeCode(cvoluntarytype.getCode());

				deductionVoluntarylist.add(deductionVoluntary);

			}

		}

		PaySlipResponse.setDeductionVoluntarylist(deductionVoluntarylist);

		PaySlipResponse.setAmountotherincome(dbm.getAmountotherincome());

		PaySlipResponse.setYear(dbm.getYear());
		PaySlipResponse.setMonth(dbm.getMonth());
		PaySlipResponse.setDay(dbm.getDay());
		PaySlipResponse.setAmounttaxable(dbm.getAmounttaxable());
		PaySlipResponse.setTotalDeductionAmount(dbm.getAmountdeduction());

		PaySlipResponse.setBasicsalaryAmount(dbm.getAmountsalarybasic());
		PaySlipResponse.setGrosssalaryAmount(dbm.getAmountsalarygross());

		PaySlipResponse.setNetsalaryAmount(dbm.getAmountsalarynet());

		PaySlipResponse.setPayeAmount(dbm.getAmounttaxpaye());

		// salary scale setting

		if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(dbm.getEmployeeid(), 1)) {

			Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
					.findByEmployeeidAndActive(dbm.getEmployeeid(), 1);
			PaySlipResponse
					.setSalaryScale(hrmsSalaryScaleRepository
							.findById(hrmsSalarystructureRepository.findById(hrmsemployeesalary.getSalarystructureId())
									.get().getScaleId())
							.get().getName()
							+ "-"
							+ hrmsSalaryscalenotchRepository
									.findById(hrmsSalarystructureRepository
											.findById(hrmsemployeesalary.getSalarystructureId()).get().getNotchId())
									.get().getName());
		}

		if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
			StringBuilder fullName = new StringBuilder();

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
			PaySlipResponse.setFileNumber(hrmsEmployee.getFileNumber());

			if (hrmsEmployee.getUnitId() != 0 && hrmsOrginisationUnitRepository.existsById(hrmsEmployee.getUnitId())) {
				PaySlipResponse.setDepartment(
						hrmsOrginisationUnitRepository.findById(hrmsEmployee.getUnitId()).get().getName());
			}

			if (hrmsEmployee.getDutystationid() != 0) {
				PaySlipResponse.setOffice(
						hrmsOrganisationOfficeRepository.findById(hrmsEmployee.getDutystationid()).get().getName());

				PaySlipResponse
						.setOfficetype(
								hrmsOrganisationOfficeCategoryRepository
										.findById(hrmsOrganisationOfficeRepository
												.findById(hrmsEmployee.getDutystationid()).get().getOfficetypeid())
										.get().getName());

			}

			if (hrmsEmployee.getDateofemployment() != null) {
				PaySlipResponse.setDateofEmployment(simpleDateFormat.format(hrmsEmployee.getDateofemployment()));

			}

			if (hrmsEmployee.getDesignationId() != 0
					&& hrmsDesignationRepository.existsById(hrmsEmployee.getDesignationId())) {
				PaySlipResponse.setDesignation(
						hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get().getName());

			}
			fullName.append(hrmsEmployee.getFirstName().trim());
			if (hrmsEmployee.getMiddleName() != null) {
				fullName.append(" " + hrmsEmployee.getMiddleName().trim());
			}

			fullName.append(" " + hrmsEmployee.getLastName().trim());
			PaySlipResponse.setEmployeeFullName(fullName.toString());
		}
		PaySlipResponse.setEmployeeid(dbm.getEmployeeid());

		PaySlipResponse.setPayrollPeriod(year + "-" + month);
		PaySlipResponse.setModeofPayment("BANK");
		// Bank transfer details
		List<PayrollBankAccountTranser> payrollBankAccountTranserlist = new ArrayList<>();
		List<HrmsPayrollBankAccount> dbms = hrmsPayrollBankAccountRepository
				.findByEmployeeidAndYearAndMonthAndActive(employeeid, year, month, 1);
		Double totalbankAccountAmountTransfered = 0.00;

		// totalemployeeNumber = hrmsPayrollBankAccountRepository
		// .countDistinctEmployeeidByEmployeeidAndYearAndMonthAndActive(empid, year,
		// month, 1);

		for (HrmsPayrollBankAccount dbmb : dbms) {

			PayrollBankAccountTranser payrollBankAccountTranser = new PayrollBankAccountTranser();
			if (hrmsEmployeeBankAccountRepository.existsById(dbmb.getBankaccountid())) {

				HrmsEmployeeBankAccount hrmsEmployeeBankAccount = hrmsEmployeeBankAccountRepository
						.findById(dbmb.getBankaccountid()).get();
				payrollBankAccountTranser.setAccountNumber(hrmsEmployeeBankAccount.getAccountnumber());

				payrollBankAccountTranser.setAccountName(hrmsEmployeeBankAccount.getAccountname());

				payrollBankAccountTranser.setBankid(hrmsEmployeeBankAccount.getBankid());
				if (hrmsEmployeeBankAccount.getBankid() != 0
						&& hrmsBankRepository.existsById(hrmsEmployeeBankAccount.getBankid())) {
					payrollBankAccountTranser.setBankName(
							hrmsBankRepository.findById(hrmsEmployeeBankAccount.getBankid()).get().getName());
				}

				if (hrmsEmployeeBankAccount.getBankbranchid() != 0
						&& hrmsBankBranchRepository.existsById(hrmsEmployeeBankAccount.getBankbranchid())) {
					payrollBankAccountTranser.setSortCode(hrmsBankBranchRepository
							.findById(hrmsEmployeeBankAccount.getBankbranchid()).get().getSortcode());
				}

			}

			payrollBankAccountTranser.setActive(dbmb.getActive());
			payrollBankAccountTranser.setAmount(dbmb.getAmount());
			totalbankAccountAmountTransfered = totalbankAccountAmountTransfered + dbmb.getAmount();// aggregate sum
																									// amount paid to
																									// all accounts
			payrollBankAccountTranser.setApproved(dbmb.getApproved());

			payrollBankAccountTranser.setCurrencyid(dbmb.getCurrencyid());
			if (dbmb.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbmb.getCurrencyid())) {
				payrollBankAccountTranser
						.setCurrency(hrmsCurrencyRepository.findById(dbmb.getCurrencyid()).get().getName());
			}

			payrollBankAccountTranser.setDatepay(year + "-" + month + "-" + dbmb.getDay());
			payrollBankAccountTranser.setDay(dbmb.getDay());
			payrollBankAccountTranser.setEmployeebankaccountid(dbmb.getBankaccountid());

			if (dbmb.getEmployeeid() != 0 && hrmsEmployeeRepository.existsById(dbmb.getEmployeeid())) {
				StringBuilder employeeFullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				employeeFullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

				payrollBankAccountTranser.setEmployeeFullName(employeeFullname.toString());
			}

			payrollBankAccountTranser.setEmployeeid(dbmb.getEmployeeid());
			payrollBankAccountTranser.setId(dbmb.getId());
			payrollBankAccountTranser.setMonth(month);
			payrollBankAccountTranser.setPayrollid(dbmb.getPayrollid());
			payrollBankAccountTranser.setYear(year);

			payrollBankAccountTranserlist.add(payrollBankAccountTranser);
		}
		PaySlipResponse.setTotalbankAccountAmountTransfered(totalbankAccountAmountTransfered);
		PaySlipResponse.setBankAccountTranserlist(payrollBankAccountTranserlist);

		return ResponseEntity.status(HttpStatus.OK).body(PaySlipResponse);

	}

}
