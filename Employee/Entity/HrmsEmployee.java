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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;

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
public class HrmsEmployee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true)
	private int id;
	@Email(message = "Please enter valid email")
	@Column(name = "email")
	private String email;

	@Column(name = "passport_no")
	private String passportNo;

	@Column(name = "national_id")
	private String nationalId;

	@Column(name = "employment_status_reason_id")
	private int employmentstatusreasonid;

	@Column(name = "tin")
	private String tin;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "employment_end_date")
	private Date employmentenddate;

	@Column(name = "signature")
	private String signature;

	@Column(name = "section_id")
	private int sectionid;

	@Column(name = "file_number")
	private String fileNumber;

	@Column(name = "card_number")
	private String cardNumber;

	@NotNull(message = "Please enter firstname")
	@Column(name = "first_name")
	private String firstName;

	@Column(name = "middle_name")
	private String middleName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "picture")
	private String picture;

	@NotNull
	@Past
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "date_of_birth")
	private Date dob;

	@NotNull
	@Past
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

	@Positive
	@Column(name = "salutation_id")
	private int salutationId;

	@Positive
	@Column(name = "duty_station_id")
	private int dutystationid;

	@Positive
	@Column(name = "unit_id")
	private int unitId;

	@Positive
	@Column(name = "gender_id")
	private int genderid;

	@Positive
	@Column(name = "marital_status_id")
	private int maritalstatusId;

	@Positive
	@Column(name = "employment_category_id")
	private int employmentcategoryId;

	@Positive
	@Column(name = "employment_status_id")
	private int employmentstatusid;

	@Column(name = "supervisor_id")
	private int supervisorId;

	@Positive
	@Column(name = "designation_id")
	private int designationId;

	@NotNull
	@Column(name = "nationality_id")
	private int nationalityId;

	@NotNull
	@Column(name = "religion_id")
	private int religionId;

	@Column(name = "approved")
	private int approved;

	@Column(name = "active")
	private int active;

	@Column(name = "supervisor_designation_id")
	private int supervisordesignationid;

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
}
