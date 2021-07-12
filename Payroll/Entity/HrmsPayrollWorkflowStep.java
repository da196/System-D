package com.Hrms.Payroll.Entity;

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
@Table(name = "hrms_payroll_workflow_step")
public class HrmsPayrollWorkflowStep {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "workflow_id")
	private int workflowid;

	@Column(name = "step_number")
	private int stepnumber;

	@Column(name = "step_weight")
	private int stepweight;

	@Column(name = "redirected")
	private int redirected;

	@Column(name = "designation_active_id")
	private int designationactiveid;

	@Column(name = "designation_next_id")
	private int designationnextid;

	@Column(name = "designation_back_id")
	private int designationbackid;

	@Column(name = "approved_by_id")
	private int approvedbyid;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "approved_date")
	private LocalDateTime approved_date;

	@Column(name = "created_by_id")
	private int createdbyid;

	@Column(name = "updated_by_id")
	private int updatedbyid;

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
