package com.Hrms.Leave.Entity;

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
@Table(name = "hrms_leave_recall_approval")
public class HrmsLeaveRecallApproval {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "description")
	private String description;

	@Column(name = "approved_by")
	private String approvedby;

	@Column(name = "step_number")
	private int stepnumber;

	@Column(name = "step_number_next")
	private int stepnumbernext;

	@Column(name = "leave_id")
	private int leaveid;

	@Column(name = "step_id")
	private int stepid;

	@Column(name = "approver_designation_id")
	private int approverdesignationid;

	@Column(name = "workflow_id")
	private int workflowid;

	@Column(name = "approver_user_id")
	private int approveruserid;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "status")
	private int status;

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
