package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceObjectiveResponse {

	private int id;

	private int goalid;
	private HrmsPerformanceGoal performanceGoal;

	private String description;

	private int approved;

	private int active;

}
