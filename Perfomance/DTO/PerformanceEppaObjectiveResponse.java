package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEppaObjectiveResponse {

	private int id;

	private String targets;

	private String criteria;

	private Double weighting;

	private String dateagreed;
	private String description;

	private int revised;

	private int eppaid;

	private HrmsPerformanceEppa performanceEppa;

	private int approved;

	private int active;

}
