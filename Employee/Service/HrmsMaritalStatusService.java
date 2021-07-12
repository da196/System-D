package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsMaritalStatus;

@Service
public interface HrmsMaritalStatusService {

	public ResponseEntity<List<HrmsMaritalStatus>> listHrmsMaritalStatus();

}
