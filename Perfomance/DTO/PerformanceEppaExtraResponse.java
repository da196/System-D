package com.Hrms.Perfomance.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceFinancialYear;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEppaExtraResponse {

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

	private List<HrmsPerformanceEppaObjective> performanceEppaObjectivelist;

	private int approved;

	private int active;

}
