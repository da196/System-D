package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsSalutation;
import com.Hrms.Employee.Repository.HrmsSalutationRepository;

@Service
public class HrmsSalutationServiceImpl implements HrmsSalutationService {

	@Autowired
	private HrmsSalutationRepository hrmsSalutationRepository;

	@Override
	public ResponseEntity<List<HrmsSalutation>> listHrmsSalutation() {

		return ResponseEntity.status(HttpStatus.OK).body(hrmsSalutationRepository.findAll());
	}

}
