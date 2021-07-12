package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsCurrency;
import com.Hrms.Employee.Repository.HrmsCurrencyRepository;

@Service
public class HrmsCurrencyServiceImpl implements HrmsCurrencyService {

	@Autowired
	private HrmsCurrencyRepository hrmsCurrencyRepository;

	@Override
	public ResponseEntity<List<HrmsCurrency>> listCurrency() {
		return ResponseEntity.status(HttpStatus.OK).body(hrmsCurrencyRepository.findByActive(1));
	}
}
