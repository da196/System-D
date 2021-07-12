package com.Hrms.Payroll.Service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.Hrms.Payroll.Entity.HrmsPayrollType;

@Service
public interface HrmsPayrollTypeService {
	public ResponseEntity<HrmsPayrollType> addPayrollType(HrmsPayrollType hrmsPayrollType);

	public ResponseEntity<HrmsPayrollType> getPayrollTypeById(int id);

	public ResponseEntity<HrmsPayrollType> updatePayrollType(HrmsPayrollType hrmsPayrollType, int id);

	public ResponseEntity<?> deletePayrollType(int id);

	public ResponseEntity<List<HrmsPayrollType>> getAllPayrollType();

}
