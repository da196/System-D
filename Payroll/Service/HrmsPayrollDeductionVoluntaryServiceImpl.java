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
import com.Hrms.Payroll.DTO.PayrollDeductionVoluntaryResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntary;
import com.Hrms.Payroll.Repository.HrmsPayrollContributionTypeVoluntaryRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollDeductionVoluntaryRepository;

@Service
public class HrmsPayrollDeductionVoluntaryServiceImpl implements HrmsPayrollDeductionVoluntaryService {

	@Autowired
	private HrmsPayrollDeductionVoluntaryRepository hrmsPayrollDeductionVoluntaryRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Autowired
	private HrmsPayrollContributionTypeVoluntaryRepository hrmsPayrollContributionTypeVoluntaryRepository;

	@Override
	public ResponseEntity<PayrollDeductionVoluntaryResponse> getDeductionVoluntaryById(int id) {
		if (hrmsPayrollDeductionVoluntaryRepository.existsByIdAndActive(id, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			HrmsPayrollDeductionVoluntary dbm = hrmsPayrollDeductionVoluntaryRepository.findById(id).get();
			PayrollDeductionVoluntaryResponse payrollDeductionVoluntaryResponse = new PayrollDeductionVoluntaryResponse();

			payrollDeductionVoluntaryResponse.setActive(dbm.getActive());
			payrollDeductionVoluntaryResponse.setAmount(dbm.getAmount());
			payrollDeductionVoluntaryResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionVoluntaryResponse.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
						.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionVoluntaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionVoluntaryResponse.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				fullName.append("  " + hrmsEmployee.getMiddleName().trim());
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionVoluntaryResponse.setFullName(fullName.toString());
			}

			payrollDeductionVoluntaryResponse.setId(dbm.getId());
			payrollDeductionVoluntaryResponse.setPayrollid(dbm.getPayrollid());

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionVoluntaryResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getDeductionVoluntaryByPayrollId(int payrollId) {
		if (hrmsPayrollDeductionVoluntaryRepository.existsByPayrollidAndActive(payrollId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			List<PayrollDeductionVoluntaryResponse> payrollDeductionVoluntaryResponselist = new ArrayList<>();

			List<HrmsPayrollDeductionVoluntary> dbms = hrmsPayrollDeductionVoluntaryRepository
					.findByPayrollidOrderByIdDesc(payrollId);

			dbms.forEach(dbm -> {
				PayrollDeductionVoluntaryResponse payrollDeductionVoluntaryResponse = new PayrollDeductionVoluntaryResponse();

				payrollDeductionVoluntaryResponse.setActive(dbm.getActive());
				payrollDeductionVoluntaryResponse.setAmount(dbm.getAmount());

				payrollDeductionVoluntaryResponse.setApproved(dbm.getApproved());
				if (dbm.getContributiontypeid() != 0
						&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
					payrollDeductionVoluntaryResponse.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
							.findById(dbm.getContributiontypeid()).get().getName());
				}
				payrollDeductionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());

				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					payrollDeductionVoluntaryResponse
							.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}

				payrollDeductionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());
				if (dbm.getDatededucted() != null) {
					payrollDeductionVoluntaryResponse.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
				}
				payrollDeductionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

				if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder fullName = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					fullName.append(" " + hrmsEmployee.getLastName().trim());
					payrollDeductionVoluntaryResponse.setFullName(fullName.toString());
				}

				payrollDeductionVoluntaryResponse.setId(dbm.getId());
				payrollDeductionVoluntaryResponse.setPayrollid(dbm.getPayrollid());
				payrollDeductionVoluntaryResponselist.add(payrollDeductionVoluntaryResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionVoluntaryResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getDeductionVoluntaryByEmpId(int empId) {
		if (hrmsPayrollDeductionVoluntaryRepository.existsByEmployeeidAndActive(empId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			List<PayrollDeductionVoluntaryResponse> payrollDeductionVoluntaryResponselist = new ArrayList<>();

			List<HrmsPayrollDeductionVoluntary> dbms = hrmsPayrollDeductionVoluntaryRepository
					.findByEmployeeidAndActiveOrderByIdDesc(empId, 1);
			PayrollDeductionVoluntaryResponse payrollDeductionVoluntaryResponse = new PayrollDeductionVoluntaryResponse();

			dbms.forEach(dbm -> {
				payrollDeductionVoluntaryResponse.setActive(dbm.getActive());
				payrollDeductionVoluntaryResponse.setAmount(dbm.getAmount());

				payrollDeductionVoluntaryResponse.setApproved(dbm.getApproved());
				if (dbm.getContributiontypeid() != 0
						&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
					payrollDeductionVoluntaryResponse.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
							.findById(dbm.getContributiontypeid()).get().getName());
				}
				payrollDeductionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());

				if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
					payrollDeductionVoluntaryResponse
							.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
				}

				payrollDeductionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());
				if (dbm.getDatededucted() != null) {
					payrollDeductionVoluntaryResponse.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
				}
				payrollDeductionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

				if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
					StringBuilder fullName = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

					fullName.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						fullName.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					fullName.append(" " + hrmsEmployee.getLastName().trim());
					payrollDeductionVoluntaryResponse.setFullName(fullName.toString());
				}

				payrollDeductionVoluntaryResponse.setId(dbm.getId());
				payrollDeductionVoluntaryResponse.setPayrollid(dbm.getPayrollid());
				payrollDeductionVoluntaryResponselist.add(payrollDeductionVoluntaryResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionVoluntaryResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollDeductionVoluntary> updatePayrollDeductionVoluntary(
			HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary, int id) {
		if (hrmsPayrollDeductionVoluntaryRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary1 = hrmsPayrollDeductionVoluntaryRepository
					.findById(id).get();
			if (hrmsPayrollDeductionVoluntary1.getContributiontypeid() == hrmsPayrollDeductionVoluntary
					.getContributiontypeid()
					&& hrmsPayrollDeductionVoluntary1.getPayrollid() == hrmsPayrollDeductionVoluntary.getPayrollid()
					&& hrmsPayrollDeductionVoluntary1.getEmployeeid() == hrmsPayrollDeductionVoluntary
							.getEmployeeid()) {
				hrmsPayrollDeductionVoluntary.setActive(1);
				hrmsPayrollDeductionVoluntary.setApproved(0);
				hrmsPayrollDeductionVoluntary.setDatededucted(hrmsPayrollDeductionVoluntary1.getDatededucted());
				hrmsPayrollDeductionVoluntary.setDate_updated(LocalDateTime.now());
				if (hrmsPayrollDeductionVoluntaryRepository.findById(id).get().getDate_created() != null) {
					hrmsPayrollDeductionVoluntary.setDate_created(
							hrmsPayrollDeductionVoluntaryRepository.findById(id).get().getDate_created());
				}

				if (hrmsPayrollDeductionVoluntaryRepository.findById(id).get().getUnique_id() != null) {
					hrmsPayrollDeductionVoluntary
							.setUnique_id(hrmsPayrollDeductionVoluntaryRepository.findById(id).get().getUnique_id());
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollDeductionVoluntaryRepository.saveAndFlush(hrmsPayrollDeductionVoluntary));
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsPayrollDeductionVoluntary);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteDeductionVoluntary(int id) {
		if (hrmsPayrollDeductionVoluntaryRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollDeductionVoluntary hrmsPayrollDeductionVoluntary = hrmsPayrollDeductionVoluntaryRepository
					.findByIdAndActive(id, 1);
			hrmsPayrollDeductionVoluntary.setActive(0);
			hrmsPayrollDeductionVoluntary.setApproved(0);
			hrmsPayrollDeductionVoluntary.setDate_updated(LocalDateTime.now());

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollDeductionVoluntaryRepository.saveAndFlush(hrmsPayrollDeductionVoluntary));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<PayrollDeductionVoluntaryResponse>> getAllDeductionVoluntary() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<PayrollDeductionVoluntaryResponse> payrollDeductionVoluntaryResponselist = new ArrayList<>();

		List<HrmsPayrollDeductionVoluntary> dbms = hrmsPayrollDeductionVoluntaryRepository.findByActiveOrderByIdDesc(1);

		PayrollDeductionVoluntaryResponse payrollDeductionVoluntaryResponse = new PayrollDeductionVoluntaryResponse();

		dbms.forEach(dbm -> {
			payrollDeductionVoluntaryResponse.setActive(dbm.getActive());
			payrollDeductionVoluntaryResponse.setAmount(dbm.getAmount());

			payrollDeductionVoluntaryResponse.setApproved(dbm.getApproved());
			if (dbm.getContributiontypeid() != 0
					&& hrmsPayrollContributionTypeVoluntaryRepository.existsById(dbm.getContributiontypeid())) {
				payrollDeductionVoluntaryResponse.setContributiontype(hrmsPayrollContributionTypeVoluntaryRepository
						.findById(dbm.getContributiontypeid()).get().getName());
			}
			payrollDeductionVoluntaryResponse.setContributiontypeid(dbm.getContributiontypeid());

			if (dbm.getCurrencyid() != 0 && hrmsCurrencyRepository.existsById(dbm.getCurrencyid())) {
				payrollDeductionVoluntaryResponse
						.setCurrency(hrmsCurrencyRepository.findById(dbm.getCurrencyid()).get().getName());
			}

			payrollDeductionVoluntaryResponse.setCurrencyid(dbm.getCurrencyid());
			if (dbm.getDatededucted() != null) {
				payrollDeductionVoluntaryResponse.setDatededucted(simpleDateFormat.format(dbm.getDatededucted()));
			}
			payrollDeductionVoluntaryResponse.setEmployeeid(dbm.getEmployeeid());

			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {
				StringBuilder fullName = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();

				fullName.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					fullName.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				fullName.append(" " + hrmsEmployee.getLastName().trim());
				payrollDeductionVoluntaryResponse.setFullName(fullName.toString());
			}

			payrollDeductionVoluntaryResponse.setId(dbm.getId());
			payrollDeductionVoluntaryResponse.setPayrollid(dbm.getPayrollid());
			payrollDeductionVoluntaryResponselist.add(payrollDeductionVoluntaryResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(payrollDeductionVoluntaryResponselist);
	}

}
