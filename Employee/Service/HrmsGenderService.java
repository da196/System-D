package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsGender;

@Service
public interface HrmsGenderService {

	public ResponseEntity<List<HrmsGender>> listHrmsGender();

}
