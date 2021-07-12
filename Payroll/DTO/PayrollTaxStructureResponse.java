package com.Hrms.Payroll.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayrollTaxStructureResponse {
	private int id;

	private Double amountmin;

	private Double amountmax;

	private Double amount;

	private Double rate;
	private Double amountPaye;

	private String datelastchanged;

	private String description;

	private int isformularcomputed;

	private int taxtypeid;
	private String taxtype;

	private int currencyid;

	private String currency;

	private int approved;

	private int active;
}
