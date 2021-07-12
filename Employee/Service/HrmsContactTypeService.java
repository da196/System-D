package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsContactType;

@Service
public interface HrmsContactTypeService {

	public ResponseEntity<List<HrmsContactType>> listContactType();

}
