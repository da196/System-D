package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcome;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceObjectiveOutcomeActivityResponse {

	private int id;

	private int outcomeid;

	private HrmsPerformanceObjectiveOutcome hrmsPerformanceObjectiveOutcome;

	private String description;

	private int approved;

	private int active;

}
