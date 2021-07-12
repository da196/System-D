package com.Hrms.Employee.DTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor

public class AttachmentRequest {
	private int id;

	private String name;

	private String description;

	private String uri;

	private int attachmenttypeid;

	private int approved;

	private int active;

}
