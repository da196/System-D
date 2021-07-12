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
@Table(name = "hrms_payroll_contribution_voluntary")
public class HrmsPayrollContributionVoluntary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "description")
	private String description;

	@Column(name = "amount")
	private Double amount;

	@Column(name = "rate")
	private Double rate;

	@Column(name = "is_formular_computed")
	private int isformularcomputed;

	@Column(name = "contribution_type_id")
	private int contributiontypeid;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "service_provider_id")
	private int serviceproviderid;

	@Column(name = "currency_id")
	private int currencyid;

	@Column(name = "contribution_mode")
	private int contributionmode;

	@Column(name = "locked")
	private int locked;

	@Column(name = "membership_number")
	private String membershipnumber;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "joining_date")
	private Date joiningdate;

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
