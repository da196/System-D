package com.Hrms.Payroll.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAllowance;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.HrmsEmployeeSocialSecurityScheme;
import com.Hrms.Employee.Entity.HrmsSalarystructure;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Repository.HrmsAllowanceRepository;
import com.Hrms.Employee.Repository.HrmsAllowancetypeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeSocialSecuritySchemeRepositoty;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;
import com.Hrms.Payroll.DTO.OneThirdBalance;
import com.Hrms.Payroll.DTO.PayrollLoanPaymentAmount;
import com.Hrms.Payroll.DTO.PayrollLoanTypeMaxAmountResponse;
import com.Hrms.Payroll.DTO.PayrollLoanTypeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Entity.HrmsPayrollContributionVoluntary;
import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoan;
import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;
import com.Hrms.Payroll.Entity.HrmsPayrollTaxStructure;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionMandatorySocialSecuritySchemeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollEmployeeLoanRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanLenderRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanTypeRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxStructureRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollTaxTypeRepository;
import com.Hrms.Payroll.Repository.HrmsSocialSecuritySchemeServiceProviderRepository;

@Service
public class HrmsPayrollLoanTypeServiceImpl implements HrmsPayrollLoanTypeService {

	@Autowired
	private HrmsPayrollLoanTypeRepository hrmsPayrollLoanTypeRepository;

	@Autowired
	private HrmsPayrollEmployeeLoanRepository hrmsPayrollEmployeeLoanRepository;

	@Autowired
	private HrmsPayrollLoanLenderRepository hrmsPayrollLoanLenderRepository;

	@Autowired
	private HrmsPayrollTaxStructureRepository hrmsPayrollTaxStructureRepository;

	@Autowired
	private HrmsPayrollTaxTypeRepository hrmsPayrollTaxTypeRepository;

	@Autowired

	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsAllowanceRepository hrmsAllowanceRepository;

	@Autowired
	private HrmsSocialSecuritySchemeServiceProviderRepository hrmsSocialSecuritySchemeServiceProviderRepository;

	@Autowired
	private HrmsEmployeeSocialSecuritySchemeRepositoty hrmsEmployeeSocialSecuritySchemeRepositoty;

	@Autowired
	private HrmsAllowancetypeRepository hrmsAllowancetypeRepository;

	@Autowired
	private HrmsPayrollContributionVoluntaryRepository hrmsPayrollContributionVoluntaryRepository;

	@Autowired
	private HrmsPayrollContributionMandatorySocialSecuritySchemeRepository hrmsPayrollContributionMandatorySocialSecuritySchemeRepository;

	@Override
	public ResponseEntity<HrmsPayrollLoanType> addPayrollLoanType(HrmsPayrollLoanType hrmsPayrollLoanType) {
		if (hrmsPayrollLoanTypeRepository.existsByNameAndActive(hrmsPayrollLoanType.getName(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollLoanType);
		} else {
			hrmsPayrollLoanType.setActive(1);
			hrmsPayrollLoanType.setApproved(0);
			hrmsPayrollLoanType.setUnique_id(UUID.randomUUID());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanTypeRepository.saveAndFlush(hrmsPayrollLoanType));

		}
	}

	@Override
	public ResponseEntity<PayrollLoanTypeResponse> getPayrollLoanTypeById(int id) {
		if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollLoanType dbm = hrmsPayrollLoanTypeRepository.findByIdAndActive(id, 1);

			PayrollLoanTypeResponse payrollLoanTypeResponse = new PayrollLoanTypeResponse();

			payrollLoanTypeResponse.setAbbreviation(dbm.getAbbreviation());
			payrollLoanTypeResponse.setActive(dbm.getActive());
			payrollLoanTypeResponse.setAmountmax(dbm.getAmountmax());
			payrollLoanTypeResponse.setAmountmin(dbm.getAmountmin());
			payrollLoanTypeResponse.setApproved(dbm.getApproved());
			payrollLoanTypeResponse.setCode(dbm.getCode());
			payrollLoanTypeResponse.setDescription(dbm.getDescription());
			payrollLoanTypeResponse.setDurationmax(dbm.getDurationmax());
			payrollLoanTypeResponse.setId(dbm.getId());
			payrollLoanTypeResponse.setInterestrate(dbm.getInterestrate());
			payrollLoanTypeResponse.setIsinternalsource(dbm.getIsinternalsource());
			if (dbm.getLenderid() != 0 && hrmsPayrollLoanLenderRepository.existsById(dbm.getLenderid())) {
				payrollLoanTypeResponse
						.setLender(hrmsPayrollLoanLenderRepository.findById(dbm.getLenderid()).get().getName());
			}
			payrollLoanTypeResponse.setLenderid(dbm.getLenderid());
			payrollLoanTypeResponse.setName(dbm.getName());
			payrollLoanTypeResponse.setRestrictioncode(dbm.getRestrictioncode());

			return ResponseEntity.status(HttpStatus.OK).body(payrollLoanTypeResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollLoanType> updatePayrollLoanType(HrmsPayrollLoanType hrmsPayrollLoanType, int id) {
		if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(id, 1)) {
			hrmsPayrollLoanType.setActive(1);
			hrmsPayrollLoanType.setApproved(0);
			hrmsPayrollLoanType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanTypeRepository.saveAndFlush(hrmsPayrollLoanType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollLoanType(int id) {

		if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollLoanType hrmsPayrollLoanType = hrmsPayrollLoanTypeRepository.findByIdAndActive(id, 1);
			hrmsPayrollLoanType.setActive(0);
			hrmsPayrollLoanType.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollLoanTypeRepository.saveAndFlush(hrmsPayrollLoanType));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollLoanTypeResponse>> getAllPayrollLoanType() {

		List<PayrollLoanTypeResponse> payrollLoanTypeResponselist = new ArrayList<>();

		List<HrmsPayrollLoanType> dms = hrmsPayrollLoanTypeRepository.findByActive(1);
		dms.forEach(dbm -> {
			PayrollLoanTypeResponse payrollLoanTypeResponse = new PayrollLoanTypeResponse();

			payrollLoanTypeResponse.setAbbreviation(dbm.getAbbreviation());
			payrollLoanTypeResponse.setActive(dbm.getActive());
			payrollLoanTypeResponse.setAmountmax(dbm.getAmountmax());
			payrollLoanTypeResponse.setAmountmin(dbm.getAmountmin());
			payrollLoanTypeResponse.setApproved(dbm.getApproved());
			payrollLoanTypeResponse.setCode(dbm.getCode());
			payrollLoanTypeResponse.setDescription(dbm.getDescription());
			payrollLoanTypeResponse.setDurationmax(dbm.getDurationmax());
			payrollLoanTypeResponse.setId(dbm.getId());
			payrollLoanTypeResponse.setInterestrate(dbm.getInterestrate());
			payrollLoanTypeResponse.setIsinternalsource(dbm.getIsinternalsource());
			if (dbm.getLenderid() != 0 && hrmsPayrollLoanLenderRepository.existsById(dbm.getLenderid())) {
				payrollLoanTypeResponse
						.setLender(hrmsPayrollLoanLenderRepository.findById(dbm.getLenderid()).get().getName());
			}
			payrollLoanTypeResponse.setLenderid(dbm.getLenderid());
			payrollLoanTypeResponse.setName(dbm.getName());
			payrollLoanTypeResponse.setRestrictioncode(dbm.getRestrictioncode());
			payrollLoanTypeResponselist.add(payrollLoanTypeResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(payrollLoanTypeResponselist);
	}

	@Override
	public ResponseEntity<PayrollLoanTypeMaxAmountResponse> getMaxLoanAmount(int empid, int loantypeid) {
		if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(loantypeid, 1)
				&& hrmsEmployeeRepository.existsByIdAndActive(empid, 1)) {

			PayrollLoanTypeMaxAmountResponse payrollLoanTypeMaxAmountResponse = new PayrollLoanTypeMaxAmountResponse();

			HrmsPayrollLoanType hrmsPayrollLoanType = hrmsPayrollLoanTypeRepository.findById(loantypeid).get();

			payrollLoanTypeMaxAmountResponse.setAbbreviation(hrmsPayrollLoanType.getAbbreviation());
			payrollLoanTypeMaxAmountResponse.setActive(hrmsPayrollLoanType.getActive());

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(empid).get();
			if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(empid, 1)) {
				Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository.findByEmployeeidAndActive(empid,
						1);
				Double bsalary = 0.00;
				if (hrmsSalarystructureRepository.existsById(hrmsemployeesalary.getSalarystructureId())) {
					HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
							.findById(hrmsemployeesalary.getSalarystructureId()).get();
					bsalary = hrmsSalarystructure.getBasicSalary();
				}
				Double amountmax = (bsalary * hrmsPayrollLoanType.getDurationmax());
				payrollLoanTypeMaxAmountResponse.setAmountmax(amountmax);
			}

			payrollLoanTypeMaxAmountResponse.setAmountmin(hrmsPayrollLoanType.getAmountmin());
			payrollLoanTypeMaxAmountResponse.setApproved(hrmsPayrollLoanType.getApproved());
			payrollLoanTypeMaxAmountResponse.setCode(hrmsPayrollLoanType.getCode());
			payrollLoanTypeMaxAmountResponse.setDescription(hrmsPayrollLoanType.getDescription());
			payrollLoanTypeMaxAmountResponse.setEmployeeid(empid);

			StringBuilder fullName = new StringBuilder();
			fullName.append(" -  " + hrmsEmployee.getFirstName().trim());
			fullName.append(" -  " + hrmsEmployee.getMiddleName().trim());
			fullName.append(" " + hrmsEmployee.getLastName().trim());

			payrollLoanTypeMaxAmountResponse.setFullName(fullName.toString());
			payrollLoanTypeMaxAmountResponse.setId(hrmsPayrollLoanType.getId());

			payrollLoanTypeMaxAmountResponse.setInterestrate(hrmsPayrollLoanType.getInterestrate());
			payrollLoanTypeMaxAmountResponse.setIsinternalsource(hrmsPayrollLoanType.getIsinternalsource());
			payrollLoanTypeMaxAmountResponse.setName(hrmsPayrollLoanType.getName());
			payrollLoanTypeMaxAmountResponse.setRestrictioncode(hrmsPayrollLoanType.getRestrictioncode());

			return ResponseEntity.status(HttpStatus.OK).body(payrollLoanTypeMaxAmountResponse);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<PayrollLoanPaymentAmount> getPaymentAmount(int loantypeid, Double loanAmount, int duration) {
		if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(loantypeid, 1) && duration > 0) {
			HrmsPayrollLoanType hrmsPayrollLoanType = hrmsPayrollLoanTypeRepository.findById(loantypeid).get();

			PayrollLoanPaymentAmount payrollLoanPaymentAmount = new PayrollLoanPaymentAmount();

			payrollLoanPaymentAmount.setAbbreviation(hrmsPayrollLoanType.getAbbreviation());
			payrollLoanPaymentAmount.setActive(hrmsPayrollLoanType.getActive());
			payrollLoanPaymentAmount.setApproved(hrmsPayrollLoanType.getApproved());
			payrollLoanPaymentAmount.setDuration(duration);
			payrollLoanPaymentAmount.setId(hrmsPayrollLoanType.getId());

			payrollLoanPaymentAmount.setInterestrate(hrmsPayrollLoanType.getInterestrate());

			payrollLoanPaymentAmount.setIsinternalsource(hrmsPayrollLoanType.getIsinternalsource());
			Double loanAmountInterest = 0.00;

			Double loanAmountMonthly = 0.00;
			Double loanAmountOutstanding = 0.00;

			if (hrmsPayrollLoanType.getIsinternalsource() == 1) {

				if (hrmsPayrollLoanType.getInterestrate() > 0.00) {
					Double r = hrmsPayrollLoanType.getInterestrate();
					int n = 12;
					int t = duration;
					Double P = loanAmount;

					// A = P (1 + r/n) (nt)

					if (n > 0) {

						loanAmountOutstanding = P * (Math.pow((1 + r / n), t));

						System.out.println("factor" + (Math.pow((1 + r / n), t)));

						loanAmountInterest = loanAmountOutstanding - loanAmount;
						loanAmountMonthly = loanAmountOutstanding / duration;
					}

					payrollLoanPaymentAmount.setLoanAmountInterest(loanAmountInterest);

					payrollLoanPaymentAmount.setLoanAmountMonthly(loanAmountMonthly);

					payrollLoanPaymentAmount.setLoanAmountOutstanding(loanAmountOutstanding);

				} else {

					payrollLoanPaymentAmount.setLoanAmountInterest(loanAmountInterest);

					payrollLoanPaymentAmount.setLoanAmountMonthly(loanAmountMonthly);

					payrollLoanPaymentAmount.setLoanAmountOutstanding(loanAmountOutstanding);
				}
			} else {

				loanAmountMonthly = loanAmount / duration;

				payrollLoanPaymentAmount.setLoanAmountInterest(loanAmountInterest);

				payrollLoanPaymentAmount.setLoanAmountMonthly(loanAmountMonthly);

				payrollLoanPaymentAmount.setLoanAmountOutstanding(loanAmount);
			}

			payrollLoanPaymentAmount.setLoanAmountDebt(loanAmount);

			payrollLoanPaymentAmount.setName(hrmsPayrollLoanType.getName());
			payrollLoanPaymentAmount.setRestrictioncode(hrmsPayrollLoanType.getRestrictioncode());

			return ResponseEntity.status(HttpStatus.OK).body(payrollLoanPaymentAmount);

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollLoanTypeResponse>> getPayrollLoanTypeByLenderId(int lenderid) {
		if (hrmsPayrollLoanLenderRepository.existsById(lenderid)) {
			List<PayrollLoanTypeResponse> payrollLoanTypeResponselist = new ArrayList<>();

			List<HrmsPayrollLoanType> dms = hrmsPayrollLoanTypeRepository.findByLenderidAndActive(lenderid, 1);
			dms.forEach(dbm -> {
				PayrollLoanTypeResponse payrollLoanTypeResponse = new PayrollLoanTypeResponse();

				payrollLoanTypeResponse.setAbbreviation(dbm.getAbbreviation());
				payrollLoanTypeResponse.setActive(dbm.getActive());
				payrollLoanTypeResponse.setAmountmax(dbm.getAmountmax());
				payrollLoanTypeResponse.setAmountmin(dbm.getAmountmin());
				payrollLoanTypeResponse.setApproved(dbm.getApproved());
				payrollLoanTypeResponse.setCode(dbm.getCode());
				payrollLoanTypeResponse.setDescription(dbm.getDescription());
				payrollLoanTypeResponse.setDurationmax(dbm.getDurationmax());
				payrollLoanTypeResponse.setId(dbm.getId());
				payrollLoanTypeResponse.setInterestrate(dbm.getInterestrate());
				payrollLoanTypeResponse.setIsinternalsource(dbm.getIsinternalsource());
				if (dbm.getLenderid() != 0 && hrmsPayrollLoanLenderRepository.existsById(dbm.getLenderid())) {
					payrollLoanTypeResponse
							.setLender(hrmsPayrollLoanLenderRepository.findById(dbm.getLenderid()).get().getName());
				}
				payrollLoanTypeResponse.setLenderid(dbm.getLenderid());
				payrollLoanTypeResponse.setName(dbm.getName());
				payrollLoanTypeResponse.setRestrictioncode(dbm.getRestrictioncode());
				payrollLoanTypeResponselist.add(payrollLoanTypeResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollLoanTypeResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<OneThirdBalance> getOneThirdofBasicAfterDeduction(int empid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		OneThirdBalance OneThirdBalanceresponse = new OneThirdBalance();

		Double allowanceAmount = 0.0;

		Double amountdeductionvoluntary = 0.0;

		Double psssfemployeeAmount = 0.0;
		Double amountdeductionloan = 0.0;

		Double amountdeductionpaye = 0.0;

		Double basicSalaryAmount = 0.0;

		Double onethirdOfBasicAmount = 0.0;
		Double totaldeductions = 0.0;

		Double netpayAmount = 0.0;
		Double grossAmount = 0.0;

		if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(empid, 1)) {
			Hrmsemployeesalary hrmsemployeesalary1 = hrmsemployeesalaryRepository.findByEmployeeidAndActive(empid, 1);
			// Allowance , house allowance, transport etc
			if (hrmsemployeesalary1.getSalarystructureId() != 0

					&& (hrmsAllowanceRepository.existsBySalarystructureidAndCurrencyidAndActive(
							hrmsemployeesalary1.getSalarystructureId(), hrmsemployeesalary1.getCurrencyId(), 1))) {

				List<HrmsAllowance> hrmsAllowancelist = hrmsAllowanceRepository
						.findBySalarystructureidAndCurrencyidAndActive(hrmsemployeesalary1.getSalarystructureId(),
								hrmsemployeesalary1.getCurrencyId(), 1);
				for (HrmsAllowance allowance : hrmsAllowancelist) {

					if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().getName()
							.toLowerCase().contains("transport")) {

						allowanceAmount = allowanceAmount + allowance.getAmount();

					}

					if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().getName()
							.toLowerCase().contains("housing")) {

						allowanceAmount = allowanceAmount + allowance.getAmount();

					}

					if (hrmsAllowancetypeRepository.findById(allowance.getAllowancetypeid()).get().getName()
							.toLowerCase().contains("servant")) {

						allowanceAmount = allowanceAmount + allowance.getAmount();

					}

				}

			}

			// calculate basic pay
			if (hrmsSalarystructureRepository.existsById(hrmsemployeesalary1.getSalarystructureId())) {
				HrmsSalarystructure hrmsSalarystructure = hrmsSalarystructureRepository
						.findById(hrmsemployeesalary1.getSalarystructureId()).get();
				basicSalaryAmount = hrmsSalarystructure.getBasicSalary();
			}
			// calculate pension

			// amount_deduction_mandatory_sssf

			List<HrmsPayrollContributionMandatorySocialSecurityScheme> psssfs = hrmsPayrollContributionMandatorySocialSecuritySchemeRepository
					.findByActive(1);
			HrmsEmployeeSocialSecurityScheme hrmsEmployeeSocialSecurityScheme = hrmsEmployeeSocialSecuritySchemeRepositoty
					.findFirstByEmployeeidAndActive(empid, 1);
			int ssserviceproviderid = 0;
			if (hrmsEmployeeSocialSecuritySchemeRepositoty.existsByEmployeeidAndActive(empid, 1)
					&& hrmsEmployeeSocialSecurityScheme.getServiceproviderid() != 0) {
				ssserviceproviderid = hrmsEmployeeSocialSecurityScheme.getServiceproviderid();

				for (HrmsPayrollContributionMandatorySocialSecurityScheme pssf : psssfs) {
					if (ssserviceproviderid != 0) {

						if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(ssserviceproviderid).get()
								.getName().toLowerCase().contains("psssf")
								&& ssserviceproviderid == pssf.getServiceproviderid()) {

							psssfemployeeAmount = (pssf.getAmount() + pssf.getRateemployee() * basicSalaryAmount);

						}

						if (hrmsSocialSecuritySchemeServiceProviderRepository.findById(ssserviceproviderid).get()
								.getName().toLowerCase().contains("zss")
								&& ssserviceproviderid == pssf.getServiceproviderid()) {

							psssfemployeeAmount = (pssf.getAmount() + pssf.getRateemployee() * basicSalaryAmount);

						}
					}

				}

			}

			// loan deduction

			Integer[] loanstatus = { 0, 1 };
			if (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndStatusInAndActive(empid, loanstatus, 1)) {

				List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
						.findByEmployeeidAndStatusInAndActive(empid, loanstatus, 1);

				for (HrmsPayrollEmployeeLoan emploan : hrmsPayrollEmployeeLoanlist) {

					if (emploan.getAmountoutstanding() < emploan.getAmountprincipal()) {
						amountdeductionloan = amountdeductionloan + emploan.getAmountoutstanding();

					} else {

						amountdeductionloan = amountdeductionloan + emploan.getAmountprincipal();

					}

				}

			}

			// voluntary contribution
			if (hrmsPayrollContributionVoluntaryRepository.existsByEmployeeidAndActiveAndLocked(empid, 1, 0)) {

				List<HrmsPayrollContributionVoluntary> voluntarycontributionlist = hrmsPayrollContributionVoluntaryRepository
						.findByEmployeeidAndActiveAndLocked(empid, 1, 0);

				for (HrmsPayrollContributionVoluntary vcontribtn : voluntarycontributionlist) {
					if (vcontribtn.getJoiningdate() != null) {

						String dt1 = simpleDateFormat.format(vcontribtn.getJoiningdate());

						String[] dateParts1 = dt1.split("-");
						int day2 = Integer.parseInt(dateParts1[2]);
						int month2 = Integer.parseInt(dateParts1[1]);
						int year2 = Integer.parseInt(dateParts1[0]);

						int month = LocalDateTime.now().getMonthValue();
						int year = LocalDateTime.now().getYear();

						if ((vcontribtn.getContributionmode() == 1) && (month2 == month) && (year2 == year)) {

							amountdeductionvoluntary = amountdeductionvoluntary
									+ (vcontribtn.getAmount() + vcontribtn.getRate() * basicSalaryAmount);

						}
						if (vcontribtn.getContributionmode() != 1) {

							amountdeductionvoluntary = amountdeductionvoluntary
									+ (vcontribtn.getAmount() + vcontribtn.getRate() * basicSalaryAmount);

						}

					}

				}

			}

		}
		grossAmount = basicSalaryAmount + allowanceAmount;

		// Paye

		Double amounttaxable = (grossAmount - psssfemployeeAmount);
		List<HrmsPayrollTaxStructure> hrmsPayrollTaxStructurelist = hrmsPayrollTaxStructureRepository.findByActive(1);
		for (HrmsPayrollTaxStructure tax : hrmsPayrollTaxStructurelist) {
			if (hrmsPayrollTaxTypeRepository.findById(tax.getTaxtypeid()).get().getName().toLowerCase().contains("paye")
					&& (amounttaxable >= tax.getAmountmin() && amounttaxable <= tax.getAmountmax())) {

				amountdeductionpaye = (tax.getAmount() + (tax.getRate() * (amounttaxable - (tax.getAmountmin() - 1))));

			}

		}

		totaldeductions = amountdeductionvoluntary + psssfemployeeAmount + amountdeductionloan + amountdeductionpaye;

		netpayAmount = (grossAmount - totaldeductions);

		OneThirdBalanceresponse.setBasicSalaryAmount(basicSalaryAmount);
		onethirdOfBasicAmount = basicSalaryAmount / 3;

		OneThirdBalanceresponse.setNetpayAmount(netpayAmount);

		OneThirdBalanceresponse.setOnethirdOfBasicAmount(onethirdOfBasicAmount);

		Double possibleLoanAmount = (netpayAmount - onethirdOfBasicAmount);

		OneThirdBalanceresponse.setPossibleLoanAmount(possibleLoanAmount);

		return ResponseEntity.status(HttpStatus.OK).body(OneThirdBalanceresponse);
	}
}
