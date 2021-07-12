package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollWorkflowStepResponse {

	private int id;

	private int workflowid;

	private String workflowName;

	private int stepnumber;

	private int stepweight;

	private int redirected;

	private int designationactiveid;

	private String designationactive;

	private int designationnextid;

	private String designationnext;

	private int designationbackid;
	private String designationback;

	private int approvedbyid;

	private String approvedby;

	private String approved_date;

	private int createdbyid;
	private String createdby;

	private int updatedbyid;

	private String updatedby;

	private int approved;

	private int active;

}
