package com.Hrms.Payroll.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Payroll.Entity.HrmsPayrollApproval;

@Repository
public interface HrmsPayrollApprovalRepository extends JpaRepository<HrmsPayrollApproval, Integer> {

	boolean existsByApprovedbyuseridAndPayrollidAndActive(int approvedbyuserid, int payrollid, int i);

	// HrmsPayrollApproval findFirstByPayrollidOrderByApprovedscoreDESC(int
	// payrollId);

	HrmsPayrollApproval findFirstByPayrollidAndActiveOrderByApprovedscoreDesc(int payrollId, int i);

	boolean existsByIdAndActive(int id, int i);

	HrmsPayrollApproval findByPayrollidAndApprovedbyuserid(int payrollid, int approvedbyuserid);

	boolean existsByPayrollidAndActiveAndAccepted(int payrollId, int i, int j);

}
