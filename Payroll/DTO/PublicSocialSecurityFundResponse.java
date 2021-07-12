package com.Hrms.Payroll.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicSocialSecurityFundResponse {

	private List<PublicSocialSecurityFund> publicSocialSecurityFundlist;
	private Double totalamountemployee;

	private Double totalamountemployer;

}
