package com.Hrms.Employee.DTO;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeBirthDetailsRequest {
	private int id;

	@NotNull
	@Past
	private Date dob;
	private String description;
	private String additionalinformation;

	private int employeeid;

	private int countryid;

	private int cityid;

	private int districtid;

	private int wardid;

	private int Attachmentid;
	private String Attachmentname;
	private String uri;
	private int attachmenttypeid;
	private int approved;

	private int active;

}
