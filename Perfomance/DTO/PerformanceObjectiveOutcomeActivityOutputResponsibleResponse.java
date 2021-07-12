package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveOutcomeActivityOutputTarget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceObjectiveOutcomeActivityOutputResponsibleResponse {

	private int id;

	private int targetid;;

	private HrmsPerformanceObjectiveOutcomeActivityOutputTarget performanceObjectiveOutcomeActivityOutputTarget;

	private int unitid;
	private String unitName;

	private int approved;

	private int active;

}
