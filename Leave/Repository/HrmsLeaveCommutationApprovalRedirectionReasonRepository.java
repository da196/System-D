package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveCommutationApprovalRedirectionReason;

@Repository
public interface HrmsLeaveCommutationApprovalRedirectionReasonRepository
		extends JpaRepository<HrmsLeaveCommutationApprovalRedirectionReason, Integer> {

}
