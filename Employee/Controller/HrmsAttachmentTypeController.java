package com.Hrms.Employee.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsAttachmentType;
import com.Hrms.Employee.Service.HrmsAttachmentTypeService;

@RestController
@RequestMapping("v1/attachmentType")
public class HrmsAttachmentTypeController {
	@Autowired
	private HrmsAttachmentTypeService hrmsAttachmentTypeService;

	@GetMapping(value = "/getAllAttachmentType")
	public ResponseEntity<List<HrmsAttachmentType>> listAttachmentType() {
		return hrmsAttachmentTypeService.listAttachmentType();

	}

}
