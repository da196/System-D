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
@Table(name = "hrms_employee_relative")
public class HrmsEmployeeRelative {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "middle_name")
	private String middlename;

	@Column(name = "last_name")
	private String lastname;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "birth_date")
	private Date dob;

	@Column(name = "phone_primary")
	private String phoneprimary;

	@Column(name = "phone_secondary")
	private String phonesecondary;

	@Column(name = "address")
	private String address;

	@Column(name = "address_permanent")
	private String addresspermanent;

	@Column(name = "employee_id")
	private int employeeid;

	@Column(name = "relative_category_id")
	private int relativecategoryid;

	@Column(name = "nationality_id")
	private int nationalityid;

	@Column(name = "country_of_birth_id")
	private int countryofbirthid;

	@Column(name = "country_of_residence_id")
	private int countryofresidenceid;

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
