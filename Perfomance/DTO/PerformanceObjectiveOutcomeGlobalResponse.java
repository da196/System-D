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

public class PerformanceObjectiveOutcomeGlobalResponse {

	private int id;

	private int objectiveid;

	private List<PerformanceObjectiveOutcomeActivityGlobalResponse> performanceObjectiveOutcomeActivityGlobalResponselist;

	private String description;

	private int approved;

	private int active;

}
