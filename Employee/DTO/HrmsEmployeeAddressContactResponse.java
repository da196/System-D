package com.Hrms.Employee.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeAddressContactResponse {
	private int empadressid;

	private Date adressdatestart;

	private Date adressdateend;

	private String adressdescriptionstart;

	private String adressdescriptionend;

	private int employeeid;

	private String employeename;
	// Adress details
	private int adressid;

	private String addressline1;

	private String addressline2;

	private String postalcode;

	private String adressdescription;

	private int addresstypeid;
	private String addresstypename;

	private int adresscityid;
	private String adresscity;

	// move to employee contact
	private int empcontactid;

	private Date contactdatestart;

	private Date contactdateend;

	private String contactdescriptionstart;

	private String contactdescriptionend;

	// contact details
	private int contactid;

	private String contactphoneprimary;

	private String contactphonesecondary;

	private String contactemailaddress;

	private int contacttypeid;
	private String contacttypename;

	private int approved;
	private String approvalComment;

	private int active;

}
