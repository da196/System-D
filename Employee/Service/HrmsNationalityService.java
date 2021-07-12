package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsNationality;

@Service
public interface HrmsNationalityService {

	public ResponseEntity<List<HrmsNationality>> listHrmsNationality();

}
