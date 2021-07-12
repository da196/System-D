package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceGoalResponse {

	private int id;

	private String name;

	private String description;

	private int planid;
	private HrmsPerformancePlan performancePlan;

	private int approved;

	private int active;

}
