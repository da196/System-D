package com.Hrms.Perfomance.DTO;

import org.springframework.stereotype.Component;

import com.Hrms.Perfomance.Entity.HrmsPerformanceEab;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class PerformanceEabQualityOprasResponse {

	private int id;

	private String name;

	private String description;

	private int factorid;

	private HrmsPerformanceEab performanceEab;

	private int approved;

	private int active;

}
