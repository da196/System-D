package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEppaResponse {

	private int id;

	private String description;

	private Double scoreannual;

	private Double scorebehaviour;

	private Double score;

	private String comments;

	private int employeeid;

	private String employeeFullName;

	// private int outputid;

	// private HrmsPerformanceObjectiveOutcomeActivityOutput
	// performanceObjectiveOutcomeActivityOutput;

	private int financialyearid;

	private HrmsPerformanceFinancialYear performanceFinancialYear;

	private int approved;

	private int active;

}
