package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsEmployeeEmploymentHistoryResponseByEmpId {
	private int id;
	private String instituteName;
	private String jobTitle;
	private String fromDate;
	private String toDate;
	private int approved;
	private String approvalComment;

}
