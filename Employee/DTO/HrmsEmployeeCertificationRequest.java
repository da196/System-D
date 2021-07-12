package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeCertificationRequest {

	private int id;

	private String description;

	private String institution;

	private String expire;

	private String datestart;

	private String datend;

	private int employeeid;

	private int certificationcategoryid;

	private int countryid;
	private int attachmentid;
	private String attachmentname;
	private int attachmenttypeid;
	private String uri;

	private int approved;

	private int active;

}
