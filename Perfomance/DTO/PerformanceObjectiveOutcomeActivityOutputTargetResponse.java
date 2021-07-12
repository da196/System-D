package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutput;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceObjectiveOutcomeActivityOutputTargetResponse {

	private int id;

	private int outputid;
	private HrmsPerformanceObjectiveOutcomeActivityOutput performanceObjectiveOutcomeActivityOutput;

	private String description;

	private int approved;

	private int active;

	private Double target;

	private String timeline;

	private String keyperformanceindicator;

}
