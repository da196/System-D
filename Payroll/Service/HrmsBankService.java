package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsBank;

@Service
public interface HrmsBankService {

	public ResponseEntity<HrmsBank> addBank(HrmsBank hrmsBank);

	public ResponseEntity<HrmsBank> getBankById(int id);

	public ResponseEntity<HrmsBank> updateBank(HrmsBank hrmsBank, int id);

	public ResponseEntity<?> deleteBank(int id);

	public ResponseEntity<List<HrmsBank>> getAllBank();

}
