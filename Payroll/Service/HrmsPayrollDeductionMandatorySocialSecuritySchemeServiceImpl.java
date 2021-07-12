package com.Hrms.Payroll.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.DTO.PayrollDeductionMandatorySocialSecuritySchemeResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatorySocialSecurityScheme;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeMandatoryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository;

@Service
public class HrmsPayrollDeductionMandatorySocialSecuritySchemeServiceImpl
		implements HrmsPayrollDeductionMandatorySocialSecuritySchemeService {

	@Autowired
	private HrmsPayrollDeductionMandatorySocialSecuritySchemeRepository hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsPayrollContributionTypeMandatoryRepository hrmsPayrollContributionTypeMandatoryRepository;

	@Override
	public ResponseEntity<PayrollDeductionMandatorySocialSecuritySchemeResponse> getDeductionMandatorySocialSecuritySchemeById(
			int id) {
		if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.existsByIdAndActive(id, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsPayrollDeductionMandatorySocialSecurityScheme dbm = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.findById(id).get();
			PayrollDeductionMandatorySocialSecuritySchemeResponse PayrollDeductionMandatorySocialSecuritySchemeResponse = new PayrollDeductionMandatorySocialSecuritySchemeResponse();

			PayrollDeductionMandatorySocialSecuritySchemeResponse.setActive(dbm.getActive());
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setAmount(dbm.getAmount());
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployee(dbm.getAmountemployee());
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployer(dbm.getAmountemployer());
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				PayrollDeductionMandatorySocialSecuritySchemeResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				PayrollDeductionMandatorySocialSecuritySchemeResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			PayrollDeductionMandatorySocialSecuritySchemeResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				PayrollDeductionMandatorySocialSecuritySchemeResponse
						.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				fullName.append("  " + hrmsEmployee.getMiddleName().trim());
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				PayrollDeductionMandatorySocialSecuritySchemeResponse.setFullName(fullName.toString());
			}

			PayrollDeductionMandatorySocialSecuritySchemeResponse.setId(dbm.getId());
			PayrollDeductionMandatorySocialSecuritySchemeResponse.setPayrollid(dbm.getPayrollid());

			return ResponseEntity.status(HttpStatus.OK).body(PayrollDeductionMandatorySocialSecuritySchemeResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<PayrollDeductionMandatorySocialSecuritySchemeResponse> getDeductionMandatorySocialSecuritySchemeByPayrollId(
			int payrollId) {
		if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.existsByPayrollidAndActive(payrollId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsPayrollDeductionMandatorySocialSecurityScheme dbm = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.findByPayrollid(payrollId);
			PayrollDeductionMandatorySocialSecuritySchemeResponse payrollDeductionMandatorySocialSecuritySchemeResponse = new PayrollDeductionMandatorySocialSecuritySchemeResponse();

			payrollDeductionMandatorySocialSecuritySchemeResponse.setActive(dbm.getActive());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setAmount(dbm.getAmount());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployee(dbm.getAmountemployee());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployer(dbm.getAmountemployer());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionMandatorySocialSecuritySchemeResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionMandatorySocialSecuritySchemeResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionMandatorySocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setFullName(fullName.toString());
			}

			payrollDeductionMandatorySocialSecuritySchemeResponse.setId(dbm.getId());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setPayrollid(dbm.getPayrollid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatorySocialSecuritySchemeResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionMandatorySocialSecuritySchemeResponse>> getDeductionMandatorySocialSecuritySchemeByEmpId(
			int empId) {
		if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.existsByEmployeeidAndActive(empId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			List<PayrollDeductionMandatorySocialSecuritySchemeResponse> payrollDeductionMandatorySocialSecuritySchemeResponselist = new ArrayList<>();

			List<HrmsPayrollDeductionMandatorySocialSecurityScheme> dbms = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.findByEmployeeidAndActiveOrderByIdDesc(empId, 1);
			PayrollDeductionMandatorySocialSecuritySchemeResponse payrollDeductionMandatorySocialSecuritySchemeResponse = new PayrollDeductionMandatorySocialSecuritySchemeResponse();

			dbms.forEach(dbm -> {
				payrollDeductionMandatorySocialSecuritySchemeResponse.setActive(dbm.getActive());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setAmount(dbm.getAmount());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployee(dbm.getAmountemployee());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployer(dbm.getAmountemployer());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setApproved(dbm.getApproved());
				if (dbm.getContributiontypeid() != 0
						&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
					payrollDeductionMandatorySocialSecuritySchemeResponse
							.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
									.findById(dbm.getContributiontypeid()).get().getName());
				}
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setContributiontypeid(dbm.getContributiontypeid());

				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					payrollDeductionMandatorySocialSecuritySchemeResponse
							.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}

				payrollDeductionMandatorySocialSecuritySchemeResponse.setCurrencyid(dbm.getCurrencyid());
				if (dbm.getDatededucted() != null) {
					payrollDeductionMandatorySocialSecuritySchemeResponse
							.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
				}
				payrollDeductionMandatorySocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

				if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder fullName = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					fullName.append(" " + hrmsEmployee.getLastName().trim());
					payrollDeductionMandatorySocialSecuritySchemeResponse.setFullName(fullName.toString());
				}

				payrollDeductionMandatorySocialSecuritySchemeResponse.setId(dbm.getId());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setPayrollid(dbm.getPayrollid());
				payrollDeductionMandatorySocialSecuritySchemeResponselist
						.add(payrollDeductionMandatorySocialSecuritySchemeResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatorySocialSecuritySchemeResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollDeductionMandatorySocialSecurityScheme> updateDeductionMandatorySocialSecurityScheme(
			HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme,
			int id) {

		if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme1 = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.findById(id).get();
			if (hrmsPayrollDeductionMandatorySocialSecurityScheme1
					.getContributiontypeid() == hrmsPayrollDeductionMandatorySocialSecurityScheme
							.getContributiontypeid()
					&& hrmsPayrollDeductionMandatorySocialSecurityScheme1
							.getPayrollid() == hrmsPayrollDeductionMandatorySocialSecurityScheme.getPayrollid()
					&& hrmsPayrollDeductionMandatorySocialSecurityScheme1
							.getEmployeeid() == hrmsPayrollDeductionMandatorySocialSecurityScheme.getEmployeeid()) {
				hrmsPayrollDeductionMandatorySocialSecurityScheme.setActive(1);
				hrmsPayrollDeductionMandatorySocialSecurityScheme.setApproved(0);
				hrmsPayrollDeductionMandatorySocialSecurityScheme
						.setDatededucted(hrmsPayrollDeductionMandatorySocialSecurityScheme1.getDatededucted());
				hrmsPayrollDeductionMandatorySocialSecurityScheme.setDate_updated(LocalDateTime.now());
				if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.findById(id).get()
						.getDate_created() != null) {
					hrmsPayrollDeductionMandatorySocialSecurityScheme
							.setDate_created(hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.findById(id)
									.get().getDate_created());
				}

				if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.findById(id).get()
						.getUnique_id() != null) {
					hrmsPayrollDeductionMandatorySocialSecurityScheme
							.setUnique_id(hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.findById(id).get()
									.getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
								.saveAndFlush(hrmsPayrollDeductionMandatorySocialSecurityScheme));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY)
						.body(hrmsPayrollDeductionMandatorySocialSecurityScheme);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteDeductionMandatorySocialSecurityScheme(int id) {
		if (hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollDeductionMandatorySocialSecurityScheme hrmsPayrollDeductionMandatorySocialSecurityScheme = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollDeductionMandatorySocialSecurityScheme.setActive(0);
			hrmsPayrollDeductionMandatorySocialSecurityScheme.setApproved(0);
			hrmsPayrollDeductionMandatorySocialSecurityScheme.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
					.saveAndFlush(hrmsPayrollDeductionMandatorySocialSecurityScheme));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionMandatorySocialSecuritySchemeResponse>> getAllDeductionMandatorySocialSecurityScheme() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PayrollDeductionMandatorySocialSecuritySchemeResponse> payrollDeductionMandatorySocialSecuritySchemeResponselist = new ArrayList<>();

		List<HrmsPayrollDeductionMandatorySocialSecurityScheme> dbms = hrmsPayrollDeductionMandatorySocialSecuritySchemeRepository
				.findByActiveOrderByIdDesc(1);
		PayrollDeductionMandatorySocialSecuritySchemeResponse payrollDeductionMandatorySocialSecuritySchemeResponse = new PayrollDeductionMandatorySocialSecuritySchemeResponse();

		dbms.forEach(dbm -> {
			payrollDeductionMandatorySocialSecuritySchemeResponse.setActive(dbm.getActive());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setAmount(dbm.getAmount());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployee(dbm.getAmountemployee());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setAmountemployer(dbm.getAmountemployer());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionMandatorySocialSecuritySchemeResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionMandatorySocialSecuritySchemeResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionMandatorySocialSecuritySchemeResponse
						.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionMandatorySocialSecuritySchemeResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionMandatorySocialSecuritySchemeResponse.setFullName(fullName.toString());
			}

			payrollDeductionMandatorySocialSecuritySchemeResponse.setId(dbm.getId());
			payrollDeductionMandatorySocialSecuritySchemeResponse.setPayrollid(dbm.getPayrollid());
			payrollDeductionMandatorySocialSecuritySchemeResponselist
					.add(payrollDeductionMandatorySocialSecuritySchemeResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatorySocialSecuritySchemeResponselist);
	}

}
