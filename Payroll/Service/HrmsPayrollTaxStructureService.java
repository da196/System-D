package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.DTO.PayrollTaxStructureResponse;
import com.Hrms.Payroll.Entity.HrmsPayrollTaxStructure;

@Service
public interface HrmsPayrollTaxStructureService {
	public ResponseEntity<HrmsPayrollTaxStructure> addPayrollTaxStructure(
			HrmsPayrollTaxStructure hrmsPayrollTaxStructure);

	public ResponseEntity<PayrollTaxStructureResponse> getPayrollTaxStructureById(int id);

	public ResponseEntity<HrmsPayrollTaxStructure> updatePayrollTaxStructure(
			HrmsPayrollTaxStructure hrmsPayrollTaxStructure, int id);

	public ResponseEntity<?> deletePayrollTaxStructure(int id);

	public ResponseEntity<List<PayrollTaxStructureResponse>> getAllPayrollTaxStructure();

	public ResponseEntity<PayrollTaxStructureResponse> getPayrollTaxStructureByAmount(Double amount);

}
