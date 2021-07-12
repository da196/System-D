package com.Hrms.Employee.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HrmsSalarystructureDetails {

	private int id;

	private Double basicSalary;

	private String description;

	private int scaleId;
	private String scalename;

	private int notchId;
	private String notch;

	private int approved;

	private int active;

	private LocalDateTime date_created;

	private LocalDateTime date_updated;

	private Double basicsalaryMin;

	private Double basicsalaryMax;

}
