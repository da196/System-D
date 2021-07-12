package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HealthInsuranceResponse {

	private List<HealthInsuarance> healthInsuarancelist;
	private Double totalnhifPay;

}
