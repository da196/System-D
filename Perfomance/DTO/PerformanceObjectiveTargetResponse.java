package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceObjectiveTargetResponse {

	private int id;

	private int objectiveid;
	private HrmsPerformanceObjective performanceObjective;

	private String name;

	private String description;

	private int approved;

	private int active;

}
