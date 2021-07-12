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
@Table(name = "hrms_employee_salary")
public class Hrmsemployeesalary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_assigned")
	@CreationTimestamp
	private LocalDateTime dateAssigned;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_approved")
	private LocalDateTime dateApproved;

	@Column(name = "description_assigned")
	private String descriptionAssigned;

	@Column(name = "description_approved")
	private String descriptionApproved;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "salary_structure_id")
	private int salarystructureId;

	@Column(name = "assigned_by_id")
	private int assignedbyId;

	@Column(name = "approved_by_id")
	private int approvedbyId;

	@Column(name = "currency_id")
	private int currencyId;

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
