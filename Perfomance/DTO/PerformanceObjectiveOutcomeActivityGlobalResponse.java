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
public class PerformanceObjectiveOutcomeActivityGlobalResponse {

	private int id;

	private int outcomeid;

	private List<PerformanceObjectiveOutcomeActivityOutputGlobalResponse> performanceObjectiveOutcomeActivityOutputGlobalResponselist;

	private String description;

	private int approved;

	private int active;

}
