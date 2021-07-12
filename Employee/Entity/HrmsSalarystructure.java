package com.Hrms.Employee.Entity;

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
@Table(name = "hrms_salary_structure")
public class HrmsSalarystructure {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "basic_salary")
	private Double basicSalary;

	@Column(name = "description")
	private String description;

	@Column(name = "scale_id")
	private int scaleId;

	@Column(name = "notch_id")
	private int notchId;

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

	@Column(name = "basic_salary_min")
	private Double basicsalaryMin;

	@Column(name = "basic_salary_max")
	private Double basicsalaryMax;

}
