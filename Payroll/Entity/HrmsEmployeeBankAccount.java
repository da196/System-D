package com.Hrms.Payroll.Entity;

import java.time.LocalDateTime;
import java.util.Date;
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
@Table(name = "hrms_employee_bank_account")
public class HrmsEmployeeBankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "account_name")
	private String accountname;

	@Column(name = "account_number")
	private String accountnumber;

	@Column(name = "bank_branch_id")
	private int bankbranchid;

	@Column(name = "priority")
	private int priority;

	@Column(name = "amount_limit")
	private Double amount;

	@Column(name = "description")
	private String description;

	@Column(name = "approvalcomment")
	private String approvalcomment;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_opened")
	private Date dateopened;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_closed")
	private Date dateclosed;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "approved_date")
	private Date approveddate;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "bank_id")
	private int bankid;

	@Column(name = "approved_by_user_id")
	private int approvedbyuserid;

	@Column(name = "approved_by_designation_id")
	private int approvedbydesignationid;

	@Column(name = "created_by_user_id")
	private int createdbyuserid;

	@Column(name = "created_by_designation_id")
	private int createdbydesignationid;

	@Column(name = "bank_branch_location_id")
	private int bankbranchlocationid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_created")
	@CreationTimestamp
	private LocalDateTime date_created;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_updated")
	private LocalDateTime date_updated;

	@Column(name = "unique_id")
	private UUID unique_id;

}
