package com.Hrms.Employee.DTO;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Userloginresponse {
	private int id;
	private String fullname;
	private String unit;
	private Set<String> role;
	private LocalDateTime timein;
	private String authenticated;
	private String status;

}
