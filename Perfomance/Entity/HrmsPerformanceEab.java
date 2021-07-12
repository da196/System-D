package com.Hrms.Perfomance.Entity;

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
@Table(name = "hrms_performance_eab")
public class HrmsPerformanceEab {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "rating_appraisee")
	private Double ratingappraisee;

	@Column(name = "rating_supervisor")
	private Double ratingsupervisor;

	@Column(name = "rating_agreed")
	private Double ratingagreed;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_reviewed")

	private Date datereviewed;

	@Column(name = "eppa_id")
	private int eppaid;

	@Column(name = "quality_id")
	private int qualityid;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "supervisor_id")
	private int supervisorid;

	@Column(name = "supervisor_designation_id")
	private int supervisordesignationid;

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
