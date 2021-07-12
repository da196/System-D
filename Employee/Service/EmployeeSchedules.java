package com.Hrms.Employee.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.Hrms.Communication.SendEmail;
import com.Hrms.Employee.Entity.HrmsEmployee;
import com.Hrms.Employee.Repository.HrmsEmployeeRepository;
import com.Hrms.Leave.Repository.HrmsLeaveApplicationsRepository;
import com.Hrms.Leave.Repository.HrmsLeaveBalanceRepository;
import com.Hrms.Leave.Repository.HrmsLeaveRecallRepository;
import com.Hrms.Leave.Repository.HrmsLeaveTypeRepository;

@Component
public class EmployeeSchedules {
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

	@Autowired

	private SendEmail sendEmail;

	private static final Logger log = LoggerFactory.getLogger(EmployeeSchedules.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(cron = "0 15 9 * * ?")
	public void employeeStatusChangeReminder() {

		String pattern = "yyyy-MM-dd";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		int currentMont = LocalDate.now().getMonthValue();
		int currentDay = LocalDate.now().getDayOfMonth();
		int currentYear = LocalDate.now().getYear();

		List<HrmsEmployee> dbms = hrmsEmployeeRepository.findByIsprobationAndActive(1, 1);// 1 = on probation

		for (HrmsEmployee dbm : dbms) {

			if (dbm.getDateofemployment() != null) {
				String dateofemploymentString = simpleDateFormat.format(dbm.getDateofemployment());

				// Parsing the date
				LocalDate dateOfEmployment = LocalDate.parse(dateofemploymentString);
				int empMonth = dateOfEmployment.getMonthValue();
				int empYear = dateOfEmployment.getYear();

				if (((currentYear - empYear) == 1) && ((((12 - empMonth) + currentMont) == 10 && currentDay == 1)

						|| (((12 - empMonth) + currentMont) == 11 && currentDay == 15)

				)) {
					int totalMonth = ((12 - empMonth) + currentMont);
					StringBuilder builderfullname = new StringBuilder();
					builderfullname.append(dbm.getFirstName().trim());

					if (dbm.getMiddleName() != null) {
						builderfullname.append("  " + dbm.getMiddleName().trim());
					}

					if (dbm.getLastName() != null) {
						builderfullname.append(" " + dbm.getLastName());
					}

					String fullname = builderfullname.toString();
					// send email to HR now
					String username = "HR Team";

					String usermail = "hr";

					String messages = "Kindly be informed that " + fullname + ", " + "has attained " + totalMonth
							+ " Months from the date was employed ,Kindly Consider changing status to Confirmed";

					sendEmail.NotificationReminderOnEmploymentCategory(username, usermail, messages);

				}

			}

		}

	}

}
