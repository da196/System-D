package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollTaxType;

@Service
public interface HrmsPayrollTaxTypeService {
	public ResponseEntity<HrmsPayrollTaxType> addPayrollTaxType(HrmsPayrollTaxType hrmsPayrollTaxType);

	public ResponseEntity<HrmsPayrollTaxType> getPayrollTaxTypeById(int id);

	public ResponseEntity<HrmsPayrollTaxType> updatePayrollTaxType(HrmsPayrollTaxType hrmsPayrollTaxType, int id);

	public ResponseEntity<?> deletePayrollTaxType(int id);

	public ResponseEntity<List<HrmsPayrollTaxType>> getAllPayrollTaxType();

}
