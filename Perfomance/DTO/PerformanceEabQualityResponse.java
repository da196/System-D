package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEabFactor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEabQualityResponse {

	private int id;

	private String name;

	private String description;

	private int factorid;

	private HrmsPerformanceEabFactor performanceEabFactor;

	private int approved;

	private int active;

}
