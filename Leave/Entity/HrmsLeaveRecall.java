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
@Table(name = "hrms_leave_recall")
public class HrmsLeaveRecall {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;
	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "empknowledge")
	private int empknowledge;

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

	private int leaveid;

	@Column(name = "number_of_days")
	private Double numberofdays;

	@Column(name = "requested_by")
	private int requestedby;

	@Column(name = "approval_comment")
	private String approvalcomment;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "estimated_cost")
	private Double estimatedcost;

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
