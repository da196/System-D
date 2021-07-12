package com.Hrms.Perfomance.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformancePlan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StrategicPlanSummaryResponse {
	private int planid;
	private HrmsPerformancePlan performancePlan;

	private List<PerformanceGoalSummary> performanceGoallist;

}
