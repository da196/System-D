package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceObjectiveOutcomeActivityOutputResponse {

	private int id;

	private int activityid;

	private HrmsPerformanceObjectiveOutcomeActivity performanceObjectiveOutcomeActivity;

	private String description;

	private int approved;

	private int active;

}
