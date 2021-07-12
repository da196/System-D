package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollEmployeeLoanApproval;

@Repository
public interface HrmsPayrollEmployeeLoanApprovalRepository
		extends JpaRepository<HrmsPayrollEmployeeLoanApproval, Integer> {

}
