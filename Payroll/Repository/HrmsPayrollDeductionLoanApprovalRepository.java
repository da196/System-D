package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionLoanApproval;

@Repository
public interface HrmsPayrollDeductionLoanApprovalRepository
		extends JpaRepository<HrmsPayrollDeductionLoanApproval, Integer> {

}
