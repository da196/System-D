package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionTaxApproval;

@Repository
public interface HrmsPayrollDeductionTaxApprovalRepository
		extends JpaRepository<HrmsPayrollDeductionTaxApproval, Integer> {

}
