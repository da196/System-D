package com.Hrms.Perfomance.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceGoal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceGoalSummary {
	private int goalid;

	private HrmsPerformanceGoal performanceGoal;

	private List<PerformanceObjectiveSummary> performanceObjectiveSummarylist;

}
