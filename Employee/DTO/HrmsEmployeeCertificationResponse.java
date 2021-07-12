package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeCertificationResponse {

	private int id;
	private int employeeid;
	private String employeename;

	private String description;

	private String institution;

	private String expire;

	private String datestart;

	private String datend;

	private int certificationcategoryid;

	private String certificationcategoryname;

	private int countryid;

	private String countryname;

	private int attachmentid;

	private String attachmentname;

	private int attachmenttypeid;

	private String attachmenttypename;

	private String uri;

	private int approved;
	private String approvalComment;

	private int active;

}
