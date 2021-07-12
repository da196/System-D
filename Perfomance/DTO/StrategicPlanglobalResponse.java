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
public class StrategicPlanglobalResponse {

	private String planName;

	private int planId;
	private String financialYear;

	private String goalName;
	private int goalid;

	private String objectiveName;

	private int objectiveId;

	private List<PerformanceObjectiveOutcomeGlobalResponse> performanceObjectiveOutcomeGlobalResponselist;

}
