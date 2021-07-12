package com.Hrms.Employee.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Employee.Entity.HrmsCurrency;

@Service
public interface HrmsCurrencyService {
	public ResponseEntity<List<HrmsCurrency>> listCurrency();

}
