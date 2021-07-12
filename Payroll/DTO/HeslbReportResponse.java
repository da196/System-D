package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HeslbReportResponse {
	private List<HeslbReport> heslbReportlist;
	private Double totalAmountDeducted;

	// private Double totalbalanceRemaining;

}
