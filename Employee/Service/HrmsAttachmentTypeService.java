package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAttachmentType;

@Service
public interface HrmsAttachmentTypeService {

	public ResponseEntity<List<HrmsAttachmentType>> listAttachmentType();

}
