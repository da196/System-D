package com.Hrms.Leave.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Communication.SendEmail;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Entity.Hrmsemployeesalary;
import com.Hrms.Employee.Repository.HrmsDesignationRepository;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Employee.Repository.HrmsOrginisationUnitRepository;
import com.Hrms.Employee.Repository.HrmsSalarystructureRepository;
import com.Hrms.Employee.Repository.HrmsemployeesalaryRepository;
import com.Hrms.Leave.DTO.EmployeeOnAnnualLeave;
import com.Hrms.Leave.DTO.LeaveApplicationsResponse;
import com.Hrms.Leave.DTO.LeaveApprovalStatus;
import com.Hrms.Leave.Entity.HrmsLeaveApplications;
import com.Hrms.Leave.Entity.HrmsLeaveApproval;
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflow;
import com.Hrms.Leave.Entity.HrmsLeaveApprovalWorkflowStep;
import com.Hrms.Leave.Entity.HrmsLeaveBalance;
import com.Hrms.Leave.Repository.HrmsLeaveApplicationsRepository;
import com.Hrms.Leave.Repository.HrmsLeaveApprovalRepository;
import com.Hrms.Leave.Repository.HrmsLeaveApprovalWorkflowRepository;
import com.Hrms.Leave.Repository.HrmsLeaveApprovalWorkflowStepRepository;
import com.Hrms.Leave.Repository.HrmsLeaveBalanceRepository;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Service
public class HrmsLeaveApplicationsServiceImpl implements HrmsLeaveApplicationsService {
	@Autowired
	private HrmsLeaveApplicationsRepository hrmsLeaveApplicationsRepository;

	@Autowired
	private HrmsLeaveBalanceRepository hrmsLeaveBalanceRepository;

	@Autowired
	private HrmsLeaveTypeRepository hrmsLeaveTypeRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsOrginisationUnitRepository hrmsOrginisationUnitRepository;

	@Autowired
	private HrmsemployeesalaryRepository hrmsemployeesalaryRepository;

	@Autowired
	private HrmsSalarystructureRepository hrmsSalarystructureRepository;

	@Autowired
	private HrmsLeaveApprovalRepository hrmsLeaveApprovalRepository;

	@Autowired
	private HrmsLeaveApprovalWorkflowRepository hrmsLeaveApprovalWorkflowRepository;

	@Autowired
	private HrmsLeaveApprovalWorkflowStepRepository hrmsLeaveApprovalWorkflowStepRepository;

	@Autowired
	private HrmsDesignationRepository hrmsDesignationRepository;

	@Autowired
	private SendEmail sendEmail;

	@Override
	public ResponseEntity<HrmsLeaveApplications> applyLeave(HrmsLeaveApplications hrmsLeaveApplications) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String dateBeforeString = simpleDateFormat.format(hrmsLeaveApplications.getStartdate());

		String dateAfterString = simpleDateFormat.format(hrmsLeaveApplications.getEnddate());

		// Parsing the date
		LocalDate dateBefore = LocalDate.parse(dateBeforeString);
		LocalDate dateAfter = LocalDate.parse(dateAfterString);

		long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);

		Double days = (double) daysnumbers + 1.00; // inclusive start and end date
		int currentYear = LocalDate.now().getYear();
		int currentMonth = LocalDate.now().getMonthValue();

		hrmsLeaveApplications.setActive(1);
		hrmsLeaveApplications.setApproved(0);
		hrmsLeaveApplications.setYear(currentYear);
		hrmsLeaveApplications.setMonth(currentMonth);
		hrmsLeaveApplications.setUnique_id(UUID.randomUUID());
		hrmsLeaveApplications.setNumberofdays(days);

		if (hrmsLeaveApplicationsRepository.existsByEmployeeidAndLeavetypeidAndStartdateAndEnddateAndActive(
				hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications.getLeavetypeid(),
				hrmsLeaveApplications.getStartdate(), hrmsLeaveApplications.getEnddate(), 1)) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(hrmsLeaveApplications);
		} else {

			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveApplications.getEmployeeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveApplications.getRequestedby(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveApplications.getActing(), 1)

					&& hrmsLeaveTypeRepository.existsByIdAndActive(hrmsLeaveApplications.getLeavetypeid(), 1)) {

				if (hrmsLeaveApplications.getEmployeeid() == hrmsLeaveApplications.getRequestedby()
						|| (hrmsOrginisationUnitRepository.findById(hrmsEmployeeRepository
								.findById(hrmsLeaveApplications.getRequestedby()).get().getUnitId()).get().getName()
								.toLowerCase().contains("resource"))) {

					if (hrmsLeaveApplications.getLeavetypeid() == 1
							&& hrmsLeaveApplications.getLeaveallowanceapplicable() == 1
							&& hrmsLeaveApplications.getNumberofdays() >= 14) {// annual leave
						// check if this user has leave balance >14 and is applying leave day >14 or 14
						// and has not yet applied for leave allowance last year
						if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(
								hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications.getLeavetypeid(), 1)
								&& hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(), 1)
										.getDays() >= 14
								&& hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(), 1)
										.getDays() >= hrmsLeaveApplications.getNumberofdays()) {

							if (hrmsLeaveApplicationsRepository
									.existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(
											hrmsLeaveApplications.getEmployeeid(),
											hrmsLeaveApplications.getLeavetypeid(),
											hrmsLeaveApplications.getLeaveallowanceapplicable(), 1, 1, currentYear - 1)
									|| hrmsLeaveApplicationsRepository
											.existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(
													hrmsLeaveApplications.getEmployeeid(),
													hrmsLeaveApplications.getLeavetypeid(),
													hrmsLeaveApplications.getLeaveallowanceapplicable(), 1, 1,
													currentYear)) {

								HrmsLeaveApplications hrmsLeaveApplications1 = hrmsLeaveApplicationsRepository
										.findByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYearOrYear(
												hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(),
												hrmsLeaveApplications.getLeaveallowanceapplicable(), 1, 1,
												currentYear - 1, currentYear);

								if ((currentYear - hrmsLeaveApplications1.getYear()) == 1
										&& hrmsLeaveApplications1.getMonth() < 7 && currentMonth >= 7) {
									if (hrmsemployeesalaryRepository
											.existsByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1)) {

										Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
												.findByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1);

										Double basicsalary = hrmsSalarystructureRepository
												.findById(hrmsemployeesalary.getSalarystructureId()).get()
												.getBasicSalary();

										hrmsLeaveApplications.setLeaveallowance(basicsalary); // apply for allowance and
																								// leave
										int applicationid = hrmsLeaveApplicationsRepository
												.saveAndFlush(hrmsLeaveApplications).getId();

										HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
												.findByEmployeeidAndLeavetypeidAndActive(
														hrmsLeaveApplications.getEmployeeid(),
														hrmsLeaveApplications.getLeavetypeid(), 1);
										hrmsLeaveBalance.setDays(
												hrmsLeaveBalance.getDays() - hrmsLeaveApplications.getNumberofdays());
										hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave
																									// balance

										HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
												.findById(hrmsLeaveApplications.getEmployeeid()).get();

										StringBuilder employeename = new StringBuilder();
										employeename.append(hrmsEmployee.getFirstName().trim());

										if (hrmsEmployee.getMiddleName() != null) {
											employeename.append(hrmsEmployee.getMiddleName().trim());
										}

										if (hrmsEmployee.getLastName() != null) {
											employeename.append(hrmsEmployee.getLastName().trim());
										}

										int supervisordesignationid = hrmsEmployeeRepository
												.findById(hrmsLeaveApplications.getEmployeeid()).get()
												.getSupervisordesignationid();

										String username = hrmsDesignationRepository.findById(supervisordesignationid)
												.get().getName();
										String usermail = hrmsEmployeeRepository
												.findByDesignationIdAndEmploymentstatusid(supervisordesignationid, 2)
												.getEmail(); // email of supervisor for testing purpose we will be using
																// john.cornery@tcra.go.tz
										String messages = " Kindly assist to approve  " + hrmsLeaveTypeRepository
												.findById(hrmsLeaveApplications.getLeavetypeid()).get().getName()
												+ "  for " + employeename.toString() + " from "
												+ hrmsLeaveApplications.getStartdate() + " to "
												+ hrmsLeaveApplications.getEnddate() + " for" + days + " days";

										// sendEmail.sendmailNotification(username, usermail, messages);

										sendEmail.sendmailNotification(username, "john.cornery@tcra.go.tz", messages);
										return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);
									} else {
										return ResponseEntity.status(HttpStatus.CONFLICT).body(hrmsLeaveApplications);
									}

								} else {

									return ResponseEntity.status(HttpStatus.LOCKED).body(hrmsLeaveApplications);
								}
							} else {
								if (hrmsemployeesalaryRepository
										.existsByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1)) {

									Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
											.findByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1);

									Double basicsalary = hrmsSalarystructureRepository
											.findById(hrmsemployeesalary.getSalarystructureId()).get().getBasicSalary();

									hrmsLeaveApplications.setLeaveallowance(basicsalary); // apply for allowance and
																							// leave
									int applicationid = hrmsLeaveApplicationsRepository
											.saveAndFlush(hrmsLeaveApplications).getId();
									HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
											.findByEmployeeidAndLeavetypeidAndActive(
													hrmsLeaveApplications.getEmployeeid(),
													hrmsLeaveApplications.getLeavetypeid(), 1);
									hrmsLeaveBalance.setDays(
											hrmsLeaveBalance.getDays() - hrmsLeaveApplications.getNumberofdays());
									hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave balance

									return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);
								} else {
									return ResponseEntity.status(HttpStatus.CONFLICT).body(hrmsLeaveApplications);
								}
							}
						} else {
							return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
									.body(hrmsLeaveApplications);
						}

					} else {
						if (hrmsLeaveTypeRepository.findByIdAndActive(hrmsLeaveApplications.getLeavetypeid(), 1)
								.getMaxdayNumber() >= hrmsLeaveApplications.getNumberofdays()) {

							if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(
									hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications.getLeavetypeid(), 1)) {

								HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(), 1);

								if (hrmsLeaveBalance.getDays() >= hrmsLeaveApplications.getNumberofdays()) {

									hrmsLeaveApplications.setLeaveallowanceapplicable(0);
									hrmsLeaveApplications.setLeaveallowance(0.00);

									int applicationid = hrmsLeaveApplicationsRepository
											.saveAndFlush(hrmsLeaveApplications).getId();

									hrmsLeaveBalance.setDays(
											hrmsLeaveBalance.getDays() - hrmsLeaveApplications.getNumberofdays());
									hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave balance
									return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);

								} else {

									return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);
									// return
									// ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);//
									// requested
									// more
									// days
									// than
									// leave
									// balance
								}

							} else {
								return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hrmsLeaveApplications);// this
																												// employee
																												// is
																												// not
																												// available
																												// on
																												// leave
																												// balance
																												// table
							}

						} else {
							// requesting number of leaves greater than allocated value eq annual is 28 but
							// user is requesting more than 28

							return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);

							// return
							// ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);
						}

					}
				} else {

					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);
				}

			} else {

				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveApplications);
			}

		}

	}

	@Override
	public ResponseEntity<?> approveLeave(int leaveid, int approverid, int status, String comment) {
		if (hrmsLeaveApplicationsRepository.existsByIdAndActiveAndApproved(leaveid, 1, 0)) {

			HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository.findById(leaveid).get();

			if (hrmsEmployeeRepository.existsByIdAndActive(approverid, 1)
					&& (hrmsEmployeeRepository.findById(hrmsLeaveApplications.getEmployeeid()).get()
							.getSupervisordesignationid() == hrmsEmployeeRepository.findById(approverid).get()
									.getDesignationId())) {// check if the approver does exist and is a supervisor

				hrmsLeaveApplications.setApprovalcomment(comment);
				hrmsLeaveApplications.setApproved(status);
				hrmsLeaveApplications.setDate_updated(LocalDateTime.now());

				hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeaveApplications);
				// send notification to HR if Leave has been approved

				if (status == 1) {

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsLeaveApplications.getEmployeeid())
							.get();

					StringBuilder employeename = new StringBuilder();
					employeename.append(hrmsEmployee.getFirstName().trim());

					if (hrmsEmployee.getMiddleName() != null) {
						employeename.append(hrmsEmployee.getMiddleName().trim());
					}

					if (hrmsEmployee.getLastName() != null) {
						employeename.append(hrmsEmployee.getLastName().trim());
					}

					int supervisordesignationid = hrmsEmployeeRepository.findById(hrmsLeaveApplications.getEmployeeid())
							.get().getSupervisordesignationid();

					String username = "HR Team";
					// String username =
					// hrmsDesignationRepository.findById(supervisordesignationid).get().getName();
					String usermail = hrmsEmployeeRepository
							.findByDesignationIdAndEmploymentstatusid(supervisordesignationid, 2).getEmail(); // email
																												// of HR
																												// for
																												// testing
																												// purpose
																												// we
																												// will
																												// be
																												// using
																												// john.cornery@tcra.go.tz
					String messages = " Kindly be informed that  "
							+ hrmsLeaveTypeRepository.findById(hrmsLeaveApplications.getLeavetypeid()).get().getName()
							+ "  for " + employeename.toString() + " from " + hrmsLeaveApplications.getStartdate()
							+ " to " + hrmsLeaveApplications.getEnddate() + " for"
							+ hrmsLeaveApplications.getNumberofdays() + " days" + "has been approved";

					// sendEmail.sendmailNotification(username, usermail, messages);

					sendEmail.sendmailNotification(username, "john.cornery@tcra.go.tz", messages);

				} else {

					HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(hrmsLeaveApplications.getEmployeeid())
							.get();

					StringBuilder employeename = new StringBuilder();
					employeename.append(hrmsEmployee.getFirstName().trim());

					if (hrmsEmployee.getMiddleName() != null) {
						employeename.append(hrmsEmployee.getMiddleName().trim());
					}

					if (hrmsEmployee.getLastName() != null) {
						employeename.append(hrmsEmployee.getLastName().trim());
					}

					int supervisordesignationid = hrmsEmployeeRepository
							.findFirstById(hrmsLeaveApplications.getEmployeeid()).getSupervisordesignationid();

					String username = hrmsDesignationRepository.findById(hrmsEmployee.getDesignationId()).get()
							.getName();
					String usermail = hrmsEmployeeRepository
							.findFirstByDesignationIdAndEmploymentstatusid(supervisordesignationid, 2).getEmail(); // email
																													// of
																													// HR
																													// for
																													// testing
																													// purpose
																													// we
																													// will
																													// be
																													// using
																													// john.cornery@tcra.go.tz
					String messages = " Kindly be informed that  "
							+ hrmsLeaveTypeRepository.findById(hrmsLeaveApplications.getLeavetypeid()).get().getName()
							+ "  for " + employeename.toString() + " from " + hrmsLeaveApplications.getStartdate()
							+ " to " + hrmsLeaveApplications.getEnddate() + " for"
							+ hrmsLeaveApplications.getNumberofdays() + " days" + "has been rejected";

					// sendEmail.sendmailNotification(username, usermail, messages);

					sendEmail.sendmailNotification(username, "john.cornery@tcra.go.tz", messages);
					// update leave balance
					HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
							.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
									hrmsLeaveApplications.getLeavetypeid(), 1);

					hrmsLeaveBalance.setDays(hrmsLeaveBalance.getDays() + hrmsLeaveApplications.getNumberofdays());
					hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave balance
				}

				return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);

			} else {
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(null);
			}

		} else {

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<HrmsLeaveApplications> updateLeave(HrmsLeaveApplications hrmsLeaveApplications, int leaveid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String dateBeforeString = simpleDateFormat.format(hrmsLeaveApplications.getStartdate());

		String dateAfterString = simpleDateFormat.format(hrmsLeaveApplications.getEnddate());

		// Parsing the date
		LocalDate dateBefore = LocalDate.parse(dateBeforeString);
		LocalDate dateAfter = LocalDate.parse(dateAfterString);

		long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);

		Double days = (double) daysnumbers + 1.00; // inclusive start and end date
		int currentYear = LocalDate.now().getYear();
		int currentMonth = LocalDate.now().getMonthValue();

		hrmsLeaveApplications.setActive(1);
		hrmsLeaveApplications.setApproved(0);
		hrmsLeaveApplications.setYear(currentYear);
		hrmsLeaveApplications.setMonth(currentMonth);

		hrmsLeaveApplications.setNumberofdays(days);
		hrmsLeaveApplications.setDate_updated(LocalDateTime.now());

		if (hrmsLeaveApplicationsRepository.existsByIdAndActiveAndApproved(leaveid, 1, 0)
				&& !hrmsLeaveApprovalRepository.existsByStatusAndLeaveidAndActive(1, leaveid, 1)) {
			HrmsLeaveApplications hrmsLeaveApplications2 = hrmsLeaveApplicationsRepository.findById(leaveid).get();

			// release leave balance first

			HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository.findByEmployeeidAndLeavetypeidAndActive(
					hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications2.getLeavetypeid(), 1);

			hrmsLeaveBalance.setDays(hrmsLeaveBalance.getDays() + hrmsLeaveApplications2.getNumberofdays());
			hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave balance

			if (hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveApplications.getEmployeeid(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveApplications.getRequestedby(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeaveApplications.getActing(), 1)

					&& hrmsLeaveTypeRepository.existsByIdAndActive(hrmsLeaveApplications.getLeavetypeid(), 1)) {

				if (hrmsLeaveApplications.getEmployeeid() == hrmsLeaveApplications.getRequestedby()
						|| (hrmsOrginisationUnitRepository.findById(hrmsEmployeeRepository
								.findById(hrmsLeaveApplications.getRequestedby()).get().getUnitId()).get().getName()
								.toLowerCase().equals("resource"))) {

					if (hrmsLeaveApplicationsRepository.findById(leaveid).get().getUnique_id() != null
							&& hrmsLeaveApplicationsRepository.findById(leaveid).get().getDate_created() != null) {
						hrmsLeaveApplications
								.setUnique_id(hrmsLeaveApplicationsRepository.findById(leaveid).get().getUnique_id());

						hrmsLeaveApplications.setDate_created(
								hrmsLeaveApplicationsRepository.findById(leaveid).get().getDate_created());
					}

					if (hrmsLeaveApplications.getLeavetypeid() == 1
							&& hrmsLeaveApplications.getLeaveallowanceapplicable() == 1
							&& hrmsLeaveApplications.getNumberofdays() >= 14) {// annual leave
						// check if this user has leave balance >14 and is applying leave day >14 or 14
						// and has not yet applied for leave allowance last year
						if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(
								hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications.getLeavetypeid(), 1)
								&& hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(), 1)
										.getDays() >= 14
								&& hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(), 1)
										.getDays() >= hrmsLeaveApplications.getNumberofdays()) {

							if (hrmsLeaveApplicationsRepository
									.existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(
											hrmsLeaveApplications.getEmployeeid(),
											hrmsLeaveApplications.getLeavetypeid(),
											hrmsLeaveApplications.getLeaveallowanceapplicable(), 1, 1, currentYear - 1)
									|| hrmsLeaveApplicationsRepository
											.existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(
													hrmsLeaveApplications.getEmployeeid(),
													hrmsLeaveApplications.getLeavetypeid(),
													hrmsLeaveApplications.getLeaveallowanceapplicable(), 1, 1,
													currentYear)) {

								HrmsLeaveApplications hrmsLeaveApplications1 = hrmsLeaveApplicationsRepository
										.findByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYearOrYear(
												hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(),
												hrmsLeaveApplications.getLeaveallowanceapplicable(), 1, 1,
												currentYear - 1, currentYear);

								if ((currentYear - hrmsLeaveApplications1.getYear()) == 1
										&& hrmsLeaveApplications1.getMonth() < 7 && currentMonth >= 7) {
									if (hrmsemployeesalaryRepository
											.existsByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1)) {

										Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
												.findByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1);

										Double basicsalary = hrmsSalarystructureRepository
												.findById(hrmsemployeesalary.getSalarystructureId()).get()
												.getBasicSalary();

										hrmsLeaveApplications.setLeaveallowance(basicsalary); // apply for allowance and
																								// leave
										int applicationid = hrmsLeaveApplicationsRepository
												.saveAndFlush(hrmsLeaveApplications).getId();

										HrmsLeaveBalance hrmsLeaveBalance1 = hrmsLeaveBalanceRepository
												.findByEmployeeidAndLeavetypeidAndActive(
														hrmsLeaveApplications.getEmployeeid(),
														hrmsLeaveApplications.getLeavetypeid(), 1);
										hrmsLeaveBalance1.setDays(
												hrmsLeaveBalance1.getDays() - hrmsLeaveApplications.getNumberofdays());
										hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance1);// update leave
																									// balance

										HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
												.findById(hrmsLeaveApplications.getEmployeeid()).get();

										StringBuilder employeename = new StringBuilder();
										employeename.append(hrmsEmployee.getFirstName().trim());

										if (hrmsEmployee.getMiddleName() != null) {
											employeename.append(hrmsEmployee.getMiddleName().trim());
										}

										if (hrmsEmployee.getLastName() != null) {
											employeename.append(hrmsEmployee.getLastName().trim());
										}

										int supervisordesignationid = hrmsEmployeeRepository
												.findById(hrmsLeaveApplications.getEmployeeid()).get()
												.getSupervisordesignationid();

										String username = hrmsDesignationRepository.findById(supervisordesignationid)
												.get().getName();
										String usermail = hrmsEmployeeRepository
												.findByDesignationIdAndEmploymentstatusid(supervisordesignationid, 2)
												.getEmail(); // email of supervisor for testing purpose we will be using
																// john.cornery@tcra.go.tz
										String messages = " Kindly assist to approve  " + hrmsLeaveTypeRepository
												.findById(hrmsLeaveApplications.getLeavetypeid()).get().getName()
												+ "  for " + employeename.toString() + " from "
												+ hrmsLeaveApplications.getStartdate() + " to "
												+ hrmsLeaveApplications.getEnddate() + " for" + days + " days";

										// sendEmail.sendmailNotification(username, usermail, messages);

										sendEmail.sendmailNotification(username, "john.cornery@tcra.go.tz", messages);
										return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);
									} else {
										return ResponseEntity.status(HttpStatus.CONFLICT).body(hrmsLeaveApplications);
									}

								} else {

									return ResponseEntity.status(HttpStatus.LOCKED).body(hrmsLeaveApplications);
								}
							} else {
								if (hrmsemployeesalaryRepository
										.existsByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1)) {

									Hrmsemployeesalary hrmsemployeesalary = hrmsemployeesalaryRepository
											.findByEmployeeidAndActive(hrmsLeaveApplications.getEmployeeid(), 1);

									Double basicsalary = hrmsSalarystructureRepository
											.findById(hrmsemployeesalary.getSalarystructureId()).get().getBasicSalary();

									hrmsLeaveApplications.setLeaveallowance(basicsalary); // apply for allowance and
																							// leave
									int applicationid = hrmsLeaveApplicationsRepository
											.saveAndFlush(hrmsLeaveApplications).getId();
									HrmsLeaveBalance hrmsLeaveBalance3 = hrmsLeaveBalanceRepository
											.findByEmployeeidAndLeavetypeidAndActive(
													hrmsLeaveApplications.getEmployeeid(),
													hrmsLeaveApplications.getLeavetypeid(), 1);
									hrmsLeaveBalance3.setDays(
											hrmsLeaveBalance3.getDays() - hrmsLeaveApplications.getNumberofdays());
									hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance3);// update leave balance

									return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);
								} else {
									return ResponseEntity.status(HttpStatus.CONFLICT).body(hrmsLeaveApplications);
								}
							}
						} else {
							return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS)
									.body(hrmsLeaveApplications);
						}

					} else {
						if (hrmsLeaveTypeRepository.findByIdAndActive(hrmsLeaveApplications.getLeavetypeid(), 1)
								.getMaxdayNumber() >= hrmsLeaveApplications.getNumberofdays()) {

							if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(
									hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications.getLeavetypeid(), 1)) {

								HrmsLeaveBalance hrmsLeaveBalance4 = hrmsLeaveBalanceRepository
										.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications.getEmployeeid(),
												hrmsLeaveApplications.getLeavetypeid(), 1);

								if (hrmsLeaveBalance4.getDays() >= hrmsLeaveApplications.getNumberofdays()) {

									hrmsLeaveApplications.setLeaveallowanceapplicable(0);
									hrmsLeaveApplications.setLeaveallowance(0.00);

									int applicationid = hrmsLeaveApplicationsRepository
											.saveAndFlush(hrmsLeaveApplications).getId();

									hrmsLeaveBalance4.setDays(
											hrmsLeaveBalance4.getDays() - hrmsLeaveApplications.getNumberofdays());
									hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance4);// update leave balance
									return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);

								} else {
									return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);// requested
																														// more
																														// days
																														// than
																														// leave
																														// balance
								}

							} else {
								return ResponseEntity.status(HttpStatus.NO_CONTENT).body(hrmsLeaveApplications);// this
																												// employee
																												// is
																												// not
																												// available
																												// on
																												// leave
																												// balance
																												// table
							}

						} else {
							// requesting number of leaves greater than allocated value eq annual is 28 but
							// user is requesting more than 28

							return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);
						}

					}
				} else {

					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(hrmsLeaveApplications);
				}

			} else {

				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveApplications);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> deleteLeave(int leaveid, int requesterid) {
		if (hrmsLeaveApplicationsRepository.existsByIdAndActiveAndApproved(leaveid, 1, 0)) {
			HrmsLeaveApplications hrmsLeaveApplications = hrmsLeaveApplicationsRepository.findById(leaveid).get();
			if (hrmsLeaveApplications.getRequestedby() == requesterid) {
				hrmsLeaveApplications.setActive(0);
				hrmsLeaveApplications.setDate_updated(LocalDateTime.now());

				hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeaveApplications);

				// update leave balance
				HrmsLeaveApplications hrmsLeaveApplications2 = hrmsLeaveApplicationsRepository.findById(leaveid).get();

				// release leave balance first

				HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository.findByEmployeeidAndLeavetypeidAndActive(
						hrmsLeaveApplications.getEmployeeid(), hrmsLeaveApplications2.getLeavetypeid(), 1);

				hrmsLeaveBalance.setDays(hrmsLeaveBalance.getDays() + hrmsLeaveApplications.getNumberofdays());
				hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave balance

				return ResponseEntity.status(HttpStatus.OK).body(hrmsLeaveApplications);
			} else {
				return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(hrmsLeaveApplications);
			}

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplications() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository.findByActiveOrderByIdDesc(1);
		for (HrmsLeaveApplications dbm : dbms) {
			LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

			leaveApplicationsResponse.setActing(dbm.getActing());

			if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
				StringBuilder actingfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

				actingfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				actingfullname.append(" " + hrmsEmployee.getLastName().trim());
				leaveApplicationsResponse.setActingfullname(actingfullname.toString());
			}

			leaveApplicationsResponse.setActive(dbm.getActive());

			leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveApplicationsResponse.setApproved(dbm.getApproved());
			if (dbm.getApproved() == 0) {
				leaveApplicationsResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == 1) {
				leaveApplicationsResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 2) {
				leaveApplicationsResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getApproved() == 3) {
				leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
			}

			if (dbm.getApproved() == 4) {
				leaveApplicationsResponse.setApprovedStatus("Recalled");
			}

			leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());
			leaveApplicationsResponse.setDependant(dbm.getDependant());

			leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
			leaveApplicationsResponse.setKids(dbm.getKids());

			leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

			if (dbm.getApprovedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
				StringBuilder approverfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedby()).get();

				approverfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approverfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approverfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
			}

			leaveApplicationsResponse.setComment(dbm.getComment());

			leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

			leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEnddate() != null) {
				leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

					leaveApplicationsResponse.setDirectorate(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

				}

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

					leaveApplicationsResponse.setSection(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

				}
			}

			leaveApplicationsResponse.setId(dbm.getId());

			leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

			leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

			leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
			if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveApplicationsResponse
						.setLeavetypename(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveApplicationsResponse.setMonth(dbm.getMonth());
			leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

			leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requesterfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requesterfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveApplicationsResponse.setYear(dbm.getYear());

			leaveApplicationsResponselist.add(leaveApplicationsResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);
	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsByEmpId(int empid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository.findByEmployeeidAndActiveOrderByIdDesc(empid,
				1);
		for (HrmsLeaveApplications dbm : dbms) {
			LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

			leaveApplicationsResponse.setActing(dbm.getActing());
			leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());

			leaveApplicationsResponse.setDependant(dbm.getDependant());

			leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
			leaveApplicationsResponse.setKids(dbm.getKids());

			if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
				StringBuilder actingfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

				actingfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				actingfullname.append(" " + hrmsEmployee.getLastName().trim());
				leaveApplicationsResponse.setActingfullname(actingfullname.toString());
			}

			leaveApplicationsResponse.setActive(dbm.getActive());
			leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveApplicationsResponse.setApproved(dbm.getApproved());
			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1) || dbm.getApproved() > 0) {
				leaveApplicationsResponse.setUnderApproval(1);
			}

			if (dbm.getApproved() == 0) {
				leaveApplicationsResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == 1) {
				leaveApplicationsResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 2) {
				leaveApplicationsResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getApproved() == 3) {
				leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
			}

			if (dbm.getApproved() == 4) {
				leaveApplicationsResponse.setApprovedStatus("Recalled");
			}

			leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

			if (dbm.getApprovedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
				StringBuilder approverfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedby()).get();

				approverfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approverfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approverfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
			}

			leaveApplicationsResponse.setComment(dbm.getComment());

			leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

			leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEnddate() != null) {
				leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

					leaveApplicationsResponse.setDirectorate(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

				}

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

					leaveApplicationsResponse.setSection(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

				}
			}

			leaveApplicationsResponse.setId(dbm.getId());

			leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

			leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

			leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
			if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveApplicationsResponse
						.setLeavetypename(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveApplicationsResponse.setMonth(dbm.getMonth());
			leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

			leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requesterfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requesterfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveApplicationsResponse.setYear(dbm.getYear());

			leaveApplicationsResponselist.add(leaveApplicationsResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);
	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsNotApproved() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository.findByActiveAndApprovedOrderByIdDesc(1, 0);
		for (HrmsLeaveApplications dbm : dbms) {
			LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

			leaveApplicationsResponse.setActing(dbm.getActing());

			if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
				StringBuilder actingfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

				actingfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				actingfullname.append(" " + hrmsEmployee.getLastName().trim());
				leaveApplicationsResponse.setActingfullname(actingfullname.toString());
			}

			leaveApplicationsResponse.setActive(dbm.getActive());
			leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());
			leaveApplicationsResponse.setDependant(dbm.getDependant());

			leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
			leaveApplicationsResponse.setKids(dbm.getKids());
			leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveApplicationsResponse.setApproved(dbm.getApproved());
			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1) || dbm.getApproved() > 0) {
				leaveApplicationsResponse.setUnderApproval(1);
			}

			if (dbm.getApproved() == 0) {
				leaveApplicationsResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == 1) {
				leaveApplicationsResponse.setUnderApproval(1);
				leaveApplicationsResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 2) {
				leaveApplicationsResponse.setUnderApproval(1);
				leaveApplicationsResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getApproved() == 3) {
				leaveApplicationsResponse.setUnderApproval(1);
				leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
			}

			if (dbm.getApproved() == 4) {
				leaveApplicationsResponse.setApprovedStatus("Recalled");
			}

			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1)) {

				HrmsLeaveApproval rejector = hrmsLeaveApprovalRepository
						.findFirstByLeaveidAndActiveAndStatus(dbm.getId(), 1, 0);
				if (rejector != null && rejector.getApprovedby() != null && rejector.getApproveruserid() != 0) {
					leaveApplicationsResponse.setApproverfullname(rejector.getApprovedby());

					leaveApplicationsResponse.setApprovedby(rejector.getApproveruserid());
				}
			}

			if (!hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
				int firstsupervisordesignationid = hrmsEmployeeRepository.findByIdAndActive(dbm.getEmployeeid(), 1)
						.getSupervisordesignationid();

				int supervisordesignation = 0;
				if (hrmsLeaveApprovalWorkflowRepository
						.existsBySupervisordesignationidAndActive(firstsupervisordesignationid, 1)) {

					HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow = hrmsLeaveApprovalWorkflowRepository
							.findBySupervisordesignationidAndActive(firstsupervisordesignationid, 1);

					supervisordesignation = hrmsLeaveApprovalWorkflow.getSupervisordesignationid();
				}
				HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository
						.findFirstByDesignationIdAndEmploymentstatusidAndActive(supervisordesignation, 2, 1);

				if (hrmsEmployee1 != null) {

					StringBuilder requesterfullname = new StringBuilder();

					requesterfullname.append(hrmsEmployee1.getFirstName().trim());
					if (hrmsEmployee1.getMiddleName() != null) {
						requesterfullname.append(" " + hrmsEmployee1.getMiddleName().trim());
					}
					requesterfullname.append(" " + hrmsEmployee1.getLastName().trim());
					leaveApplicationsResponse.setApproverfullname(requesterfullname.toString());

					leaveApplicationsResponse.setApprovedby(hrmsEmployee1.getId());
				}
			}

			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1)
					&& hrmsEmployeeRepository.existsByIdAndActive(dbm.getEmployeeid(), 1)) {
				int firstsupervisordesignationid = hrmsEmployeeRepository.findByIdAndActive(dbm.getEmployeeid(), 1)
						.getSupervisordesignationid();

				int supervisordesignation = 0;
				int workflowid = 0;
				if (hrmsLeaveApprovalWorkflowRepository
						.existsBySupervisordesignationidAndActive(firstsupervisordesignationid, 1)) {

					HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow = hrmsLeaveApprovalWorkflowRepository
							.findBySupervisordesignationidAndActive(firstsupervisordesignationid, 1);

					supervisordesignation = hrmsLeaveApprovalWorkflow.getSupervisordesignationid();

					workflowid = hrmsLeaveApprovalWorkflow.getId();
					if (hrmsLeaveApprovalWorkflowStepRepository.existsByWorkflowidAndActive(workflowid, 1)) {

						List<HrmsLeaveApprovalWorkflowStep> dbmsteps = hrmsLeaveApprovalWorkflowStepRepository
								.findByWorkflowidAndActiveOrderByStepnumberAsc(workflowid, 1);

						for (HrmsLeaveApprovalWorkflowStep dbmstep : dbmsteps) {

							if (dbmstep.getApproverdesignationnextid() != 0
									&& hrmsLeaveApprovalRepository.existsByApproverdesignationidAndLeaveidAndActive(
											dbmstep.getApproverdesignationid(), dbm.getId(), 1)
									&& !hrmsLeaveApprovalRepository.existsByApproverdesignationidAndLeaveidAndActive(
											dbmstep.getApproverdesignationnextid(), dbm.getId(), 1)) {

								HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository
										.findFirstByDesignationIdAndEmploymentstatusidAndActive(
												dbmstep.getApproverdesignationnextid(), 2, 1);

								if (hrmsEmployee2 != null) {

									StringBuilder requesterfullname = new StringBuilder();

									requesterfullname.append(hrmsEmployee2.getFirstName().trim());
									if (hrmsEmployee2.getMiddleName() != null) {
										requesterfullname.append(" " + hrmsEmployee2.getMiddleName().trim());
									}
									requesterfullname.append(" " + hrmsEmployee2.getLastName().trim());
									leaveApplicationsResponse.setApproverfullname(requesterfullname.toString());

									leaveApplicationsResponse.setApprovedby(hrmsEmployee2.getId());
								}

							}

						}

					}

				}

			}

			leaveApplicationsResponse.setComment(dbm.getComment());

			leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

			leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEnddate() != null) {
				leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

					leaveApplicationsResponse.setDirectorate(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

				}

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

					leaveApplicationsResponse.setSection(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

				}
			}

			leaveApplicationsResponse.setId(dbm.getId());

			leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

			leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

			leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
			if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveApplicationsResponse
						.setLeavetypename(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveApplicationsResponse.setMonth(dbm.getMonth());
			leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

			leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requesterfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requesterfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveApplicationsResponse.setYear(dbm.getYear());

			leaveApplicationsResponselist.add(leaveApplicationsResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);
	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsBySupervisorId(int supervisorid) {
		String pattern = "yyyy-MM-dd";
		List<Integer> employeeundersupervisor = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();
		if (hrmsEmployeeRepository.existsByIdAndActiveOrderByIdDesc(supervisorid, 1)) {
			int supervisordesignationid = hrmsEmployeeRepository.findById(supervisorid).get().getDesignationId();
			List<HrmsEmployee> emps = hrmsEmployeeRepository
					.findBySupervisordesignationidAndEmploymentstatusid(supervisordesignationid, 2);// active employee
																									// under supervisor
																									// desination given

			for (HrmsEmployee emp : emps) {
				employeeundersupervisor.add(emp.getId());

			}
		}

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository
				.findByEmployeeidInAndActive(employeeundersupervisor, 1);
		for (HrmsLeaveApplications dbm : dbms) {
			LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();
			leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());

			leaveApplicationsResponse.setActing(dbm.getActing());

			if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
				StringBuilder actingfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

				actingfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				actingfullname.append(" " + hrmsEmployee.getLastName().trim());
				leaveApplicationsResponse.setActingfullname(actingfullname.toString());
			}

			leaveApplicationsResponse.setActive(dbm.getActive());
			leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveApplicationsResponse.setApproved(dbm.getApproved());

			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1)) {
				leaveApplicationsResponse.setUnderApproval(1);
			}

			leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

			if (dbm.getApprovedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
				StringBuilder approverfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedby()).get();

				approverfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approverfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approverfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
			}

			leaveApplicationsResponse.setComment(dbm.getComment());

			leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

			leaveApplicationsResponse.setDependant(dbm.getDependant());

			leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
			leaveApplicationsResponse.setKids(dbm.getKids());

			leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEnddate() != null) {
				leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

					leaveApplicationsResponse.setDirectorate(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

				}

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

					leaveApplicationsResponse.setSection(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

				}
			}

			leaveApplicationsResponse.setId(dbm.getId());

			leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

			leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

			leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
			if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveApplicationsResponse
						.setLeavetypename(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveApplicationsResponse.setMonth(dbm.getMonth());
			leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

			leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requesterfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requesterfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveApplicationsResponse.setYear(dbm.getYear());

			leaveApplicationsResponselist.add(leaveApplicationsResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);
	}

	@Override
	public ResponseEntity<LeaveApplicationsResponse> getLeaveApplicationByLeaveId(int leaveid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		if (hrmsLeaveApplicationsRepository.existsById(leaveid)) {
			HrmsLeaveApplications dbm = hrmsLeaveApplicationsRepository.findByIdAndActive(leaveid, 1);

			LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

			leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());

			leaveApplicationsResponse.setActing(dbm.getActing());

			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActive(dbm.getId(), 1)) {
				leaveApplicationsResponse.setUnderApproval(1);
			}

			if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
				StringBuilder actingfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

				actingfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				actingfullname.append(" " + hrmsEmployee.getLastName().trim());
				leaveApplicationsResponse.setActingfullname(actingfullname.toString());
			}

			leaveApplicationsResponse.setActive(dbm.getActive());
			leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());

			leaveApplicationsResponse.setDependant(dbm.getDependant());

			leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
			leaveApplicationsResponse.setKids(dbm.getKids());
			leaveApplicationsResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 0) {
				leaveApplicationsResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == 1) {
				leaveApplicationsResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 2) {
				leaveApplicationsResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getApproved() == 3) {
				leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
			}

			if (dbm.getApproved() == 4) {
				leaveApplicationsResponse.setApprovedStatus("Recalled");
			}

			leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

			if (dbm.getApprovedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
				StringBuilder approverfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedby()).get();

				approverfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					approverfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				approverfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
			}

			leaveApplicationsResponse.setComment(dbm.getComment());

			leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

			leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEnddate() != null) {
				leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

					leaveApplicationsResponse.setDirectorate(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

				}

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

					leaveApplicationsResponse.setSection(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

				}
			}

			leaveApplicationsResponse.setId(dbm.getId());

			leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

			leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

			leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
			if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveApplicationsResponse
						.setLeavetypename(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveApplicationsResponse.setMonth(dbm.getMonth());
			leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

			leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requesterfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requesterfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveApplicationsResponse.setYear(dbm.getYear());

			return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponse);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@Override
	public ResponseEntity<?> verifyLeaveAllowanceEligibility(int employeeid) {

		int currentYear = LocalDate.now().getYear();
		int currentMonth = LocalDate.now().getMonthValue();

		if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(employeeid, 1, 1)
				&& hrmsLeaveBalanceRepository.findByEmployeeidAndLeavetypeidAndActive(employeeid, 1, 1)
						.getDays() >= 14) {

			if (hrmsLeaveApplicationsRepository
					.existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(employeeid,
							1, 1, 1, 1, currentYear - 1)
					|| hrmsLeaveApplicationsRepository
							.existsByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYear(
									employeeid, 1, 1, 1, 1, currentYear)) {

				HrmsLeaveApplications hrmsLeaveApplications1 = hrmsLeaveApplicationsRepository
						.findByEmployeeidAndLeavetypeidAndLeaveallowanceapplicableAndApprovedAndActiveAndYearOrYear(
								employeeid, 1, 1, 1, 1, currentYear - 1, currentYear);

				if ((currentYear - hrmsLeaveApplications1.getYear()) == 1 && hrmsLeaveApplications1.getMonth() < 7
						&& currentMonth >= 7) {
					if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(employeeid, 1)) {
						return ResponseEntity.status(HttpStatus.OK).body(1);
					} else {
						return ResponseEntity.status(HttpStatus.CONFLICT).body(0);
					}

				} else {

					return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(0);
				}

			} else {
				if (hrmsemployeesalaryRepository.existsByEmployeeidAndActive(employeeid, 1)) {

					return ResponseEntity.status(HttpStatus.OK).body(1);
				} else {
					return ResponseEntity.status(HttpStatus.CONFLICT).body(0);
				}
			}
		} else {
			return ResponseEntity.status(HttpStatus.IM_USED).body(0);
		}
	}

	@Override
	public ResponseEntity<?> leaveRecall(int supervisorid, int leaveid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> verifyLeaverequestEligibility(int employeeid) {

		HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndEmploymentstatusidAndActive(employeeid, 2, 1);
		if (hrmsEmployee != null && hrmsEmployee.getDateofemployment() != null
				&& validateLeaveEligibility(hrmsEmployee.getDateofemployment()) == 1) {
			return ResponseEntity.status(HttpStatus.OK).body(1);
		} else {
			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(0);
		}

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

			}

		}

		return response;
	}

	@Override
	public ResponseEntity<EmployeeOnAnnualLeave> verifyEmployeeOnAnnualLeave(int employeeid) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		int year = LocalDateTime.now().getYear();
		int month = LocalDateTime.now().getMonthValue();
		EmployeeOnAnnualLeave employeeOnAnnualLeave = new EmployeeOnAnnualLeave();

		if (hrmsLeaveApplicationsRepository.existsByEmployeeidAndLeavetypeidAndApprovedAndActive(employeeid, 1, 1, 1)) {

			List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository
					.findByEmployeeidAndLeavetypeidAndApprovedAndActive(employeeid, 1, 1, 1);

			employeeOnAnnualLeave.setEmployeeid(employeeid);

			employeeOnAnnualLeave.setOnleave(0);

			for (HrmsLeaveApplications dbm : dbms) {

				Date today = new Date();

				if (today.after(dbm.getStartdate()) && today.before(dbm.getEnddate())) {

					employeeOnAnnualLeave.setEmployeeid(employeeid);
					employeeOnAnnualLeave.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
					employeeOnAnnualLeave.setOnleave(1);

				}

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(employeeOnAnnualLeave);
	}

	@Override
	public ResponseEntity<?> ApproveLeaveV2(int leaveid, int supervisorid, int status, String comment) {
		if (hrmsLeaveApplicationsRepository.existsByIdAndActiveAndApproved(leaveid, 1, 0)
				&& hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findByIdAndIssupervisorAndActive(supervisorid, 1, 1);

			HrmsLeaveApplications hrmsLeave = hrmsLeaveApplicationsRepository.findByIdAndActive(leaveid, 1);

			int employeeid = hrmsLeave.getEmployeeid();

			int firstsupervisordesignationid = 0;
			if (hrmsEmployeeRepository.existsByIdAndActive(employeeid, 1)) {
				firstsupervisordesignationid = hrmsEmployeeRepository.findByIdAndActive(employeeid, 1)
						.getSupervisordesignationid();
			}

			int workflowid = 0;
			if (hrmsLeaveApprovalWorkflowRepository
					.existsBySupervisordesignationidAndActive(firstsupervisordesignationid, 1)) {

				HrmsLeaveApprovalWorkflow hrmsLeaveApprovalWorkflow = hrmsLeaveApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(firstsupervisordesignationid, 1);

				workflowid = hrmsLeaveApprovalWorkflow.getId();

				if (hrmsLeaveApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
						workflowid, hrmsEmployee.getDesignationId(), 1)
						&& !hrmsLeaveApprovalRepository.existsByApproverdesignationidAndLeaveidAndActive(
								hrmsEmployee.getDesignationId(), leaveid, 1)) {

					HrmsLeaveApprovalWorkflowStep step = hrmsLeaveApprovalWorkflowStepRepository
							.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
									hrmsEmployee.getDesignationId(), 1);

					int stepnext = 0;
					if (step.getApproverdesignationnextid() != 0) {
						HrmsLeaveApprovalWorkflowStep step1 = hrmsLeaveApprovalWorkflowStepRepository
								.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
										step.getApproverdesignationnextid(), 1);

						stepnext = step1.getStepnumber();
					}

					HrmsLeaveApproval hrmsLeaveApproval = new HrmsLeaveApproval();

					hrmsLeaveApproval.setActive(1);
					hrmsLeaveApproval.setApproved(1);

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

					hrmsLeaveApproval.setApprovedby(approvedby.toString());

					if (hrmsEmployee.getDesignationId() != 0
							&& hrmsDesignationRepository.existsByIdAndActive(hrmsEmployee.getDesignationId(), 1)) {
						hrmsLeaveApproval.setApproverdesignationid(hrmsEmployee.getDesignationId());
					}

					hrmsLeaveApproval.setDate_created(LocalDateTime.now());

					hrmsLeaveApproval.setDescription(comment);
					hrmsLeaveApproval.setStepid(step.getId());
					hrmsLeaveApproval.setStepnumber(step.getStepnumber());
					hrmsLeaveApproval.setStepnumbernext(stepnext);
					hrmsLeaveApproval.setLeaveid(leaveid);
					hrmsLeaveApproval.setUnique_id(UUID.randomUUID());
					hrmsLeaveApproval.setWorkflowid(workflowid);
					hrmsLeaveApproval.setApproveruserid(supervisorid);

					hrmsLeaveApproval.setStatus(status);

					hrmsLeaveApprovalRepository.saveAndFlush(hrmsLeaveApproval);

					if (hrmsLeaveApprovalRepository.countByLeaveid(leaveid) == hrmsLeaveApprovalWorkflowStepRepository
							.countByWorkflowid(workflowid) && status == 1) {

						hrmsLeave.setDate_updated(LocalDateTime.now());
						hrmsLeave.setApproved(1);

						hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeave);

						return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);

					} else {
						if (status == 0) {

							hrmsLeave.setDate_updated(LocalDateTime.now());
							hrmsLeave.setApproved(2);// rejected

							// release leave

							// update leave balance
							HrmsLeaveApplications hrmsLeaveApplications2 = hrmsLeaveApplicationsRepository
									.findById(leaveid).get();

							// release leave balance first

							HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
									.findByEmployeeidAndLeavetypeidAndActive(hrmsLeaveApplications2.getEmployeeid(),
											hrmsLeaveApplications2.getLeavetypeid(), 1);

							hrmsLeaveBalance
									.setDays(hrmsLeaveBalance.getDays() + hrmsLeaveApplications2.getNumberofdays());
							hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave balance

							hrmsLeaveApplicationsRepository.saveAndFlush(hrmsLeave);

							return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);

						} else {

							return ResponseEntity.status(HttpStatus.OK).body(hrmsLeave);
						}
					}

				} else {
					return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(workflowid);
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(leaveid);
			}

		} else {

			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(leaveid);
		}
	}

	@Override
	public ResponseEntity<List<LeaveApprovalStatus>> getLeaveApprovers(int id) {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveApprovalStatus> leaveApprovalStatuslist = new ArrayList<>();

		if (hrmsLeaveApplicationsRepository.existsByIdAndActive(id, 1)) {

			HrmsLeaveApplications hrmsLeave = hrmsLeaveApplicationsRepository.findByIdAndActive(id, 1);
			int supervisordesignationid = 0;
			if (hrmsLeave.getEmployeeid() != 0
					&& hrmsEmployeeRepository.existsByIdAndActive(hrmsLeave.getEmployeeid(), 1)) {

				supervisordesignationid = hrmsEmployeeRepository.findByIdAndActive(hrmsLeave.getEmployeeid(), 1)
						.getSupervisordesignationid();
			}

			if (hrmsLeaveApprovalWorkflowRepository.existsBySupervisordesignationidAndActive(supervisordesignationid,
					1)) {
				int workflowid = hrmsLeaveApprovalWorkflowRepository
						.findBySupervisordesignationidAndActive(supervisordesignationid, 1).getId();

				if (hrmsLeaveApprovalWorkflowStepRepository.existsByWorkflowidAndActive(workflowid, 1)) {

					List<HrmsLeaveApprovalWorkflowStep> dbms = hrmsLeaveApprovalWorkflowStepRepository
							.findByWorkflowidAndActiveOrderByStepnumberAsc(workflowid, 1);

					for (HrmsLeaveApprovalWorkflowStep dbm : dbms) {

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository
								.findFirstByDesignationIdAndIssupervisorAndActive(dbm.getApproverdesignationid(), 1, 1);

						if (hrmsLeaveApprovalRepository.existsByApproverdesignationidAndLeaveidAndActive(
								dbm.getApproverdesignationid(), id, 1)) {

							HrmsLeaveApproval hrmsLeaveApproval = hrmsLeaveApprovalRepository
									.findByApproverdesignationidAndLeaveidAndActive(dbm.getApproverdesignationid(), id,
											1);

							LeaveApprovalStatus leaveApprovalStatus = new LeaveApprovalStatus();

							if (hrmsLeaveApproval != null && hrmsLeaveApproval.getStatus() == 1) {
								leaveApprovalStatus.setApprovalStatus("Approved");
							}

							if (hrmsLeaveApproval != null && hrmsLeaveApproval.getStatus() == 0) {
								leaveApprovalStatus.setApprovalStatus("Rejected");
							}

							if (hrmsLeaveApproval == null) {
								leaveApprovalStatus.setApprovalStatus("Pending");
							}

							leaveApprovalStatus.setApproved(hrmsLeave.getApproved());

							if (hrmsLeaveApproval != null && hrmsLeaveApproval.getDate_created() != null) {

								String dateapproved = hrmsLeaveApproval.getDate_created()
										.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

								leaveApprovalStatus.setDateApproved(dateapproved);
							}
							if (hrmsLeaveApproval != null) {
								leaveApprovalStatus.setDescription(hrmsLeaveApproval.getDescription());
							}

							leaveApprovalStatus.setId(id);
							if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

								char initial = hrmsEmployee.getFirstName().charAt(0);
								leaveApprovalStatus.setSupervisorInitial(initial);
							}

							if (hrmsEmployee != null) {

								StringBuilder supervisorName = new StringBuilder();

								supervisorName.append(hrmsEmployee.getFirstName().trim());
								if (hrmsEmployee.getMiddleName() != null) {
									supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
								}

								supervisorName.append(" " + hrmsEmployee.getLastName().trim());

								leaveApprovalStatus.setSupervisorName(supervisorName.toString());

							}
							if (hrmsLeave.getApproved() == 1) {
								leaveApprovalStatus.setLeaveStatus("Approved");
							}

							if (hrmsLeave.getApproved() == 0) {
								leaveApprovalStatus.setLeaveStatus("On Progress");
							}

							if (hrmsLeave.getApproved() == -1) {
								leaveApprovalStatus.setLeaveStatus("Rejected");
							}

							leaveApprovalStatuslist.add(leaveApprovalStatus);

						} else {
							// approver has not yet approved so get his/her details in a way around that
							// checking on approval table

							// confirm or check if previous approver rejected this training then do not
							// return the rest approvers
							if (dbm.getApproverdesignationprevid() != 0
									&& hrmsLeaveApprovalRepository.existsByStatusAndLeaveidAndActive(0, id, 1)) {

							} else {

								LeaveApprovalStatus leaveApprovalStatus = new LeaveApprovalStatus();

								leaveApprovalStatus.setApprovalStatus("Pending");

								leaveApprovalStatus.setApproved(hrmsLeave.getApproved());

								// do not set date approved as it does not exist
								leaveApprovalStatus.setDateApproved("");

								// do not set description of approval as it does not exist
								leaveApprovalStatus.setDescription("");

								leaveApprovalStatus.setId(id);
								if (hrmsEmployee != null && hrmsEmployee.getFirstName() != null) {

									char initial = hrmsEmployee.getFirstName().charAt(0);
									leaveApprovalStatus.setSupervisorInitial(initial);
								}

								if (hrmsEmployee != null) {

									StringBuilder supervisorName = new StringBuilder();

									supervisorName.append(hrmsEmployee.getFirstName().trim());
									if (hrmsEmployee.getMiddleName() != null) {
										supervisorName.append(" " + hrmsEmployee.getMiddleName().trim());
									}

									supervisorName.append(" " + hrmsEmployee.getLastName().trim());

									leaveApprovalStatus.setSupervisorName(supervisorName.toString());

								}
								if (hrmsLeave.getApproved() == 1) {
									leaveApprovalStatus.setLeaveStatus("Approved");
								}

								if (hrmsLeave.getApproved() == 0) {
									leaveApprovalStatus.setLeaveStatus("On Progress");
								}

								if (hrmsLeave.getApproved() == -1) {
									leaveApprovalStatus.setLeaveStatus("Rejected");
								}

								if (hrmsLeave.getApproved() == 2) {
									leaveApprovalStatus.setLeaveStatus("Rejected");
								}
								if (hrmsLeave.getApproved() == 3) {
									leaveApprovalStatus.setLeaveStatus("Cancelled by System");
								}

								leaveApprovalStatuslist.add(leaveApprovalStatus);

							}

						}

					}

				}

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApprovalStatuslist);
	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getHrmsLeaveNotApprovedBySupervisorNext(int supervisorid) {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();

		if (hrmsEmployeeRepository.existsByIdAndIssupervisorAndActive(supervisorid, 1, 1)) {

			List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository.findByApprovedAndActive(0, 1);

			for (HrmsLeaveApplications dbm : dbms) {
				int supervisordbm = 0;
				if (hrmsEmployeeRepository.existsByIdAndActiveAndEmploymentstatusid(dbm.getEmployeeid(), 1, 2)) {
					supervisordbm = hrmsEmployeeRepository
							.findByIdAndActiveAndEmploymentstatusid(dbm.getEmployeeid(), 1, 2).getSupervisorId();
				}

				if (supervisorid == supervisordbm) {

					if (hrmsLeaveApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisorid, dbm.getId(),
							1)) {
						// do nothing you already approved it
					} else {

						LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

						leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());

						leaveApplicationsResponse.setActing(dbm.getActing());

						if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
							StringBuilder actingfullname = new StringBuilder();

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

							actingfullname.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
							}
							actingfullname.append(" " + hrmsEmployee.getLastName().trim());
							leaveApplicationsResponse.setActingfullname(actingfullname.toString());
						}

						leaveApplicationsResponse.setActive(dbm.getActive());
						leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());

						leaveApplicationsResponse.setDependant(dbm.getDependant());

						leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
						leaveApplicationsResponse.setKids(dbm.getKids());
						leaveApplicationsResponse.setApproved(dbm.getApproved());
						if (dbm.getApproved() == 0) {
							leaveApplicationsResponse.setApprovedStatus("On Progress");
						}

						if (dbm.getApproved() == 1) {
							leaveApplicationsResponse.setApprovedStatus("Approved");
						}

						if (dbm.getApproved() == 2) {
							leaveApplicationsResponse.setApprovedStatus("Rejected");
						}

						if (dbm.getApproved() == 3) {
							leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
						}

						if (dbm.getApproved() == 4) {
							leaveApplicationsResponse.setApprovedStatus("Recalled");
						}

						leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

						if (dbm.getApprovedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
							StringBuilder approverfullname = new StringBuilder();

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedby()).get();

							approverfullname.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								approverfullname.append(" " + hrmsEmployee.getMiddleName().trim());
							}
							approverfullname.append(" " + hrmsEmployee.getLastName().trim());

							leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
						}

						leaveApplicationsResponse.setComment(dbm.getComment());

						leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

						leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

						if (dbm.getEnddate() != null) {
							leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
						}
						if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
							if (hrmsEmployee.getLastName() != null) {
								leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
							}
							if (hrmsEmployee.getLastName() != null) {
								leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
							}

							if (hrmsEmployee.getLastName() != null) {
								leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
							}

							if (hrmsEmployee.getUnitId() != 0 && hrmsOrginisationUnitRepository
									.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

								leaveApplicationsResponse.setDirectorate(hrmsOrginisationUnitRepository
										.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

							}

							if (hrmsEmployee.getSectionid() != 0 && hrmsOrginisationUnitRepository
									.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

								leaveApplicationsResponse.setSection(hrmsOrginisationUnitRepository
										.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

							}
						}

						leaveApplicationsResponse.setId(dbm.getId());

						leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

						leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

						leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
						if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
							leaveApplicationsResponse.setLeavetypename(
									hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
						}
						leaveApplicationsResponse.setMonth(dbm.getMonth());
						leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

						leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

						if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
							StringBuilder requesterfullname = new StringBuilder();

							HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

							requesterfullname.append(hrmsEmployee.getFirstName().trim());
							if (hrmsEmployee.getMiddleName() != null) {
								requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
							}
							requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

							leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
						}

						if (dbm.getStartdate() != null) {
							leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
						}

						leaveApplicationsResponse.setYear(dbm.getYear());

						leaveApplicationsResponselist.add(leaveApplicationsResponse);

					}

				} else {

					// check if first supervisor has approved and this user has not approved

					if (hrmsLeaveApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisordbm,
							dbm.getId(), 1)

							&& !hrmsLeaveApprovalRepository.existsByApproveruseridAndLeaveidAndActive(supervisorid,
									dbm.getId(), 1)) {

						// check if this supervisor is the next to approve by verifying if the back to
						// this has approved

						int workflowid = hrmsLeaveApprovalRepository
								.findFirstByApproveruseridAndLeaveidAndActive(supervisordbm, dbm.getId(), 1)
								.getWorkflowid();

						// check if the step number of this supervisor on step

						// get supervisor designation

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(supervisorid).get();

						if (hrmsLeaveApprovalWorkflowStepRepository.existsByWorkflowidAndApproverdesignationidAndActive(
								workflowid, hrmsEmployee.getDesignationId(), 1)) {

							HrmsLeaveApprovalWorkflowStep step = hrmsLeaveApprovalWorkflowStepRepository
									.findByWorkflowidAndApproverdesignationidAndActive(workflowid,
											hrmsEmployee.getDesignationId(), 1);

							int stepnow = step.getStepnumber();

							int designationprev = step.getApproverdesignationprevid();

							// check if this user has approved already

							if (hrmsLeaveApprovalRepository.existsByApproverdesignationidAndLeaveidAndActiveAndStatus(
									designationprev, dbm.getId(), 1, 1)) {

								// add this leave as it deserves to be approved

								LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

								leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());

								leaveApplicationsResponse.setActing(dbm.getActing());

								if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
									StringBuilder actingfullname = new StringBuilder();

									HrmsEmployee hrmsEmployee1 = hrmsEmployeeRepository.findById(dbm.getActing()).get();

									actingfullname.append(hrmsEmployee1.getFirstName().trim());
									if (hrmsEmployee1.getMiddleName() != null) {
										actingfullname.append(" " + hrmsEmployee1.getMiddleName().trim());
									}
									actingfullname.append(" " + hrmsEmployee1.getLastName().trim());
									leaveApplicationsResponse.setActingfullname(actingfullname.toString());
								}

								leaveApplicationsResponse.setActive(dbm.getActive());
								leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());

								leaveApplicationsResponse.setDependant(dbm.getDependant());

								leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
								leaveApplicationsResponse.setKids(dbm.getKids());
								leaveApplicationsResponse.setApproved(dbm.getApproved());

								if (dbm.getApproved() == 0) {
									leaveApplicationsResponse.setApprovedStatus("On Progress");
								}

								if (dbm.getApproved() == 1) {
									leaveApplicationsResponse.setApprovedStatus("Approved");
								}

								if (dbm.getApproved() == 2) {
									leaveApplicationsResponse.setApprovedStatus("Rejected");
								}

								if (dbm.getApproved() == 3) {
									leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
								}

								if (dbm.getApproved() == 4) {
									leaveApplicationsResponse.setApprovedStatus("Recalled");
								}

								leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

								if (dbm.getApprovedby() != 0
										&& hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
									StringBuilder approverfullname = new StringBuilder();

									HrmsEmployee hrmsEmployee2 = hrmsEmployeeRepository.findById(dbm.getApprovedby())
											.get();

									approverfullname.append(hrmsEmployee2.getFirstName().trim());
									if (hrmsEmployee2.getMiddleName() != null) {
										approverfullname.append(" " + hrmsEmployee2.getMiddleName().trim());
									}
									approverfullname.append(" " + hrmsEmployee2.getLastName().trim());

									leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
								}

								leaveApplicationsResponse.setComment(dbm.getComment());

								leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

								leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

								if (dbm.getEnddate() != null) {
									leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
								}
								if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

									HrmsEmployee hrmsEmployee3 = hrmsEmployeeRepository.findById(dbm.getEmployeeid())
											.get();
									if (hrmsEmployee3.getLastName() != null) {
										leaveApplicationsResponse.setFirstname(hrmsEmployee3.getFirstName());
									}
									if (hrmsEmployee3.getLastName() != null) {
										leaveApplicationsResponse.setMiddlename(hrmsEmployee3.getMiddleName());
									}

									if (hrmsEmployee3.getLastName() != null) {
										leaveApplicationsResponse.setLastname(hrmsEmployee3.getLastName());
									}

									if (hrmsEmployee.getUnitId() != 0 && hrmsOrginisationUnitRepository
											.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

										leaveApplicationsResponse.setDirectorate(hrmsOrginisationUnitRepository
												.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

									}

									if (hrmsEmployee.getSectionid() != 0 && hrmsOrginisationUnitRepository
											.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

										leaveApplicationsResponse.setSection(hrmsOrginisationUnitRepository
												.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

									}
								}

								leaveApplicationsResponse.setId(dbm.getId());

								leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

								leaveApplicationsResponse
										.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

								leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
								if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
									leaveApplicationsResponse.setLeavetypename(
											hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
								}
								leaveApplicationsResponse.setMonth(dbm.getMonth());
								leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

								leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

								if (dbm.getRequestedby() != 0
										&& hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
									StringBuilder requesterfullname = new StringBuilder();

									HrmsEmployee hrmsEmployee4 = hrmsEmployeeRepository.findById(dbm.getRequestedby())
											.get();

									requesterfullname.append(hrmsEmployee4.getFirstName().trim());
									if (hrmsEmployee4.getMiddleName() != null) {
										requesterfullname.append(" " + hrmsEmployee4.getMiddleName().trim());
									}
									requesterfullname.append(" " + hrmsEmployee4.getLastName().trim());

									leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
								}

								if (dbm.getStartdate() != null) {
									leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
								}

								leaveApplicationsResponse.setYear(dbm.getYear());

								leaveApplicationsResponselist.add(leaveApplicationsResponse);

							}

						}

					}

				}

			}

			return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);

		} else {

			return ResponseEntity.status(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS).body(null);
		}

	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsRejected() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository.findByActiveAndApprovedOrderByIdDesc(1, 2);
		for (HrmsLeaveApplications dbm : dbms) {
			LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();

			leaveApplicationsResponse.setActing(dbm.getActing());

			if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
				StringBuilder actingfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

				actingfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				actingfullname.append(" " + hrmsEmployee.getLastName().trim());
				leaveApplicationsResponse.setActingfullname(actingfullname.toString());
			}

			leaveApplicationsResponse.setActive(dbm.getActive());
			leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());
			leaveApplicationsResponse.setDependant(dbm.getDependant());

			leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
			leaveApplicationsResponse.setKids(dbm.getKids());
			leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());
			leaveApplicationsResponse.setApproved(dbm.getApproved());

			if (dbm.getApproved() == 0) {
				leaveApplicationsResponse.setApprovedStatus("On Progress");
			}

			if (dbm.getApproved() == 1) {
				leaveApplicationsResponse.setApprovedStatus("Approved");
			}

			if (dbm.getApproved() == 2) {
				leaveApplicationsResponse.setApprovedStatus("Rejected");
			}

			if (dbm.getApproved() == 3) {
				leaveApplicationsResponse.setApprovedStatus("Cancelled by System");
			}

			if (dbm.getApproved() == 4) {
				leaveApplicationsResponse.setApprovedStatus("Recalled");
			}

			if (hrmsLeaveApprovalRepository.existsByLeaveidAndActiveAndStatus(dbm.getId(), 1, 0)) {

				HrmsLeaveApproval rejector = hrmsLeaveApprovalRepository
						.findFirstByLeaveidAndActiveAndStatus(dbm.getId(), 1, 0);

				leaveApplicationsResponse.setApproverfullname(rejector.getApprovedby());

				leaveApplicationsResponse.setApprovedby(rejector.getApproveruserid());
			}

			leaveApplicationsResponse.setComment(dbm.getComment());

			leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

			leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

			if (dbm.getEnddate() != null) {
				leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
			}
			if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
				}
				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
				}

				if (hrmsEmployee.getLastName() != null) {
					leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
				}

				if (hrmsEmployee.getUnitId() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

					leaveApplicationsResponse.setDirectorate(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

				}

				if (hrmsEmployee.getSectionid() != 0
						&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

					leaveApplicationsResponse.setSection(
							hrmsOrginisationUnitRepository.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

				}
			}

			leaveApplicationsResponse.setId(dbm.getId());

			leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

			leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

			leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
			if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
				leaveApplicationsResponse
						.setLeavetypename(hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
			}
			leaveApplicationsResponse.setMonth(dbm.getMonth());
			leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

			leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

			if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
				StringBuilder requesterfullname = new StringBuilder();

				HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

				requesterfullname.append(hrmsEmployee.getFirstName().trim());
				if (hrmsEmployee.getMiddleName() != null) {
					requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
				}
				requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

				leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
			}

			if (dbm.getStartdate() != null) {
				leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
			}

			leaveApplicationsResponse.setYear(dbm.getYear());

			leaveApplicationsResponselist.add(leaveApplicationsResponse);

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);
	}

	@Override
	public ResponseEntity<List<LeaveApplicationsResponse>> getAllLeaveApplicationsApprovedBySupervisorId(
			int supervisorid, String startdate) {
		String pattern = "yyyy-MM-dd";
		List<Integer> employeeundersupervisor = new ArrayList<>();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		List<LeaveApplicationsResponse> leaveApplicationsResponselist = new ArrayList<>();
		if (hrmsEmployeeRepository.existsByIdAndActiveOrderByIdDesc(supervisorid, 1)) {
			int supervisordesignationid = hrmsEmployeeRepository.findById(supervisorid).get().getDesignationId();
			List<HrmsEmployee> emps = hrmsEmployeeRepository
					.findBySupervisordesignationidAndEmploymentstatusid(supervisordesignationid, 2);// active employee
																									// under supervisor
																									// desination given

			for (HrmsEmployee emp : emps) {
				employeeundersupervisor.add(emp.getId());

			}
		}

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository
				.findByEmployeeidInAndActiveAndApproved(employeeundersupervisor, 1, 1);
		for (HrmsLeaveApplications dbm : dbms) {

			if (dbm.getStartdate() != null && startdate != null) {

				String begin = simpleDateFormat.format(dbm.getStartdate());

				String end = simpleDateFormat.format(dbm.getEnddate());

				// Parsing the date
				LocalDate tarehekuanzadb = LocalDate.parse(begin);
				LocalDate tarehekuanzapi = LocalDate.parse(startdate);

				if (ChronoUnit.DAYS.between(tarehekuanzapi, tarehekuanzadb) >= 0) {

					LeaveApplicationsResponse leaveApplicationsResponse = new LeaveApplicationsResponse();
					leaveApplicationsResponse.setPhoneNumber(dbm.getPhoneNumber());

					leaveApplicationsResponse.setActing(dbm.getActing());

					if (dbm.getActing() != 0 && hrmsEmployeeRepository.existsById(dbm.getActing())) {
						StringBuilder actingfullname = new StringBuilder();

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getActing()).get();

						actingfullname.append(hrmsEmployee.getFirstName().trim());
						if (hrmsEmployee.getMiddleName() != null) {
							actingfullname.append(" " + hrmsEmployee.getMiddleName().trim());
						}
						actingfullname.append(" " + hrmsEmployee.getLastName().trim());
						leaveApplicationsResponse.setActingfullname(actingfullname.toString());
					}

					leaveApplicationsResponse.setActive(dbm.getActive());
					leaveApplicationsResponse.setApprovalcomment(dbm.getApprovalcomment());
					leaveApplicationsResponse.setApproved(dbm.getApproved());

					leaveApplicationsResponse.setApprovedby(dbm.getApprovedby());

					if (dbm.getApprovedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getApprovedby())) {
						StringBuilder approverfullname = new StringBuilder();

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getApprovedby()).get();

						approverfullname.append(hrmsEmployee.getFirstName().trim());
						if (hrmsEmployee.getMiddleName() != null) {
							approverfullname.append(" " + hrmsEmployee.getMiddleName().trim());
						}
						approverfullname.append(" " + hrmsEmployee.getLastName().trim());

						leaveApplicationsResponse.setApproverfullname(approverfullname.toString());
					}

					leaveApplicationsResponse.setComment(dbm.getComment());

					leaveApplicationsResponse.setContactaddress(dbm.getContactaddress());

					leaveApplicationsResponse.setDependant(dbm.getDependant());

					leaveApplicationsResponse.setIncludefamilymember(dbm.getIncludefamilymember());
					leaveApplicationsResponse.setKids(dbm.getKids());

					leaveApplicationsResponse.setEmployeeid(dbm.getEmployeeid());

					if (dbm.getEnddate() != null) {
						leaveApplicationsResponse.setEnddate(simpleDateFormat.format(dbm.getEnddate()));
					}
					if (hrmsEmployeeRepository.existsById(dbm.getEmployeeid())) {

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getEmployeeid()).get();
						if (hrmsEmployee.getLastName() != null) {
							leaveApplicationsResponse.setFirstname(hrmsEmployee.getFirstName());
						}
						if (hrmsEmployee.getLastName() != null) {
							leaveApplicationsResponse.setMiddlename(hrmsEmployee.getMiddleName());
						}

						if (hrmsEmployee.getLastName() != null) {
							leaveApplicationsResponse.setLastname(hrmsEmployee.getLastName());
						}

						if (hrmsEmployee.getUnitId() != 0
								&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getUnitId(), 1)) {

							leaveApplicationsResponse.setDirectorate(hrmsOrginisationUnitRepository
									.findByIdAndActive(hrmsEmployee.getUnitId(), 1).getName());

						}

						if (hrmsEmployee.getSectionid() != 0
								&& hrmsOrginisationUnitRepository.existsByIdAndActive(hrmsEmployee.getSectionid(), 1)) {

							leaveApplicationsResponse.setSection(hrmsOrginisationUnitRepository
									.findByIdAndActive(hrmsEmployee.getSectionid(), 1).getName());

						}
					}

					leaveApplicationsResponse.setId(dbm.getId());

					leaveApplicationsResponse.setLeaveallowance(dbm.getLeaveallowance());

					leaveApplicationsResponse.setLeaveallowanceapplicable(dbm.getLeaveallowanceapplicable());

					leaveApplicationsResponse.setLeavetypeid(dbm.getLeavetypeid());
					if (hrmsLeaveTypeRepository.existsById(dbm.getLeavetypeid())) {
						leaveApplicationsResponse.setLeavetypename(
								hrmsLeaveTypeRepository.findById(dbm.getLeavetypeid()).get().getName());
					}
					leaveApplicationsResponse.setMonth(dbm.getMonth());
					leaveApplicationsResponse.setNumberofdays(dbm.getNumberofdays());

					leaveApplicationsResponse.setRequestedby(dbm.getRequestedby());

					if (dbm.getRequestedby() != 0 && hrmsEmployeeRepository.existsById(dbm.getRequestedby())) {
						StringBuilder requesterfullname = new StringBuilder();

						HrmsEmployee hrmsEmployee = hrmsEmployeeRepository.findById(dbm.getRequestedby()).get();

						requesterfullname.append(hrmsEmployee.getFirstName().trim());
						if (hrmsEmployee.getMiddleName() != null) {
							requesterfullname.append(" " + hrmsEmployee.getMiddleName().trim());
						}
						requesterfullname.append(" " + hrmsEmployee.getLastName().trim());

						leaveApplicationsResponse.setRequesterfullname(requesterfullname.toString());
					}

					if (dbm.getStartdate() != null) {
						leaveApplicationsResponse.setStartdate(simpleDateFormat.format(dbm.getStartdate()));
					}

					leaveApplicationsResponse.setYear(dbm.getYear());

					leaveApplicationsResponselist.add(leaveApplicationsResponse);
				}

			}

		}

		return ResponseEntity.status(HttpStatus.OK).body(leaveApplicationsResponselist);
	}

}
