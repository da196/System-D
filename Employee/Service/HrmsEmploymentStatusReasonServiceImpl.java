package com.Hrms.Employee.Service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmploymentStatusReason;
import com.Hrms.Employee.Repository.HrmsEmploymentStatusReasonRepository;

@Service
public class HrmsEmploymentStatusReasonServiceImpl implements HrmsEmploymentStatusReasonService {

	@Autowired

	private HrmsEmploymentStatusReasonRepository hrmsEmploymentStatusReasonRepository;

	@Override
	public ResponseEntity<HrmsEmploymentStatusReason> addEmploymentStatusReason(
			HrmsEmploymentStatusReason hrmsEmploymentStatusReason) {
		hrmsEmploymentStatusReason.setUnique_id(UUID.randomUUID());
		hrmsEmploymentStatusReason.setActive(1);
		hrmsEmploymentStatusReason.setApproved(0);

		return ResponseEntity.status(HttpStatus.OK)
				.body(hrmsEmploymentStatusReasonRepository.save(hrmsEmploymentStatusReason));
	}

	@Override
	public ResponseEntity<HrmsEmploymentStatusReason> getEmploymentStatusReason(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<HrmsEmploymentStatusReason> updateEmploymentStatusReason(
			HrmsEmploymentStatusReason hrmsEmploymentStatusReason, int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<?> deleteEmploymentStatusReason(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<List<HrmsEmploymentStatusReason>> listEmploymentStatusReason() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsEmploymentStatusReasonRepository.findByActive(1));
	}

}
