package com.Hrms.Employee.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsAttachment;

@Service
public interface HrmsAttachmentService {

	public ResponseEntity<HrmsAttachment> addAttachment(HrmsAttachment hrmsAttachment);

	public ResponseEntity<Optional<HrmsAttachment>> getAttachment(int id);

	public ResponseEntity<HrmsAttachment> updateAttachment(HrmsAttachment hrmsAttachment, int id);

	public ResponseEntity<?> deleteAttachment(int id);

	public ResponseEntity<List<HrmsAttachment>> listAttachment();

}
