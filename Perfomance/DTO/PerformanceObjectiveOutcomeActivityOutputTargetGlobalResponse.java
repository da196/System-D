package com.Hrms.Perfomance.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse {

	private int id;

	private int outputid;
	private List<PerformanceObjectiveOutcomeActivityOutputResponsibleResponse> performanceObjectiveOutcomeActivityOutputResponsibleResponselist;

	private String description;

	private int approved;

	private int active;

	private Double target;

	private String timeline;

	private String keyperformanceindicator;

}
