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

public class PerformanceEppaReviewAnnualResponse {

	private int id;

	private String targets;

	private String outputtarget;

	private String outputactual;

	private Double weighting;

	private Double ratingappraisee;

	private Double ratingsupervisor;

	private Double ratingagreed;

	private Double score;

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
