package com.Hrms.Employee.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeAddressContactRequest {
	// employee adress
	private int empadressid;

	private Date adressdatestart;

	private Date adressdateend;

	private String adressdescriptionstart;

	private String adressdescriptionend;

	private int employeeid;
	// Adress details
	private int adressid;

	private String addressline1;

	private String addressline2;

	private String postalcode;

	private String adressdescription;

	private int addresstypeid;

	private int adresscityid;

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

	private int approved;

	private int active;

}
