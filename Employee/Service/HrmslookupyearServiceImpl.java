package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.Hrmslookupyear;
import com.Hrms.Employee.Repository.HrmslookupyearRepository;

@Service
public class HrmslookupyearServiceImpl implements HrmslookupyearService {

	@Autowired
	private HrmslookupyearRepository hrmslookupyearRepository;

	@Override
	public ResponseEntity<List<Hrmslookupyear>> listyear() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmslookupyearRepository.findAll());
	}

}
