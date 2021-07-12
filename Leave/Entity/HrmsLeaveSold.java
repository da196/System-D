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
@Table(name = "hrms_leave_sold")
public class HrmsLeaveSold {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;
	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "emp_knowledge")
	private int empknowledge;

	@Column(name = "leave_type_id")
	private int leavetypeid;

	@Column(name = "number_of_days")
	private Double numberofdaysSold;

	@Column(name = "amount")
	private Double amount;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "sold_date")

	private Date solddate;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "requster_id")
	private int requesterid;

	@Column(name = "year")
	private int year;

	@Column(name = "month")
	private int month;

	@Column(name = "reason")
	private String reason;

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
