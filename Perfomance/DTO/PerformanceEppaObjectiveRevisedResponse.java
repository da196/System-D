package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEppaObjectiveRevisedResponse {

	private int id;

	private String targets;

	private String criteria;

	private String daterevised;
	private String description;

	private int objectiveid;
	private HrmsPerformanceEppaObjective performanceEppaObjective;

	private int approved;

	private int active;

}
