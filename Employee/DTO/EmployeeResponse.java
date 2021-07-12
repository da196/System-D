package com.Hrms.Employee.DTO;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {
	private int id;

	private String email;

	private String passportNo;

	private String nationalId;

	private String fileNumber;

	private String cardNumber;

	private String firstName;

	private String middleName;

	private String lastName;

	private String picture;
	private String dob;
	private String dateofemployment;

	private String dateofretirement;

	private int issupervisor;

	private int isprobation;

	private String salutation;
	private int salutationId;

	private String unit;
	private int unitId;
	private int sectionid;
	private String section;

	private String gender;

	private int genderid;

	private String maritalstatus;
	private int maritalstatusId;

	private String employmentcategory;

	private int employmentcategoryId;

	private String employmentstatus;
	private int employmentstatusid;

	private String supervisor;
	private int supervisordesignationid;

	private String designation;

	private int designationId;

	private String nationality;
	private int nationalityId;

	private String religion;

	private int religionId;

	private int dutystationid;
	private String dutystation;
	private int dutystationtypeid;
	private String dutystationtypename;
	private int dutystationcityid;
	private String dutystationcity;

	private String salarystructure;

	private int employmentstatusreasonid;

	private String employmentstatusreason;

	private String tin;

	private String employmentenddate;

	private String signature;

	private int approved;

	private int active;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

	private LocalDateTime date_created;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

	private LocalDateTime date_updated;

	private UUID unique_id;
}
