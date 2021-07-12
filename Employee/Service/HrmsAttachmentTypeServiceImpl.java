package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAttachmentType;
import com.Hrms.Employee.Repository.HrmsAttachmentTypeRepository;

@Service
public class HrmsAttachmentTypeServiceImpl implements HrmsAttachmentTypeService {

	@Autowired
	private HrmsAttachmentTypeRepository hrmsAttachmentTypeRepository;

	@Override
	public ResponseEntity<List<HrmsAttachmentType>> listAttachmentType() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsAttachmentTypeRepository.findByActive(1));
	}

}
