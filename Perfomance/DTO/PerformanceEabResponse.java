package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEabQuality;
import com.Hrms.Perfomance.Entity.HrmsPerformanceEppa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEabResponse {

	private int id;

	private Double ratingappraisee;

	private Double ratingsupervisor;

	private Double ratingagreed;

	private String datereviewed;

	private int eppaid;

	private HrmsPerformanceEppa performanceEppa;

	private int qualityid;
	private HrmsPerformanceEabQuality performanceEabQuality;

	private int employeeid;

	private String employeeFullName;

	private int supervisorid;

	private String supervisorFullName;

	private int supervisordesignationid;
	private String supervisordesignation;

	private int approved;

	private int active;

}
