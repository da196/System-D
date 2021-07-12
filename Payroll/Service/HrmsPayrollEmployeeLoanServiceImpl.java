package com.Hrms.Payroll.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Payroll.DTO.EmployeeLoanResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoan;
import com.Hrms.Payroll.Entity.HrmsPayrollLoanType;
import com.Hrms.Payroll.Repository.HrmsPayrollEmployeeLoanRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanFrequencyRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanLenderRepository;
import com.Hrms.Payroll.Repository.HrmsPayrollLoanTypeRepository;

@Service
public class HrmsPayrollEmployeeLoanServiceImpl implements HrmsPayrollEmployeeLoanService {

	@Autowired
	private HrmsPayrollEmployeeLoanRepository hrmsPayrollEmployeeLoanRepository;

	@Autowired
	private HrmsPayrollLoanTypeService hrmsPayrollLoanTypeService;

	@Autowired
	private HrmsPayrollLoanTypeRepository hrmsPayrollLoanTypeRepository;

	@Autowired
	private HrmsPayrollLoanLenderRepository hrmsPayrollLoanLenderRepository;

	@Autowired
	private HrmsPayrollLoanFrequencyRepository hrmsPayrollLoanFrequencyRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Override
	public ResponseEntity<HrmsPayrollEmployeeLoan> addPayrollEmployeeLoan(
			HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan) {
		if (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndLenderidAndLoantypeidAndActive(
				hrmsPayrollEmployeeLoan.getEmployeeid(), hrmsPayrollEmployeeLoan.getLenderid(),
				hrmsPayrollEmployeeLoan.getLoantypeid(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsPayrollEmployeeLoan);
		} else {

			if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoantypeid(), 1)
					&& hrmsPayrollLoanTypeRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoantypeid(), 1)
					&& hrmsPayrollLoanLenderRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLenderid(), 1)
					&& hrmsPayrollLoanFrequencyRepository
							.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoanfrequencyid(), 1)
					&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())
					&& hrmsCurrencyRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getCurrencyid(), 1)

					&& (hrmsPayrollLoanTypeService
							.getOneThirdofBasicAfterDeduction(hrmsPayrollEmployeeLoan.getEmployeeid()).getBody()
							.getPossibleLoanAmount() >= hrmsPayrollEmployeeLoan.getAmountprincipal()
							|| hrmsPayrollEmployeeLoan.getLoantypeid() == 2)) {
				hrmsPayrollEmployeeLoan.setActive(1);

				int year = LocalDateTime.now().getYear();
				if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoantypeid(), 1)) {
					HrmsPayrollLoanType dbm = hrmsPayrollLoanTypeRepository
							.findByIdAndActive(hrmsPayrollEmployeeLoan.getLoantypeid(), 1);
					hrmsPayrollEmployeeLoan.setRate(dbm.getRate());

				}

				int month = LocalDateTime.now().getMonthValue();
				hrmsPayrollEmployeeLoan.setYear(year);
				hrmsPayrollEmployeeLoan.setMonth(month);
				hrmsPayrollEmployeeLoan.setUnique_id(UUID.randomUUID());
				if (hrmsPayrollEmployeeLoan.getAmountoutstanding() == 0.00) {
					hrmsPayrollEmployeeLoan.setAmountoutstanding(hrmsPayrollEmployeeLoan.getAmountdebt());
					hrmsPayrollEmployeeLoan.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountdebt());
				} else {
					hrmsPayrollEmployeeLoan.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountoutstanding());
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != null) {

					// Convert Date to Calendar
					Calendar c = Calendar.getInstance();

					c.setTime(hrmsPayrollEmployeeLoan.getDaterepaymentstart());

					// Perform addition/subtraction
					c.add(Calendar.MONTH, (int) Math.round(hrmsPayrollEmployeeLoan.getDuration()));

					// Convert calendar back to Date
					Date daterepaymentend = c.getTime();

					hrmsPayrollEmployeeLoan.setDaterepaymentend(daterepaymentend);
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollEmployeeLoanRepository.saveAndFlush(hrmsPayrollEmployeeLoan));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollEmployeeLoan);
			}

		}
	}

	@Override
	public ResponseEntity<EmployeeLoanResponse> getPayrollEmployeeLoanById(int id) {
		if (hrmsPayrollEmployeeLoanRepository.existsByIdAndActive(id, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			EmployeeLoanResponse employeeLoanResponse = new EmployeeLoanResponse();

			HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan = hrmsPayrollEmployeeLoanRepository.findByIdAndActive(id,
					1);

			employeeLoanResponse.setActive(hrmsPayrollEmployeeLoan.getActive());
			employeeLoanResponse.setAmountdebt(hrmsPayrollEmployeeLoan.getAmountdebt());
			employeeLoanResponse.setAmountprincipal(hrmsPayrollEmployeeLoan.getAmountprincipal());
			employeeLoanResponse.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountloanbalance());

			employeeLoanResponse.setAmountoutstanding(hrmsPayrollEmployeeLoan.getAmountoutstanding());
			Double amountpaid = hrmsPayrollEmployeeLoan.getAmountoutstanding()
					- hrmsPayrollEmployeeLoan.getAmountloanbalance();

			employeeLoanResponse.setAmountpaid(amountpaid);

			employeeLoanResponse.setApproved(hrmsPayrollEmployeeLoan.getApproved());
			employeeLoanResponse.setStatus(hrmsPayrollEmployeeLoan.getStatus());
			if (hrmsPayrollEmployeeLoan.getCurrencyid() != 0
					&& hrmsCurrencyRepository.existsById(hrmsPayrollEmployeeLoan.getCurrencyid())) {
				employeeLoanResponse.setCurrency(
						hrmsCurrencyRepository.findById(hrmsPayrollEmployeeLoan.getCurrencyid()).get().getName());
			}
			employeeLoanResponse.setCurrencyid(hrmsPayrollEmployeeLoan.getCurrencyid());
			if (hrmsPayrollEmployeeLoan.getDateissued() != null) {
				employeeLoanResponse.setDateissued(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDateissued()));
			}

			if (hrmsPayrollEmployeeLoan.getDaterepaymentend() != null) {
				employeeLoanResponse
						.setDaterepaymentend(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentend()));
			}

			if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != null) {
				employeeLoanResponse.setDaterepaymentstart(
						simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentstart()));
			}
			employeeLoanResponse.setDescription(hrmsPayrollEmployeeLoan.getDescription());
			employeeLoanResponse.setDuration(hrmsPayrollEmployeeLoan.getDuration());

			if (hrmsPayrollEmployeeLoan.getEmployeeid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())) {
				StringBuilder employeeFullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollEmployeeLoan.getEmployeeid())
						.get();

				employeeFullname.append(hrmsEmployee.getFirstName().trim());
				employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
				employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

				employeeLoanResponse.setEmployeeFullname(employeeFullname.toString());
			}

			employeeLoanResponse.setEmployeeid(hrmsPayrollEmployeeLoan.getEmployeeid());

			employeeLoanResponse.setId(id);

			employeeLoanResponse.setInterestrate(hrmsPayrollEmployeeLoan.getInterestrate());
			if (hrmsPayrollEmployeeLoan.getLenderid() != 0
					&& hrmsPayrollLoanLenderRepository.existsById(hrmsPayrollEmployeeLoan.getLenderid())) {
				employeeLoanResponse.setLender(hrmsPayrollLoanLenderRepository
						.findById(hrmsPayrollEmployeeLoan.getLenderid()).get().getName());
			}
			employeeLoanResponse.setLenderid(hrmsPayrollEmployeeLoan.getLenderid());
			if (hrmsPayrollEmployeeLoan.getLoanfrequencyid() != 0
					&& hrmsPayrollLoanFrequencyRepository.existsById(hrmsPayrollEmployeeLoan.getLoanfrequencyid())) {
				employeeLoanResponse.setLoanfrequency(hrmsPayrollLoanFrequencyRepository
						.findById(hrmsPayrollEmployeeLoan.getLoanfrequencyid()).get().getName());
			}

			employeeLoanResponse.setLoanfrequencyid(hrmsPayrollEmployeeLoan.getLoanfrequencyid());

			if (hrmsPayrollEmployeeLoan.getLoantypeid() != 0
					&& hrmsPayrollLoanTypeRepository.existsById(hrmsPayrollEmployeeLoan.getLoantypeid())) {
				employeeLoanResponse.setLoantype(hrmsPayrollLoanTypeRepository
						.findById(hrmsPayrollEmployeeLoan.getLoantypeid()).get().getName());
			}
			employeeLoanResponse.setLoantypeid(hrmsPayrollEmployeeLoan.getLoantypeid());

			return ResponseEntity.status(HttpStatus.OK).body(employeeLoanResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsPayrollEmployeeLoan> updatePayrollEmployeeLoan(
			HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan, int id, Double durationAdjust) {
		if (hrmsPayrollEmployeeLoanRepository.existsById(id)) {

			if (hrmsPayrollLoanTypeRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoantypeid(), 1)
					&& hrmsPayrollLoanTypeRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoantypeid(), 1)
					&& hrmsPayrollLoanLenderRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLenderid(), 1)
					&& hrmsPayrollLoanFrequencyRepository
							.existsByIdAndActive(hrmsPayrollEmployeeLoan.getLoanfrequencyid(), 1)
					&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())
					&& hrmsCurrencyRepository.existsByIdAndActive(hrmsPayrollEmployeeLoan.getCurrencyid(), 1)) {
				hrmsPayrollEmployeeLoan.setActive(1);

				hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());
				HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan1 = hrmsPayrollEmployeeLoanRepository.findById(id).get();

				hrmsPayrollEmployeeLoan.setUnique_id(hrmsPayrollEmployeeLoan1.getUnique_id());
				hrmsPayrollEmployeeLoan.setDate_created(hrmsPayrollEmployeeLoan1.getDate_created());

				if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != hrmsPayrollEmployeeLoan1
						.getDaterepaymentstart()) {
					Calendar c = Calendar.getInstance();

					c.setTime(hrmsPayrollEmployeeLoan.getDaterepaymentstart());

					// Perform addition/subtraction

					c.add(Calendar.MONTH, (int) Math.round(hrmsPayrollEmployeeLoan.getDuration()));

					// Convert calendar back to Date
					Date daterepaymentend = c.getTime();

					hrmsPayrollEmployeeLoan.setDaterepaymentend(daterepaymentend);
				}

				if (durationAdjust != 0) {

					// Convert Date to Calendar
					Calendar c = Calendar.getInstance();

					c.setTime(hrmsPayrollEmployeeLoan.getDaterepaymentend());

					// Perform addition/subtraction

					c.add(Calendar.MONTH, (int) Math.round(durationAdjust));

					// Convert calendar back to Date
					Date daterepaymentend = c.getTime();

					hrmsPayrollEmployeeLoan.setDaterepaymentend(daterepaymentend);
				}

				return ResponseEntity.status(HttpStatus.OK)
						.body(hrmsPayrollEmployeeLoanRepository.saveAndFlush(hrmsPayrollEmployeeLoan));
			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(hrmsPayrollEmployeeLoan);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hrmsPayrollEmployeeLoan);
		}
	}

	@Override
	public ResponseEntity<?> deletePayrollEmployeeLoan(int id) {
		if (hrmsPayrollEmployeeLoanRepository.existsByIdAndActive(id, 1)) {
			HrmsPayrollEmployeeLoan hrmsPayrollEmployeeLoan = hrmsPayrollEmployeeLoanRepository.findByIdAndActive(id,
					1);
			hrmsPayrollEmployeeLoan.setActive(0);
			hrmsPayrollEmployeeLoan.setDate_updated(LocalDateTime.now());
			hrmsPayrollEmployeeLoan.setApproved(0);

			return ResponseEntity.status(HttpStatus.OK)
					.body(hrmsPayrollEmployeeLoanRepository.saveAndFlush(hrmsPayrollEmployeeLoan));
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeLoanResponse>> getAllPayrollEmployeeLoan() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<EmployeeLoanResponse> employeeLoanResponselist = new ArrayList<>();

		List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
				.findByActiveOrderByIdDesc(1);
		hrmsPayrollEmployeeLoanlist.forEach(hrmsPayrollEmployeeLoan -> {
			EmployeeLoanResponse employeeLoanResponse = new EmployeeLoanResponse();
			employeeLoanResponse.setStatus(hrmsPayrollEmployeeLoan.getStatus());
			employeeLoanResponse.setAmountoutstanding(hrmsPayrollEmployeeLoan.getAmountoutstanding());
			employeeLoanResponse.setActive(hrmsPayrollEmployeeLoan.getActive());
			employeeLoanResponse.setAmountdebt(hrmsPayrollEmployeeLoan.getAmountdebt());
			employeeLoanResponse.setAmountprincipal(hrmsPayrollEmployeeLoan.getAmountprincipal());
			employeeLoanResponse.setApproved(hrmsPayrollEmployeeLoan.getApproved());
			employeeLoanResponse.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountloanbalance());

			Double amountpaid = hrmsPayrollEmployeeLoan.getAmountoutstanding()
					- hrmsPayrollEmployeeLoan.getAmountloanbalance();

			employeeLoanResponse.setAmountpaid(amountpaid);
			if (hrmsPayrollEmployeeLoan.getCurrencyid() != 0
					&& hrmsCurrencyRepository.existsById(hrmsPayrollEmployeeLoan.getCurrencyid())) {
				employeeLoanResponse.setCurrency(
						hrmsCurrencyRepository.findById(hrmsPayrollEmployeeLoan.getCurrencyid()).get().getName());
			}
			employeeLoanResponse.setCurrencyid(hrmsPayrollEmployeeLoan.getCurrencyid());
			if (hrmsPayrollEmployeeLoan.getDateissued() != null) {
				employeeLoanResponse.setDateissued(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDateissued()));
			}

			if (hrmsPayrollEmployeeLoan.getDaterepaymentend() != null) {
				employeeLoanResponse
						.setDaterepaymentend(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentend()));
			}

			if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != null) {
				employeeLoanResponse.setDaterepaymentstart(
						simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentstart()));
			}
			employeeLoanResponse.setDescription(hrmsPayrollEmployeeLoan.getDescription());
			employeeLoanResponse.setDuration(hrmsPayrollEmployeeLoan.getDuration());

			if (hrmsPayrollEmployeeLoan.getEmployeeid() != 0
					&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())) {
				StringBuilder employeeFullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollEmployeeLoan.getEmployeeid())
						.get();

				employeeFullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

				employeeLoanResponse.setEmployeeFullname(employeeFullname.toString());
			}

			employeeLoanResponse.setEmployeeid(hrmsPayrollEmployeeLoan.getEmployeeid());

			employeeLoanResponse.setId(hrmsPayrollEmployeeLoan.getId());

			employeeLoanResponse.setInterestrate(hrmsPayrollEmployeeLoan.getInterestrate());
			if (hrmsPayrollEmployeeLoan.getLenderid() != 0
					&& hrmsPayrollLoanLenderRepository.existsById(hrmsPayrollEmployeeLoan.getLenderid())) {
				employeeLoanResponse.setLender(hrmsPayrollLoanLenderRepository
						.findById(hrmsPayrollEmployeeLoan.getLenderid()).get().getName());
			}
			employeeLoanResponse.setLenderid(hrmsPayrollEmployeeLoan.getLenderid());
			if (hrmsPayrollEmployeeLoan.getLoanfrequencyid() != 0
					&& hrmsPayrollLoanFrequencyRepository.existsById(hrmsPayrollEmployeeLoan.getLoanfrequencyid())) {
				employeeLoanResponse.setLoanfrequency(hrmsPayrollLoanFrequencyRepository
						.findById(hrmsPayrollEmployeeLoan.getLoanfrequencyid()).get().getName());
			}

			employeeLoanResponse.setLoanfrequencyid(hrmsPayrollEmployeeLoan.getLoanfrequencyid());

			if (hrmsPayrollEmployeeLoan.getLoantypeid() != 0
					&& hrmsPayrollLoanTypeRepository.existsById(hrmsPayrollEmployeeLoan.getLoantypeid())) {
				employeeLoanResponse.setLoantype(hrmsPayrollLoanTypeRepository
						.findById(hrmsPayrollEmployeeLoan.getLoantypeid()).get().getName());
			}
			employeeLoanResponse.setLoantypeid(hrmsPayrollEmployeeLoan.getLoantypeid());

			employeeLoanResponselist.add(employeeLoanResponse);

		});

		return ResponseEntity.status(HttpStatus.OK).body(employeeLoanResponselist);
	}

	@Override
	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByEmpId(int EmpId) {
		if (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndActive(EmpId, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			List<EmployeeLoanResponse> employeeLoanResponselist = new ArrayList<>();

			List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
					.findByEmployeeidAndActiveOrderByIdDesc(EmpId, 1);
			hrmsPayrollEmployeeLoanlist.forEach(hrmsPayrollEmployeeLoan -> {
				EmployeeLoanResponse employeeLoanResponse = new EmployeeLoanResponse();
				employeeLoanResponse.setStatus(hrmsPayrollEmployeeLoan.getStatus());
				employeeLoanResponse.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountloanbalance());
				employeeLoanResponse.setAmountoutstanding(hrmsPayrollEmployeeLoan.getAmountoutstanding());

				Double amountpaid = hrmsPayrollEmployeeLoan.getAmountoutstanding()
						- hrmsPayrollEmployeeLoan.getAmountloanbalance();

				employeeLoanResponse.setAmountpaid(amountpaid);
				employeeLoanResponse.setActive(hrmsPayrollEmployeeLoan.getActive());
				employeeLoanResponse.setAmountdebt(hrmsPayrollEmployeeLoan.getAmountdebt());
				employeeLoanResponse.setAmountprincipal(hrmsPayrollEmployeeLoan.getAmountprincipal());
				employeeLoanResponse.setApproved(hrmsPayrollEmployeeLoan.getApproved());
				if (hrmsPayrollEmployeeLoan.getCurrencyid() != 0
						&& hrmsCurrencyRepository.existsById(hrmsPayrollEmployeeLoan.getCurrencyid())) {
					employeeLoanResponse.setCurrency(
							hrmsCurrencyRepository.findById(hrmsPayrollEmployeeLoan.getCurrencyid()).get().getName());
				}
				employeeLoanResponse.setCurrencyid(hrmsPayrollEmployeeLoan.getCurrencyid());
				if (hrmsPayrollEmployeeLoan.getDateissued() != null) {
					employeeLoanResponse
							.setDateissued(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDateissued()));
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentend() != null) {
					employeeLoanResponse.setDaterepaymentend(
							simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentend()));
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != null) {
					employeeLoanResponse.setDaterepaymentstart(
							simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentstart()));
				}
				employeeLoanResponse.setDescription(hrmsPayrollEmployeeLoan.getDescription());
				employeeLoanResponse.setDuration(hrmsPayrollEmployeeLoan.getDuration());

				if (hrmsPayrollEmployeeLoan.getEmployeeid() != 0
						&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())) {
					StringBuilder employeeFullname = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollEmployeeLoan.getEmployeeid())
							.get();

					employeeFullname.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

					employeeLoanResponse.setEmployeeFullname(employeeFullname.toString());
				}

				employeeLoanResponse.setEmployeeid(hrmsPayrollEmployeeLoan.getEmployeeid());

				employeeLoanResponse.setId(hrmsPayrollEmployeeLoan.getId());

				employeeLoanResponse.setInterestrate(hrmsPayrollEmployeeLoan.getInterestrate());
				if (hrmsPayrollEmployeeLoan.getLenderid() != 0
						&& hrmsPayrollLoanLenderRepository.existsById(hrmsPayrollEmployeeLoan.getLenderid())) {
					employeeLoanResponse.setLender(hrmsPayrollLoanLenderRepository
							.findById(hrmsPayrollEmployeeLoan.getLenderid()).get().getName());
				}
				employeeLoanResponse.setLenderid(hrmsPayrollEmployeeLoan.getLenderid());
				if (hrmsPayrollEmployeeLoan.getLoanfrequencyid() != 0 && hrmsPayrollLoanFrequencyRepository
						.existsById(hrmsPayrollEmployeeLoan.getLoanfrequencyid())) {
					employeeLoanResponse.setLoanfrequency(hrmsPayrollLoanFrequencyRepository
							.findById(hrmsPayrollEmployeeLoan.getLoanfrequencyid()).get().getName());
				}

				employeeLoanResponse.setLoanfrequencyid(hrmsPayrollEmployeeLoan.getLoanfrequencyid());

				if (hrmsPayrollEmployeeLoan.getLoantypeid() != 0
						&& hrmsPayrollLoanTypeRepository.existsById(hrmsPayrollEmployeeLoan.getLoantypeid())) {
					employeeLoanResponse.setLoantype(hrmsPayrollLoanTypeRepository
							.findById(hrmsPayrollEmployeeLoan.getLoantypeid()).get().getName());
				}
				employeeLoanResponse.setLoantypeid(hrmsPayrollEmployeeLoan.getLoantypeid());

				employeeLoanResponselist.add(employeeLoanResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeLoanResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByEmpIdAndStatus(int EmpId, int status) {
		if (hrmsPayrollEmployeeLoanRepository.existsByEmployeeidAndStatusAndActive(EmpId, status, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			List<EmployeeLoanResponse> employeeLoanResponselist = new ArrayList<>();

			List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
					.findByEmployeeidAndStatusAndActive(EmpId, status, 1);
			hrmsPayrollEmployeeLoanlist.forEach(hrmsPayrollEmployeeLoan -> {
				EmployeeLoanResponse employeeLoanResponse = new EmployeeLoanResponse();
				employeeLoanResponse.setStatus(hrmsPayrollEmployeeLoan.getStatus());
				employeeLoanResponse.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountloanbalance());
				employeeLoanResponse.setAmountoutstanding(hrmsPayrollEmployeeLoan.getAmountoutstanding());

				Double amountpaid = hrmsPayrollEmployeeLoan.getAmountoutstanding()
						- hrmsPayrollEmployeeLoan.getAmountloanbalance();

				employeeLoanResponse.setAmountpaid(amountpaid);
				employeeLoanResponse.setActive(hrmsPayrollEmployeeLoan.getActive());
				employeeLoanResponse.setAmountdebt(hrmsPayrollEmployeeLoan.getAmountdebt());
				employeeLoanResponse.setAmountprincipal(hrmsPayrollEmployeeLoan.getAmountprincipal());
				employeeLoanResponse.setApproved(hrmsPayrollEmployeeLoan.getApproved());
				if (hrmsPayrollEmployeeLoan.getCurrencyid() != 0
						&& hrmsCurrencyRepository.existsById(hrmsPayrollEmployeeLoan.getCurrencyid())) {
					employeeLoanResponse.setCurrency(
							hrmsCurrencyRepository.findById(hrmsPayrollEmployeeLoan.getCurrencyid()).get().getName());
				}
				employeeLoanResponse.setCurrencyid(hrmsPayrollEmployeeLoan.getCurrencyid());
				if (hrmsPayrollEmployeeLoan.getDateissued() != null) {
					employeeLoanResponse
							.setDateissued(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDateissued()));
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentend() != null) {
					employeeLoanResponse.setDaterepaymentend(
							simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentend()));
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != null) {
					employeeLoanResponse.setDaterepaymentstart(
							simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentstart()));
				}
				employeeLoanResponse.setDescription(hrmsPayrollEmployeeLoan.getDescription());
				employeeLoanResponse.setDuration(hrmsPayrollEmployeeLoan.getDuration());

				if (hrmsPayrollEmployeeLoan.getEmployeeid() != 0
						&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())) {
					StringBuilder employeeFullname = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollEmployeeLoan.getEmployeeid())
							.get();

					employeeFullname.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

					employeeLoanResponse.setEmployeeFullname(employeeFullname.toString());
				}

				employeeLoanResponse.setEmployeeid(hrmsPayrollEmployeeLoan.getEmployeeid());

				employeeLoanResponse.setId(hrmsPayrollEmployeeLoan.getId());

				employeeLoanResponse.setInterestrate(hrmsPayrollEmployeeLoan.getInterestrate());
				if (hrmsPayrollEmployeeLoan.getLenderid() != 0
						&& hrmsPayrollLoanLenderRepository.existsById(hrmsPayrollEmployeeLoan.getLenderid())) {
					employeeLoanResponse.setLender(hrmsPayrollLoanLenderRepository
							.findById(hrmsPayrollEmployeeLoan.getLenderid()).get().getName());
				}
				employeeLoanResponse.setLenderid(hrmsPayrollEmployeeLoan.getLenderid());
				if (hrmsPayrollEmployeeLoan.getLoanfrequencyid() != 0 && hrmsPayrollLoanFrequencyRepository
						.existsById(hrmsPayrollEmployeeLoan.getLoanfrequencyid())) {
					employeeLoanResponse.setLoanfrequency(hrmsPayrollLoanFrequencyRepository
							.findById(hrmsPayrollEmployeeLoan.getLoanfrequencyid()).get().getName());
				}

				employeeLoanResponse.setLoanfrequencyid(hrmsPayrollEmployeeLoan.getLoanfrequencyid());

				if (hrmsPayrollEmployeeLoan.getLoantypeid() != 0
						&& hrmsPayrollLoanTypeRepository.existsById(hrmsPayrollEmployeeLoan.getLoantypeid())) {
					employeeLoanResponse.setLoantype(hrmsPayrollLoanTypeRepository
							.findById(hrmsPayrollEmployeeLoan.getLoantypeid()).get().getName());
				}
				employeeLoanResponse.setLoantypeid(hrmsPayrollEmployeeLoan.getLoantypeid());

				employeeLoanResponselist.add(employeeLoanResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeLoanResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<EmployeeLoanResponse>> getPayrollEmployeeLoanByStatus(int status) {
		if (hrmsPayrollEmployeeLoanRepository.existsByStatusAndActive(status, 1)) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			List<EmployeeLoanResponse> employeeLoanResponselist = new ArrayList<>();

			List<HrmsPayrollEmployeeLoan> hrmsPayrollEmployeeLoanlist = hrmsPayrollEmployeeLoanRepository
					.findByStatusAndActive(status, 1);
			hrmsPayrollEmployeeLoanlist.forEach(hrmsPayrollEmployeeLoan -> {
				EmployeeLoanResponse employeeLoanResponse = new EmployeeLoanResponse();
				employeeLoanResponse.setStatus(hrmsPayrollEmployeeLoan.getStatus());
				employeeLoanResponse.setAmountoutstanding(hrmsPayrollEmployeeLoan.getAmountoutstanding());
				employeeLoanResponse.setActive(hrmsPayrollEmployeeLoan.getActive());
				employeeLoanResponse.setAmountloanbalance(hrmsPayrollEmployeeLoan.getAmountloanbalance());

				Double amountpaid = hrmsPayrollEmployeeLoan.getAmountoutstanding()
						- hrmsPayrollEmployeeLoan.getAmountloanbalance();

				employeeLoanResponse.setAmountpaid(amountpaid);
				employeeLoanResponse.setAmountdebt(hrmsPayrollEmployeeLoan.getAmountdebt());
				employeeLoanResponse.setAmountprincipal(hrmsPayrollEmployeeLoan.getAmountprincipal());
				employeeLoanResponse.setApproved(hrmsPayrollEmployeeLoan.getApproved());
				if (hrmsPayrollEmployeeLoan.getCurrencyid() != 0
						&& hrmsCurrencyRepository.existsById(hrmsPayrollEmployeeLoan.getCurrencyid())) {
					employeeLoanResponse.setCurrency(
							hrmsCurrencyRepository.findById(hrmsPayrollEmployeeLoan.getCurrencyid()).get().getName());
				}
				employeeLoanResponse.setCurrencyid(hrmsPayrollEmployeeLoan.getCurrencyid());
				if (hrmsPayrollEmployeeLoan.getDateissued() != null) {
					employeeLoanResponse
							.setDateissued(simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDateissued()));
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentend() != null) {
					employeeLoanResponse.setDaterepaymentend(
							simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentend()));
				}

				if (hrmsPayrollEmployeeLoan.getDaterepaymentstart() != null) {
					employeeLoanResponse.setDaterepaymentstart(
							simpleDateFormat.format(hrmsPayrollEmployeeLoan.getDaterepaymentstart()));
				}
				employeeLoanResponse.setDescription(hrmsPayrollEmployeeLoan.getDescription());
				employeeLoanResponse.setDuration(hrmsPayrollEmployeeLoan.getDuration());

				if (hrmsPayrollEmployeeLoan.getEmployeeid() != 0
						&& hrmsEmployeeRepository.existsById(hrmsPayrollEmployeeLoan.getEmployeeid())) {
					StringBuilder employeeFullname = new StringBuilder();

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsPayrollEmployeeLoan.getEmployeeid())
							.get();

					employeeFullname.append(hrmsEmployee.getFirstName().trim());
					if (hrmsEmployee.getMiddleName() != null) {
						employeeFullname.append(" " + hrmsEmployee.getMiddleName().trim());
					}
					employeeFullname.append(" " + hrmsEmployee.getLastName().trim());

					employeeLoanResponse.setEmployeeFullname(employeeFullname.toString());
				}

				employeeLoanResponse.setEmployeeid(hrmsPayrollEmployeeLoan.getEmployeeid());

				employeeLoanResponse.setId(hrmsPayrollEmployeeLoan.getId());

				employeeLoanResponse.setInterestrate(hrmsPayrollEmployeeLoan.getInterestrate());
				if (hrmsPayrollEmployeeLoan.getLenderid() != 0
						&& hrmsPayrollLoanLenderRepository.existsById(hrmsPayrollEmployeeLoan.getLenderid())) {
					employeeLoanResponse.setLender(hrmsPayrollLoanLenderRepository
							.findById(hrmsPayrollEmployeeLoan.getLenderid()).get().getName());
				}
				employeeLoanResponse.setLenderid(hrmsPayrollEmployeeLoan.getLenderid());
				if (hrmsPayrollEmployeeLoan.getLoanfrequencyid() != 0 && hrmsPayrollLoanFrequencyRepository
						.existsById(hrmsPayrollEmployeeLoan.getLoanfrequencyid())) {
					employeeLoanResponse.setLoanfrequency(hrmsPayrollLoanFrequencyRepository
							.findById(hrmsPayrollEmployeeLoan.getLoanfrequencyid()).get().getName());
				}

				employeeLoanResponse.setLoanfrequencyid(hrmsPayrollEmployeeLoan.getLoanfrequencyid());

				if (hrmsPayrollEmployeeLoan.getLoantypeid() != 0
						&& hrmsPayrollLoanTypeRepository.existsById(hrmsPayrollEmployeeLoan.getLoantypeid())) {
					employeeLoanResponse.setLoantype(hrmsPayrollLoanTypeRepository
							.findById(hrmsPayrollEmployeeLoan.getLoantypeid()).get().getName());
				}
				employeeLoanResponse.setLoantypeid(hrmsPayrollEmployeeLoan.getLoantypeid());

				employeeLoanResponselist.add(employeeLoanResponse);

			});

			return ResponseEntity.status(HttpStatus.OK).body(employeeLoanResponselist);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

}
