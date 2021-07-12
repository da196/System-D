package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionMandatoryApproval;

@Repository
public interface HrmsPayrollDeductionMandatoryApprovalRepository
		extends JpaRepository<HrmsPayrollDeductionMandatoryApproval, Integer> {

}
