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
@Table(name = "hrms_payroll_deduction_tax_approval")
public class HrmsPayrollDeductionTaxApproval {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "accepted")
	private int accepted;

	@Column(name = "comments")
	private String comments;

	@Column(name = "approved_score")
	private int approvedscore;

	@Column(name = "deduction_id")
	private int deductionid;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "approved_date")
	private Date approveddate;

	@Column(name = "approved_by_user_id")
	private int approvedbyuserid;

	@Column(name = "approved_by_designation_id")
	private int approvedbydesignationid;

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
