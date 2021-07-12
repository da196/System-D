package com.Hrms.Perfomance.DTO;

import java.util.List;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceObjective;
import com.Hrms.Perfomance.Entity.HrmsPerformanceObjectiveTarget;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceObjectiveSummary {

	private int objectiveid;

	private HrmsPerformanceObjective performanceObjective;

	private List<HrmsPerformanceObjectiveTarget> targetlist;

}
