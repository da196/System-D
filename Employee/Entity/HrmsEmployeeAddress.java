package com.Hrms.Employee.Entity;

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
@Table(name = "hrms_employee_address")
public class HrmsEmployeeAddress {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_start")
	private Date date_start;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_end")
	private Date date_end;

	@Column(name = "description_start")
	private String description_start;

	@Column(name = "description_end")
	private String description_end;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "address_id")
	private int addressid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

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

}
