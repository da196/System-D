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
public class EmployeeResponseEdms {
	private int id;

	private String email;

	private String fileNumber;

	private String cardNumber;

	private String firstName;

	private String middleName;

	private String lastName;

	private String picture;
	private String dob;

	private String dateofemployment;

	private int issupervisor;

	private int isprobation;

	private String salutation;

	private int unitid;

	private String gender;

	private String maritalstatus;

	private String employmentcategory;

	private int employmentstatusId;

	private int supervisordesgnationid;

	private int designationid;

	private String nationality;

	private String religion;

	private int approved;

	private int active;

	private int salaryStructureid;

	private int dutystationid;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

	private LocalDateTime date_created;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")

	private LocalDateTime date_updated;

	private UUID unique_id;
}
