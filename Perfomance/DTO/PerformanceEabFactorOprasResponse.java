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

public class PerformanceEabFactorOprasResponse {

	private int id;

	private String name;

	private String description;

	private int approved;

	private int active;
	List<PerformanceEabQualityOprasResponse> performanceEabQualityOprasResponselist;

}
