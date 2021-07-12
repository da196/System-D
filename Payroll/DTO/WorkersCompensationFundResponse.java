package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkersCompensationFundResponse {

	private List<WorkersCompensationFund> workersCompensationFundlist;

	private Double totalwcfPay;

}
