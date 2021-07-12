package com.Hrms.Employee.Entity;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "hrms_employee_employment_history")
public class HrmsEmployeeEmploymentHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "position")
	private String position;

	@Column(name = "date_start")
	private String datestart;

	@Column(name = "date_end")
	private String datend;

	@Column(name = "reason_end")
	private String reasonend;

	@Column(name = "employer")
	private String employer;

	@Column(name = "employer_phone_primary")
	private String employerphoneprimary;

	@Column(name = "employer_phone_secondary")
	private String employerphonesecondary;

	@Column(name = "employer_email")
	private String employeremail;

	@Column(name = "employer_address")
	private String employeraddress;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "country_id")
	private int countryid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "duties")
	private String duties;

	@Column(name = "supervisor_name")
	private String supervisorname;

	@Column(name = "supervisor_telephone_number")
	private String supervisortelephonenumber;

	@Column(name = "supervisor_address")
	private String supervisoraddress;
	@Column(name = "approvalComment")
	private String approvalComment;

	@Column(name = "approvedby")
	private int approverEmployeeid;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_created")
	@CreationTimestamp
	private LocalDateTime date_created;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_updated")
	private LocalDateTime date_updated;

	@Column(name = "unique_id")
	private UUID unique_id;

	@Column(name = "is_your_current_job")
	private String isyourcurrentjob;

}
