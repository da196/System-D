package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsEmploymentStatusReason;

@Service
public interface HrmsEmploymentStatusReasonService {

	public ResponseEntity<HrmsEmploymentStatusReason> addEmploymentStatusReason(
			HrmsEmploymentStatusReason hrmsEmploymentStatusReason);

	public ResponseEntity<HrmsEmploymentStatusReason> getEmploymentStatusReason(int id);

	public ResponseEntity<HrmsEmploymentStatusReason> updateEmploymentStatusReason(
			HrmsEmploymentStatusReason hrmsEmploymentStatusReason, int id);

	public ResponseEntity<?> deleteEmploymentStatusReason(int id);

	public ResponseEntity<List<HrmsEmploymentStatusReason>> listEmploymentStatusReason();

}
