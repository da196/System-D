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
@Table(name = "hrms_employee_certification")
public class HrmsEmployeeCertification {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "institution")
	private String institution;

	@Column(name = "expire")
	private String expire;

	@Column(name = "date_start")
	private String datestart;

	@Column(name = "date_end")
	private String datend;

	@Column(name = "description")
	private String description;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "certification_category_id")
	private int certificationcategoryid;

	@Column(name = "country_id")
	private int countryid;

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
