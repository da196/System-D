package com.Hrms.Leave.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Leave.DTO.LeaveBalance;
import com.Hrms.Leave.DTO.LeaveBalanceResponse;
import com.Hrms.Leave.Entity.HrmsLeaveApplications;
import com.Hrms.Leave.Entity.HrmsLeaveBalance;
import com.Hrms.Leave.Entity.HrmsLeaveSold;
import com.Hrms.Leave.Entity.HrmsLeaveType;
import com.Hrms.Leave.Repository.HrmsLeaveApplicationsRepository;
import com.Hrms.Leave.Repository.HrmsLeaveBalanceRepository;
import com.Hrms.Leave.Repository.HrmsLeaveSoldRepository;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Service
public class HrmsLeaveBalanceServiceImpl implements HrmsLeaveBalanceService {

	private static final Logger log = LoggerFactory.getLogger(HrmsLeaveBalanceServiceImpl.class);
	@Autowired
	private HrmsLeaveBalanceRepository hrmsLeaveBalanceRepository;

	@Autowired
	private HrmsLeaveSoldRepository hrmsLeaveSoldRepository;
	@Autowired
	private HrmsLeaveTypeRepository hrmsLeaveTypeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsLeaveApplicationsRepository hrmsLeaveApplicationsRepository;

	@Override
	public ResponseEntity<LeaveBalanceResponse> getLeaveBalanceByEmpId(int empid) {

		LeaveBalanceResponse leaveBalanceResponse = new LeaveBalanceResponse();

		HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndEmploymentstatusidAndActive(empid, 2, 1);// empstatusid=2
																												// =
																												// active
		if (hrmsEmployee != null && hrmsEmployee.getDateofemployment() != null
		/* && validateLeaveEligibility(hrmsEmployee.getDateofemployment()) == 1 */) {
			leaveBalanceResponse.setEmployeeid(empid);
			if (hrmsEmployee.getFirstName() != null) {
				leaveBalanceResponse.setFirstname(hrmsEmployee.getFirstName());
			}
			if (hrmsEmployee.getLastName() != null) {
				leaveBalanceResponse.setLastname(hrmsEmployee.getLastName());
			}

			if (hrmsEmployee.getMiddleName() != null) {
				leaveBalanceResponse.setMiddlename(hrmsEmployee.getMiddleName());
			}

			List<HrmsLeaveBalance> dbms = hrmsLeaveBalanceRepository.findByEmployeeidAndActive(empid, 1);
			List<LeaveBalance> leaveBalancelist = new ArrayList<>();
			for (HrmsLeaveBalance dbm : dbms) {
				LeaveBalance LeaveBalance = new LeaveBalance();

				LeaveBalance.setActive(dbm.getActive());
				LeaveBalance.setId(dbm.getId());

				LeaveBalance.setApproved(dbm.getApproved());
				LeaveBalance.setLeavetypeid(dbm.getLeavetypeid());
				if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
					HrmsLeaveType hrmsLeaveType = hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get();
					LeaveBalance.setLeavetypename(hrmsLeaveType.getName());
					Double taken = getdaysTaken(hrmsEmployee.getDateofemployment(), hrmsEmployee.getId(),
							dbm.getLeavetypeid());

					Double commuted = getdaysCommuted(hrmsEmployee.getDateofemployment(), hrmsEmployee.getId(),
							dbm.getLeavetypeid());
					taken = taken + commuted;

					LeaveBalance.setDaysremaining(dbm.getDays());

					LeaveBalance.setDaystaken(taken);
					if (dbm.getLeavetypeid() == 1) {
						LeaveBalance.setDaystotal(taken + dbm.getDays());
					} else {
						LeaveBalance.setDaystotal(hrmsLeaveType.getMaxdayNumber());
					}
				}
				leaveBalancelist.add(LeaveBalance);
			}

			leaveBalanceResponse.setLeaveBalancelist(leaveBalancelist);
		}
		return ResponseEntity.status(HttpStatus.OK).body(leaveBalanceResponse);
	}

	@Override
	public ResponseEntity<List<LeaveBalanceResponse>> getAllLeaveBalance() {
		List<LeaveBalanceResponse> leaveBalanceResponselist = new ArrayList<>();
		List<HrmsEmployee> dbmzz = hrmsEmployeeRepository.findByAndEmploymentstatusidAndActive(2, 1);
		for (HrmsEmployee dbmz : dbmzz) {
			if (validateLeaveEligibility(dbmz.getDateofemployment()) == 1) {

				LeaveBalanceResponse leaveBalanceResponse = new LeaveBalanceResponse();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndActive(dbmz.getId(), 1);
				leaveBalanceResponse.setEmployeeid(dbmz.getId());
				if (hrmsEmployee.getFirstName() != null) {
					leaveBalanceResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveBalanceResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getMiddleName() != null) {
					leaveBalanceResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				List<HrmsLeaveBalance> dbms = hrmsLeaveBalanceRepository.findByEmployeeidAndActive(dbmz.getId(), 1);
				List<LeaveBalance> leaveBalancelist = new ArrayList<>();
				for (HrmsLeaveBalance dbm : dbms) {
					LeaveBalance LeaveBalance = new LeaveBalance();

					LeaveBalance.setActive(dbm.getActive());
					LeaveBalance.setId(dbm.getId());

					LeaveBalance.setApproved(dbm.getApproved());
					LeaveBalance.setLeavetypeid(dbm.getLeavetypeid());
					if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
						HrmsLeaveType hrmsLeaveType = hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get();
						LeaveBalance.setLeavetypename(hrmsLeaveType.getName());
						Double taken = getdaysTaken(hrmsEmployee.getDateofemployment(), dbmz.getId(),
								dbm.getLeavetypeid());

						Double commuted = getdaysCommuted(hrmsEmployee.getDateofemployment(), hrmsEmployee.getId(),
								dbm.getLeavetypeid());
						taken = taken + commuted;

						LeaveBalance.setDaysremaining(dbm.getDays());

						LeaveBalance.setDaystaken(taken);
						if (dbm.getLeavetypeid() == 1) {
							LeaveBalance.setDaystotal(taken + dbm.getDays());
						} else {
							LeaveBalance.setDaystotal(hrmsLeaveType.getMaxdayNumber());
						}
					}
					leaveBalancelist.add(LeaveBalance);
				}

				leaveBalanceResponse.setLeaveBalancelist(leaveBalancelist);

				leaveBalanceResponselist.add(leaveBalanceResponse);
			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveBalanceResponselist);
	}

	public int validateLeaveEligibility(Date doe) {
		int response = 0;
		int year = 0;
		if (doe != null) {
			Calendar dateofemp = Calendar.getInstance();
			dateofemp.setTimeInMillis(doe.getTime());

			// create calendar object for current day
			long currentTime = System.currentTimeMillis();
			Calendar now = Calendar.getInstance();
			now.setTimeInMillis(currentTime);

			// Get difference between years
			year = now.get(Calendar.YEAR) - dateofemp.get(Calendar.YEAR);
			int mostart = dateofemp.get(Calendar.MONTH) + 1;
			int mostart1 = 12 - mostart; // get month left to complete a year

			int moend = now.get(Calendar.MONTH) + 1;
			int Monthtotal = mostart1 + moend;

			if (year > 1 || (year == 1 && Monthtotal > 8) || (year == 0 && (moend - mostart) > 8)) {
				response = 1;

				log.info("The date of employment is {} ", doe);
				log.info("The year is {} ", year);
				log.info("Themonth total is  is {} ", Monthtotal);

			}

		}

		return response;
	}

	@Override
	public ResponseEntity<List<LeaveBalanceResponse>> getLeaveBalanceBySupervisorId(int supervisorId) {
		String pattern = "yyyy-MM-dd";
		List<Integer> employeeundersupervisor = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveBalanceResponse> leaveBalanceResponselist = new ArrayList<>();
		if (hrmsEmployeeRepository.existsByIdAndActive(supervisorId, 1)) {
			int supervisordesignationid = hrmsEmployeeRepository.findById(supervisorId).get().getDesignationId();
			List<HrmsEmployee> emps = hrmsEmployeeRepository
					.findBySupervisordesignationidAndEmploymentstatusid(supervisordesignationid, 2);// active employee
																									// under supervisor
																									// desination given

			for (HrmsEmployee emp : emps) {
				employeeundersupervisor.add(emp.getId());

			}
		}

		for (int empid : employeeundersupervisor) {

			LeaveBalanceResponse leaveBalanceResponse = new LeaveBalanceResponse();

			if (empid != 0 && hrmsEmployeeRepository.findById(empid).get().getDateofemployment() != null) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(empid).get();
				leaveBalanceResponse.setEmployeeid(empid);
				if (hrmsEmployee.getFirstName() != null) {
					leaveBalanceResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveBalanceResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getMiddleName() != null) {
					leaveBalanceResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				List<HrmsLeaveBalance> dbms = hrmsLeaveBalanceRepository.findByEmployeeidAndActive(empid, 1);
				List<LeaveBalance> leaveBalancelist = new ArrayList<>();
				for (HrmsLeaveBalance dbm : dbms) {
					LeaveBalance LeaveBalance = new LeaveBalance();

					LeaveBalance.setActive(dbm.getActive());
					LeaveBalance.setId(dbm.getId());

					LeaveBalance.setApproved(dbm.getApproved());
					LeaveBalance.setLeavetypeid(dbm.getLeavetypeid());
					if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
						HrmsLeaveType hrmsLeaveType = hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get();
						LeaveBalance.setLeavetypename(hrmsLeaveType.getName());
						Double taken = getdaysTaken(hrmsEmployee.getDateofemployment(), empid, dbm.getLeavetypeid());

						Double commuted = getdaysCommuted(hrmsEmployee.getDateofemployment(), hrmsEmployee.getId(),
								dbm.getLeavetypeid());
						taken = taken + commuted;

						LeaveBalance.setDaysremaining(dbm.getDays());

						LeaveBalance.setDaystaken(taken);
						if (dbm.getLeavetypeid() == 1) {
							LeaveBalance.setDaystotal(taken + dbm.getDays());
						} else {
							LeaveBalance.setDaystotal(hrmsLeaveType.getMaxdayNumber());
						}
					}
					leaveBalancelist.add(LeaveBalance);
				}

				leaveBalanceResponse.setLeaveBalancelist(leaveBalancelist);
			}

			leaveBalanceResponselist.add(leaveBalanceResponse);
		}
		return ResponseEntity.status(HttpStatus.OK).body(leaveBalanceResponselist);
	}

	public Double getdaysTaken(Date doe, int empid, int leavetypeid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String doemp = simpleDateFormat.format(doe);

		LocalDate dateofemployment = LocalDate.parse(doemp);
		int currentMonth = LocalDate.now().getMonthValue();
		int currentYear = LocalDate.now().getYear();
		int monthofemployment = dateofemployment.getMonthValue();
		Double daystaken = 0.0;
		List<Integer> monthlist = new ArrayList<>();

		List<Integer> approved = new ArrayList<>();
		approved.add(1);
		approved.add(0);

		if (currentMonth > monthofemployment) {
			for (int i = monthofemployment; i <= currentMonth; i++) {
				monthlist.add(i);

			}

			List<HrmsLeaveApplications> leavelist = hrmsLeaveApplicationsRepository
					.findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(empid, leavetypeid, approved,
							1, currentYear, monthlist);
			for (HrmsLeaveApplications lv : leavelist) {
				daystaken = daystaken + lv.getNumberofdays();
			}

		} else {

			for (int i = monthofemployment; i <= 12; i++) {
				monthlist.add(i);

			}
			List<HrmsLeaveApplications> leavelist = hrmsLeaveApplicationsRepository
					.findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(empid, leavetypeid, approved,
							1, currentYear - 1, monthlist);
			for (HrmsLeaveApplications lv : leavelist) {
				daystaken = daystaken + lv.getNumberofdays();
			}

			// for month from january to current month
			monthlist = new ArrayList<>();
			for (int i = 1; i <= currentMonth; i++) {
				monthlist.add(i);

			}

			List<HrmsLeaveApplications> leavelist1 = hrmsLeaveApplicationsRepository
					.findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(empid, leavetypeid, approved,
							1, currentYear, monthlist);
			for (HrmsLeaveApplications lv : leavelist1) {
				daystaken = daystaken + lv.getNumberofdays();
			}

		}

		// String dateAfterString =
		// simpleDateFormat.format(hrmsLeaveApplications.getEnddate());

		// Parsing the date

		// LocalDate dateAfter = LocalDate.parse(dateAfterString);

		// long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);

		return daystaken;

	}

	public Double getdaysCommuted(Date doe, int empid, int leavetypeid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String doemp = simpleDateFormat.format(doe);

		LocalDate dateofemployment = LocalDate.parse(doemp);
		int currentMonth = LocalDate.now().getMonthValue();
		int currentYear = LocalDate.now().getYear();
		int monthofemployment = dateofemployment.getMonthValue();
		Double daystaken = 0.0;
		List<Integer> monthlist = new ArrayList<>();

		List<Integer> approved = new ArrayList<>();
		approved.add(1);// approved
		approved.add(0);// pending approve
		approved.add(4); // recalled

		if (currentMonth > monthofemployment) {
			for (int i = monthofemployment; i <= currentMonth; i++) {
				monthlist.add(i);

			}

			List<HrmsLeaveSold> leavelist = hrmsLeaveSoldRepository
					.findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(empid, leavetypeid, approved,
							1, currentYear, monthlist);
			for (HrmsLeaveSold lv : leavelist) {
				daystaken = daystaken + lv.getNumberofdaysSold();
			}

		} else {

			for (int i = monthofemployment; i <= 12; i++) {
				monthlist.add(i);

			}
			List<HrmsLeaveSold> leavelist = hrmsLeaveSoldRepository
					.findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(empid, leavetypeid, approved,
							1, currentYear - 1, monthlist);
			for (HrmsLeaveSold lv : leavelist) {
				daystaken = daystaken + lv.getNumberofdaysSold();
			}

			// for month from january to current month
			monthlist = new ArrayList<>();
			for (int i = 1; i <= currentMonth; i++) {
				monthlist.add(i);

			}

			List<HrmsLeaveSold> leavelist1 = hrmsLeaveSoldRepository
					.findByEmployeeidAndLeavetypeidAndApprovedInAndActiveAndYearAndMonthIn(empid, leavetypeid, approved,
							1, currentYear, monthlist);
			for (HrmsLeaveSold lv : leavelist1) {
				daystaken = daystaken + lv.getNumberofdaysSold();
			}

		}

		// String dateAfterString =
		// simpleDateFormat.format(hrmsLeaveApplications.getEnddate());

		// Parsing the date

		// LocalDate dateAfter = LocalDate.parse(dateAfterString);

		// long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);

		return daystaken;

	}

}
