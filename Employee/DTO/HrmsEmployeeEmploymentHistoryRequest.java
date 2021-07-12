package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeEmploymentHistoryRequest {

	private int id;

	private String position;

	private String datestart;

	private String date_end;

	private String reasonend;

	private String employer;

	private String employerphoneprimary;

	private String employerphonesecondary;

	private String employeremail;

	private String employeraddress;

	private int employeeid;

	private int countryid;

	private int approved;

	private int active;

	private String duties;

	private String supervisorname;

	private String supervisortelephonenumber;

	private String supervisoraddress;

	private String isyourcurrentjob;

}
