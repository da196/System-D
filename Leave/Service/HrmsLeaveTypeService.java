package com.Hrms.Leave.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.Hrms.Leave.Entity.HrmsLeaveType;

public interface HrmsLeaveTypeService {
	public ResponseEntity<HrmsLeaveType> addLeaveType(HrmsLeaveType hrmsLeaveType);

	public ResponseEntity<HrmsLeaveType> getLeaveTypeById(int id);

	public ResponseEntity<HrmsLeaveType> updateLeaveType(HrmsLeaveType hrmsLeaveType, int id);

	public ResponseEntity<?> deleteLeaveType(int id);

	public ResponseEntity<List<HrmsLeaveType>> getAllLeaveType();

}
