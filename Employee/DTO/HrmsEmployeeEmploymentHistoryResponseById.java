package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeEmploymentHistoryResponseById {
	private int id;
	private int employeeid;
	private String employeename;
	private String position;

	private String datestart;

	private String date_end;

	private String reasonend;

	private String employer;

	private String employerphoneprimary;

	private String employerphonesecondary;

	private String employeremail;

	private String employeraddress;

	private int countryid;
	private String countryname;

	private String duties;

	private String supervisorname;

	private String supervisortelephonenumber;

	private String supervisoraddress;

	private String isyourcurrentjob;

	private int approved;
	private String approvalComment;

}
