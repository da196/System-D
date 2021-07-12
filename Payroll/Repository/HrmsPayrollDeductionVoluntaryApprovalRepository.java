package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollDeductionVoluntaryApproval;

@Repository
public interface HrmsPayrollDeductionVoluntaryApprovalRepository
		extends JpaRepository<HrmsPayrollDeductionVoluntaryApproval, Integer> {

}
