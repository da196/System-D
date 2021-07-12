package com.Hrms.Employee.Entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "hrms_employee")
public class HrmsEmployeeRead {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;
	@Column(name = "email")
	private String email;

	@Column(name = "passport_no")
	private String passportNo;

	@Column(name = "national_id")
	private String nationalId;

	@Column(name = "section_id")
	private int sectionid;

	@Column(name = "file_number")
	private String fileNumber;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "picture")
	private String picture;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_birth")
	private Date dob;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_employment")
	private Date dateofemployment;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_retirement")
	private Date dateofretirement;

	@Column(name = "is_supervisor")
	private int issupervisor;

	@Column(name = "is_probation")
	private int isprobation;

	@ManyToOne
	@JoinColumn(name = "salutation_id")
	private HrmsSalutation salutation;

	@ManyToOne
	@JoinColumn(name = "unit_id")
	private HrmsOrginisationUnit orgunit;

	@ManyToOne
	@JoinColumn(name = "gender_id")
	private HrmsGender gender;

	@ManyToOne
	@JoinColumn(name = "marital_status_id")
	private HrmsMaritalStatus maritalstatus;

	@Column(name = "duty_station_id")
	private int dutystationid;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "duty_station_id") private HrmsOrganisationOffice
	 * dutystation;
	 */

	@ManyToOne
	@JoinColumn(name = "employment_category_id")
	private HrmsEmploymentCategory employmentcategory;

	@ManyToOne
	@JoinColumn(name = "employment_status_id")
	private HrmsEmploymentStatus employmentStatus;

	@ManyToOne
	@JoinColumn(name = "supervisor_id")
	private HrmsEmployee supervisorId;
	// private HrmsEmployeeRead supervisorId;

	@ManyToOne
	@JoinColumn(name = "designation_id")
	private HrmsDesignation designation;

	@ManyToOne
	@JoinColumn(name = "nationality_id")
	private HrmsNationality nationality;

	@ManyToOne
	@JoinColumn(name = "religion_id")
	private HrmsEmployeeReligion religion;

	@ManyToOne
	@JoinColumn(name = "supervisor_designation_id")
	private HrmsDesignation supervisordesignation;

	@ManyToOne
	@JoinColumn(name = "employment_status_reason_id")
	private HrmsEmploymentStatusReason hrmsEmploymentStatusReason;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;
	@Column(name = "createdby")
	private int createdby;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_created")
	@CreationTimestamp
	private LocalDateTime date_created;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "date_updated")
	private LocalDateTime date_updated;

	@Column(name = "unique_id")
	private UUID unique_id;
	/*
	 * @OneToMany
	 * 
	 * @JoinTable(name = "hrms_employee_salary", joinColumns = @JoinColumn(name =
	 * "employee_id"), inverseJoinColumns = @JoinColumn(name =
	 * "salary_structure_id")) private List<HrmsSalarystructure> SalaryStructure;
	 */

	private String tin;

	private String employmentenddate;

	private String signature;
}
