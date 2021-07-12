package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankBranchResponse {

	private int id;

	private int code;

	private String name;

	private String physicaladdress;

	private String postaladdress;

	private String telephone;

	private String sortcode;

	private int bankid;
	private String bankName;

	private int approved;

	private int active;

}
