package com.Hrms.Leave.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hrms.Leave.Entity.HrmsLeaveApprovalRedirectionReason;

@Repository
public interface HrmsLeaveApprovalRedirectionReasonRepository
		extends JpaRepository<HrmsLeaveApprovalRedirectionReason, Integer> {

}
