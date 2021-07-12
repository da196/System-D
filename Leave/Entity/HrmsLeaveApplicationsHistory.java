package com.Hrms.Leave.Entity;

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
@Table(name = "hrms_leave_applications_history")
public class HrmsLeaveApplicationsHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;
	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "leave_type_id")
	private int leavetypeid;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "start_date")

	private Date startdate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "end_date")

	private Date enddate;

	@Column(name = "comment")
	private String comment;

	@Column(name = "contact_address")
	private String contactaddress;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "dependant")
	private int dependant;

	@Column(name = "include_family_member")
	private int includefamilymember;

	@Column(name = "kids")
	private int kids;

	@Column(name = "acting")
	private int acting;

	@Column(name = "leave_allowance")
	private Double leaveallowance;

	@Column(name = "is_leave_allowance_applicable")
	private int leaveallowanceapplicable;

	@Column(name = "requested_by")
	private int requestedby;

	@Column(name = "number_of_days")
	private Double numberofdays;

	@Column(name = "approved_by")
	private int approvedby;

	@Column(name = "approval_comment")
	private String approvalcomment;

	@Column(name = "approved")
	private int approved;

	@Column(name = "action") // 1= create, 2 = update ,3 = delete, 4 = approve
	private int action;

	@Column(name = "active")
	private int active;

	@Column(name = "year")
	private int year;

	@Column(name = "month")
	private int month;

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
