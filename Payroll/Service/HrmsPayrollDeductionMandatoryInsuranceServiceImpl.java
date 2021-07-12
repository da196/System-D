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
import com.Hrms.Payroll.DTO.PayrollDeductionMandatoryInsuranceResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryInsurance;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeMandatoryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionMandatoryInsuranceRepository;

@Service
public class HrmsPayrollDeductionMandatoryInsuranceServiceImpl
		implements HrmsPayrollDeductionMandatoryInsuranceService {

	@Autowired
	private HrmsPayrollDeductionMandatoryInsuranceRepository hrmsPayrollDeductionMandatoryInsuranceRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsPayrollContributionTypeMandatoryRepository hrmsPayrollContributionTypeMandatoryRepository;

	@Override
	public ResponseEntity<PayrollDeductionMandatoryInsuranceResponse> getDeductionMandatoryInsuranceById(int id) {
		if (hrmsPayrollDeductionMandatoryInsuranceRepository.existsByIdAndActive(id, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsPayrollDeductionMandatoryInsurance dbm = hrmsPayrollDeductionMandatoryInsuranceRepository.findById(id)
					.get();
			PayrollDeductionMandatoryInsuranceResponse payrollDeductionMandatoryInsuranceResponse = new PayrollDeductionMandatoryInsuranceResponse();

			payrollDeductionMandatoryInsuranceResponse.setActive(dbm.getActive());
			payrollDeductionMandatoryInsuranceResponse.setAmount(dbm.getAmount());
			payrollDeductionMandatoryInsuranceResponse.setAmountemployee(dbm.getAmountemployee());
			payrollDeductionMandatoryInsuranceResponse.setAmountemployer(dbm.getAmountemployer());
			payrollDeductionMandatoryInsuranceResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionMandatoryInsuranceResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionMandatoryInsuranceResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionMandatoryInsuranceResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionMandatoryInsuranceResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionMandatoryInsuranceResponse
						.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionMandatoryInsuranceResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionMandatoryInsuranceResponse.setFullName(fullName.toString());
			}

			payrollDeductionMandatoryInsuranceResponse.setId(dbm.getId());
			payrollDeductionMandatoryInsuranceResponse.setPayrollid(dbm.getPayrollid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatoryInsuranceResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<PayrollDeductionMandatoryInsuranceResponse> getDeductionMandatoryInsuranceByPayrollId(
			int payrollId) {
		if (hrmsPayrollDeductionMandatoryInsuranceRepository.existsByPayrollidAndActive(payrollId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsPayrollDeductionMandatoryInsurance dbm = hrmsPayrollDeductionMandatoryInsuranceRepository
					.findByPayrollid(payrollId);
			PayrollDeductionMandatoryInsuranceResponse payrollDeductionMandatoryInsuranceResponse = new PayrollDeductionMandatoryInsuranceResponse();

			payrollDeductionMandatoryInsuranceResponse.setActive(dbm.getActive());
			payrollDeductionMandatoryInsuranceResponse.setAmount(dbm.getAmount());
			payrollDeductionMandatoryInsuranceResponse.setAmountemployee(dbm.getAmountemployee());
			payrollDeductionMandatoryInsuranceResponse.setAmountemployer(dbm.getAmountemployer());
			payrollDeductionMandatoryInsuranceResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionMandatoryInsuranceResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionMandatoryInsuranceResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionMandatoryInsuranceResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionMandatoryInsuranceResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionMandatoryInsuranceResponse
						.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionMandatoryInsuranceResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionMandatoryInsuranceResponse.setFullName(fullName.toString());
			}

			payrollDeductionMandatoryInsuranceResponse.setId(dbm.getId());
			payrollDeductionMandatoryInsuranceResponse.setPayrollid(dbm.getPayrollid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatoryInsuranceResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionMandatoryInsuranceResponse>> getDeductionMandatoryInsuranceByEmpId(
			int empId) {
		if (hrmsPayrollDeductionMandatoryInsuranceRepository.existsByEmployeeidAndActive(empId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			List<PayrollDeductionMandatoryInsuranceResponse> payrollDeductionMandatoryInsuranceResponselist = new ArrayList<>();

			List<HrmsPayrollDeductionMandatoryInsurance> dbms = hrmsPayrollDeductionMandatoryInsuranceRepository
					.findByEmployeeidAndActiveOrderByIdDesc(empId, 1);
			PayrollDeductionMandatoryInsuranceResponse payrollDeductionMandatoryInsuranceResponse = new PayrollDeductionMandatoryInsuranceResponse();

			dbms.forEach(dbm -> {
				payrollDeductionMandatoryInsuranceResponse.setActive(dbm.getActive());
				payrollDeductionMandatoryInsuranceResponse.setAmount(dbm.getAmount());
				payrollDeductionMandatoryInsuranceResponse.setAmountemployee(dbm.getAmountemployee());
				payrollDeductionMandatoryInsuranceResponse.setAmountemployer(dbm.getAmountemployer());
				payrollDeductionMandatoryInsuranceResponse.setApproved(dbm.getApproved());
				if (dbm.getContributiontypeid() != 0
						&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
					payrollDeductionMandatoryInsuranceResponse
							.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
									.findById(dbm.getContributiontypeid()).get().getName());
				}
				payrollDeductionMandatoryInsuranceResponse.setContributiontypeid(dbm.getContributiontypeid());

				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					payrollDeductionMandatoryInsuranceResponse
							.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}

				payrollDeductionMandatoryInsuranceResponse.setCurrencyid(dbm.getCurrencyid());
				if (dbm.getDatededucted() != null) {
					payrollDeductionMandatoryInsuranceResponse
							.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
				}
				payrollDeductionMandatoryInsuranceResponse.setEmployeeid(dbm.getEmployeeid());

				if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder fullName = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					fullName.append(" " + hrmsEmployee.getLastName().trim());
					payrollDeductionMandatoryInsuranceResponse.setFullName(fullName.toString());
				}

				payrollDeductionMandatoryInsuranceResponse.setId(dbm.getId());
				payrollDeductionMandatoryInsuranceResponse.setPayrollid(dbm.getPayrollid());
				payrollDeductionMandatoryInsuranceResponselist.add(payrollDeductionMandatoryInsuranceResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatoryInsuranceResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollDeductionMandatoryInsurance> updateDeductionMandatoryInsurance(
			HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance, int id) {

		if (hrmsPayrollDeductionMandatoryInsuranceRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance1 = hrmsPayrollDeductionMandatoryInsuranceRepository
					.findById(id).get();
			if (hrmsPayrollDeductionMandatoryInsurance1
					.getContributiontypeid() == hrmsPayrollDeductionMandatoryInsurance.getContributiontypeid()
					&& hrmsPayrollDeductionMandatoryInsurance1.getPayrollid() == hrmsPayrollDeductionMandatoryInsurance
							.getPayrollid()
					&& hrmsPayrollDeductionMandatoryInsurance1.getEmployeeid() == hrmsPayrollDeductionMandatoryInsurance
							.getEmployeeid()) {
				hrmsPayrollDeductionMandatoryInsurance.setActive(1);
				hrmsPayrollDeductionMandatoryInsurance.setApproved(0);
				hrmsPayrollDeductionMandatoryInsurance
						.setDatededucted(hrmsPayrollDeductionMandatoryInsurance1.getDatededucted());
				hrmsPayrollDeductionMandatoryInsurance.setDate_updated(LocalDateTime.now());
				if (hrmsPayrollDeductionMandatoryInsuranceRepository.findById(id).get().getDate_created() != null) {
					hrmsPayrollDeductionMandatoryInsurance.setDate_created(
							hrmsPayrollDeductionMandatoryInsuranceRepository.findById(id).get().getDate_created());
				}

				if (hrmsPayrollDeductionMandatoryInsuranceRepository.findById(id).get().getUnique_id() != null) {
					hrmsPayrollDeductionMandatoryInsurance.setUnique_id(
							hrmsPayrollDeductionMandatoryInsuranceRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollDeductionMandatoryInsuranceRepository
						.saveAndFlush(hrmsPayrollDeductionMandatoryInsurance));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollDeductionMandatoryInsurance);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteDeductionMandatoryInsurance(int id) {
		if (hrmsPayrollDeductionMandatoryInsuranceRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollDeductionMandatoryInsurance hrmsPayrollDeductionMandatoryInsurance = hrmsPayrollDeductionMandatoryInsuranceRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollDeductionMandatoryInsurance.setActive(0);
			hrmsPayrollDeductionMandatoryInsurance.setApproved(0);
			hrmsPayrollDeductionMandatoryInsurance.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK).body(hrmsPayrollDeductionMandatoryInsuranceRepository
					.saveAndFlush(hrmsPayrollDeductionMandatoryInsurance));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionMandatoryInsuranceResponse>> getAllDeductionMandatoryInsurance() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PayrollDeductionMandatoryInsuranceResponse> payrollDeductionMandatoryInsuranceResponselist = new ArrayList<>();

		List<HrmsPayrollDeductionMandatoryInsurance> dbms = hrmsPayrollDeductionMandatoryInsuranceRepository
				.findByActiveOrderByIdDesc(1);
		PayrollDeductionMandatoryInsuranceResponse payrollDeductionMandatoryInsuranceResponse = new PayrollDeductionMandatoryInsuranceResponse();

		dbms.forEach(dbm -> {
			payrollDeductionMandatoryInsuranceResponse.setActive(dbm.getActive());
			payrollDeductionMandatoryInsuranceResponse.setAmount(dbm.getAmount());
			payrollDeductionMandatoryInsuranceResponse.setAmountemployee(dbm.getAmountemployee());
			payrollDeductionMandatoryInsuranceResponse.setAmountemployer(dbm.getAmountemployer());
			payrollDeductionMandatoryInsuranceResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeMandatoryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionMandatoryInsuranceResponse
						.setContributiontype(hrmsPayrollContributionTypeMandatoryRepository
								.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionMandatoryInsuranceResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionMandatoryInsuranceResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionMandatoryInsuranceResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionMandatoryInsuranceResponse
						.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionMandatoryInsuranceResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionMandatoryInsuranceResponse.setFullName(fullName.toString());
			}

			payrollDeductionMandatoryInsuranceResponse.setId(dbm.getId());
			payrollDeductionMandatoryInsuranceResponse.setPayrollid(dbm.getPayrollid());
			payrollDeductionMandatoryInsuranceResponselist.add(payrollDeductionMandatoryInsuranceResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionMandatoryInsuranceResponselist);
	}

}
