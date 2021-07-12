package com.Hrms.Leave.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Leave.Entity.HrmsLeaveApplications;
import com.Hrms.Leave.Entity.HrmsLeaveBalance;
import com.Hrms.Leave.Entity.HrmsLeaveRecall;
import com.Hrms.Leave.Entity.HrmsLeaveType;
import com.Hrms.Leave.Repository.HrmsLeaveApplicationsRepository;
import com.Hrms.Leave.Repository.HrmsLeaveBalanceRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallRepository;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Component
public class LeaveSchedules {
	@Autowired
	private HrmsLeaveApplicationsRepository hrmsLeaveApplicationsRepository;

	@Autowired
	private HrmsLeaveBalanceRepository hrmsLeaveBalanceRepository;

	@Autowired
	private HrmsEmployeeRepository hrmsEmployeeRepository;

	@Autowired
	private HrmsLeaveTypeRepository hrmsLeaveTypeRepository;

	@Autowired
	private HrmsLeaveRecallRepository hrmsLeaveRecallRepository;

	private static final Logger log = LoggerFactory.getLogger(LeaveSchedules.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	// @Scheduled(fixedRate = 3600000) // 60 minutes
	@Scheduled(cron = "0 5 0 * * ?")
	public void cancellLeaveRequestExpired() {
		log.info("The time is now {}", dateFormat.format(new Date()));

		List<HrmsLeaveApplications> dbms = hrmsLeaveApplicationsRepository.findByActiveAndApproved(1, 0);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		for (HrmsLeaveApplications dbm : dbms) {

			String dateBeforeString = simpleDateFormat.format(new Date());

			String dateAfterString = simpleDateFormat.format(dbm.getStartdate());

			// Parsing the date
			LocalDate dateBefore = LocalDate.parse(dateBeforeString);
			LocalDate dateAfter = LocalDate.parse(dateAfterString);

			long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);

			if (daysnumbers == 0) {
				dbm.setApproved(3);// cancelled by system
				dbm.setDate_updated(LocalDateTime.now());
				dbm.setApprovalcomment("Supervisor could not approve in time , system rejected this leave application");
				hrmsLeaveApplicationsRepository.saveAndFlush(dbm);

				// update leave days

				HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
						.findByEmployeeidAndLeavetypeidAndActive(dbm.getEmployeeid(), dbm.getLeavetypeid(), 1);
				hrmsLeaveBalance.setDays(hrmsLeaveBalance.getDays() + dbm.getNumberofdays());
				hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

				hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);// update leave
																			// balance

			}

		}
	}

	// @Scheduled(cron = "0 10 0 1 7 ?") // every july 1 run this
	@Scheduled(cron = "0 35 0 * * ?") // everyday at 1 and 35 minutes
	public void issueLeaveToEmployee() {
		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		int currentMont = LocalDate.now().getMonthValue();
		int currentDay = LocalDate.now().getDayOfMonth();

		List<HrmsLeaveType> hrmsLeaveTypelist = hrmsLeaveTypeRepository.findByActive(1);

		List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByEmploymentstatusidAndActive(2, 1);// 2 = status
		for (HrmsLeaveType dbmm : hrmsLeaveTypelist) {

			for (HrmsEmployee dbm : dbms) {

				if (hrmsLeaveBalanceRepository.existsByEmployeeidAndLeavetypeidAndActive(dbm.getId(), dbmm.getId(),
						1)) {
					HrmsLeaveBalance hrmsLeaveBalance = hrmsLeaveBalanceRepository
							.findByEmployeeidAndLeavetypeidAndActive(dbm.getId(), dbmm.getId(), 1);

					hrmsLeaveBalance.setDate_updated(LocalDateTime.now());

					if (dbmm.getCode() == 1) {// annual leave
						if ((hrmsLeaveBalance.getDays() + 0.08) <= 56) {
							hrmsLeaveBalance.setDays(hrmsLeaveBalance.getDays() + 0.08);
							hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
						} else {
							hrmsLeaveBalance.setDays(56.00);// if previous remaining days plus new ones will exceed 56
															// then
															// add 56 only
							hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
						}
					}

					if (dbm.getDateofemployment() != null && dbmm.getCode() != 1) {

						String doe = simpleDateFormat.format(dbm.getDateofemployment());

						// Parsing the date
						LocalDate dateofemployemnt = LocalDate.parse(doe);

						if (dateofemployemnt.getMonthValue() == currentMont
								&& dateofemployemnt.getDayOfMonth() == currentDay) {// confirm if date of employment has
																					// reached date for month and day
							if (dbmm.getCode() == 2 && dbm.getGenderid() == 2) {// Maternity leave for women only, and
																				// gender =
																				// female(2)

								hrmsLeaveBalance.setDays(dbmm.getMaxdayNumber());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

							}

							if (dbmm.getCode() == 3 && dbm.getGenderid() == 1) { // paternity leave =3 and men =1
								hrmsLeaveBalance.setDays(dbmm.getMaxdayNumber());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
							}

							if (dbmm.getCode() != 1 && dbmm.getCode() != 2 && dbmm.getCode() != 3) {

								hrmsLeaveBalance.setDays(dbmm.getMaxdayNumber());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

							}
						}
					}
					// hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
				} else {

					HrmsLeaveBalance hrmsLeaveBalance = new HrmsLeaveBalance();
					hrmsLeaveBalance.setLeavetypeid(dbmm.getId());
					hrmsLeaveBalance.setEmployeeid(dbm.getId());
					hrmsLeaveBalance.setActive(1);
					hrmsLeaveBalance.setApproved(0);
					hrmsLeaveBalance.setDate_updated(LocalDateTime.now());
					hrmsLeaveBalance.setUnique_id(UUID.randomUUID());

					if (dbmm.getCode() == 1) {// annual leave
						// add number of leave days from date employed to now
						if (dbm.getDateofemployment() != null) {

							String doe = simpleDateFormat.format(dbm.getDateofemployment());

							// Parsing the date
							LocalDate dateofemployemnt = LocalDate.parse(doe);

							Double daysnumbers = (double) ChronoUnit.DAYS.between(dateofemployemnt, LocalDate.now());

							double leavedays = daysnumbers * 0.08;

							if (leavedays >= 56.00) {
								hrmsLeaveBalance.setDays(56.00);
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
							} else {

								hrmsLeaveBalance.setDays(leavedays);
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
							}

						}
					}

					if (dbm.getDateofemployment() != null) {

						String doe = simpleDateFormat.format(dbm.getDateofemployment());

						// Parsing the date
						LocalDate dateofemployemnt = LocalDate.parse(doe);

						if (dateofemployemnt.getMonthValue() == currentMont
								&& dateofemployemnt.getDayOfMonth() == currentDay) {
							if (dbmm.getCode() == 2 && dbm.getGenderid() == 2) {// Maternity leave for women only, and
																				// gender =
																				// female(2)
								hrmsLeaveBalance.setDays(dbmm.getMaxdayNumber());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

							}

							if (dbmm.getCode() == 3 && dbm.getGenderid() == 1) { // paternity leave =3 and men =1
								hrmsLeaveBalance.setDays(dbmm.getMaxdayNumber());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);
							}

							if (dbmm.getCode() != 1 && dbmm.getCode() != 2 && dbmm.getCode() != 3) {

								hrmsLeaveBalance.setDays(dbmm.getMaxdayNumber());
								hrmsLeaveBalanceRepository.saveAndFlush(hrmsLeaveBalance);

							}

						}
					}

				}

			}

		}

	}

	// @Scheduled(fixedRate = 3600000) // 60 minutes
	@Scheduled(cron = "0 15 0 * * ?")
	public void cancellLeaveRecallExpired() {
		log.info("The time is now {}", dateFormat.format(new Date()));

		List<HrmsLeaveRecall> dbms = hrmsLeaveRecallRepository.findByActiveAndApproved(1, 0);

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		for (HrmsLeaveRecall dbm : dbms) {

			String dateBeforeString = simpleDateFormat.format(new Date());

			String dateAfterString = simpleDateFormat.format(dbm.getStartdate());

			// Parsing the date
			LocalDate dateBefore = LocalDate.parse(dateBeforeString);
			LocalDate dateAfter = LocalDate.parse(dateAfterString);

			long daysnumbers = ChronoUnit.DAYS.between(dateBefore, dateAfter);

			if (daysnumbers == 0) {
				dbm.setApproved(3);// cancelled by system
				dbm.setDate_updated(LocalDateTime.now());
				dbm.setApprovalcomment("Supervisor could not approve in time , system rejected this leave application");
				hrmsLeaveRecallRepository.saveAndFlush(dbm);

			}

		}
	}

}
