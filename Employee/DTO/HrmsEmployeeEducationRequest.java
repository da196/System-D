package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeEducationRequest {

	private int id;

	private String datestart;

	private String datend;

	private String descriptionstart;

	private String descriptionend;
	private int employeeid;
	private int attchmentid;
	private String attachmentname;

	private String attachmentdescription;

	private String uri;

	private int attachmenttypeid;

	private int levelid;

	private int courseid;

	private int countryid;

	private int institutionid;

	private int approved;

	private int active;

}
