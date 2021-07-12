package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEdattachmentType;

@Service
public interface HrmsEdattachmentTypeService {
	public ResponseEntity<HrmsEdattachmentType> save(HrmsEdattachmentType hrmsEdattachmentType);

	public ResponseEntity<Optional<HrmsEdattachmentType>> viewHrmsEdattachmentType(int id);

	public ResponseEntity<HrmsEdattachmentType> update(HrmsEdattachmentType hrmsEdattachmentType, int id);

	public ResponseEntity<?> deleteHrmsEdattachmentType(int id);

	public ResponseEntity<List<HrmsEdattachmentType>> listHrmsEdattachmentType();

}
