package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppaObjective;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEppaReviewMidYearResponse {

	private int id;

	private String progresstowardstarget;

	private String factorsaffectingperformance;

	private String datereviewed;

	private int eppaid;
	private HrmsPerformanceEppa performanceEppa;

	private int objectiveid;
	private HrmsPerformanceEppaObjective performanceEppaObjective;

	private int supervisorid;

	private String supervisor;

	private int supervisordesignationid;

	private String supervisordesignation;

	private int approved;

	private int active;

}
