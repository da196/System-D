package com.Hrms.Payroll.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Hrms.Employee.Entity.HrmsCurrency;
import com.Hrms.Employee.Service.HrmsCurrencyService;

@RestController
@RequestMapping("v1/currency")
public class HrmsCurrencyController {

	@Autowired
	private HrmsCurrencyService hrmsCurrencyService;

	@GetMapping(value = "/getAllCurrencies")
	public ResponseEntity<List<HrmsCurrency>> getAllCurrencies() {

		return hrmsCurrencyService.listCurrency();

	}

}
