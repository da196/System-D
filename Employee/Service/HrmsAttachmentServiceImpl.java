package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAttachment;
import com.Hrms.Employee.Repository.HrmsAttachmentRepository;

@Service
public class HrmsAttachmentServiceImpl implements HrmsAttachmentService {
	@Autowired
	private HrmsAttachmentRepository hrmsAttachmentRepository;

	@Override
	public ResponseEntity<HrmsAttachment> addAttachment(HrmsAttachment hrmsAttachment) {

		return null;
	}

	@Override
	public ResponseEntity<Optional<HrmsAttachment>> getAttachment(int id) {

		return null;
	}

	@Override
	public ResponseEntity<HrmsAttachment> updateAttachment(HrmsAttachment hrmsAttachment, int id) {

		return null;
	}

	@Override
	public ResponseEntity<?> deleteAttachment(int id) {

		return null;
	}

	@Override
	public ResponseEntity<List<HrmsAttachment>> listAttachment() {

		return null;
	}

}
