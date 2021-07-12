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

public class PerformanceObjectiveOutcomeActivityOutputGlobalResponse {

	private int id;

	private int activityid;

	private List<PerformanceObjectiveOutcomeActivityOutputTargetGlobalResponse> performanceObjectiveOutcomeActivityOutputTargetGlobalResponselist;

	private String description;

	private int approved;

	private int active;

}
